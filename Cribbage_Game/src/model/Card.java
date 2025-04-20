package model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * Flyweight Card: only 52 instances ever created.
 */
public final class Card {
    public enum Suit  { HEARTS, DIAMONDS, CLUBS, SPADES }
    public enum Rank {
        A(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
        EIGHT(8), NINE(9), TEN(10), J(10), Q(10), K(10);
        private final int value;
        Rank(int v) { value = v; }
        public int getValue() { return value; }
    }

    private static final Map<Suit, Map<Rank, Card>> POOL = new EnumMap<>(Suit.class);
    static {
        for (Suit s : Suit.values()) {
            Map<Rank, Card> m = new EnumMap<>(Rank.class);
            for (Rank r : Rank.values()) {
                m.put(r, new Card(r, s));
            }
            POOL.put(s, m);
        }
    }

    private final Rank rank;
    private final Suit suit;
    private Card(Rank rank, Suit suit) {
        this.rank = Objects.requireNonNull(rank);
        this.suit = Objects.requireNonNull(suit);
    }

    public static Card of(Rank rank, Suit suit) {
        return POOL.get(suit).get(rank);
    }
    public Rank getRank()      { return rank; }
    public Suit getSuit()      { return suit; }
    public int  getCribValue() { return rank.getValue(); }

    @Override public String toString() {
        return rank + "_of_" + suit;
    }
    @Override public boolean equals(Object o) {
        return this == o;
    }
    @Override public int hashCode() {
        return System.identityHashCode(this);
    }
}