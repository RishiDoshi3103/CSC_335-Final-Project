package ui;

import model.Card;

/**
 * The CardActionListener interface should be implemented by any class
 * that wants to respond to card selection events in the user interface.
 *
 * It is typically used in GUI components to detect when a user selects a card.
 */
public interface CardActionListener {

    /**
     * Called when a card is selected by the user.
     *
     * @param card the card that was selected
     * @param i    the index of the selected card in the hand
     */
    void onCardSelected(Card card, int i);
}
