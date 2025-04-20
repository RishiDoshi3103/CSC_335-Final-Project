package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Hand {
    private final List<Card> cards = new ArrayList<>();
    private static final int MAX = 6;

    public void add(Card card) {
        Objects.requireNonNull(card);
        if (cards.size() >= MAX) throw new IllegalStateException("Hand full");
        cards.add(card);
    }

    public boolean remove(Card card) {
        Objects.requireNonNull(card);
        return cards.remove(card);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(new ArrayList<>(cards));
    }

    public void clear() {
        cards.clear();
    }
}