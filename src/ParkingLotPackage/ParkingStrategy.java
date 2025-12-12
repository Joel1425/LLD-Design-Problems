package ParkingLotPackage;

import java.util.List;

public interface ParkingStrategy {
    public ParkingSpot getParkingSpot( Vehicle vehicle, List<List<ParkingSpot>> parkingSpots);
}
