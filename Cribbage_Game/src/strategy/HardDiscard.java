package strategy;

import java.util.ArrayList;

import model.Card;
import model.Rank;
import model.Suit;

public class HardDiscard implements DiscardStrategy {
	private int nextDiscardIndex;
	private boolean firstDiscard;
	
	public HardDiscard() {
		this.nextDiscardIndex = 0;
		this.firstDiscard = true;
	}
	
    // - Discard:	IF this is dealer:
    //					if four cards have same suit, discard other 2 cards.
    //					else if have matching < 3, discard that
    //					else if have cards that add to 15, discard those
    //					else discard two random cards
    //				IF this is not the dealer:
    //					if four cards have same suit, discard other 2 cards.
    //					else if have (ace & queen || king) OR (2 & queen || king) discard ace/2 and king if possible, then queen if not
    //						but not two of the same kind
    //					else if can pick a card at random that is NOT a jack OR a five
    //						Discard Helper
    //					else:
    //						Discard Helper

	/**
	 * selectDiscard(model.Hand hand)
	 * Return the index of an optimal card to discard from hand
	 * @param hand		a Hand object with 5 or 6 cards.
	 */
	@Override
	public int selectDiscard(ArrayList<Card> hand, boolean dealer) {
		
		ArrayList<Integer> spadeIndices = new ArrayList<Integer>();
		ArrayList<Integer> heartIndices = new ArrayList<Integer>();
		ArrayList<Integer> clubIndices = new ArrayList<Integer>();
		ArrayList<Integer> diamondIndices = new ArrayList<Integer>();
		
		// Create ArrayLists with the indices of cards by suit
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getSuit().equals(Suit.SPADES)) {
				spadeIndices.add(i);
			}
			else if (hand.get(i).getSuit().equals(Suit.HEARTS)) {
				heartIndices.add(i);
			}
			else if (hand.get(i).getSuit().equals(Suit.CLUBS)) {
				clubIndices.add(i);
			}
			else {
				diamondIndices.add(i);
			}
		}
		
		// Determine the max suit
		ArrayList<Integer> maxSuit;
		if (spadeIndices.size() >= 4) {
			maxSuit = spadeIndices;
		}
		else if (heartIndices.size() >= 4) {
			maxSuit = heartIndices;
		}
		else if (clubIndices.size() >= 4) {
			maxSuit = clubIndices;
		}
		else {
			maxSuit = diamondIndices;
		}
		
		if (firstDiscard) {
			firstDiscard = false;
		    // IF this is dealer:
		    //		if four cards have same suit, discard other 2 cards.
			//		else if five cards have same suit:
			//			if one of the five matches RANK of single, return the match
			//			else if one of the five + single = 15, return that one
			//			else discard two random 
		    //		else if have matching rank < 3, discard that
		    //		else if have cards that add to 15, discard those
			//		else discard a 5
			//		else discard a run of 2
			//		else discard two random cards (NOT aces, 2s, or 3s)
		    //		else discard two random cards (SAME SUIT)
			if (dealer) {
				//////////////////////////////////////////////////////////
				// if four cards have same suit, discard other 2 cards.	//
				//////////////////////////////////////////////////////////
				if (maxSuit.size() == 4) {
					return suit4DiscardHelper(hand, maxSuit);
				}
				//////////////////////////////////////////
				//	else if five cards have same suit:	//
				//////////////////////////////////////////
				else if (maxSuit.size() == 5) {
					int single = 0;
					// Set single to the index of the single card of a different suit
					for (int i = 0; i < hand.size(); i++) {
						if (!maxSuit.contains(i)) {
							single = i;
						}
					}
					nextDiscardIndex = single;
					// If one of the five matches RANK of single, return the match
					for (int index : maxSuit) {
						if (hand.get(index).getRank().equals(hand.get(single).getRank())) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// else if one of the five + single = 15, return that one
					for (int index : maxSuit) {
						if (hand.get(index).getRank().getValue() + hand.get(single).getRank().getValue() == 15) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// else discard a 5
					for (int index : maxSuit) {
						if (hand.get(index).getRank().getValue() == 5) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// else discard a run of 2
					for (int index : maxSuit) {
						if (hand.get(index).getRank().isSequential(hand.get(single).getRank()) != 0
								&& !hand.get(index).getRank().equals(Rank.ACE)
								&& !hand.get(index).getRank().equals(Rank.TWO)) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// else discard a random card (NOT aces or 2s)
					for (int index : maxSuit) {
						if (!hand.get(index).getRank().equals(Rank.ACE)
								&& !hand.get(index).getRank().equals(Rank.TWO)) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// else discard an arbitrary card
					if (nextDiscardIndex == 0) {
						return 1;
					}
					else {
						nextDiscardIndex --;
						return 0;
					}
				}
				
				//////////////////////////////////////////////////////
				//	else if have matching rank < 3, discard that	//
				//////////////////////////////////////////////////////
				ArrayList<Rank> pairRank = new ArrayList<Rank>();
				ArrayList<ArrayList<Integer>> pairIndices = new ArrayList<ArrayList<Integer>>();
				for (int i = 0; i < hand.size(); i ++) {
					for (int j = 0; j < hand.size(); j ++) {
						if (i != j) {
							if (hand.get(i).getRank().equals(hand.get(j).getRank())) {
								if (pairRank.contains(hand.get(i).getRank())) {
									pairIndices.get(pairRank.indexOf(hand.get(i).getRank())).add(i);
								}
								else {
									pairRank.add(hand.get(i).getRank());
									ArrayList<Integer> temp = new ArrayList<Integer>();
									temp.add(i);
									temp.add(j);
									pairIndices.add(temp);
								}
							}
						}
					}
				}
				for (int i = 0; i < pairIndices.size(); i ++) {
					if (pairIndices.get(i).size() == 3) {
						nextDiscardIndex = pairIndices.get(i).get(0);
						if (nextDiscardIndex > pairIndices.get(i).get(1)) nextDiscardIndex--;
						return pairIndices.get(i).get(1);
					}
				}
				
				//////////////////////////////////////////////////////////
				//	else if have cards that add to 15, discard those	//
				//////////////////////////////////////////////////////////
				for (int i = 0; i < hand.size(); i ++) {
					for (int j = 0; j < hand.size(); j ++) {
						if (i != j) {
							if (hand.get(i).getRank().getValue() + hand.get(j).getRank().getValue() == 15) {
								nextDiscardIndex = i;
								if (nextDiscardIndex > j) {
									nextDiscardIndex --;
								}
								return j;
							}
						}
					}
				}
				
				//////////////////////////////////////
				// else discard two random cards	//
				//////////////////////////////////////
				nextDiscardIndex = 0;
				return 0;
			}
			// IF not Dealer
			else {
			    //	if four cards have same suit, discard other 2 cards.
			    //	else if have (ace & queen || king) OR (2 & queen || king) discard ace/2 and king if possible, then queen if not
			    //		but not two of the same kind
			    //	else if can pick a card at random that is NOT a jack OR a five
			    //		Discard Helper
			    //	else:
			    //		Discard Helper
				
				//////////////////////////////////////////////////////////
				// if four cards have same suit, discard other 2 cards.	//
				//////////////////////////////////////////////////////////
				if (maxSuit.size() == 4) {
					return suit4DiscardHelper(hand, maxSuit);
				}
				
				//////////////////////////////////////////////////////////////////////////////////////////
				// if five cards have same suit, discard 1 of those cards and an optimal other card.	//
				//////////////////////////////////////////////////////////////////////////////////////////
				else if (maxSuit.size() == 5) {
					int single = 0;
					// Set single to the index of the single card of a different suit
					for (int i = 0; i < hand.size(); i++) {
						if (!maxSuit.contains(i)) {
							single = i;
						}
					}
					nextDiscardIndex = single;
					// If one of the five is a KING and doesn't match rank of single, return the index
					for (int index : maxSuit) {
						if (hand.get(index).getRank().equals(Rank.KING) 
								&& !hand.get(index).getRank().equals(hand.get(nextDiscardIndex).getRank())) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// If one of the five is not a 5, Ace or 2 and doesn't form a run, return the index
					for (int index : maxSuit) {
						if (!hand.get(index).getRank().equals(Rank.FIVE) 
								&& !hand.get(index).getRank().equals(Rank.ACE) 
								&& !hand.get(index).getRank().equals(Rank.TWO) 
								&& !hand.get(index).getRank().equals(hand.get(nextDiscardIndex).getRank()) 
								&& hand.get(index).getRank().isSequential(hand.get(nextDiscardIndex).getRank()) == 0) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// If one of the five is not a 5 and doesn't form a run, return the index
					for (int index : maxSuit) {
						if (!hand.get(index).getRank().equals(Rank.FIVE) 
								&& !hand.get(index).getRank().equals(hand.get(nextDiscardIndex).getRank()) 
								&& hand.get(index).getRank().isSequential(hand.get(nextDiscardIndex).getRank()) == 0) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
					// If one of the five is not a 5 and doesn't match rank of current, return the index
//					for (int index : maxSuit) {
//						if (!hand.get(index).getRank().equals(Rank.FIVE) 
//								&& !hand.get(index).getRank().equals(hand.get(nextDiscardIndex).getRank())) {
//							if (nextDiscardIndex > index) {
//								nextDiscardIndex--;
//							}
//							return index;
//						}
//					}
					// If one of the five doesn't match rank of current, return the index
					for (int index : maxSuit) {
						if (!hand.get(index).getRank().equals(hand.get(nextDiscardIndex).getRank())) {
							if (nextDiscardIndex > index) {
								nextDiscardIndex--;
							}
							return index;
						}
					}
				}
				// else DO NOT discard a matching pair
				ArrayList<Integer> offLimits = new ArrayList<Integer>();
				for (int i = 0; i < hand.size(); i++) {
					for (int j = 0; j < hand.size(); j++) {
						if (i != j) {
							if (hand.get(i).getRank().equals(hand.get(j).getRank())) {
								offLimits.add(i);
								offLimits.add(j);
							}
						}
					}
				}
				
				//	else if have (ace & queen || king) OR (2 & queen || king) 
				//	discard ace/2 and king if possible, then queen if not
			    //	but not two of the same kind or two in a row
				boolean haveKing = false;
				for (int i = 0; i < hand.size(); i++) {
					if (hand.get(i).getRank().equals(Rank.KING)
							&& !offLimits.contains(i)) {
						nextDiscardIndex = i;
						haveKing = true;
					}
				}
				if (haveKing) {
					for (int i = 0; i < hand.size(); i++) {
						if (!hand.get(i).getRank().equals(Rank.KING) 
								&& !hand.get(i).getRank().equals(Rank.QUEEN)
								&& !hand.get(i).getRank().equals(Rank.FIVE)
								&& !offLimits.contains(i)) {
							if (nextDiscardIndex > i) nextDiscardIndex --;
							return i;
						}
					}
				}
				//	else if can pick a card at random that is NOT a five, NOT a pair, doesn't add to 15
				for (int i = 0; i < hand.size(); i++) {
					if (!offLimits.contains(i) && !hand.get(i).getRank().equals(Rank.FIVE)) {
						nextDiscardIndex = i;
					}
				}
				for (int i = 0; i < hand.size(); i++) {
					if (i != nextDiscardIndex
							&& !offLimits.contains(i) && !hand.get(i).getRank().equals(Rank.FIVE)
							&& hand.get(i).getRank().getValue() + hand.get(nextDiscardIndex).getRank().getValue() != 15) {
						if (nextDiscardIndex > i) nextDiscardIndex --;
						return i;
					}
				}
				//	else if can pick a card at random that is NOT a five, doesn't add to 15
				for (int i = 0; i < hand.size(); i++) {
					if (!hand.get(i).getRank().equals(Rank.FIVE)) {
						nextDiscardIndex = i;
					}
				}
				for (int i = 0; i < hand.size(); i++) {
					if (i != nextDiscardIndex
							&& !hand.get(i).getRank().equals(Rank.FIVE)
							&& hand.get(i).getRank().getValue() + hand.get(nextDiscardIndex).getRank().getValue() != 15) {
						if (nextDiscardIndex > i) nextDiscardIndex --;
						return i;
					}
				}
				//	else if can pick a card at random that doesn't add to 15
				for (int i = 0; i < hand.size(); i++) {
					if (i != nextDiscardIndex
						&& hand.get(i).getRank().getValue() + hand.get(nextDiscardIndex).getRank().getValue() != 15) {
						if (nextDiscardIndex > i) nextDiscardIndex --;
						return i;
					}
				}
				//	else if can pick a card at random that doesn't add to 15
//				for (int i = 0; i < hand.size(); i++) {
//					if (hand.get(i).getRank().equals(Rank.FIVE)) {
//						nextDiscardIndex = i;
//					}
//				}
//				for (int i = 0; i < hand.size(); i++) {
//					if (i != nextDiscardIndex
//						&& hand.get(i).getRank().getValue() + hand.get(nextDiscardIndex).getRank().getValue() != 15) {
//						if (nextDiscardIndex > i) nextDiscardIndex --;
//						return i;
//					}
//				}
				// else
				nextDiscardIndex = 0;
				return 1;
			}
		}
		else {
			firstDiscard = true;
			return nextDiscardIndex;
		}
		
	}
	
	
	/*
	 * if four cards have same suit, discard other 2 cards.
	 */
	private int suit4DiscardHelper(ArrayList<Card> hand, ArrayList<Integer> maxSuit) {
		int first = -1;
		int second = -1;
		for (int i = 0; i < hand.size(); i++) {
			if (!maxSuit.contains(i)) {
				if (first < 0) {
					first = i;
				}
				else {
					second = i;
				}
			}
		}
		if (first < second) {
			second--;
		}
		nextDiscardIndex = second;
		return first;
	}

}
