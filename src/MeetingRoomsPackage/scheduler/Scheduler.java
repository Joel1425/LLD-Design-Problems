package MeetingRoomsPackage.scheduler;

import MeetingRoomsPackage.models.Booking;
import MeetingRoomsPackage.models.Employee;
import MeetingRoomsPackage.models.MeetingRoom;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler {
    /*
   - employeeDB : map<String, Employee>
   - meetingRoomDB : map<String, MeetingRoom>
   - bookingDB : map<String, Booking>

   + addAnEmployee( employee )
   + addAMeetingRoom( meetingRoom )
   + getAllBookingsByTime( startTime, endTime ) -> all booking for a time window from all meeting rooms
   + bookMeetingRoom( employeeID, startTime, endTime, meetingRoomID )
   + cancelExistingBooking( bookingID )
   + getAllMeetingsForAMeetingRoom( meetingRoomID )
   + getAllMeetingsByEmployee( employeeID )
   */
    private int bookingIDCount;
    private final Map<String, Employee> employeeDB;
    private final Map<String, MeetingRoom> meetingRoomDB;
    private final Map<String, Booking> bookingDB;
    private final Map<String, Object> roomLocks;


    public Scheduler() {
        this.bookingIDCount = 0;
        this.bookingDB = new HashMap<>();
        this.meetingRoomDB = new HashMap<>();
        this.employeeDB = new HashMap<>();
        roomLocks = new ConcurrentHashMap<>();
    }

    public void addAnEmployee( Employee employee ){
        if (employeeDB.containsKey(employee.getId())){
            System.out.println( "[ERROR - ALREADY EXISTS] "+employee );
            return;
        }
        employeeDB.put(employee.getId(), employee);
        System.out.println( "[EMPLOYEE - ADDED] "+employee );
    }

    public void addAMeetingRoom( MeetingRoom meetingRoom ){
        if (meetingRoomDB.containsKey(meetingRoom.getId())){
            System.out.println( "[ERROR - ALREADY EXISTS] "+ meetingRoom);
            return;
        }
        meetingRoomDB.put(meetingRoom.getId(), meetingRoom);
        roomLocks.put(meetingRoom.getId(), new Object());
        System.out.println( "[MEETING ROOM - ADDED] "+meetingRoom );
    }

    private boolean fallsInsideWindow( LocalDateTime s1, LocalDateTime e1, LocalDateTime s2, LocalDateTime e2){
        return s1.isBefore(e2) && e1.isAfter(s2);
    }

    private void getAllBookingsByTime( LocalDateTime startTime, LocalDateTime endTime ){
        System.out.println("[BOOKINGS] for "+startTime+" - "+endTime);
        System.out.println("=".repeat(40));
        for (Map.Entry<String, Booking> entry: bookingDB.entrySet()){
            if (fallsInsideWindow(  startTime, endTime,
                                    entry.getValue().getStartTime(), entry.getValue().getEndTime()) ){
                System.out.println(entry.getValue());
            }
        }
        System.out.println("=".repeat(40));
    }

    private Employee getEmployeeByID( String employeeID ){
        return employeeDB.get(employeeID);
    }

    private MeetingRoom getMeetingRoomByID( String meetingRoomID ){
        return meetingRoomDB.get(meetingRoomID);
    }

    private Booking getBookingByID( String bookingID ){
        return bookingDB.get(bookingID);
    }

    private boolean isRoomAvailable( LocalDateTime startTime, LocalDateTime endTime, String meetingRoomID ){
        for ( Booking booking : bookingDB.values()){
            if ( booking.getMeetingRoomID().compareTo(meetingRoomID)==0 &&
                 fallsInsideWindow( startTime, endTime,
                         booking.getStartTime(),
                         booking.getEndTime() ) ){
                return false;
            }
        }
        return true;
    }

    public void bookMeetingRoom( String employeeID, LocalDateTime startTime, LocalDateTime endTime, String meetingRoomID ){
        Employee employee = getEmployeeByID( employeeID );
        if (employee == null){
            System.out.println("[ERROR] - EMPLOYEE "+employeeID+" DOES NOT EXIST]" );
            return;
        }
        MeetingRoom meetingRoom = getMeetingRoomByID( meetingRoomID );
        if (meetingRoom == null){
            System.out.println("[ERROR] - MEETING ROOM "+meetingRoomID+" DOES NOT EXIST]" );
            return;
        }

        Object lock = roomLocks.get(meetingRoomID);
        synchronized (lock) {
            boolean roomAvailable = isRoomAvailable(startTime, endTime, meetingRoomID);
            if (!roomAvailable) {
                System.out.println(startTime + " " + endTime);
                System.out.println("[ERROR - MEETING ROOM UNAVAILABLE] for " + startTime + " " + endTime);
                return;
            }

            // Booking Starts
            Booking booking = new Booking(String.valueOf(bookingIDCount), startTime, endTime, meetingRoomID, employeeID);
            bookingDB.put(String.valueOf(bookingIDCount), booking);
            bookingIDCount++;
            // Booking Ends
        }
    }

    public void cancelExistingBooking(String bookingID) {
        Booking booking = getBookingByID(bookingID);
        if (booking == null) {
            System.out.println("[ERROR] BOOKING " + bookingID + " DOES NOT EXIST");
            return;
        }

        String meetingRoomID = booking.getMeetingRoomID();
        Object lock = roomLocks.get(meetingRoomID);

        synchronized (lock) {
            // Re-check inside lock (defensive)
            Booking existing = getBookingByID(bookingID);
            if (existing == null) {
                System.out.println("[ERROR] BOOKING ALREADY CANCELLED");
                return;
            }

            bookingDB.remove(bookingID);
            System.out.println("[SUCCESS] CANCELLED " + existing);
        }
    }


    public void getAllMeetingsForAMeetingRoom( String meetingRoomID ){
        System.out.println("[BOOKINGS] for "+meetingRoomID);
        System.out.println("=".repeat(40));
        for ( Booking booking: bookingDB.values()){
            if (booking.getMeetingRoomID().compareTo(meetingRoomID)==0){
                System.out.println("+".repeat(40));
                System.out.println(booking);
                System.out.println("+".repeat(40));
            }
        }
        System.out.println("=".repeat(40));
    }

    public void getAllMeetingsByEmployee( String employeeID ){
        System.out.println("[BOOKINGS] for "+employeeID);
        System.out.println("=".repeat(40));
        for ( Booking booking: bookingDB.values()){
            if (booking.getEmployeeID().compareTo(employeeID)==0){
                System.out.println("+".repeat(40));
                System.out.println(booking);
                System.out.println("+".repeat(40));
            }
        }
        System.out.println("=".repeat(40));
    }
}

