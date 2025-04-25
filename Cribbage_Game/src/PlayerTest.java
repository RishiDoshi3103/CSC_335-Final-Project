package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Player.HumanPlayer;
import Player.Player;
import model.Card;
import model.Deck;
import model.Rank;
import model.Suit;

class PlayerTest {

	@Test
	void testNameHandAndPlayed() {
		Deck deck = new Deck();
		Player p1 = new HumanPlayer("Test Name");
		
		assertEquals(p1.getName(), "Test Name");
		
		p1.addCard(deck.draw());
		p1.addCard(deck.draw());
		
		assertEquals(p1.getHand().size(), 2);
		
		ArrayList<Card> badHand = p1.getHand();
		badHand.add(deck.draw());
		
		// Encapsulates.
		assertEquals(p1.getHand().size(), 2);
		
		Card testCard = p1.discard(0);
		p1.addToPlayed(testCard);
		
		assertEquals(p1.getHand().size(), 1);
		assertEquals(p1.getPlayed().get(0), testCard);
		
		p1.clearHand();
		p1.clearPlayed();
		
		assertEquals(p1.getHand().size(), 0);
		assertEquals(p1.getPlayed().size(), 0);
	}
	
	@Test
	void testScoringAndWins() {
		Player p1 = new HumanPlayer("Me");
		
		assertEquals(p1.getScore(), 0);
		assertEquals(p1.getWins(), 0);
		assertEquals(p1.getLosses(), 0);
		
		p1.addPoints(15);
		p1.recordWin();
		p1.recordWin();
		p1.recordLoss();
		
		assertEquals(p1.getScore(), 15);
		assertEquals(p1.getWins(), 2);
		assertEquals(p1.getLosses(), 1);
		
		p1.resetScore();
		assertEquals(p1.getScore(), 0);
	}
}
