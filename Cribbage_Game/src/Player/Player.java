package Player;

import java.util.ArrayList;

import model.Card;
import model.Hand;

/**
 * The Player abstract class represents any participant in the Cribbage game.
 * It contains common attributes like the player's name, win/loss records, and a hand of cards.
 * It also defines abstract methods for playing a turn and discarding cards.
 */
public abstract class Player {
    protected String name;
    protected int wins;
    protected int losses;
    protected int score;
    protected ArrayList<Card> hand;  // The player's current set of cards
    protected ArrayList<Card> playedCards;

    /**
     * Constructs a new Player with the specified name.
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.wins = 0;
        this.losses = 0;
        this.score = 0;
        this.hand = new ArrayList<Card>();
        this.playedCards = new ArrayList<Card>();
    }

    /**
     * Returns the player's name.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the player's hand of cards (copy).
     *
     * @return the Hand object representing the player's cards
     */
    public ArrayList<Card> getHand() {
    	ArrayList<Card> cards = new ArrayList<Card>(hand);
        return cards;
    }
    
    /**
     * Adds a card to the player's list of played cards
     * 
     * @param card
     */
    public void addToPlayed(Card card) {
    	this.playedCards.add(card);
    }

    /**
     * Returns list of cards played by player
     * 
     * @return playedCards copy
     */
    public ArrayList<Card> getPlayed() {
    	ArrayList<Card> cards = new ArrayList<Card>(playedCards);
    	return cards;
    }
    /**
     * Adds a card to the player's hand.
     *
     * @param card the Card to add
     */
    public void addCard(Card card) {
        hand.add(card);
    }
    
    /**
     * Removes a card from player's hand.
     * Also used to 'play' a card, during
     * play phase.
     * 
     * @param  index
     * @return discarded card
     */
    public Card discard(int index) {
    	return hand.remove(index);
    }
    
    /**
     * Adds points to score.
     * 
     * @param points
     */
    public void addPoints(int points) {
    	score += points;
    }
    
    /**
     * Returns player's score 
     * 
     * @return score
     */
    public int getScore() {
    	return score;
    }
    
   
    /**
     * Clears all cards from the player's hand.
     */
    public void clearHand() {
        hand.clear();
    }
    
    /**
     * Clears all cards played by player - resets.
     */
    public void clearPlayed() {
    	playedCards.clear();
    }

    /**
     * Simulates the player's turn.
     * Concrete subclasses must provide an implementation.
     */
    public abstract void playTurn();

    /**
     * Handles the player's discard phase.
     * Concrete subclasses must provide an implementation.
     */
    public abstract void discardCards();

    /**
     * Records a win for the player.
     */
    public void recordWin() {
        wins++;
    }

    /**
     * Records a loss for the player.
     */
    public void recordLoss() {
        losses++;
    }

    /**
     * Returns the number of wins.
     *
     * @return the win count
     */
    public int getWins() {
        return wins;
    }

    /**
     * Returns the number of losses.
     *
     * @return the loss count
     */
    public int getLosses() {
        return losses;
    }
    
    /**
     * Resets score between games
     */
    public void resetScore() {
    	this.score = 0; 
    }
    
    /**
     * Returns a string representation of the player, including their name and win/loss record.
     *
     * @return a string representing the player
     */
    @Override
    public String toString() {
        return "Player: " + name + " | Wins: " + wins + " | Losses: " + losses;
    }
}
