package observer;

/**
 * The Observer interface should be implemented by any class that wishes to
 * receive updates from a subject it is observing.
 *
 * It is used as part of the Observer design pattern, where the subject
 * notifies all registered observers of changes or events by calling update().
 */
public interface Observer {

    /**
     * Called by the subject to notify the observer of an event or update.
     *
     * @param message the message sent from the subject
     */
    void update(String message);
}
