package TransportSystem.strategy;

import TransportSystem.model.Location;
import TransportSystem.model.Path;
import TransportSystem.model.Route;

import java.util.List;
import java.util.Map;

public interface PathFindingStrategy {
    Path findPath(Location source, Location destination, Map<Location, List<Route>> graph);
}
