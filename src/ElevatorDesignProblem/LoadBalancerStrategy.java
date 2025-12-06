package ElevatorDesignProblem;

public class LoadBalancerStrategy implements ElevatorSelectionStrategy {

    @Override
    public Elevator select(Elevator[] elevators, int floor, Direction direction) {
        Elevator minQueueElevator = null;
        int minQueueSize = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            int size = e.queueSize();
            if (size < minQueueSize) {
                minQueueSize = size;
                minQueueElevator = e;
            }
        }

        return minQueueElevator;
    }
}
