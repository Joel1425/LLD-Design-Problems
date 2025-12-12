package ParkingLotPackage;

import java.util.List;

public class FarthestFirstStrategy implements ParkingStrategy{
    @Override
    public ParkingSpot getParkingSpot(Vehicle vehicle, List<List<ParkingSpot>> parkingSpots ) {
        for (int i=parkingSpots.size()-1;i>=0;i--) {
            for (int j = parkingSpots.get(i).size()-1; j >=0; j--) {
                if ( parkingSpots.get(i).get(j).getOccupied() == false &&
                        parkingSpots.get(i).get(j).getVehicleType() == vehicle.vehicleType) return parkingSpots.get(i).get(j);
            }
        }
        return null;
    }
}
