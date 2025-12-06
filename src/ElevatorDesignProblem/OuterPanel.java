package ElevatorDesignProblem;

public class OuterPanel implements ElevatorObserver {

    private ElevatorManager manager;
    private int floor;

    public OuterPanel(int floor, ElevatorManager manager) {
        this.floor = floor;
        this.manager = manager;
    }

    public void requestElevator(Direction direction) {
        System.out.println("Floor " + floor + " requests elevator " + direction);
        manager.addToQueue(floor, direction);
    }

    @Override
    public void update() {
        // Gets notified when elevators change state
        System.out.println("OuterPanel at floor " + floor + " received update.");
    }
}