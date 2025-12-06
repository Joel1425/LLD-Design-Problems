package ElevatorDesignProblem;

public class Main {
    public static void main(String[] args) {

        // Create manager
        ElevatorManager manager = new ElevatorManager();

        // Add elevators
        Elevator e1 = new Elevator("E1", manager);
        Elevator e2 = new Elevator("E2", manager);

        manager.addElevator(e1);
        manager.addElevator(e2);

        // Add outer panels (floors 0 to 5)
        for (int floor = 0; floor <= 5; floor++) {
            OuterPanel p = new OuterPanel(floor, manager);
            manager.addPanel(p);
        }

        // Choose elevator selection strategy
        manager.setStrategy(new NearestElevatorStrategy());
        // manager.setStrategy(new LoadBalancerStrategy()); // alternate strategy

        System.out.println("\n========= Simulation Started =========\n");

        // Request 1: Floor 3 wants to go UP
        manager.addToQueue(3, Direction.UP);

        System.out.println();

        // Request 2: Floor 1 wants to go DOWN
        manager.addToQueue(1, Direction.DOWN);

        System.out.println();

        // Request 3: Floor 5 wants to go DOWN
        manager.addToQueue(5, Direction.DOWN);

        System.out.println("\n========= Simulation Ended =========\n");
    }
}
