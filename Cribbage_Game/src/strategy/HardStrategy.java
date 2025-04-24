package strategy;

import java.util.ArrayList;
import java.util.List;

import model.Card;
import model.Rank;

public class HardStrategy implements Strategy {
    // Score rankings:	FOUR OF A KIND 						(12+ points)
    //					TRIPLET AND ADDS TO 15/31			(8 points)
    //					5 CARD SEQUENCE AND ADDS TO 15/31 	(7 points)
    //					TRIPLET								(6 points)
    //					4 CARD SEQUENCE AND ADDS TO 15/31	(6 points)
	//					5 CARD SEQUENCE						(5 points)
    //					3 CARD SEQUENCE AND ADDS TO 15/31	(5 points)
    //					PAIR AND ADDS TO 15/31				(4 points)
    //					3 CARD SEQUENCE						(3 points)
    //					SPECIAL CASE:
    //						IF can add to 31:								add to 31
    //						ELSE IF have a DOUBLE MATCH on last card played: play PAIR
    //						ELSE IF can add to 15:							add to 15
    //						ELSE:											play PAIR
    //					ELSE PLAY RANDOM CARD (conforming to notes below)
    //				
    //				IMPORTANT PLAY NOTES:
    //					IF HAVE 4 lead with 4
    //					DO NOT lead with 5 (unless this is impossible)
    //					LEAD WITH A DOUBLE MATCH if possible
    //					DO NOT LEAD WITH ACE OR TWO
    //					DO NOT hit exactly 21
	//
	//
	//
    // Logic:			IF stack is empty
	//						IF have a 4
	//							play 4
	//						IF have a double match
	//							play one of them
	//						DO NOT LEAD WITH ACE, TWO, FIVE
	//					IF last 2 cards played were matching && have another matching card && total < 31
	//						play matching card
	//					IF can make >3 card sequence AND adds to 15/31
	//						play card in sequence
	//					IF can make 5 card sequence && total < 31
	//						play card in sequence
	//					IF can make pair AND adds to 15/31
	//						play card to make pair
	//					IF can make 4 card sequence && total < 31
	//						play card in sequence
	//					IF can make 3 card sequence && total < 31
	//						play card in sequence
	//					IF can add to 31
	//						add to 31
	//					IF can play pair && have extra match && total < 31
	//						play card in pair
	//					IF can add to 15
	//						add to 15
	//					IF can play pair && total < 31
	//						play card in pair
	//					IF can play a card in sequence && does NOT add to 21 && total < 31
	//						play card in sequence
	//					ELSE
	//						IF can play random card that DOES NOT add to 21 && total < 31
	//							do so
	//						ELSE play random card, total < 31
	

