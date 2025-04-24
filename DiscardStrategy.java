package strategy;

import java.util.ArrayList;

import model.Card;

public interface DiscardStrategy {
	
	/**
	 * selectDiscard(model.Hand hand)
	 * Return the index of a card to discard from hand
	 * @param hand		a Hand object with 5 or 6 cards.
	 */
	public int selectDiscard(ArrayList<Card> hand);
	
}
