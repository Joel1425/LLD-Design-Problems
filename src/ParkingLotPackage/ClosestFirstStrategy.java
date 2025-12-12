package ParkingLotPackage;

import java.util.List;

public class ClosestFirstStrategy implements ParkingStrategy{
    @Override
    public ParkingSpot getParkingSpot( Vehicle vehicle, List<List<ParkingSpot>> parkingSpots ) {
        for (int i=0;i<parkingSpots.size();i++) {
            for (int j = 0; j < parkingSpots.get(i).size(); j++) {
                if ( parkingSpots.get(i).get(j).getOccupied() == false &&
                     parkingSpots.get(i).get(j).getVehicleType() == vehicle.vehicleType) return parkingSpots.get(i).get(j);
            }
        }
        return null;
    }
}
