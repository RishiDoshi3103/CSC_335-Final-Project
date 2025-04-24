// src/model/Hand.java
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** A player's hand of up to 6 cards */
public class Hand {
    private final List<Card> cards = new ArrayList<>();

    /** Add one card (up to 6 max) */
    public void add(Card c) {
        Objects.requireNonNull(c);
        if (cards.size() >= 6) throw new IllegalStateException("Hand is full");
        cards.add(c);
    }

    /** Remove the given card */
    public boolean remove(Card c) {
        return cards.remove(Objects.requireNonNull(c));
    }

    /** Immutable view of current cards */
    public List<Card> getCards() {
        return Collections.unmodifiableList(new ArrayList<>(cards));
    }

    /** Clear all cards */
    public void clear() {
        cards.clear();
    }
}
