package model;

import java.util.Comparator;
import java.util.HashMap;

/**
 * The {@code Card} class represents a standard playing card with a {@link Rank} and {@link Suit}.
 * It uses a Flyweight-like pattern to reuse card instances from a shared pool for memory efficiency.
 * It also implements {@link Comparator} for use in custom sorting, though currently the comparator logic is basic.
 */
public class Card implements Comparator {

    /** The suit of this card (e.g., ♠, ♥, ♦, ♣). */
    private final Suit suit;

    /** The rank of this card (e.g., Ace, 2, ..., King). */
    private final Rank rank;

    /** A shared pool of all possible card combinations for reuse. */
    private static final HashMap<String, Card> pool = new HashMap<>();

    /**
     * Private constructor to prevent direct instantiation. Use {@link #get(Suit, Rank)} to access cards.
     *
     * @param suit the suit of the card
     * @param rank the rank of the card
     */
    private Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    // Static block initializes the card pool with all 52 unique cards
    static {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                String key = suit + ":" + rank;
                if (!pool.containsKey(key)) {
                    pool.put(key, new Card(suit, rank));
                }
            }
        }
    }

    /**
     * Returns a card instance from the pool for the specified suit and rank.
     * This ensures all cards are reused from a single shared set.
     *
     * @param suit the suit of the desired card
     * @param rank the rank of the desired card
     * @return the corresponding {@code Card} from the pool
     * @throws AssertionError if suit or rank is null
     */
    public static Card get(Suit suit, Rank rank) {
        assert suit != null && rank != null;
        return pool.get(suit + ":" + rank);
    }

    /**
     * Returns the suit of this card.
     *
     * @return the {@code Suit} of the card
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the rank of this card.
     *
     * @return the {@code Rank} of the card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns a string representation of the card combining rank and suit symbol.
     * E.g., "A♠" or "10♦".
     *
     * @return the string representation of the card
     */
    @Override
    public String toString() {
        String symbol;
        if (suit.equals(Suit.SPADES)) {
            symbol = "\u2660";
        } else if (suit.equals(Suit.HEARTS)) {
            symbol = "\u2665";
        } else if (suit.equals(Suit.DIAMONDS)) {
            symbol = "\u2666";
        } else {
            symbol = "\u2663";
        }
        return rank + symbol;
    }

    /**
     * Compares two {@code Card} objects based on their rank.
     * Currently, this method only returns 0 if ranks are equal and 0 otherwise.
     * This may be used for grouping or matching, but is not suitable for sorting.
     *
     * @param o1 the first object to compare
     * @param o2 the second object to compare
     * @return 0 if both cards have equal rank, otherwise 0 (default)
     */
    @Override
    public int compare(Object o1, Object o2) {
        if (o1 == o2) {
            return 0;
        }
        if (o1 instanceof Card) {
            if (o2 instanceof Card) {
                if (((Card) o1).getRank().equals(((Card) o2).getRank())) {
                    return 0;
                }
            }
        }
        return 0;
    }
}
