package ParkingLotPackage;

public class VehicleBasedStrategy implements ParkingPriceStrategy{
    @Override
    public float getTotalFare( ParkingTicket parkingTicket ) {
        if (parkingTicket.vehicle.getVehicleType() == VehicleType.CAR ){
            return 100.0f;
        }
        return 50.0f;
    }
}
