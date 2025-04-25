package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Rank;
import model.Suit;
import strategy.HardStrategy;

class HardStrategyTest {
	
	HardStrategy strat = new HardStrategy();
	ArrayList<Card> hand;
	ArrayList<Card> sequence;
	
	@Test
	void testSequence0_1() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		assertEquals(strat.playIndex(hand, sequence, 0), 3);
	}
	
	@Test
	void testSequence0_2() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 0), 1);
	}
	
	@Test
	void testSequence0_3() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 0), 1);
	}
	
	@Test
	void testSequence0_4() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 0), 2);
	}
	
	@Test
	void testSequence0_5() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.SPADES, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 0), 2);
	}
	
	@Test
	void testSequence0_6() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		hand.add(Card.get(Suit.CLUBS, Rank.FIVE));
		hand.add(Card.get(Suit.DIAMONDS, Rank.FIVE));
		hand.add(Card.get(Suit.SPADES, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 0), 0);
	}
	
	@Test
	void testThirdMatch() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		
		sequence.add(Card.get(Suit.CLUBS, Rank.FIVE));
		sequence.add(Card.get(Suit.SPADES, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 10), 3);
	}
	
	@Test
	void testThirdMatch2() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.FIVE));
		
		sequence.add(Card.get(Suit.CLUBS, Rank.TEN));
		sequence.add(Card.get(Suit.SPADES, Rank.TEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		assertEquals(strat.playIndex(hand, sequence, 30), 0);
	}
	
	@Test
	void testLongSequenceAdd31() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.SIX));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.THREE));
		sequence.add(Card.get(Suit.CLUBS, Rank.THREE));
		sequence.add(Card.get(Suit.SPADES, Rank.FOUR));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 25), 3);
	}
	
	@Test
	void testLongSequenceAdd15() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.THREE));
		sequence.add(Card.get(Suit.CLUBS, Rank.THREE));
		sequence.add(Card.get(Suit.SPADES, Rank.FOUR));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 11), 1);
	}
	
	@Test
	void testLongSequence1() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.JACK));
		assertEquals(strat.playIndex(hand, sequence, 20), 0);
	}
	
	@Test
	void testLongSequence2() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.QUEEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.JACK));
		assertEquals(strat.playIndex(hand, sequence, 20), 2);
	}
	
	@Test
	void testLongSequence3() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.QUEEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.JACK));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		assertEquals(strat.playIndex(hand, sequence, 30), 2);
	}
	
	@Test
	void testLongSequence4() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.SIX));
		hand.add(Card.get(Suit.HEARTS, Rank.NINE));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.FOUR));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.FIVE));
		assertEquals(strat.playIndex(hand, sequence, 9), 2);
	}
	
	@Test
	void testPair() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		assertEquals(strat.playIndex(hand, sequence, 10), 2);
	}
	
	@Test
	void test2Sequence() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.NINE));
		assertEquals(strat.playIndex(hand, sequence, 9), 2);
	}
	
	@Test
	void test2Sequence2() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.EIGHT));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.NINE));
		assertEquals(strat.playIndex(hand, sequence, 9), 2);
	}
	
	@Test
	void testRandomCard() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.EIGHT));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		assertEquals(strat.playIndex(hand, sequence, 10), 0);
	}
	
	@Test
	void testRandomCardless31() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.EIGHT));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.EIGHT));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.NINE));
		assertEquals(strat.playIndex(hand, sequence, 28), 0);
	}
	
	@Test
	void testRandomCardAdds21() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.TEN));
		hand.add(Card.get(Suit.DIAMONDS, Rank.JACK));
		hand.add(Card.get(Suit.HEARTS, Rank.KING));
		hand.add(Card.get(Suit.HEARTS, Rank.QUEEN));
		
		sequence.add(Card.get(Suit.DIAMONDS, Rank.TEN));
		sequence.add(Card.get(Suit.DIAMONDS, Rank.ACE));
		assertEquals(strat.playIndex(hand, sequence, 11), 0);
	}
	
	
	

	@Test
	void testAddTo15() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		
		sequence.add(Card.get(Suit.CLUBS, Rank.FIVE));
		sequence.add(Card.get(Suit.SPADES, Rank.SEVEN));
		assertEquals(strat.playIndex(hand, sequence, 12), 2);
	}

}
