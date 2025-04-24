package strategy;

import java.util.ArrayList;

import model.Card;

public class EasyDiscard implements DiscardStrategy {

	/**
	 * selectDiscard(model.Hand hand)
	 * Return the index of an arbitrary card to discard from hand
	 * @param hand		a Hand object with 5 or 6 cards.
	 */
	@Override
	public int selectDiscard(ArrayList<Card> hand, boolean dealer) {
		// Choose an arbitrary card from hand to discard
		return 0;
	}

}
