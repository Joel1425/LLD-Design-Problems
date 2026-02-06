package TransportSystem.model;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<Route> routes;
    private double totalCost;
    private double totalTime;

    public Path() {
        this.routes = new ArrayList<>();
        this.totalCost = 0;
        this.totalTime = 0;
    }

    public Path(Path other) {
        this.routes = new ArrayList<>(other.routes);
        this.totalCost = other.totalCost;
        this.totalTime = other.totalTime;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
        this.totalCost += route.getCost();
        this.totalTime += route.getTime();
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public Location getLastLocation() {
        if (routes.isEmpty()) return null;
        return routes.get(routes.size() - 1).getDestination();
    }

    public boolean isEmpty() {
        return routes.isEmpty();
    }

    public void printPath() {
        if (routes.isEmpty()) {
            System.out.println("No path found!");
            return;
        }
        System.out.println("Path from " + routes.get(0).getSource() + " to " + getLastLocation() + ":");
        for (Route route : routes) {
            route.printRoute();
        }
        System.out.println("Total Cost: " + totalCost + ", Total Time: " + totalTime + " mins");
    }
}
