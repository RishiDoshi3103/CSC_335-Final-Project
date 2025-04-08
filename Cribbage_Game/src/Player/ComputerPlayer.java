package Player;

import strategy.DiscardStrategy;

/**
 * The ComputerPlayer class represents an AI-controlled player.
 * It extends the abstract Player class and uses a DiscardStrategy to decide which cards to discard.
 * This class can be used for different levels of AI by changing its discard strategy.
 */
public class ComputerPlayer extends Player {
    private DiscardStrategy discardStrategy;

    /**
     * Constructs a new ComputerPlayer with the specified name and discard strategy.
     *
     * @param name the name of the computer player
     * @param discardStrategy the strategy to use for selecting discards
     */
    public ComputerPlayer(String name, DiscardStrategy discardStrategy) {
        super(name);
        this.discardStrategy = discardStrategy;
    }

    /**
     * Simulates the computer player's turn.
     * In a full implementation, this method would include AI logic to make game decisions.
     */
    @Override
    public void playTurn() {
        System.out.println(name + " (Computer) is taking its turn.");
        // Implement computer-specific turn logic here.
    }

    /**
     * Uses the assigned discard strategy to select cards to discard from the computer player's hand.
     */
    @Override
    public void discardCards() {
        System.out.println(name + " (Computer) is discarding cards using its strategy.");
        if (discardStrategy != null) {
            discardStrategy.selectDiscard(hand);
        }
    }

    /**
     * Sets a new discard strategy for the computer player.
     *
     * @param discardStrategy the new DiscardStrategy to use
     */
    public void setDiscardStrategy(DiscardStrategy discardStrategy) {
        this.discardStrategy = discardStrategy;
    }
}
