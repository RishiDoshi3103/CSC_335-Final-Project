package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Hand;

/**
 * Unit tests for the Hand class.
 */
public class HandTest {

    /**
     * Tests adding and removing a card from a hand.
     */
    @Test
    public void testAddAndRemoveCard() {
        Hand hand = new Hand();
        Card card = new Card("A", "Spades");
        hand.addCard(card);
        assertTrue(hand.getCards().contains(card));
        hand.getCards().remove(card);
        assertFalse(hand.getCards().contains(card));
    }

    /**
     * Tests retrieving cards from the hand.
     */
    @Test
    public void testGetCards() {
        Hand hand = new Hand();
        Card card1 = new Card("2", "Hearts");
        Card card2 = new Card("3", "Clubs");
        hand.addCard(card1);
        hand.addCard(card2);
        List<Card> cards = hand.getCards();
        assertEquals(2, cards.size());
        assertTrue(cards.contains(card1));
        assertTrue(cards.contains(card2));
    }
}