package tests;

import model.Card;
import model.Hand;
import strategy.HardStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HardStrategyTest {

    @Test
    public void testSelectDiscardReturnsTwoCards() {
        HardStrategy hard = new HardStrategy();
        Hand hand = new Hand();

        // Fill hand
        hand.add(Card.of(Card.Rank.FOUR, Card.Suit.SPADES));
        hand.add(Card.of(Card.Rank.FIVE, Card.Suit.SPADES));
        hand.add(Card.of(Card.Rank.SIX, Card.Suit.SPADES));
        hand.add(Card.of(Card.Rank.NINE, Card.Suit.CLUBS));
        hand.add(Card.of(Card.Rank.TWO, Card.Suit.HEARTS));
        hand.add(Card.of(Card.Rank.K, Card.Suit.DIAMONDS));

        List<Card> discard = hard.selectDiscard(hand);

        assertEquals(2, discard.size(), "Hard strategy should discard exactly 2 cards.");
    }
}
