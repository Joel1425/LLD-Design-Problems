package ParkingLotPackage;

public class EntryGate extends Gate{
    public EntryGate(String id, ParkingLot parkingLot) {
        super(id, parkingLot);
    }

    public ParkingTicket processEntry(User user){
        System.out.println("Processing Entry for User "+user.id);
        ParkingSpot parkingSpot = parkingLot.assignSpot(user.vehicle);
        ParkingTicket parkingTicket = parkingLot.generateTicket(parkingSpot);
        user.setParkingTicket(parkingTicket);
        return parkingTicket;
    }
}
