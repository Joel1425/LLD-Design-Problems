import java.util.ArrayList;
import java.util.List;

interface WeatherObserver{
    public void update( String temp );
}
class Mobile implements WeatherObserver{
    String username;
    Mobile( String username ){
        this.username = username;
    }
    @Override
    public void update(String temp) {
        System.out.println(this.username+ " updated with weather " + temp);
    }
}
class Watch implements WeatherObserver{
    String username;
    Watch( String username ){
        this.username = username;
    }
    @Override
    public void update(String temp) {
        System.out.println(this.username+ "'s iWatch updated with weather " + temp);
    }
}
class WeatherStation{
    List<WeatherObserver> observers;
    String temp;
    WeatherStation(){
        this.observers = new ArrayList<>();
    }
    public void addObserver( WeatherObserver w){
        this.observers.add(w);
    }
    public void setTemp( String temp ){
        this.temp = temp + "°C";
        notifyObservers();
    }
    public void notifyObservers(){
        for (WeatherObserver w: this.observers){
            w.update(this.temp);
        }
    }
}
public class ObserverDesignPatternExample {
    public static void main(String[] args) {
        WeatherStation ws = new WeatherStation();
        WeatherObserver m1 = new Mobile( "Joel" );
        WeatherObserver m2 = new Mobile( "Andria" );
        WeatherObserver w1 = new Watch( "Bob" );
        ws.addObserver(m1);
        ws.addObserver(m2);
        ws.setTemp("25");
        System.out.println();
        ws.addObserver(w1);
        ws.setTemp("32");

    }
}
