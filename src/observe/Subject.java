package observe;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Marks a class as observable, and adds methods to add and notify observers.
 */
public abstract class Subject {
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    /**
     * Add an observer to the list of Observers.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }


    /**
     * Notify all obserevers that data has changed.
     */
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