	/**
	 * playIndex(ArrayList<Card> hand, ArrayList<Card> sequence, int total)
	 * Purpose: Return the index of the most optimal card to play next,
	 * 			using a greedy algorithm.
	 * 
	 * @param hand - ArrayList<Card> containing at least one card that can be played
	 */
	@Override
	public int playIndex(ArrayList<Card> hand, ArrayList<Card> sequence, int total) {
		// IF Stack is empty
		if (sequence.size() == 0) {
			//	IF have a 4
			//		play 4
			//	IF have a double match
			//		play one of them
			//	DO NOT LEAD WITH ACE, TWO, FIVE
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getRank().equals(Rank.FOUR)) {
					return i;
				}
			}
			for (int i = 0; i < hand.size(); i++) {
				for (int j = 0; j < hand.size(); j++) {
					if (i != j) {
						if (hand.get(i).getRank().equals(hand.get(j).getRank())) {
							if (!hand.get(i).getRank().equals(Rank.ACE)
									&& !hand.get(i).getRank().equals(Rank.TWO)
									&& !hand.get(i).getRank().equals(Rank.FIVE))
							return i;
						}
					}
				}
			}
			for (int i = 0; i < hand.size(); i++) {
				if (!hand.get(i).getRank().equals(Rank.ACE)
						&& !hand.get(i).getRank().equals(Rank.TWO)
						&& !hand.get(i).getRank().equals(Rank.FIVE))
				return i;
			}
			for (int i = 0; i < hand.size(); i++) {
				if (!hand.get(i).getRank().equals(Rank.ACE)
						&& !hand.get(i).getRank().equals(Rank.FIVE))
				return i;
			}
			for (int i = 0; i < hand.size(); i++) {
				if (!hand.get(i).getRank().equals(Rank.FIVE))
				return i;
			}
			return 0;
		}
		//	IF last 2 cards played were matching && have another matching card && total < 31
		//		play matching card
		if (sequence.size() >= 2 && sequence.get(sequence.size() - 1).getRank().equals(sequence.get(sequence.size() - 2).getRank())) {
			for (int i = 0; i < hand.size(); i++) {
				if (hand.get(i).getRank().equals(sequence.get(sequence.size() - 1).getRank())
						&& hand.get(i).getRank().getValue() + total <= 31) {
					return i;
				}
			}
		}
		// 	IF can make >3 card sequence AND adds to 15/31
		//		play card in sequence
		int five = fiveSequence(hand, sequence);
		if (five >= 0 && (hand.get(five).getRank().getValue() + total == 31 
				|| hand.get(five).getRank().getValue() + total == 15 )) return five;
		int four = fourSequence(hand, sequence);
		if (four >= 0 && (hand.get(four).getRank().getValue() + total == 31 
				|| hand.get(four).getRank().getValue() + total == 15 )) return four;
		int three = threeSequence(hand, sequence);
		if (three >= 0 && (hand.get(three).getRank().getValue() + total == 31 
				|| hand.get(three).getRank().getValue() + total == 15 )) return three;
		// 	IF can make 5 card sequence && total < 31
		//		play card in sequence
		if (five >= 0 && hand.get(five).getRank().getValue() + total <= 31) return five;
		//	IF can make pair AND adds to 15/31
		//		play card to make pair
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().equals(sequence.get(sequence.size() - 1).getRank())
					&& (hand.get(i).getRank().getValue() + total == 31 || hand.get(i).getRank().getValue() + total == 15)) {
				return i;
			}
		}
		//	IF can make 4 card sequence && total < 31
		//		play card in sequence
		if (four >= 0 && hand.get(four).getRank().getValue() + total <= 31) return four;
		//	IF can make 3 card sequence && total < 31
		//		play card in sequence
		if (three >= 0 && hand.get(three).getRank().getValue() + total <= 31) return three;
		// 	IF can add to 31
		//		add to 31
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().getValue() + total == 31) {
				return i;
			}
		}
		//	IF can play pair && have extra match && total < 31
		//		play card in pair
		int indexA = -1;
		int indexB = -1;
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().equals(sequence.get(sequence.size() - 1).getRank())) {
				indexA = i;
			}
		}
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().equals(sequence.get(sequence.size() - 1).getRank())) {
				if (i != indexA) indexB = i;
			}
		}
		if (indexA >= 0 && indexB >= 0 && hand.get(indexB).getRank().getValue() + total <= 31) {
			return indexB;
		}
		//	IF can add to 15
		//		add to 15
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().getValue() + total == 15) {
				return i;
			}
		}
		//	IF can play pair && total < 31
		//		play card in pair
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().equals(sequence.get(sequence.size() - 1).getRank())
					&& hand.get(i).getRank().getValue() + total <= 31) {
				return i;
			}
		}
		//	IF can play a card in sequence && does NOT add to 21 && total < 31
		//		play card in sequence
		int two = twoSequenceLow(hand, sequence);
		if (two >= 0 && hand.get(two).getRank().getValue() + total != 21 
				&& hand.get(two).getRank().getValue() + total <= 31) {
			return two;
		}
		two = twoSequenceHigh(hand, sequence);
		if (two >= 0 && hand.get(two).getRank().getValue() + total != 21 
				&& hand.get(two).getRank().getValue() + total <= 31) {
			return two;
		}
		//	IF can play random card that DOES NOT add to 21 && total < 31
		//		do so
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().getValue() + total != 21 
					&& hand.get(i).getRank().getValue() + total <= 31) {
				return i;
			}
		}
		//	ELSE play random card, total < 31
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().getValue() + total <= 31) {
				return i;
			}
		}
		return 0;
	}
	
	
	/*
	 * fiveSequence
	 * return index of value to play if can make a sequence of 5
	 * return -1 if not possible
	 */
	private int fiveSequence(ArrayList<Card> hand, ArrayList<Card> sequence) {
		if (sequence.size() < 4) {
			return -1;
		}
		List<Card> lastFour = sequence.subList(sequence.size() - 4, sequence.size());
		for (int i = 0; i < hand.size(); i++) {
			if (isSequence(lastFour, hand.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * Check if a given list of Card objects is a sequence.
	 * Algorithm: Identify the smallest card in the list.
	 * Then loop through all the cards, checking if there is a
	 * next sequential card, removing the previous sequential card as we go.
	 * If the list goes all the way to empty, return true. Else return false.
	 */
	private boolean isSequence(List<Card> sequence, Card card) {
		ArrayList<Card> temp = new ArrayList<Card>(sequence);
		temp.add(card);
		
		Rank min = Rank.KING;
		int index = 0;
		// Find the min card value in temp
		for (int i = 0; i < temp.size(); i++) {
			if (temp.get(i).getRank().getValue() < min.getValue()) {
				index = i;
			}
			if (temp.get(i).getRank().getValue() == min.getValue() && min.getValue() == 10) {
				if (min == Rank.KING) {
					index = i;
				}
				else if (min == Rank.QUEEN) {
					if (temp.get(i).getRank() == Rank.JACK || temp.get(i).getRank() == Rank.TEN) {
						min = temp.get(i).getRank();
						index = i;
					}
				}
				else if (min == Rank.JACK) {
					if (temp.get(i).getRank() == Rank.TEN) {
						min = temp.get(i).getRank();
						index = i;
					}
				}
			}
		}
		Rank cur = temp.remove(index).getRank();
		while (temp.size() > 0) {
			boolean hasSequential = false;
			index = 0;
			for (int i = 0; i < temp.size(); i ++) {
				if (cur.isSequential(temp.get(i).getRank()) == 1) {
					hasSequential = true;
					index = i;
				}
			}
			if (!hasSequential) {
				return false;
			}
			cur = temp.remove(index).getRank();
		}
		
		return true;
	}

	/*
	 * fourSequence
	 * return index of value to play if can make a sequence of 4
	 * return -1 if not possible
	 */
	private int fourSequence(ArrayList<Card> hand, ArrayList<Card> sequence) {
		if (sequence.size() < 3) {
			return -1;
		}
		List<Card> lastFour = sequence.subList(sequence.size() - 3, sequence.size());
		for (int i = 0; i < hand.size(); i++) {
			if (isSequence(lastFour, hand.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * threeSequence
	 * return index of value to play if can make a sequence of 3
	 * return -1 if not possible
	 */
	private int threeSequence(ArrayList<Card> hand, ArrayList<Card> sequence) {
		if (sequence.size() < 2) {
			return -1;
		}
		List<Card> lastFour = sequence.subList(sequence.size() - 2, sequence.size());
		for (int i = 0; i < hand.size(); i++) {
			if (isSequence(lastFour, hand.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * twoSequence
	 * return index of value to play if can make a sequence of 2
	 * return -1 if not possible
	 */
	private int twoSequenceLow(ArrayList<Card> hand, ArrayList<Card> sequence) {
		if (sequence.size() < 1) {
			return -1;
		}
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().isSequential(sequence.get(sequence.size() - 1).getRank()) < 0) {
				return i;
			}
		}
		return -1;
	}
	
	/*
	 * twoSequence
	 * return index of value to play if can make a sequence of 2
	 * return -1 if not possible
	 */
	private int twoSequenceHigh(ArrayList<Card> hand, ArrayList<Card> sequence) {
		if (sequence.size() < 1) {
			return -1;
		}
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getRank().isSequential(sequence.get(sequence.size() - 1).getRank()) > 0) {
				return i;
			}
		}
		return -1;
	}
}
