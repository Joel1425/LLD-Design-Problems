package MeetingRoomsPackage.models;

import java.time.LocalDateTime;

public class Booking {
    private String id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String meetingRoomID;
    private String employeeID;

    public Booking(String id, LocalDateTime startTime, LocalDateTime endTime, String meetingRoomID, String employeeID) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingRoomID = meetingRoomID;
        this.employeeID = employeeID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getMeetingRoomID() {
        return meetingRoomID;
    }

    public void setMeetingRoomID(String meetingRoomID) {
        this.meetingRoomID = meetingRoomID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    @Override
    public String toString(){
        return "[BOOKING]: "+this.id
                +"\nStartTime: "+this.startTime
                +"\nEndTime: "+this.endTime
                +"\nBookedBy: "+this.employeeID
                +"\nMeetingRoom: "+this.meetingRoomID;
    }
}
