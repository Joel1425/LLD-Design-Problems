package TransportSystem.service;

import TransportSystem.enums.TransportMode;
import TransportSystem.model.Location;
import TransportSystem.model.Path;
import TransportSystem.model.Route;
import TransportSystem.strategy.CheapestPathStrategy;
import TransportSystem.strategy.FastestPathStrategy;
import TransportSystem.strategy.PathFindingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportService {
    private Map<Location, List<Route>> graph;
    private Map<String, Location> locations;

    public TransportService() {
        this.graph = new HashMap<>();
        this.locations = new HashMap<>();
    }

    public Location addLocation(String name) {
        if (locations.containsKey(name)) {
            return locations.get(name);
        }
        Location location = new Location(name);
        locations.put(name, location);
        graph.put(location, new ArrayList<>());
        return location;
    }

    public Location getLocation(String name) {
        return locations.get(name);
    }

    public void addRoute(Location source, Location destination, TransportMode mode, double cost, double time) {
        Route route = new Route(source, destination, mode, cost, time);
        graph.get(source).add(route);
        System.out.println("Route added: " + source + " -> " + destination + " via " + mode);
    }

    public void addBidirectionalRoute(Location loc1, Location loc2, TransportMode mode, double cost, double time) {
        addRoute(loc1, loc2, mode, cost, time);
        addRoute(loc2, loc1, mode, cost, time);
    }

    public Path findCheapestPath(Location source, Location destination) {
        PathFindingStrategy strategy = new CheapestPathStrategy();
        return strategy.findPath(source, destination, graph);
    }

    public Path findFastestPath(Location source, Location destination) {
        PathFindingStrategy strategy = new FastestPathStrategy();
        return strategy.findPath(source, destination, graph);
    }

    public Path findPath(Location source, Location destination, PathFindingStrategy strategy) {
        return strategy.findPath(source, destination, graph);
    }

    public void printAllLocations() {
        System.out.println("All Locations:");
        for (String name : locations.keySet()) {
            System.out.println("  - " + name);
        }
    }

    public void printAllRoutes() {
        System.out.println("All Routes:");
        for (Location location : graph.keySet()) {
            for (Route route : graph.get(location)) {
                route.printRoute();
            }
        }
    }
}
