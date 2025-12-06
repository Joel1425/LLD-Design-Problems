import java.awt.*;

interface Vehicle{
    public void drive();
}
class Bike implements Vehicle{
    @Override
    public void drive(){
        System.out.println("Driving a Bike");
    }
}
class Car implements Vehicle{
    @Override
    public void drive(){
        System.out.println("Driving a Car");
    }
}
class VehicleFactory{
    public Vehicle createVehicle(String vehicle){
        return switch (vehicle) {
            case "bike" -> new Bike();
            case "car" -> new Car();
            default -> null;
        };
    }
}
public class FactoryDesignPatternExample {
    public static void main(String[] args) {
        VehicleFactory factory = new VehicleFactory();
        Vehicle bike = factory.createVehicle("bike");
        bike.drive();
        Vehicle car = factory.createVehicle("car");
        car.drive();
    }
}
