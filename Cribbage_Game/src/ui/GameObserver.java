package ui;

/**
 * The GameObserver interface is used for classes that want to listen
 * to updates or messages from the game logic.
 *
 * This is typically used in GUI components to reflect changes in the game's state,
 * such as moves, score updates, or phase transitions.
 *
 * It serves a similar purpose to the traditional Observer interface,
 * but can be tailored for more specific game-related messaging if needed.
 */
public interface GameObserver {

    /**
     * Called when there is an update from the game.
     *
     * @param message A string describing the latest game event or change
     */
    void onGameUpdate(String message);
}
