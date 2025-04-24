package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Hand;
import player.ComputerPlayer;

/**
 * Unit tests for the ComputerPlayer class.
 */
public class ComputerPlayerTest {

    /**
     * A dummy discard strategy used for testing.
     * This strategy always selects the first two cards in the hand.
     */
    private class DummyStrategy implements strategy.Strategy {
        public List<Card> lastDiscarded = null;

        @Override
        public List<Card> selectDiscard(Hand hand) {
            List<Card> cards = hand.getCards();
            List<Card> discard = new ArrayList<>();
            if (cards.size() >= 2) {
                discard.add(cards.get(0));
                discard.add(cards.get(1));
            }
            lastDiscarded = discard;
            return discard;
        }
    }

    /**
     * Tests that the ComputerPlayer's name is correctly returned.
     */
    @Test
    public void testGetName() {
        DummyStrategy dummy = new DummyStrategy();
        ComputerPlayer cp = new ComputerPlayer("Comp", dummy);
        assertEquals("Comp", cp.getName());
    }

    /**
     * Tests that wins and losses are recorded correctly for a ComputerPlayer.
     */
    @Test
    public void testRecordWinLoss() {
        DummyStrategy dummy = new DummyStrategy();
        ComputerPlayer cp = new ComputerPlayer("Computer1", dummy);
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
        DummyStrategy dummy = new DummyStrategy();
        ComputerPlayer cp = new ComputerPlayer("Comp", dummy);
        Card card1 = new Card("2", "Clubs");
        Card card2 = new Card("3", "Diamonds");
        cp.addCard(card1);
        cp.addCard(card2);
        List<Card> handCards = cp.getHand().getCards();
        assertEquals(2, handCards.size());
        assertTrue(handCards.contains(card1));
        assertTrue(handCards.contains(card2));
        cp.clearHand();
        assertEquals(0, cp.getHand().getCards().size());
    }

    /**
     * Tests the discardCards method using the DummyStrategy.
     * Verifies that the strategy's selectDiscard method is called and returns the expected discard list.
     */
    @Test
    public void testDiscardCards() {
        DummyStrategy dummy = new DummyStrategy();
        ComputerPlayer cp = new ComputerPlayer("Comp", dummy);
        // Add four cards to the hand for testing.
        Card card1 = new Card("A", "Spades");
        Card card2 = new Card("2", "Hearts");
        Card card3 = new Card("3", "Diamonds");
        Card card4 = new Card("4", "Clubs");
        cp.addCard(card1);
        cp.addCard(card2);
        cp.addCard(card3);
        cp.addCard(card4);

        // Before discarding, dummy.lastDiscarded should be null.
        assertNull(dummy.lastDiscarded);
        // Call discardCards (which invokes dummy.selectDiscard).
        cp.discardCards();
        // After discarding, dummy.lastDiscarded should not be null and should contain the first two cards.
        assertNotNull(dummy.lastDiscarded);
        assertEquals(2, dummy.lastDiscarded.size());
        assertEquals(card1, dummy.lastDiscarded.get(0));
        assertEquals(card2, dummy.lastDiscarded.get(1));
    }

    /**
     * Tests setting a new discard strategy for the ComputerPlayer.
     * Uses a modified dummy strategy that returns the last two cards.
     */
    @Test
    public void testSetDiscardStrategy() {
        DummyStrategy dummy1 = new DummyStrategy();
        ComputerPlayer cp = new ComputerPlayer("Computer2", dummy1);
        // Add three cards to the hand.
        Card card1 = new Card("5", "Spades");
        Card card2 = new Card("6", "Hearts");
        Card card3 = new Card("7", "Clubs");
        cp.addCard(card1);
        cp.addCard(card2);
        cp.addCard(card3);
        // Use dummy1 first.
        cp.discardCards();
        assertNotNull(dummy1.lastDiscarded);

        // Create a new strategy that returns the last two cards.
        DummyStrategy dummy2 = new DummyStrategy() {
            @Override
            public List<Card> selectDiscard(Hand hand) {
                List<Card> cards = hand.getCards();
                List<Card> discard = new ArrayList<>();
                if (cards.size() >= 2) {
                    discard.add(cards.get(cards.size() - 2));
                    discard.add(cards.get(cards.size() - 1));
                }
                lastDiscarded = discard;
                return discard;
            }
        };
        cp.setDiscardStrategy(dummy2);
        cp.discardCards();
        assertNotNull(dummy2.lastDiscarded);
        List<Card> handCards = cp.getHand().getCards();
        // For a hand of 3 cards, the last two cards should be card2 and card3.
        assertEquals(2, dummy2.lastDiscarded.size());
        assertEquals(card2, dummy2.lastDiscarded.get(0));
        assertEquals(card3, dummy2.lastDiscarded.get(1));
    }
}
