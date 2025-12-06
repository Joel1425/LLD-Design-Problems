package ElevatorDesignProblem;

public class IdleState implements State{
    Elevator elevator;

    public IdleState(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void up() {
        System.out.println(elevator.getID() + " Starting Up...");
        this.elevator.setState(this.elevator.getUpState());
    }

    @Override
    public void down() {
        System.out.println(elevator.getID() + " Starting Down...");
        this.elevator.setState(this.elevator.getDownState());
    }

    @Override
    public void idle() {
        System.out.println(elevator.getID() + " Already Idle...");
        this.elevator.setState(this.elevator.getIdleState());
    }
}

