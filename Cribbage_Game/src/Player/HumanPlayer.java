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

    /**
     * Simulates the human player's turn.
     * In a GUI-based application, this method would trigger actions based on user interaction.
     */
    @Override
    public void playTurn() {
        System.out.println(name + " is taking their turn.");
        // In a complete implementation, you would integrate GUI event handling here.
    }

    /**
     * Simulates the human player's discard action.
     * In a GUI-based application, this method would allow the user to select cards to discard.
     */
    @Override
    public void discardCards() {
        System.out.println(name + " is discarding cards.");
        // In a complete implementation, this would involve user input from the GUI.
    }
}
