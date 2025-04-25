package observer;

/**
 * The Subject interface defines the contract for any class that wants to
 * manage a list of observers and notify them of changes or events.
 *
 * This is part of the classic Observer Design Pattern:
 * - Observers subscribe to a Subject.
 * - The Subject broadcasts updates to all registered Observers.
 *
 * Example usage in this project:
 * - `GameController` implements Subject.
 * - `GuiViewer` implements Observer.
 */
public interface Subject {

    /**
     * Registers an observer to receive updates from this subject.
     *
     * @param o the observer to add
     */
    void registerObserver(Observer o);

    /**
     * Removes a previously registered observer.
     *
     * @param o the observer to remove
     */
    void removeObserver(Observer o);

    /**
     * Sends a message update to all registered observers.
     *
     * @param message the update message to send
     */
    void notifyObservers(String message);
}
