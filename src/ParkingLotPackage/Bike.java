package ParkingLotPackage;

public class Bike extends Vehicle{
    VehicleType vehicleType;
    public Bike( String id ){
        super(id, VehicleType.BIKE);
    }
}
