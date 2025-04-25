package model;

import java.util.ArrayList;
import java.util.Comparator;

import Player.ComputerPlayer;
import Player.HumanPlayer;
import Player.Player;
import ui.GameStarter;

/**
 * The {@code Game} class manages the overall game state for a round or match of Cribbage.
 * It handles player objects, scoring, card sequences, and control over rounds and phases.
 */
public class Game {
	private Deck deck;
	private Player player1;
	private Player player2;
	private int dealer = 2; // Dealer flag (1: player1, 2: player2)
	private Card starter;
	private ArrayList<Card> crib;

	// Play Phase Variables
	private ArrayList<Card> sequenceCards;
	private int playPointTotal;

	/**
	 * Constructs a new {@code Game} object with two players, which may be human or computer,
	 * and sets up initial round state, crib, and play sequence.
	 *
	 * @param name1         the name of the first player
	 * @param name2         the name of the second player
	 * @param numComputers  number of computer-controlled players (0, 1, or 2)
	 */
	public Game(String name1, String name2, int numComputers) {
	    deck = new Deck();
	    if (numComputers < 1) {
	        player1 = new HumanPlayer(name1);
	        player2 = new HumanPlayer(name2);
	    }
	    else if (numComputers < 2) {
	        player1 = new HumanPlayer(name1);
	        player2 = new ComputerPlayer(name2, GameStarter.getStrat(name2));
	    }
	    else {
	        player1 = new ComputerPlayer(name1, GameStarter.getStrat(name1));
	        player2 = new ComputerPlayer(name2, GameStarter.getStrat(name2));
	    }
	    crib = new ArrayList<Card>();
	    sequenceCards = new ArrayList<Card>();
	}

	/**
	 * Starts a new round of the game: switches dealer, resets all hands, crib, sequence, and deck.
	 */
	public void startRound() {
		if (dealer != 1) {
			dealer = 1;
		} else {
			dealer = 2;
		}
		crib.clear();
		player1.clearHand();
		player2.clearHand();
		player1.clearPlayed();
		player2.clearPlayed();
		sequenceCards.clear();
		playPointTotal = 0;

		deck = new Deck();
		deck.shuffle();
		deal();
	}

	/**
	 * Deals 6 cards to each player at the beginning of a round.
	 */
	private void deal() {
		for (int i = 0; i < 6; i++) {
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

	public ArrayList<Card> getSequence() {
		return new ArrayList<Card>(sequenceCards);
	}

	public void addToSequence(Card card) {
		sequenceCards.add(card);
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
		ArrayList<Card> result = new ArrayList<Card>(this.crib);
		return result;
	}

	/**
	 * Draws and returns the starter card. If the starter is a Jack, the dealer gains 2 points.
	 *
	 * @return the drawn starter card
	 */
	public Card drawStarter() {
		starter = deck.draw();
		if (starter.getRank().equals(Rank.JACK)) {
			if (dealer == 1) {
				player1.addPoints(2);
			} else {
				player2.addPoints(2);
			}
		}
		return starter;
	}

	public Card getStarter() {
		return starter;
	}

	public int pointTotal() {
		return playPointTotal;
	}

	public void resetTotal() {
		this.playPointTotal = 0;
	}

	public void addToTotal(int n) {
		this.playPointTotal += n;
	}

	public int getTotal() {
		return this.playPointTotal;
	}

	public void resetSequence() {
		sequenceCards.clear();
	}

	public boolean gameOver() {
		return player1.getScore() >= 61 || player2.getScore() >= 61;
	}

	/**
	 * Checks whether the player has any cards that can be played without going over 31.
	 *
	 * @param player the player to check
	 * @return true if they can play a card, false otherwise
	 */
	public boolean handCheck(Player player) {
		boolean playable = false;
		ArrayList<Card> cards = player.getHand();
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getRank().getValue() + playPointTotal <= 31) {
				playable = true;
			}
		}
		return playable;
	}

	public int checkPairs() {
		if (sequenceCards.size() < 2) {
			return 0;
		}
		int matches = 0;
		Card lastPlayed = sequenceCards.get(sequenceCards.size() - 1);
		for (int i = sequenceCards.size() - 2; i >= 0; i--) {
			if (sequenceCards.get(i).getRank().equals(lastPlayed.getRank())) {
				matches++;
			} else {
				break;
			}
		}
		if (matches == 1) return 2;
		else if (matches == 2) return 6;
		else if (matches == 3) return 12;
		else return 0;
	}

