package ElevatorDesignProblem;

public class UpState implements State{
    Elevator elevator;

    public UpState(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void up() {
        System.out.println(elevator.getID() + " Going Up...");
        // stays in UP state — correct
    }

    @Override
    public void down() {
        System.out.println(elevator.getID() + " Cannot go DOWN while moving UP");
    }

    @Override
    public void idle() {
        System.out.println(elevator.getID() + " Stopping. Becoming IDLE...");
        elevator.setState(elevator.getIdleState());
    }
}
