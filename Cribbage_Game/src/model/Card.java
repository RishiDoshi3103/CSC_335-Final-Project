package model;

public class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank.getLabel() + suit.getSymbol();
    }

    public static Card fromString(String text) {
        if (text.length() < 2) return null;

        String rankLabel = text.substring(0, text.length() - 1);
        String suitSymbol = text.substring(text.length() - 1);

        Rank rank = null;
        Suit suit = null;

        for (Rank r : Rank.values()) {
            if (r.getLabel().equals(rankLabel)) {
                rank = r;
                break;
            }
        }

        for (Suit s : Suit.values()) {
            if (s.getSymbol().equals(suitSymbol)) {
                suit = s;
                break;
            }
        }

        if (rank != null && suit != null) {
            return new Card(rank, suit);
        }

        return null;
    }

}
