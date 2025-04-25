// observer/Subject.java
package observer;

/**
 * The Subject interface defines the core methods for managing observers
 * in the Observer design pattern.
 *
 * A class that implements this interface can register, remove, and notify
 * multiple observers about updates or changes in state.
 */
public interface Subject {

    /**
     * Registers an observer to receive updates.
     *
     * @param o the observer to register
     */
    void registerObserver(Observer o);

    /**
     * Removes a previously registered observer.
     *
     * @param o the observer to remove
     */
    void removeObserver(Observer o);

    /**
     * Notifies all registered observers by calling their update() method.
     *
     * @param message the message to send to observers
     */
    void notifyObservers(String message);
}
