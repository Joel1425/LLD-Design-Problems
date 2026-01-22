package MeetingRoomsPackage;

import MeetingRoomsPackage.models.Employee;
import MeetingRoomsPackage.models.MeetingRoom;
import MeetingRoomsPackage.scheduler.Scheduler;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.addAnEmployee(new Employee("USER-1"));
        scheduler.addAnEmployee(new Employee("USER-2"));
        scheduler.addAMeetingRoom(new MeetingRoom("MEETING-ROOM-1"));
        scheduler.addAMeetingRoom(new MeetingRoom("MEETING-ROOM-2"));
        scheduler.bookMeetingRoom("USER-1", LocalDateTime.of(2026, 01, 22, 18,0),
                LocalDateTime.of(2026, 01, 22, 19,0), "MEETING-ROOM-1");
        scheduler.bookMeetingRoom("USER-1", LocalDateTime.of(2026, 01, 22, 19,0),
                LocalDateTime.of(2026, 01, 22, 20,0), "MEETING-ROOM-2");
        scheduler.bookMeetingRoom("USER-2", LocalDateTime.of(2026, 01, 22, 4,0),
                LocalDateTime.of(2026, 01, 22, 5,0), "MEETING-ROOM-2");
        scheduler.getAllMeetingsByEmployee("USER-1");
        scheduler.getAllMeetingsByEmployee("USER-2");
        scheduler.cancelExistingBooking(String.valueOf(0));
        scheduler.getAllMeetingsByEmployee("USER-1");
        scheduler.bookMeetingRoom("USER-2", LocalDateTime.of(2026, 01, 22, 18,0),
                LocalDateTime.of(2026, 01, 22, 19,0), "MEETING-ROOM-1");
        scheduler.getAllMeetingsByEmployee("USER-1");
        scheduler.getAllMeetingsByEmployee("USER-2");
    }
}
