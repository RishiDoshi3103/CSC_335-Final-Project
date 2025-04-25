package Player;

import java.util.ArrayList;

import model.Card;
import strategy.DiscardStrategy;
import strategy.EasyDiscard;
import strategy.EasyStrategy;
import strategy.HardDiscard;
import strategy.HardStrategy;
import strategy.Strategy;

/**
 * The ComputerPlayer class represents a player controlled by AI.
 * It extends the Player class and uses predefined strategies for 
 * discarding and playing cards.
 *
 * The strategy can be either "easy" or "hard" and determines how the
 * computer makes decisions during the game.
 */
public class ComputerPlayer extends Player {
    private DiscardStrategy discardStrategy;
    private Strategy strategy;

    /**
     * Constructs a new ComputerPlayer with the given name and strategy type.
     * Strategy type 1 is easy, anything else is treated as hard.
     *
     * @param name the name of the computer player
     * @param strategy the difficulty level (1 = easy, 2 = hard)
     */
    public ComputerPlayer(String name, int strategy) {
        super(name);
        if (strategy == 1) {
            System.out.println("Initializing easy strategy");
            this.discardStrategy = new EasyDiscard();
            this.strategy = new EasyStrategy();
        } else {
            System.out.println("Initializing hard strategy");
            this.discardStrategy = new HardDiscard();
            this.strategy = new HardStrategy();
        }
    }

    /**
     * Simulates the computer player's turn.
     * This method can be expanded to include full AI decision logic.
     */
    @Override
    public void playTurn() {
        System.out.println(name + " (Computer) is taking its turn.");
    }

    /**
     * Uses the assigned strategy to determine which card to play from the hand.
     *
     * @param sequence the sequence of cards already played
     * @param total the current point total in the play phase
     * @return the 1-based index of the card to play
     */
    public int getPlayIndex(ArrayList<Card> sequence, int total) {
        return strategy.playIndex(this.hand, sequence, total) + 1;
    }

    /**
     * Uses the assigned discard strategy to select cards to discard from the hand.
     * This method is used during the discard phase.
     */
    @Override
    public void discardCards() {
        System.out.println(name + " (Computer) is discarding cards using its strategy.");
        if (discardStrategy != null) {
            discardStrategy.selectDiscard(hand, true);
        }
    }

    /**
     * Selects a card index to discard using the current discard strategy.
     *
     * @param dealer true if the computer is the dealer
     * @return the 1-based index of the card to discard
     */
    public int discardIndex(boolean dealer) {
        System.out.println(name + " (Computer) is discarding cards using its strategy.");
        return discardStrategy.selectDiscard(hand, dealer) + 1;
    }

    /**
     * Sets a new discard strategy for the computer player.
     *
     * @param discardStrategy the discard strategy to set
     */
    public void setDiscardStrategy(DiscardStrategy discardStrategy) {
        this.discardStrategy = discardStrategy;
    }
}
