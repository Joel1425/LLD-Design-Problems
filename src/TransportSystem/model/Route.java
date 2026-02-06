package TransportSystem.model;

import TransportSystem.enums.TransportMode;

public class Route {
    private Location source;
    private Location destination;
    private TransportMode mode;
    private double cost;
    private double time;

    public Route(Location source, Location destination, TransportMode mode, double cost, double time) {
        this.source = source;
        this.destination = destination;
        this.mode = mode;
        this.cost = cost;
        this.time = time;
    }

    public Location getSource() {
        return source;
    }

    public Location getDestination() {
        return destination;
    }

    public TransportMode getMode() {
        return mode;
    }

    public double getCost() {
        return cost;
    }

    public double getTime() {
        return time;
    }

    public void printRoute() {
        System.out.println("  " + source + " -> " + destination +
                " [" + mode + "] Cost: " + cost + ", Time: " + time + " mins");
    }
}
