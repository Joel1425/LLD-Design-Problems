package ElevatorDesignProblem;

import java.util.LinkedList;
import java.util.Queue;

public class Elevator {

    private ElevatorManager manager;
    private State state;
    private int currentFloor = 0;
    private Queue<Integer> queue = new LinkedList<>();

    private UpState upState;
    private DownState downState;
    private IdleState idleState;

    private String id;

    public Elevator(String id, ElevatorManager manager) {
        this.id = id;
        this.manager = manager;

        this.upState = new UpState(this);
        this.downState = new DownState(this);
        this.idleState = new IdleState(this);

        this.state = idleState;
    }

    public String getID() { return id; }

    public int getCurrentFloor() { return currentFloor; }

    public void setState(State state) {
        this.state = state;
        manager.notifyObservers();
    }

    public void moveUp() {
        currentFloor++;
    }

    public void moveDown() {
        currentFloor--;
    }

    public void addToQueue(int floor) {
        queue.offer(floor);
        processQueue();
    }

    public int queueSize() {
        return queue.size();
    }

    public void processQueue() {
        if (queue.isEmpty()) return;

        int target = queue.peek();

        if (target > currentFloor) {
            state.up();
            moveUp();
        }
        else if (target < currentFloor) {
            state.down();
            moveDown();
        }
        else {
            System.out.println(id + " reached floor " + currentFloor);
            queue.poll();
            state.idle();
        }

        if (!queue.isEmpty()) {
            processQueue();
        }
    }

    public UpState getUpState() { return upState; }
    public DownState getDownState() { return downState; }
    public IdleState getIdleState() { return idleState; }
}
