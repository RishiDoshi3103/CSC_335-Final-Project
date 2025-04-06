package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Card;

/**
 * Unit tests for the Card class.
 */
public class CardTest {

    /**
     * Tests the properties and computed values of a Card object.
     */
    @Test
    public void testCardProperties() {
        Card cardA = new Card("A", "Spades");
        assertEquals("A", cardA.getRank());
        assertEquals("Spades", cardA.getSuit());
        assertEquals(1, cardA.getCribbageValue());
        assertEquals(1, cardA.getRankValue());

        Card cardTen = new Card("10", "Hearts");
        assertEquals(10, cardTen.getCribbageValue());
        assertEquals(10, cardTen.getRankValue());

        Card cardJ = new Card("J", "Diamonds");
        assertEquals(10, cardJ.getCribbageValue());
        assertEquals(11, cardJ.getRankValue());
    }
}