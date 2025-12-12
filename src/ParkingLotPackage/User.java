package ParkingLotPackage;

public class User {
    String id;
    Vehicle vehicle;
    ParkingTicket parkingTicket;

    public User( String id, Vehicle vehicle ) {
        this.id = id;
        this.vehicle = vehicle;
        this.parkingTicket = null;
    }

    public void setParkingTicket(ParkingTicket parkingTicket) {
        this.parkingTicket = parkingTicket;
    }

    public String getId() {
        return id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingTicket getParkingTicket() {
        return parkingTicket;
    }
}
