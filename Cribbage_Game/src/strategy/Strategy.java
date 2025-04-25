package strategy;

import java.util.ArrayList;
import model.Card;

/**
 * The Strategy interface defines the behavior for selecting which card to play
 * during the play phase of the game.
 *
 * This is part of the Strategy design pattern, allowing different implementations
 * for AI decision-making.
 */
public interface Strategy {

    /**
     * Determines the index of the card to play from the hand based on the current
     * sequence of played cards and the running total.
     *
     * @param hand     the current hand of cards
     * @param sequence the cards played so far in the sequence
     * @param total    the current total value in the play phase
     * @return the index of the card to play from the hand
     */
    int playIndex(ArrayList<Card> hand, ArrayList<Card> sequence, int total);
}
