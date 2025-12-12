package ParkingLotPackage;

public class Main {
    public static void main(String[] args) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        parkingLot.printParkingLot();
        User u1 = new User("User-1", new Bike("Bike-1"));
        EntryGate entryGate = parkingLot.getEntryGate();
        ParkingTicket parkingTicket = entryGate.processEntry(u1);
        parkingTicket.printTicket();
        parkingLot.printParkingLot();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ExitGate exitGate = parkingLot.getExitGate();
        exitGate.processExit(u1);
        parkingTicket.printTicket();
        parkingLot.printParkingLot();
    }
}
