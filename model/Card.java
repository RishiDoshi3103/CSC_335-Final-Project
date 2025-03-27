package model;

/**
 * class Card represents one of the 52 poker cards. There are no comments before
 * methods because the method name says it all.
 * 
 * @author Kyle Velasco
 */

public class Card implements Comparable<Card> {
	private final Rank rank;
	private final Suit suit;

	// Constructor
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public Rank getRank() {
		return this.rank;
	}

	public int getValue() {
//    return Integer.MAX_VALUE;
		return rank.getValue();
	}

	public String toString() {
		// Use these four Unicode icons for the solid suit icons.
//		char suitIcon = '\u2663';
//		if (suit == Suit.DIAMONDS)
//			suitIcon = '\u2666';
//		if (suit == Suit.HEARTS)
//			suitIcon = '\u2665';
//		if (suit == Suit.SPADES)
//			suitIcon = '\u2660';

		char suitIcon = 'C';
		if (suit == Suit.DIAMONDS)
			suitIcon = 'D';
		if (suit == Suit.HEARTS)
			suitIcon = 'H';
		if (suit == Suit.SPADES)
			suitIcon = 'S';

		return String.valueOf(rank.getValue()) + suitIcon;

//		♥♦♠♣
//		char suitIcon = '♣';
//		if (suit == Suit.DIAMONDS)
//			suitIcon = '♦';
//		if (suit == Suit.HEARTS)
//			suitIcon = '♥';
//		if (suit == Suit.SPADES)
//			suitIcon = '♠';

		// Need to get the value instead of "?"
//		return String.valueOf(rank.getValue()) + suitIcon;

//		String rankString="";
//		if(rank.getValue()>10) {
//			if(rank.getValue()==11) {
//				rankString="J";
//			}
//			if(rank.getValue()==12) {
//				rankString="Q";
//			}
//			if(rank.getValue()==13) {
//				rankString="K";
//			}
//			if(rank.getValue()==14) {
//				rankString="A";
//			}
//		}
//		else{
//			rankString=String.valueOf(rank.getValue());
//		}
//		return rankString + suitIcon;
	}

	/*
	 * parameter takes a Card type and compares it to the other card
	 * returns: 0 if equal, negative int if thisCard is lower, positive
	 * 			int if this card is greater 
	 */
	@Override
	public int compareTo(Card other) {
		int thisCard = (int) this.getValue();
		int otherCard = (int) other.getValue();
		return thisCard - otherCard;
	}

//	public boolean equals(Card other) {
//		return compareTo(other) == 0;
//	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Card) {
			Card arg = (Card) other;
			return this.rank.equals(arg.rank) && this.suit.equals(arg.suit);
		}
		return false;
	}

}