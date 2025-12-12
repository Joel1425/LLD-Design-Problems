package ParkingLotPackage;

import java.time.Duration;

public class TimeBasedStrategy implements ParkingPriceStrategy{
    @Override
    public float getTotalFare( ParkingTicket parkingTicket ) {
        Duration duration = Duration.between(parkingTicket.getEntryTime(), parkingTicket.getExitTime());
        long seconds = duration.getSeconds();
        return (float)10.0*seconds;
    }
}
