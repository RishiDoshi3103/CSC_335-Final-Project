package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Rank;
import model.Suit;
import strategy.HardDiscard;

class HardDiscardTest {
	
	HardDiscard discardStrat = new HardDiscard();

	@Test
	void testDealer1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}

	@Test
	void testDealer2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.SEVEN));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		assertEquals(discardStrat.selectDiscard(hand, true), 1);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testDealer5MatchAdd15() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.SEVEN));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		assertEquals(discardStrat.selectDiscard(hand, true), 1);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testDealer5MatchDisc5() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testDealer5MatchDisc2Match() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.FOUR));
		assertEquals(discardStrat.selectDiscard(hand, true), 3);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testDealer5MatchDisc2Run() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.SIX));
		hand.add(Card.get(Suit.CLUBS, Rank.SEVEN));
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testDealer5DiscardCareful() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		hand.add(Card.get(Suit.CLUBS, Rank.SEVEN));
		assertEquals(discardStrat.selectDiscard(hand, true), 2);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testDealerMatchingLessThree() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.CLUBS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		hand.add(Card.get(Suit.CLUBS, Rank.SEVEN));
		assertEquals(discardStrat.selectDiscard(hand, true), 1);
		assertEquals(discardStrat.selectDiscard(hand, true), 0);
	}
	
	@Test
	void testDealerMatchingGreaterThree() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.CLUBS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		hand.add(Card.get(Suit.CLUBS, Rank.SEVEN));
		assertEquals(discardStrat.selectDiscard(hand, true), 0);
		assertEquals(discardStrat.selectDiscard(hand, true), 0);
	}
	
	@Test
	void testDealerAdd15() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.CLUBS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.EIGHT));
		hand.add(Card.get(Suit.CLUBS, Rank.SEVEN));
		assertEquals(discardStrat.selectDiscard(hand, true), 5);
		assertEquals(discardStrat.selectDiscard(hand, true), 4);
	}
	
	@Test
	void testNotDealer4SameSuit() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		assertEquals(discardStrat.selectDiscard(hand, false), 4);
		assertEquals(discardStrat.selectDiscard(hand, false), 4);
	}
	
	@Test
	void testNotDealer5SameSuit1King() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		hand.add(Card.get(Suit.HEARTS, Rank.KING));
		assertEquals(discardStrat.selectDiscard(hand, false), 5);
		assertEquals(discardStrat.selectDiscard(hand, false), 4);
	}
	
	@Test
	void testNotDealer5SameSuit2King() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.KING));
		hand.add(Card.get(Suit.HEARTS, Rank.KING));
		assertEquals(discardStrat.selectDiscard(hand, false), 2);
		assertEquals(discardStrat.selectDiscard(hand, false), 3);
	}
	
	@Test
	void testNotDealer5SameSuit2King2Three() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		assertEquals(discardStrat.selectDiscard(hand, false), 5);
		assertEquals(discardStrat.selectDiscard(hand, false), 4);
	}
	
	@Test
	void testNotDealer5SameSuitEdge1() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
		assertEquals(discardStrat.selectDiscard(hand, false), 3);
	}
	
	@Test
	void testNotDealer5NotSameSuit() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
		assertEquals(discardStrat.selectDiscard(hand, false), 3);
	}
	
	@Test
	void testNotDealerDontDiscardPair() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.KING));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
	}
	
	@Test
	void testNotDealerDontDiscardPair2() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.DIAMONDS, Rank.KING));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		assertEquals(discardStrat.selectDiscard(hand, false), 5);
		assertEquals(discardStrat.selectDiscard(hand, false), 1);
	}
	
	@Test
	void testNotDealerNot5NotPairNot15() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.SEVEN));
		hand.add(Card.get(Suit.DIAMONDS, Rank.EIGHT));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.CLUBS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
		assertEquals(discardStrat.selectDiscard(hand, false), 4);
	}
	
	@Test
	void testNotDealerNot5Not15() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.SEVEN));
		hand.add(Card.get(Suit.DIAMONDS, Rank.SEVEN));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.SEVEN));
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.SPADES, Rank.SEVEN));
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
		assertEquals(discardStrat.selectDiscard(hand, false), 4);
	}
	
	@Test
	void testNotDealerNot5Not152() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.QUEEN));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.SPADES, Rank.FIVE));
		assertEquals(discardStrat.selectDiscard(hand, false), 2);
		assertEquals(discardStrat.selectDiscard(hand, false), 3);
	}
	
	@Test
	void testNotDealerNot15() {
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.EIGHT));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.SEVEN));
		hand.add(Card.get(Suit.SPADES, Rank.FIVE));
		assertEquals(discardStrat.selectDiscard(hand, false), 0);
		assertEquals(discardStrat.selectDiscard(hand, false), 3);
	}
}
