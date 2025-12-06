package JobSchedulingPackage;

import java.util.*;
import java.util.concurrent.*;

class Cluster {
    private final String name;
    private final int totalCores;
    private final int totalRam;
    private int availableCores;
    private int availableRam;

    public Cluster(String name, int cores, int ram) {
        this.name = name;
        this.totalCores = cores;
        this.totalRam = ram;
        this.availableCores = cores;
        this.availableRam = ram;
    }

    public String getName() {
        return name;
    }

    public int getAvailableCores() {
        return availableCores;
    }

    public void setAvailableCores(int availableCores) {
        this.availableCores = availableCores;
    }

    public int getAvailableRam() {
        return availableRam;
    }

    public void setAvailableRam(int availableRam) {
        this.availableRam = availableRam;
    }

    public synchronized String getStatus() {
        return name + " [Available: " + availableCores + " cores, " + availableRam + " GB RAM]";
    }
}

class ClusterManager {
    private final List<Cluster> clusters = new ArrayList<>();
    private final ConcurrentHashMap<String, String> jobAllocationMap = new ConcurrentHashMap<>();

    public synchronized void addCluster(Cluster cluster) {
        System.out.println("[ADDED] " + cluster.getStatus());
        clusters.add(cluster);
    }

    public synchronized Cluster allocateResources(Job job) {
        for (Cluster cluster : clusters) {
            if (cluster.getAvailableCores() >= job.getCoresNeeded() && cluster.getAvailableRam() >= job.getRamNeeded()) {
                cluster.setAvailableCores(cluster.getAvailableCores() - job.getCoresNeeded());
                cluster.setAvailableRam(cluster.getAvailableRam() - job.getRamNeeded());
                jobAllocationMap.put(job.getJobID(), cluster.getName());
                return cluster;
            }
        }
        return null;
    }

    public synchronized Cluster getClusterForJob(Job job) {
        String clusterName = jobAllocationMap.get(job.getJobID());
        if (clusterName != null) {
            for (Cluster cluster : clusters) {
                if (cluster.getName().equals(clusterName)) {
                    return cluster;
                }
            }
        }
        return null;
    }

    public synchronized void releaseResources(Job job) {
        Cluster cluster = getClusterForJob(job);
        if (cluster != null) {
            cluster.setAvailableCores(cluster.getAvailableCores() + job.getCoresNeeded());
            cluster.setAvailableRam(cluster.getAvailableRam() + job.getRamNeeded());
        }
    }
}

class Job {
    private final String jobID;
    private final int coresNeeded;
    private final int ramNeeded;
    private final int duration; // in seconds

    public Job(String jobID, int coresNeeded, int ramNeeded, int duration) {
        this.jobID = jobID;
        this.coresNeeded = coresNeeded;
        this.ramNeeded = ramNeeded;
        this.duration = duration;
    }

    public String getJobID() {
        return jobID;
    }

    public int getCoresNeeded() {
        return coresNeeded;
    }

    public int getRamNeeded() {
        return ramNeeded;
    }

    public int getDuration() {
        return duration;
    }

    public String to_String() {
        return jobID + " [Cores=" + coresNeeded + ", RAM=" + ramNeeded + ", Time=" + duration + "s]";
    }
}

class JobScheduler {
    private final PriorityQueue<Job> jobs;
    private final ClusterManager clusterManager;

    public JobScheduler(ClusterManager clusterManager) {
        this.clusterManager = clusterManager;
        this.jobs = new PriorityQueue<>((a, b) -> {
            if (a.getCoresNeeded() != b.getCoresNeeded())
                return a.getCoresNeeded() - b.getCoresNeeded();
            return a.getRamNeeded() - b.getRamNeeded();
        });
    }

    public synchronized void pushJob(Job job) {
        System.out.println("[PUSHED] " + job.to_String());
        jobs.add(job);
        notify(); // Notify the runJobs thread
    }

    public void runJobs() {
        while (true) {
            Job jobToRun = null;
            Cluster clusterAllocated = null;

            synchronized (this) {
                while (jobs.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }

                for (Job job : jobs) {
                    Cluster cluster = clusterManager.allocateResources(job);
                    if (cluster != null) {
                        jobToRun = job;
                        clusterAllocated = cluster;
                        jobs.remove(job);
                        synchronized (System.out) {
                            System.out.println("[ALLOCATED] " + job.to_String() + " | " + cluster.getStatus());
                        }
                        break;
                    }
                }
            }

            if (jobToRun != null) {
                Job finalJob = jobToRun;
                Cluster finalCluster = clusterAllocated;

                new Thread(() -> {
                    synchronized (System.out) {
                        System.out.println("[STARTED]   " + finalJob.to_String() + " | " + finalCluster.getStatus());
                    }

                    try {
                        Thread.sleep(finalJob.getDuration() * 1000L);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }

                    clusterManager.releaseResources(finalJob);

                    synchronized (System.out) {
                        System.out.println("[COMPLETED] " + finalJob.to_String() + " | " + finalCluster.getStatus());
                    }

                    synchronized (JobScheduler.this) {
                        JobScheduler.this.notify();
                    }
                }, finalJob.to_String() + "-Thread").start();
            } else {
                synchronized (this) {
                    try {
                        wait(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
    }
}

public class JobSchedulerMain {
    public static void main(String[] args) {
        ClusterManager clusterManager = new ClusterManager();
        clusterManager.addCluster(new Cluster("Cluster-A", 8, 32));
        clusterManager.addCluster(new Cluster("Cluster-B", 4, 16));

        JobScheduler jobScheduler = new JobScheduler(clusterManager);
        Thread jobThread = new Thread(jobScheduler::runJobs, "JobSchedulerThread");
        jobThread.start();

        // Submitting 5 jobs concurrently
        for (int i = 1; i <= 5; i++) {
            int jobId = i;
            new Thread(() -> {
                int cores = (jobId % 2 == 0) ? 2 : 4;
                int ram = (jobId % 2 == 0) ? 8 : 16;
                int duration = 6 + jobId * 10; // different durations
                jobScheduler.pushJob(new Job("Job-" + jobId, cores, ram, duration));
            }, "JobSubmitter-" + i).start();
        }
    }
}
