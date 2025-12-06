package ElevatorDesignProblem;

public class DownState implements State{
    Elevator elevator;

    public DownState(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void up() {
        System.out.println(elevator.getID() + " Cannot go UP while moving DOWN");
    }

    @Override
    public void down() {
        System.out.println(elevator.getID() + " Going Down...");
        // stays in DOWN state — correct
    }

    @Override
    public void idle() {
        System.out.println(elevator.getID() + " Stopping. Becoming IDLE...");
        elevator.setState(elevator.getIdleState());
    }
}
