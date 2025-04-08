package model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Hand class represents a collection of cards held by a player.
 * It provides methods for adding, removing, and retrieving cards.
 */
public class Hand {
    private List<Card> cards;

    /**
     * Constructs an empty hand.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Adds a card to the hand.
     *
     * @param card the Card to add
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Returns the list of cards in the hand.
     *
     * @return a List of Card objects
     */
    public List<Card> getCards() {
        return cards;
    }
}
