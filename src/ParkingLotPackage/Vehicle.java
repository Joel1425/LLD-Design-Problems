package ParkingLotPackage;

public abstract class Vehicle {
    String id;
    VehicleType vehicleType;

    public Vehicle(String id, VehicleType vehicleType) {
        this.id = id;
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

}
