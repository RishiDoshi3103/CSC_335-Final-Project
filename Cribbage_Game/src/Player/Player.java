package Player;

import java.util.ArrayList;
import model.Card;
import model.Hand;

/**
 * The Player abstract class represents a general participant in a Cribbage game.
 * It defines shared attributes and behaviors for both human and computer players,
 * such as name, hand, score, and win/loss tracking.
 *
 * Subclasses must implement how the player plays a turn and discards cards.
 */
public abstract class Player {
    protected String name;
    protected int wins;
    protected int losses;
    protected int score;
    protected ArrayList<Card> hand;         // The player's current set of cards
    protected ArrayList<Card> playedCards;  // Cards played during the round

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
     * Returns a copy of the player's current hand.
     *
     * @return a copy of the player's hand of cards
     */
    public ArrayList<Card> getHand() {
        ArrayList<Card> cards = new ArrayList<Card>(hand);
        return cards;
    }

    /**
     * Adds a card to the player's list of played cards.
     *
     * @param card the card to add
     */
    public void addToPlayed(Card card) {
        this.playedCards.add(card);
    }

    /**
     * Returns a copy of the list of cards the player has played.
     *
     * @return a copy of the played cards list
     */
    public ArrayList<Card> getPlayed() {
        ArrayList<Card> cards = new ArrayList<Card>(playedCards);
        return cards;
    }

    /**
     * Adds a card to the player's hand.
     *
     * @param card the card to add to the hand
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Removes and returns a card from the player's hand at the given index.
     * This is typically used during the play phase.
     *
     * @param index the index of the card to remove
     * @return the card removed from the hand
     */
    public Card discard(int index) {
        return hand.remove(index);
    }

    /**
     * Adds points to the player's score.
     *
     * @param points the number of points to add
     */
    public void addPoints(int points) {
        score += points;
    }

    /**
     * Returns the current score of the player.
     *
     * @return the player's score
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
     * Clears all cards from the player's played cards list.
     */
    public void clearPlayed() {
        playedCards.clear();
    }

    /**
     * Abstract method that simulates the player's turn.
     * Must be implemented by subclasses.
     */
    public abstract void playTurn();

    /**
     * Abstract method that handles discarding cards.
     * Must be implemented by subclasses.
     */
    public abstract void discardCards();

    /**
     * Increments the player's win count.
     */
    public void recordWin() {
        wins++;
    }

    /**
     * Increments the player's loss count.
     */
    public void recordLoss() {
        losses++;
    }

    /**
     * Returns the number of games the player has won.
     *
     * @return the win count
     */
    public int getWins() {
        return wins;
    }

    /**
     * Returns the number of games the player has lost.
     *
     * @return the loss count
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Resets the player's score to zero.
     * Typically used at the start of a new game.
     */
    public void resetScore() {
        this.score = 0;
    }

    /**
     * Returns a string representation of the player, including name and win/loss record.
     *
     * @return a string describing the player
     */
    @Override
    public String toString() {
        return "Player: " + name + " | Wins: " + wins + " | Losses: " + losses;
    }
}
