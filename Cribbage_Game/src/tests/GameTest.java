package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Game;
import model.Rank;
import model.Suit;

class GameTest {

	@Test
	void testGameCheckRun() {
		
		ArrayList<testCard> cards = new ArrayList<testCard>();
		cards.add(new testCard(Suit.CLUBS, Rank.ACE));
		cards.add(new testCard(Suit.DIAMONDS, Rank.JACK));
		cards.add(new testCard(Suit.DIAMONDS, Rank.FOUR));
		cards.add(new testCard(Suit.HEARTS, Rank.TWO));
		cards.add(new testCard(Suit.SPADES, Rank.THREE));
		
		
		System.out.println("Pre: " + cards.toString());
		
		assertEquals(testCheckRun(cards), 3);
		
		System.out.println("Post: " + cards.toString());
		assertEquals(cards.get(0).getRank(), Rank.ACE);
		assertEquals(cards.get(1).getRank(), Rank.JACK);
		assertEquals(cards.get(2).getRank(), Rank.FOUR);
		assertEquals(cards.get(3).getRank(), Rank.TWO);
		assertEquals(cards.get(4).getRank(), Rank.THREE);
	}
	
	public int testCheckRun(ArrayList<testCard> cards) {
		if (cards.size() < 3) {
			return 0;
		}
		
		int maxRun = 0;
		for (int i = cards.size()-3; i >= 0; i--) {
			boolean valid = true;
			ArrayList<testCard> check = new ArrayList<testCard>(cards.subList(i, cards.size()));
			check.sort(Comparator.comparingInt(card -> card.getRank().ordinal()));
			for (int j = 0; j < check.size()-1; j++) {
				if (check.get(j+1).getRank().ordinal() != check.get(j).getRank().ordinal() + 1) {
					valid = false;
					break;
				}
			}
			if (valid) {
				maxRun = cards.size() - i;
			}
		}
		
		return maxRun;
	}
	
	public class testCard {
		private final Suit suit;
		private final Rank rank;
		
		public testCard(Suit suit, Rank rank) {
			this.suit = suit;
			this.rank = rank;
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
	}
}
