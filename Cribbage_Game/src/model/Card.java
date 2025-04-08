package model;

/**
 * The Card class represents a playing card with a rank and a suit.
 * It provides methods to access its properties and compute values needed for Cribbage scoring.
 */
public class Card {
    private String suit;
    private String rank;

    /**
     * Constructs a new Card with the specified rank and suit.
     *
     * @param rank the rank of the card (e.g., "A", "2", ..., "K")
     * @param suit the suit of the card (e.g., "Hearts", "Spades")
     */
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank as a String
     */
    public String getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit as a String
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Returns the cribbage value of the card.
     * For cribbage, Ace is 1; cards 2–10 are their face value; face cards count as 10.
     *
     * @return the cribbage value of the card
     */
    public int getCribbageValue() {
        switch(rank) {
            case "A": return 1;
            case "J":
            case "Q":
            case "K": return 10;
            default: return Integer.parseInt(rank);
        }
    }

    /**
     * Returns the numeric rank value for evaluating runs.
     * Ace is 1; 2–10 are their face values; Jack is 11; Queen is 12; King is 13.
     *
     * @return the numeric rank value
     */
    public int getRankValue() {
        switch(rank) {
            case "A": return 1;
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            default: return Integer.parseInt(rank);
        }
    }

    /**
     * Returns a string representation of the card.
     *
     * @return a string in the format "rank of suit"
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
