package model;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> cards;
	
	public Deck() {
		cards = new ArrayList<Card>();
		
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards.add(Card.get(suit, rank));
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	public Card draw() {
		return cards.remove(0);
	}
	
	public int size() {
		return cards.size();
	}
}
