package ElevatorDesignProblem;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator select(Elevator[] elevators, int floor, Direction direction) {
        Elevator best = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            int distance = Math.abs(e.getCurrentFloor() - floor);
            if (distance < minDistance) {
                minDistance = distance;
                best = e;
            }
        }

        return best;
    }
}