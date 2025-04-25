package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import controller.GameController;
import model.Card;
import model.Game;
import model.Rank;
import model.Suit;
import ui.TextViewer;

class GameTest {
	@Test
	void testBaseGameStates() {
		Game model = new Game("test1", "test2", 0);
		
		model.startRound();
		assertEquals(model.getDealer(), model.getPlayer1());
		assertEquals(model.showCrib().size(), 0);
		assertEquals(model.getPlayer1().getHand().size(), 6);
		assertEquals(model.getPlayer2().getHand().size(), 6);

		model.drawStarter();
		assertNotEquals(model.getStarter(), null);
		
		// By design, must run in this sequence - consider combining
		// functionality.
		Card seqCard = model.getPlayer1().discard(0);
		model.addToSequence(seqCard);
		model.addToTotal(seqCard.getRank().getValue());
		
		model.cribCard(model.getPlayer2().discard(0));
		
		assertEquals(model.getPlayer1().getHand().size(), 5);
		assertEquals(model.getPlayer2().getHand().size(), 5);
		
		assertEquals(model.showCrib().size(), 1);
		assertTrue(model.pointTotal() > 0);
		
		model.resetSequence();
		model.resetTotal();
		assertEquals(model.pointTotal(), 0);
		assertEquals(model.gameOver(), false);
		
		model.getPlayer2().addPoints(61);
		assertEquals(model.gameOver(), true);
		
		model.getPlayer2().resetScore();
		model.getPlayer1().addPoints(60);
		assertEquals(model.gameOver(), false);
		
		model.getPlayer1().addPoints(2);
		assertEquals(model.gameOver(), true);
		
		model.getPlayer2().addPoints(61);
		assertEquals(model.gameOver(), true);

		model.startRound();
		assertEquals(model.getDealer(), model.getPlayer2());
	}
	
	@Test
	void testGameSequenceScoring() {
		Game model = new Game("test1", "test2", 0);
		
		assertEquals(model.checkPairs(), 0);
		assertEquals(model.checkRuns(), 0);
		
		Card card1 = Card.get(Suit.DIAMONDS, Rank.JACK);
		Card card2 = Card.get(Suit.CLUBS, Rank.JACK);
		model.addToSequence(card1);	
		model.addToSequence(card2);	
		
		assertEquals(model.checkPairs(), 2);
		assertEquals(model.checkRuns(), 0);
		
		Card card3 = Card.get(Suit.HEARTS, Rank.JACK);
		Card card4 = Card.get(Suit.SPADES, Rank.JACK);
		
		model.addToSequence(card3);
		assertEquals(model.checkPairs(), 6);
		
		model.addToSequence(card4);
		assertEquals(model.checkPairs(), 12);
		assertEquals(model.checkRuns(), 0);
		
		assertEquals(model.getSequence().get(0), card1);
		
		model.resetSequence();
		
		Card card5 = Card.get(Suit.DIAMONDS, Rank.TEN);
		Card card6 = Card.get(Suit.SPADES, Rank.QUEEN);
		Card card7 = Card.get(Suit.DIAMONDS, Rank.KING);
		
		model.addToSequence(card3);
		model.addToSequence(card5);
		model.addToSequence(card7);
		
		assertEquals(model.checkPairs(), 0);
		assertEquals(model.checkRuns(), 0);
		
		model.addToSequence(card6);
		assertEquals(model.checkRuns(), 4);
	}
	
	@Test
	void testGameHandChecking() {
		Game model = new Game("test1", "test2", 0);
		
		model.startRound();
		
		// Fresh Instance - should always be able to play
		assertTrue(model.handCheck(model.getPlayer1()));
		
		model.addToTotal(31);
		assertFalse(model.handCheck(model.getPlayer1()));
		assertEquals(model.getTotal(), 31);

	}
	
	@Test
	void testDefaultGameHandScoring() {
		Game model = new Game("test1", "test2", 0);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		assertEquals(model.score15(cards), 0);
		assertEquals(model.scorePairs(cards), 0);
		assertEquals(model.scoreRuns(cards), 0);
		assertEquals(model.scoreFlush(cards, null, false), 0);
		assertEquals(model.scoreNob(cards, null), 0);
		
		Card card1 = Card.get(Suit.DIAMONDS, Rank.JACK);
		Card card2 = Card.get(Suit.CLUBS, Rank.TWO);
		Card card3 = Card.get(Suit.CLUBS, Rank.TWO);
		/**
		 * Game model = new Game("test1", "test2");
		
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = Card.get(Suit.DIAMONDS, Rank.JACK);
		Card card2 = Card.get(Suit.CLUBS, Rank.TWO);
		Card card3 = Card.get(Suit.HEARTS, Rank.THREE);
		Card card4 = Card.get(Suit.DIAMONDS, Rank.FIVE);
		Card card5 = Card.get(Suit.SPADES, Rank.FIVE);
		
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);
		 */

	}
	
