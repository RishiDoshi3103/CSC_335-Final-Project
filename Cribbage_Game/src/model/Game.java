package model;

import java.util.ArrayList;

import Player.HumanPlayer;
import Player.Player;

public class Game {
	private Deck deck;
	private Player player1;
	private Player player2;
	private int dealer = 2; // Dealer flag (1: player1, 2: player2)
	private Card starter;
	private ArrayList<Card> crib;
	
	public Game(String name1, String name2) {
		player1 = new HumanPlayer("Player 1");
		player2 = new HumanPlayer("Player 2");
		crib = new ArrayList<Card>();
	}
	
	public void startRound() {
		if (dealer != 1) {
			dealer = 1;
		}
		else {
			dealer = 2;
		}
		crib.clear();
		player1.clearHand();
		player2.clearHand();
		deck = new Deck();
		deck.shuffle();
		deal();
	}
	
	private void deal() {
		for (int i=0; i < 6; i++) {
			player1.addCard(deck.draw());
			player2.addCard(deck.draw());
		}
	}
	
	public Player getPlayer1() {
		return player1;
	}
	
	public Player getPlayer2() {
		return player2;
	}
	
	public Player getDealer() {
		if (dealer == 1) {
			return player1;
		}
		return player2;
	}
	
	public Card cribCard(Card card) {
		crib.add(card);
		return card;
	}
	
	public ArrayList<Card> showCrib() {
		return crib;
	}
	
	/**
	 * This function draws the starter card. If the 
	 * starter is a Jack - current dealer gets 2 points.
	 * 
	 * @return Card (starter)
	 */
	public Card drawStarter() {
		Card starter = deck.draw();
		if (starter.getRank().equals(Rank.JACK)) {
			if (dealer == 1) {
				player1.addPoints(2);
			}
			else {
				player2.addPoints(2);
			}
		}
		return starter;
	}
	
	public boolean gameOver() {
		return player1.getScore() >= 61 || player2.getScore() >= 61;
	}
}
