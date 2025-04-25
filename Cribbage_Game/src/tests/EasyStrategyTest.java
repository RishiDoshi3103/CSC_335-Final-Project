package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Rank;
import model.Suit;
import strategy.EasyStrategy;

class EasyStrategyTest {
	
	EasyStrategy strat = new EasyStrategy();
	ArrayList<Card> hand;
	ArrayList<Card> sequence;
	
	@Test
	void testSequence1() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		assertEquals(strat.playIndex(hand, sequence, 0), 0);
	}
	
	@Test
	void testSequence2() {
		hand = new ArrayList<Card>();
		sequence = new ArrayList<Card>();
		hand.add(Card.get(Suit.HEARTS, Rank.FOUR));
		hand.add(Card.get(Suit.HEARTS, Rank.TWO));
		hand.add(Card.get(Suit.HEARTS, Rank.THREE));
		hand.add(Card.get(Suit.HEARTS, Rank.ACE));
		
		sequence.add(Card.get(Suit.HEARTS, Rank.TEN));
		sequence.add(Card.get(Suit.HEARTS, Rank.JACK));
		sequence.add(Card.get(Suit.HEARTS, Rank.KING));
		assertEquals(strat.playIndex(hand, sequence, 30), 3);
	}
}
