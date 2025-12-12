package ParkingLotPackage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParkingLot {
    private static volatile ParkingLot parkingLot;
    private List<List<ParkingSpot>> parkingSpots;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;
    private ParkingStrategy parkingStrategy;
    private ParkingPriceStrategy parkingPriceStrategy;
    private int ticketCounter;
    private boolean isFull;
    private Random random;
    public ParkingLot() {
        initParkingSpots();
        initEntryGates();
        initExitGates();
        this.parkingPriceStrategy = new TimeBasedStrategy();
        this.parkingStrategy = new ClosestFirstStrategy();
        this.isFull = false;
        random = new Random();
        this.ticketCounter = 0;
    }

    public void initParkingSpots(){
        parkingSpots = new ArrayList<>();
        for (int i=0;i<2;i++){
            List<ParkingSpot> tile = new ArrayList<>();
            for (int j=0;j<4;j++){
                tile.add(new ParkingSpot(i+"-"+j, VehicleType.CAR));
            }
            this.parkingSpots.add(tile);
        }
        for (int i=2;i<4;i++){
            List<ParkingSpot> tile = new ArrayList<>();
            for (int j=0;j<4;j++){
                tile.add(new ParkingSpot(i+"-"+j, VehicleType.BIKE));
            }
            this.parkingSpots.add(tile);
        }
    }

    public void initEntryGates(){
        this.entryGates = new ArrayList<>();
        this.entryGates.add(new EntryGate("EntryGate-1", this));
        this.entryGates.add(new EntryGate("EntryGate-2", this));
    }

    public void initExitGates(){
        this.exitGates = new ArrayList<>();
        this.exitGates.add(new ExitGate("ExitGate-1", this));
        this.exitGates.add(new ExitGate("ExitGate-2", this));
    }

    public static synchronized ParkingLot getInstance(){
        if (parkingLot == null){
            parkingLot = new ParkingLot();
        }
        return parkingLot;
    }

    public ParkingSpot assignSpot( Vehicle vehicle ){
        ParkingSpot parkingSpot = this.parkingStrategy.getParkingSpot( vehicle, this.parkingSpots );
        for (int i=0;i<this.parkingSpots.size();i++) {
            for (int j = 0; j < this.parkingSpots.get(i).size(); j++) {
                if (parkingSpot.id.compareTo(this.parkingSpots.get(i).get(j).getId()) == 0){
                    this.parkingSpots.get(i).get(j).setOccupied(true);
                    this.parkingSpots.get(i).get(j).setVehicle(vehicle);
                    System.out.println("Parking Spot " + parkingSpot.getId() + " ASSIGNED to " + vehicle.getId());
                    return parkingSpot;
                }
            }
        }
        return null;
    }

    public ParkingTicket generateTicket( ParkingSpot parkingSpot){
        ParkingTicket parkingTicket = new ParkingTicket( Integer.toString(this.ticketCounter),
                                                         Instant.now(),
                                                         parkingSpot.getVehicle(),
                                                         parkingSpot );
        this.ticketCounter++;
        return parkingTicket;

    }

    public boolean releaseSpot( ParkingSpot parkingSpot ){
        for (int i=0;i<this.parkingSpots.size();i++) {
            for (int j = 0; j < this.parkingSpots.get(i).size(); j++) {
                if (parkingSpot.id.compareTo(this.parkingSpots.get(i).get(j).getId()) == 0){
                    this.parkingSpots.get(i).get(j).setOccupied(false);
                    this.parkingSpots.get(i).get(j).setVehicle(null);
                    System.out.println("Parking Spot " + parkingSpot.getId() + " RELEASED");
                    return true;
                }
            }
        }
        return false;
    }

    public float calculateTotalFare( ParkingTicket parkingTicket ){
        parkingTicket.setExitTime( Instant.now() );
        float totalFare = this.parkingPriceStrategy.getTotalFare( parkingTicket );
        return totalFare;
    }

    public EntryGate getEntryGate(){
        int index = random.nextInt(this.entryGates.size());
        return this.entryGates.get(index);
    }

    public ExitGate getExitGate(){
        int index = random.nextInt(this.exitGates.size());
        return this.exitGates.get(index);
    }



    public void printParkingLot(){
        for (int i=0;i<this.parkingSpots.size();i++){
            for (int j=0;j<this.parkingSpots.get(i).size();j++){
                boolean isOccupied = this.parkingSpots.get(i).get(j).getOccupied();
                if (isOccupied)
                    System.out.print(this.parkingSpots.get(i).get(j).getVehicle().getId()+"        ");
                else
                    System.out.print(this.parkingSpots.get(i).get(j).getVehicleType()+"        ");
            }
            System.out.println();
        }
    }
}
