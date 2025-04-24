package model;

import java.util.Comparator;
import java.util.HashMap;

/**
 * The Card class represents a playing card with a rank and a suit.
 * It provides methods to access its properties and compute values needed for Cribbage scoring.
 */
public class Card implements Comparator {
	private final Suit suit;
	private final Rank rank;
	private static final HashMap<String, Card> pool = new HashMap<>();
	
	private Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	static {
		for (Suit suit : Suit.values() ) {
			for (Rank rank : Rank.values() ) {
				String key = suit + ":" + rank;
				if (!pool.containsKey(key)) {
					pool.put(key, new Card(suit, rank));
				}
			}
		}
	}
	
	public static Card get(Suit suit, Rank rank) {
		assert suit != null && rank != null;
		return pool.get(suit+":"+rank);
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public Rank getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		String symbol;
		if (suit.equals(Suit.SPADES)) {
			symbol = "\u2660";
		}
		else if (suit.equals(Suit.HEARTS)) {
			symbol = "\u2665";
		}
		else if (suit.equals(Suit.DIAMONDS)) {
			symbol = "\u2666";
		}
		else {
			symbol = "\u2663";
		}
		return rank+symbol;
	}

	// This always returns 0 - We need to review
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

