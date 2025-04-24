package strategy;

import java.util.ArrayList;

import model.Card;

public class EasyStrategy implements Strategy {

	/**
	 * playIndex(ArrayList<Card> hand, ArrayList<Card> sequence, int total)
	 * Purpose: Return the index of an arbitrary card to play.
	 * 
	 * @param hand - ArrayList<Card> containing at least one card that can be played
	 */
	@Override
	public int playIndex(ArrayList<Card> hand, ArrayList<Card> sequence, int total) {
		for (int i = 0; i < hand.size(); i ++) {
			if (hand.get(i).getRank().getValue() + total <= 31) {
				return i;
			}
		}
		return 0;
	}

}
