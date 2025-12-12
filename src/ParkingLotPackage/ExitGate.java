package ParkingLotPackage;

public class ExitGate extends Gate{
    public ExitGate(String id, ParkingLot parkingLot) {
        super(id, parkingLot);
    }

    public void processExit( User user ){
        float totalFare = parkingLot.calculateTotalFare(user.getParkingTicket());
        System.out.println("Please pay Rs. "+totalFare);
        parkingLot.releaseSpot(user.getParkingTicket().getParkingSpot());
        System.out.println("Thank you so much! Visit Again!");
    }
}
