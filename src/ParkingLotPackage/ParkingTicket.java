package ParkingLotPackage;

import java.time.Instant;

public class ParkingTicket {
    String id;
    Instant entryTime, exitTime;
    Float totalFare;
    Vehicle vehicle;
    ParkingSpot parkingSpot;

    public ParkingTicket(String id, Instant entryTime, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.id = id;
        this.entryTime = entryTime;
        this.exitTime = null;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.totalFare = null;
    }

    public Instant getEntryTime() {
        return entryTime;
    }

    public Instant getExitTime() {
        return exitTime;
    }

    public Float getTotalFare(){
        return 0.0F;
    }

    public void setExitTime(Instant exitTime) {
        this.exitTime = exitTime;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void printTicket(){
        System.out.println("\n");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("PARKING TICKET FOR "+this.vehicle.id+" "+this.vehicle.getVehicleType());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("ID: "+this.id);
        System.out.println("Entry Time: "+this.entryTime);
        if (this.exitTime != null ){
            System.out.println("Exit Time: "+this.exitTime);
        }
        System.out.println("Vehicle: "+this.vehicle.getId());
        System.out.println("Parking Spot: "+this.parkingSpot.getId());
        if (this.totalFare != null){
            System.out.println("Total Fare: "+this.totalFare);
        }
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("\n");
    }

}
