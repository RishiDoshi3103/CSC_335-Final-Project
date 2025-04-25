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
 * The ComputerPlayer class represents an AI-controlled player.
 * It extends the abstract Player class and uses a DiscardStrategy to decide which cards to discard.
 * This class can be used for different levels of AI by changing its discard strategy.
 */
public class ComputerPlayer extends Player {
    private DiscardStrategy discardStrategy;
    private Strategy strategy;

    /**
     * Constructs a new ComputerPlayer with the specified name and discard strategy.
     *
     * @param name the name of the computer player
     * @param discardStrategy the strategy to use for selecting discards
     */
    public ComputerPlayer(String name, int strategy) {
        super(name);
        if (strategy == 1) {
        	System.out.println("Initializing easy strategy");
        	this.discardStrategy = new EasyDiscard();
        	this.strategy = new EasyStrategy();
        }
        else {
        	System.out.println("Initializing hard strategy");
        	this.discardStrategy = new HardDiscard();
        	this.strategy = new HardStrategy();
        }
        
    }

    
    public int getPlayIndex(ArrayList<Card> sequence, int total) {
    	return strategy.playIndex(this.hand, sequence, total) + 1;
    }

    
    /*
     * Discard a card from hand based on discardStrategy
     * Return:	the index of the card to discard.
     */
    public int discardIndex(boolean dealer) {
        System.out.println(name + " (Computer) is discarding cards using its strategy.");
        return discardStrategy.selectDiscard(hand, dealer) + 1;
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
