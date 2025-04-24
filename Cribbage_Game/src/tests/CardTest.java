package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Rank;
import model.Suit;

/**
 * Unit tests for the Card class.
 */
public class CardTest {

    /**
     * Tests the properties and computed values of a Card object.
     */
    @Test
    public void testCardProperties() {
       Card card1 = Card.get(Suit.DIAMONDS, Rank.FIVE);
       Card card2 = Card.get(Suit.DIAMONDS, Rank.FIVE);
       Card card3 = Card.get(Suit.HEARTS, Rank.TWO);
       Card card4 = Card.get(Suit.SPADES, Rank.KING);
       Card card5 = Card.get(Suit.CLUBS, Rank.ACE);
       
       assertSame(card1, card2);
       
       assertEquals(card1.getRank(), Rank.FIVE);
       assertEquals(card2.getSuit(), Suit.DIAMONDS);
       assertEquals(card1.toString(), "FIVE\u2666");
       assertEquals(card3.toString(), "TWO\u2665");
       assertEquals(card4.toString(), "KING\u2660");
       assertEquals(card5.toString(), "ACE\u2663");
       
       assertThrows(AssertionError.class, () -> Card.get(null, Rank.ACE));
       assertThrows(AssertionError.class, () -> Card.get(Suit.DIAMONDS, null));

       //assertTrue(card1.compare(card2, card3) > 0 );
       
       // -- Testing Rank isSequential --
       assertEquals(card1.getRank().isSequential(Rank.SIX), 1);
       assertEquals(card1.getRank().isSequential(Rank.FOUR), -1);
       assertEquals(card1.getRank().isSequential(Rank.FIVE), 0);
       assertEquals(card1.getRank().isSequential(Rank.SEVEN), 0);
       
       assertEquals(Rank.NINE.isSequential(Rank.EIGHT), -1);
       assertEquals(Rank.NINE.isSequential(Rank.TEN), 1 );
       assertEquals(Rank.NINE.isSequential(Rank.SEVEN), 0);
       
       assertEquals(Rank.TEN.isSequential(Rank.NINE), -1);
       assertEquals(Rank.TEN.isSequential(Rank.JACK), 1);
       assertEquals(Rank.TEN.isSequential(Rank.ACE), 0);
       
       assertEquals(Rank.JACK.isSequential(Rank.TEN), -1);
       assertEquals(Rank.JACK.isSequential(Rank.QUEEN), 1);
       assertEquals(Rank.JACK.isSequential(Rank.TWO), 0);
       
       assertEquals(Rank.QUEEN.isSequential(Rank.JACK), -1);
       assertEquals(Rank.QUEEN.isSequential(Rank.KING), 1);
       assertEquals(Rank.QUEEN.isSequential(Rank.FIVE), 0);
       
       assertEquals(Rank.KING.isSequential(Rank.QUEEN), -1);
       assertEquals(Rank.KING.isSequential(Rank.KING), 0);
       assertEquals(Rank.KING.isSequential(Rank.SEVEN), 0);

    }
    
}