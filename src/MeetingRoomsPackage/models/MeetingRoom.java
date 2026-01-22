package MeetingRoomsPackage.models;

public class MeetingRoom {
    private String id;

    public MeetingRoom(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[MEETING ROOM]: "+this.id;
    }
}
