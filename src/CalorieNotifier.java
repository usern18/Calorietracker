import java.util.ArrayList;
import java.util.List;

public class CalorieNotifier {
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}

class ConsoleLogObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("[LOG OBSERVER] Подія в системі: " + message);
    }
}

class CalorieLimitObserver implements Observer {
    @Override
    public void update(String message) {
        System.out.println("[ALERT OBSERVER] Увага до раціону: " + message);
    }
}