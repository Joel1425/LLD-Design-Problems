package ElevatorDesignProblem;

public interface ElevatorSelectionStrategy {
    Elevator select(Elevator[] elevators, int requestedFloor, Direction direction);
}
