package tests;

import model.Card;
import model.Hand;
import strategy.EasyStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EasyStrategyTest {

    @Test
    public void testSelectDiscardReturnsTwoCards() {
        EasyStrategy easy = new EasyStrategy();
        Hand hand = new Hand();

        // Fill hand
        hand.add(Card.of(Card.Rank.A, Card.Suit.SPADES));
        hand.add(Card.of(Card.Rank.FIVE, Card.Suit.CLUBS));
        hand.add(Card.of(Card.Rank.TEN, Card.Suit.HEARTS));
        hand.add(Card.of(Card.Rank.THREE, Card.Suit.DIAMONDS));
        hand.add(Card.of(Card.Rank.J, Card.Suit.SPADES));
        hand.add(Card.of(Card.Rank.K, Card.Suit.CLUBS));

        List<Card> discard = easy.selectDiscard(hand);

        assertEquals(2, discard.size(), "Easy strategy should discard exactly 2 cards.");
    }
}
