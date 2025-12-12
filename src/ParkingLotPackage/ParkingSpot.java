package ParkingLotPackage;

public class ParkingSpot {
    String id;
    VehicleType vehicleType;
    Boolean occupied;
    Vehicle vehicle;

    public ParkingSpot(String id, VehicleType vehicleType) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.occupied = false;
        this.vehicle = null;
    }

    public String getId() {
        return id;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public Boolean getOccupied() {
        return occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setOccupied(Boolean occupied) {
        this.occupied = occupied;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
