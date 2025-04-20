package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import Player.ComputerPlayer;
import model.Card;
import strategy.EasyStrategy;
import strategy.HardStrategy;

public class ComputerPlayerTest {

    @Test
    public void testDiscardToCrib() {
        ComputerPlayer computer = new ComputerPlayer(
                "AI",
                List.of(new EasyStrategy(), new HardStrategy())
        );

        // Simulate giving 6 cards
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                if (computer.getHand().getCards().size() < 6) {
                    computer.getHand().add(Card.of(rank, suit));
                }
            }
        }

        List<Card> discarded = computer.discardToCrib(null);
        assertEquals(2, discarded.size(), "Computer should discard exactly 2 cards.");
    }

    @Test
    public void testPlayCard() {
        ComputerPlayer computer = new ComputerPlayer(
                "AI",
                List.of(new EasyStrategy())
        );

        // Give computer small hand
        computer.getHand().add(Card.of(Card.Rank.FIVE, Card.Suit.HEARTS));
        computer.getHand().add(Card.of(Card.Rank.TWO, Card.Suit.CLUBS));

        Card played = computer.playCard(10);
        assertNotNull(played, "Computer should be able to play a card.");
    }
}