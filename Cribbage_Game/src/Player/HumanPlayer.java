package Player;

/**
 * The HumanPlayer class represents a player controlled by a human user.
 * This class extends the abstract Player class and defines behavior for
 * human-based interaction during gameplay.
 *
 * In a GUI-based implementation, these methods would be tied to user input.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a new HumanPlayer with the given name.
     *
     * @param name the name of the human player
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Represents the human player taking a turn.
     * This method is expected to be triggered by GUI events.
     */
    @Override
    public void playTurn() {
        System.out.println(name + " is taking their turn.");
        // In a complete implementation, you would integrate GUI event handling here.
    }

    /**
     * Represents the discard phase for a human player.
     * Typically connected to user input in a graphical interface.
     */
    @Override
    public void discardCards() {
        System.out.println(name + " is discarding cards.");
        // In a complete implementation, this would involve user input from the GUI.
    }
}
