package observer;

/**
 * The Observer interface defines the contract for any class
 * that wants to be notified of changes or events in a Subject.
 *
 * Implementing classes must define how to handle update messages,
 * typically used to reflect changes in the UI or logging.
 *
 * This is part of the classic Observer Design Pattern,
 * where observers "subscribe" to subjects and are updated
 * whenever the subject's state changes.
 */
public interface Observer {

    /**
     * Receives an update message from the subject.
     *
     * @param message A string representing the latest game update or event
     */
    void update(String message);
}
