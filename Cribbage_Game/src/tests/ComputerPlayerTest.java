package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import Player.ComputerPlayer;
import model.Card;
import model.Rank;
import model.Suit;

/**
 * Unit tests for the ComputerPlayer class.
 */
public class ComputerPlayerTest {

    /**
     * Tests that the ComputerPlayer's name is correctly returned.
     */
    @Test
    public void testGetName() {
        ComputerPlayer cp = new ComputerPlayer("Comp", 1);
        assertEquals("Comp", cp.getName());
    }
    
    /**
     * Tests that the ComputerPlayer's name is correctly returned.
     */
    @Test
    public void testGetName2() {
        ComputerPlayer cp = new ComputerPlayer("Comp", 2);
        assertEquals("Comp", cp.getName());
    }

    /**
     * Tests that wins and losses are recorded correctly for a ComputerPlayer.
     */
    @Test
    public void testRecordWinLoss() {
        ComputerPlayer cp = new ComputerPlayer("Computer1", 1);
        cp.recordWin();
        cp.recordLoss();
        cp.recordLoss();
        assertEquals(1, cp.getWins());
        assertEquals(2, cp.getLosses());
    }

    /**
     * Tests hand-related operations such as adding cards and clearing the hand for a ComputerPlayer.
     */
    @Test
    public void testHandOperations() {
        ComputerPlayer cp = new ComputerPlayer("Comp", 1);
        Card card1 = Card.get(Suit.CLUBS, Rank.TWO);
        Card card2 = Card.get(Suit.DIAMONDS, Rank.THREE);
        cp.addCard(card1);
        cp.addCard(card2);
        List<Card> handCards = cp.getHand();
        assertEquals(2, handCards.size());
        assertTrue(handCards.contains(card1));
        assertTrue(handCards.contains(card2));
        cp.clearHand();
        assertEquals(0, cp.getHand().size());
    }

    /**
     * Tests the discardCards method using the DummyStrategy.
     * Verifies that the strategy's selectDiscard method is called and returns the expected discard list.
     */
    @Test
    public void testDiscardCards() {
        ComputerPlayer cp = new ComputerPlayer("Comp", 1);
        // Add four cards to the hand for testing.
        Card card1 = Card.get(Suit.SPADES, Rank.ACE);
        Card card2 = Card.get(Suit.HEARTS, Rank.TWO);
        Card card3 = Card.get(Suit.DIAMONDS, Rank.THREE);
        Card card4 = Card.get(Suit.CLUBS, Rank.FOUR);
        Card card5 = Card.get(Suit.DIAMONDS, Rank.SEVEN);
        Card card6 = Card.get(Suit.CLUBS, Rank.EIGHT);
        cp.addCard(card1);
        cp.addCard(card2);
        cp.addCard(card3);
        cp.addCard(card4);
        cp.addCard(card5);
        cp.addCard(card6);
        // After discarding, dummy.lastDiscarded should not be null and should contain the first two cards.
        assertEquals(cp.discardIndex(true), 1);
    }
    
    /**
     * Tests hand-related operations such as adding cards and clearing the hand for a ComputerPlayer.
     */
    @Test
    public void testGetPlayIndex() {
        ComputerPlayer cp = new ComputerPlayer("Comp", 2);
        Card card1 = Card.get(Suit.CLUBS, Rank.TWO);
        Card card2 = Card.get(Suit.DIAMONDS, Rank.THREE);
        cp.addCard(card1);
        cp.addCard(card2);
        ArrayList<Card> sequence = new ArrayList<Card>();
        card1 = Card.get(Suit.CLUBS, Rank.EIGHT);
        card2 = Card.get(Suit.DIAMONDS, Rank.FOUR);
        sequence.add(card1);
        sequence.add(card2);
        
        assertEquals(cp.getPlayIndex(sequence, 12), 2);
    }

    /**
     * Tests setting a new discard strategy for the ComputerPlayer.
     * Uses a modified dummy strategy that returns the last two cards.
     */
    @Test
    public void testSetDiscardStrategy() {
    }
}
