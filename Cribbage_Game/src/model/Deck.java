package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {
    private final List<Card> cards = new ArrayList<>();
    private final Random rnd = new Random();

    public Deck() {
        for (Card.Suit s : Card.Suit.values()) {
            for (Card.Rank r : Card.Rank.values()) {
                cards.add(Card.of(r, s));
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards, rnd);
    }

    public Card draw() {
        if (cards.isEmpty()) throw new IllegalStateException("Deck empty");
        return cards.remove(cards.size() - 1);
    }

    public int size() {
        return cards.size();
    }
}