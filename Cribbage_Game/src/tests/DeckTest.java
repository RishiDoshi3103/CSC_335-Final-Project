package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Deck;
import model.Rank;
import model.Suit;

class DeckTest {
	
	@Test
	void DeckTest() {
		Deck deck = new Deck();
		assertEquals(deck.size(), 52);
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		hand.add(deck.draw());
		assertEquals(hand.get(0), Card.get(Suit.HEARTS, Rank.ACE));
		assertEquals(deck.size(), 51);
	}

}
