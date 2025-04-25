package Player;

/**
 * The HumanPlayer class represents a player controlled by a human.
 * It extends the abstract Player class and implements the methods for playing a turn
 * and discarding cards. In a GUI application, these methods would be connected to user inputs.
 */
public class HumanPlayer extends Player {

    /**
     * Constructs a new HumanPlayer with the specified name.
     *
     * @param name the name of the human player
     */
    public HumanPlayer(String name) {
        super(name);
    }
}
