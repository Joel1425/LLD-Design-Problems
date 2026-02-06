package TransportSystem.strategy;

import TransportSystem.model.Location;
import TransportSystem.model.Path;
import TransportSystem.model.Route;

import java.util.*;

public class FastestPathStrategy implements PathFindingStrategy {

    @Override
    public Path findPath(Location source, Location destination, Map<Location, List<Route>> graph) {
        Map<Location, Double> minTime = new HashMap<>();
        Map<Location, Route> previousRoute = new HashMap<>();
        PriorityQueue<Location> pq = new PriorityQueue<>(
                Comparator.comparingDouble(l -> minTime.getOrDefault(l, Double.MAX_VALUE))
        );

        minTime.put(source, 0.0);
        pq.add(source);

        while (!pq.isEmpty()) {
            Location current = pq.poll();

            if (current.equals(destination)) {
                break;
            }

            List<Route> routes = graph.getOrDefault(current, new ArrayList<>());
            for (Route route : routes) {
                Location neighbor = route.getDestination();
                double newTime = minTime.get(current) + route.getTime();

                if (newTime < minTime.getOrDefault(neighbor, Double.MAX_VALUE)) {
                    minTime.put(neighbor, newTime);
                    previousRoute.put(neighbor, route);
                    pq.add(neighbor);
                }
            }
        }

        return buildPath(source, destination, previousRoute);
    }

    private Path buildPath(Location source, Location destination, Map<Location, Route> previousRoute) {
        Path path = new Path();
        List<Route> routes = new ArrayList<>();

        Location current = destination;
        while (previousRoute.containsKey(current)) {
            Route route = previousRoute.get(current);
            routes.add(route);
            current = route.getSource();
        }

        if (routes.isEmpty() && !source.equals(destination)) {
            return path;
        }

        Collections.reverse(routes);
        for (Route route : routes) {
            path.addRoute(route);
        }

        return path;
    }
}