	public int checkRuns() {
		if (sequenceCards.size() < 3) {
			return 0;
		}
		int maxRun = 0;
		for (int i = sequenceCards.size() - 3; i >= 0; i--) {
			boolean valid = true;
			ArrayList<Card> check = new ArrayList<Card>(sequenceCards.subList(i, sequenceCards.size()));
			check.sort(Comparator.comparingInt(card -> card.getRank().ordinal()));
			for (int j = 0; j < check.size() - 1; j++) {
				if (check.get(j + 1).getRank().ordinal() != check.get(j).getRank().ordinal() + 1) {
					valid = false;
					break;
				}
			}
			if (valid) {
				maxRun = sequenceCards.size() - i;
			}
		}
		return maxRun;
	}

	public int scoreHand(Player player, boolean isCrib) {
		ArrayList<Card> handAndStarter = new ArrayList<Card>(player.getPlayed());
		handAndStarter.add(starter);
		int score = 0;
		score += score15(handAndStarter);
		score += scorePairs(handAndStarter);
		score += scoreRuns(handAndStarter);
		score += scoreFlush(player.getPlayed(), starter, isCrib);
		score += scoreNob(player.getPlayed(), starter);
		return score;
	}

	public int score15(ArrayList<Card> cards) {
		int points = 0;
		int n = cards.size();
		// All 2, 3, 4, and 5-card combinations
		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++)
				if (cards.get(i).getRank().getValue() + cards.get(j).getRank().getValue() == 15)
					points += 2;

		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++)
				for (int k = j + 1; k < n; k++)
					if (cards.get(i).getRank().getValue() + cards.get(j).getRank().getValue() +
						cards.get(k).getRank().getValue() == 15)
						points += 2;

		for (int i = 0; i < n; i++)
			for (int j = i + 1; j < n; j++)
				for (int k = j + 1; k < n; k++)
					for (int l = k + 1; l < n; l++)
						if (cards.get(i).getRank().getValue() + cards.get(j).getRank().getValue() +
							cards.get(k).getRank().getValue() + cards.get(l).getRank().getValue() == 15)
							points += 2;

		if (n >= 5) {
			int sum = 0;
			for (Card card : cards) sum += card.getRank().getValue();
			if (sum == 15) points += 2;
		}
		return points;
	}

	public int scorePairs(ArrayList<Card> cards) {
		int points = 0;
		for (int i = 0; i < cards.size(); i++) {
			for (int j = i + 1; j < cards.size(); j++) {
				if (cards.get(i).getRank() == cards.get(j).getRank()) {
					points += 2;
				}
			}
		}
		return points;
	}

	public int scoreRuns(ArrayList<Card> cards) {
		ArrayList<Card> check = new ArrayList<Card>(cards);
		check.sort(Comparator.comparingInt(card -> card.getRank().ordinal()));
		int maxRun = 0;
		for (int i = 0; i < check.size(); i++) {
			int run = 1;
			int cur = check.get(i).getRank().ordinal();
			int j = i + 1;
			while (j < check.size()) {
				if (check.get(j).getRank().ordinal() == cur) {
					j++;
					continue;
				}
				if (check.get(j).getRank().ordinal() == cur + 1) {
					run++;
					cur = check.get(j).getRank().ordinal();
					j++;
				} else {
					break;
				}
			}
			if (run >= 3 && run > maxRun) {
				maxRun = run;
			}
		}
		return maxRun;
	}

	public int scoreFlush(ArrayList<Card> cards, Card starter, boolean isCrib) {
		if (cards.size() < 3) return 0;
		for (int i = 0; i < cards.size() - 1; i++) {
			if (cards.get(i).getSuit() != cards.get(i + 1).getSuit()) return 0;
		}
		if (starter.getSuit() == cards.get(0).getSuit()) {
			return 5;
		}
		return isCrib ? 0 : 4;
	}

	public int scoreNob(ArrayList<Card> cards, Card starter) {
		for (Card card : cards) {
			if (card.getRank() == Rank.JACK && card.getSuit() == starter.getSuit()) {
				return 1;
			}
		}
		return 0;
	}
}
