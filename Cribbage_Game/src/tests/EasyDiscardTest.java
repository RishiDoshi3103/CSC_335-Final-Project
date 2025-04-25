package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Rank;
import model.Suit;
import strategy.EasyDiscard;

class EasyDiscardTest {
	
	EasyDiscard discardStrat = new EasyDiscard();

	@Test
	void testDealer1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		assertEquals(discardStrat.selectDiscard(hand, true), 0);
		assertEquals(discardStrat.selectDiscard(hand, true), 0);
	}

}
