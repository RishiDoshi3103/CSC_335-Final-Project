package tests;

import Player.HumanPlayer;
import model.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HumanPlayerTest {

    @Test
    public void testDiscardThrowsException() {
        HumanPlayer human = new HumanPlayer("You");

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            human.discardToCrib(null);
        });

        assertTrue(exception.getMessage().contains("manually"));
    }

    @Test
    public void testPlayThrowsException() {
        HumanPlayer human = new HumanPlayer("You");

        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            human.playCard(10);
        });

        assertTrue(exception.getMessage().contains("manually"));
    }
}
