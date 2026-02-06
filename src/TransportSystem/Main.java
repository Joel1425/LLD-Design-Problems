package TransportSystem;

import TransportSystem.enums.TransportMode;
import TransportSystem.model.Location;
import TransportSystem.model.Path;
import TransportSystem.service.TransportService;

public class Main {
    public static void main(String[] args) {
        TransportService transportService = new TransportService();

        // Add locations
        Location A = transportService.addLocation("A");
        Location B = transportService.addLocation("B");
        Location X = transportService.addLocation("X");
        Location Y = transportService.addLocation("Y");
        Location Z = transportService.addLocation("Z");

        System.out.println("\n--- Adding Routes ---");

        // Direct route A -> B (expensive but fast via CAB)
        transportService.addRoute(A, B, TransportMode.CAB, 500, 20);

        // Indirect route A -> X -> Y -> B (cheaper via BUS but slower)
        transportService.addRoute(A, X, TransportMode.BUS, 50, 30);
        transportService.addRoute(X, Y, TransportMode.BUS, 40, 25);
        transportService.addRoute(Y, B, TransportMode.BUS, 60, 35);

        // Another path A -> Z -> B (METRO - moderate cost and time)
        transportService.addRoute(A, Z, TransportMode.METRO, 80, 15);
        transportService.addRoute(Z, B, TransportMode.METRO, 100, 18);

        // Additional routes for flexibility
        transportService.addRoute(X, Z, TransportMode.BUS, 30, 20);
        transportService.addRoute(Z, Y, TransportMode.METRO, 50, 10);

        System.out.println("\n--- All Locations ---");
        transportService.printAllLocations();

        System.out.println("\n--- Finding Cheapest Path from A to B ---");
        Path cheapestPath = transportService.findCheapestPath(A, B);
        cheapestPath.printPath();

        System.out.println("\n--- Finding Fastest Path from A to B ---");
        Path fastestPath = transportService.findFastestPath(A, B);
        fastestPath.printPath();

        // Another query: X to B
        System.out.println("\n--- Finding Cheapest Path from X to B ---");
        Path cheapestXtoB = transportService.findCheapestPath(X, B);
        cheapestXtoB.printPath();

        System.out.println("\n--- Finding Fastest Path from X to B ---");
        Path fastestXtoB = transportService.findFastestPath(X, B);
        fastestXtoB.printPath();
    }
}
