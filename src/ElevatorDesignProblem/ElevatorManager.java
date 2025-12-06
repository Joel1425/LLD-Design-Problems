package ElevatorDesignProblem;

import java.util.ArrayList;
import java.util.List;

public class ElevatorManager {

    private List<OuterPanel> panels = new ArrayList<>();
    private List<Elevator> elevators = new ArrayList<>();
    private ElevatorSelectionStrategy strategy = new NearestElevatorStrategy();
    private List<ElevatorObserver> observers = new ArrayList<>();

    public void addPanel(OuterPanel p) {
        panels.add(p);
        observers.add(p);
    }

    public void addElevator(Elevator e) {
        elevators.add(e);
    }

    public void setStrategy(ElevatorSelectionStrategy s) {
        this.strategy = s;
    }

    public void notifyObservers() {
        for (ElevatorObserver obs : observers) {
            obs.update();
        }
    }

    public void addToQueue(int floor, Direction dir) {
        Elevator best = strategy.select(
                elevators.toArray(new Elevator[0]),
                floor,
                dir
        );

        if (best != null) {
            best.addToQueue(floor);
            System.out.println("Elevator " + best.getID() + " assigned to request at floor " + floor);
        }
    }
}