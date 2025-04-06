package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import Player.HumanPlayer;
import model.Card;


/**
 * Unit tests for the HumanPlayer class.
 */
public class HumanPlayerTest {

    /**
     * Tests that the HumanPlayer's name is correctly returned.
     */
    @Test
    public void testGetName() {
        HumanPlayer hp = new HumanPlayer("Alice");
        assertEquals("Alice", hp.getName());
    }

    /**
     * Tests that wins and losses are recorded correctly.
     */
    @Test
    public void testRecordWinLoss() {
        HumanPlayer hp = new HumanPlayer("Bob");
        hp.recordWin();
        hp.recordWin();
        hp.recordLoss();
        assertEquals(2, hp.getWins());
        assertEquals(1, hp.getLosses());
    }

    /**
     * Tests hand-related operations such as adding cards and clearing the hand.
     */
    @Test
    public void testHandOperations() {
        HumanPlayer hp = new HumanPlayer("Charlie");
        Card card1 = new Card("A", "Spades");
        Card card2 = new Card("10", "Hearts");
        hp.addCard(card1);
        hp.addCard(card2);
        List<Card> handCards = hp.getHand().getCards();
        assertEquals(2, handCards.size());
        assertTrue(handCards.contains(card1));
        assertTrue(handCards.contains(card2));
        hp.clearHand();
        assertEquals(0, hp.getHand().getCards().size());
    }
}