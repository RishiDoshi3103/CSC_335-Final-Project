package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The {@code Deck} class represents a standard deck of playing cards used in the game.
 * It supports operations such as shuffling and drawing cards.
 */
public class Deck {

    /** The list of cards in the deck. */
    private ArrayList<Card> cards;

    /**
     * Constructs a new deck containing all 52 standard playing cards,
     * one for each combination of suit and rank.
     */
    public Deck() {
        cards = new ArrayList<Card>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(Card.get(suit, rank));
            }
        }
    }

    /**
     * Randomly shuffles the order of cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draws (removes and returns) the top card from the deck.
     *
     * @return the card removed from the top of the deck
     */
    public Card draw() {
        return cards.remove(0);
    }

    /**
     * Returns the number of cards currently remaining in the deck.
     *
     * @return the size of the deck
     */
    public int size() {
        return cards.size();
    }
}