	@Test
	void testGameScore15AndHand() {
		Game model = new Game("test1", "test2",0 );
		
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = Card.get(Suit.DIAMONDS, Rank.JACK);
		Card card2 = Card.get(Suit.CLUBS, Rank.TWO);
		Card card3 = Card.get(Suit.HEARTS, Rank.THREE);
		Card card4 = Card.get(Suit.DIAMONDS, Rank.FIVE);
		Card card5 = Card.get(Suit.SPADES, Rank.FIVE);
		
		cards.add(card1);
		cards.add(card2);
		// None = 15
		assertEquals(model.score15(cards), 0);
		cards.add(card3);
		// 10 + 2 + 3
		assertEquals(model.score15(cards), 2);
		cards.add(card4);
		cards.add(card5);
		// (10 + 2 + 3), (10 + 5), (10 + 5), (5 + 5 + 2 + 3)
		assertEquals(model.score15(cards), 8);
		
		model.drawStarter();
		assertNotEquals(model.getStarter(), null);
		model.getPlayer1().addToPlayed(card1);
		model.getPlayer1().addToPlayed(card2);
		model.getPlayer1().addToPlayed(card3);
		model.getPlayer1().addToPlayed(card4);
		
		assertTrue(model.scoreHand(model.getPlayer1(), false) > 0);
	}
	
	@Test
	void testGameScorePairs() {
		Game model = new Game("test1", "test2", 0);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = Card.get(Suit.DIAMONDS, Rank.JACK);
		Card card2 = Card.get(Suit.CLUBS, Rank.JACK);
		Card card3 = Card.get(Suit.HEARTS, Rank.THREE);
		Card card4 = Card.get(Suit.DIAMONDS, Rank.FIVE);
		Card card5 = Card.get(Suit.SPADES, Rank.FIVE);
		
		cards.add(card1);
		assertEquals(model.scorePairs(cards), 0);
		cards.add(card2);
		assertEquals(model.scorePairs(cards), 2);

		cards.add(card3);
		assertEquals(model.scorePairs(cards), 2);

		cards.add(card4);
		cards.add(card5);
		assertEquals(model.scorePairs(cards), 4);
	}
	
	@Test
	void testGameScoreRuns() {
		Game model = new Game("test1", "test2", 0);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = Card.get(Suit.DIAMONDS, Rank.ACE);
		Card card2 = Card.get(Suit.CLUBS, Rank.TWO);
		Card card3 = Card.get(Suit.HEARTS, Rank.THREE);
		Card card4 = Card.get(Suit.DIAMONDS, Rank.FOUR);
		Card card5 = Card.get(Suit.SPADES, Rank.FIVE);
		
		cards.add(card1);
		cards.add(card2);
		assertEquals(model.scoreRuns(cards), 0);
		
		cards.add(card3);
		assertEquals(model.scoreRuns(cards), 3);

		cards.add(card4);
		assertEquals(model.scoreRuns(cards), 4);

		cards.add(card5);
		assertEquals(model.scoreRuns(cards), 5);

	}
	
	@Test
	void testGameScoreFlush() {
		 Game model = new Game("test1", "test2", 0);
			
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = Card.get(Suit.SPADES, Rank.JACK);
		Card card2 = Card.get(Suit.DIAMONDS, Rank.TWO);
		Card card3 = Card.get(Suit.DIAMONDS, Rank.THREE);
		Card card4 = Card.get(Suit.DIAMONDS, Rank.FIVE);
		Card card5 = Card.get(Suit.DIAMONDS, Rank.KING);
		Card card6 = Card.get(Suit.DIAMONDS, Rank.QUEEN);
		
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		
		assertEquals(model.scoreFlush(cards, card6, false), 0);
		
		cards.remove(0);
		cards.add(card5);
		
		// flush in hand, but wrong starter suit - No crib
		assertEquals(model.scoreFlush(cards, card1, false), 4);
		// flush in hand, AND matching starter suit - No crib
		assertEquals(model.scoreFlush(cards, card6, false), 5);

		// crib conditions - only scores if starter AND flush match
		assertEquals(model.scoreFlush(cards, card6, true), 5);
		assertEquals(model.scoreFlush(cards, card1, true), 0);
	}
	
	@Test
	void testTwistingTheNob() {
		Game model = new Game("test1", "test2", 0);
		
		ArrayList<Card> cards = new ArrayList<Card>();
		Card card1 = Card.get(Suit.DIAMONDS, Rank.JACK);
		Card card2 = Card.get(Suit.CLUBS, Rank.TWO);
		Card card3 = Card.get(Suit.HEARTS, Rank.THREE);
		Card card4 = Card.get(Suit.DIAMONDS, Rank.FIVE);
		Card card5 = Card.get(Suit.DIAMONDS, Rank.SIX);
		Card card6 = Card.get(Suit.SPADES, Rank.SIX);
		
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		cards.add(card4);
		cards.add(card5);
		
		// Starter suit different than Jack
		assertEquals(model.scoreNob(cards, card6), 0);
		
		// Starter suit same as Jack
		assertEquals(model.scoreNob(cards, card5), 1);
	}
	
	@Test
	void testGameCheckRun() {
		// Logic testing, using similar function prior to introduction in
		// Game.
		ArrayList<testCard> cards = new ArrayList<testCard>();
		cards.add(new testCard(Suit.CLUBS, Rank.ACE));
		cards.add(new testCard(Suit.DIAMONDS, Rank.JACK));
		cards.add(new testCard(Suit.DIAMONDS, Rank.FOUR));
		cards.add(new testCard(Suit.HEARTS, Rank.TWO));
		cards.add(new testCard(Suit.SPADES, Rank.THREE));
		
		
		//System.out.println("Pre: " + cards.toString());
		
		assertEquals(testCheckRun(cards), 3);
		
		//System.out.println("Post: " + cards.toString());
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
