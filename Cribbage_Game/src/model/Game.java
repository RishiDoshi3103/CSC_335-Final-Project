package model;

import java.util.ArrayList;
import java.util.Comparator;

import Player.ComputerPlayer;
import Player.HumanPlayer;
import Player.Player;
import ui.GameStarter;

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
	 * This function sets the Game state variables:
	 * Players and their types
	 * Sets a new blank crib
	 * and sets the sequence (play stack between two players)
	 * 
	 * Note: Additional Parameters can be added to designate
	 * Computer/Human
	 * 
	 * @param name1 Name of First Player 
	 * @param name2 Name of Second Player 
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
	 * This function starts a new Round - meaning, dealer swaps to 
	 * other player, crib is cleared, hands are cleared, played cards
	 * are cleared, sequence and related points are reset, and a 
	 * fresh 52 card deck is used.
	 * 
	 * The deck is shuffled, then calls deal() - giving each player
	 * 6 card hands to start.
	 */
	public void startRound() {
		// Switch Dealer
		if (dealer != 1) {
			dealer = 1;
		}
		else {
			dealer = 2;
		}
		// Reset State
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
	 * This function allocates six cards to each player.
	 * Occurs at start of each round.
	 */
	private void deal() {
		for (int i=0; i < 6; i++) {
			player1.addCard(deck.draw());
			player2.addCard(deck.draw());
		}
	}
	
	/**
	 * Return player1, is left to return direct object as some 
	 * functionality may be useful / checks done to see if
	 * human or computer is used (for future STRATEGY).
	 * 
	 * @return player1
	 */
	public Player getPlayer1() {
		return player1;
	}
	
	/**
	 * Returns player2, see getPlayer1().
	 * 
	 * @return player2
	 */
	public Player getPlayer2() {
		return player2;
	}
	
	
	/**
	 * Returns a copy of sequenceCards.
	 * 
	 * @return ArrayList<Card> copy of sequenceCards
	 */
	public ArrayList<Card> getSequence() {
		return new ArrayList<Card>(sequenceCards);
	}
	
	/**
	 * Places the 'played' card into the sequence manager
	 * 
	 * @param card
	 */
	public void addToSequence(Card card) {
		sequenceCards.add(card);
	}
	
	/**
	 * Returns whichever player is the designated dealer -
	 * depending on the dealer flag.
	 * 1 -> player1
	 * 2 -> player2
	 * 
	 * @return Dealer (player1/player2)
	 */
	public Player getDealer() {
		if (dealer == 1) {
			return player1;
		}
		return player2;
	}
	
	/**
	 * This function places a discarded card into the crib.
	 * 
	 * @param card Discarded by player at start of round
	 * @return The card that was placed, for display/viewer purposes
	 */
	public Card cribCard(Card card) {
		crib.add(card);
		return card;
	}
	
	/**
	 * Shows current crib - for viewer display purposes. Encapsulated.
	 * 
	 * @return crib copy
	 */
	public ArrayList<Card> showCrib() {
		ArrayList<Card> result = new ArrayList<Card>(this.crib);
		return result;
	}
	
	/**
	 * This function draws the starter card. If the 
	 * starter is a Jack - current dealer gets 2 points.
	 * 
	 * @return Card (starter)
	 */
	public Card drawStarter() {
		starter = deck.draw();
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
	
	/**
	 * Returns the current starter card for the round
	 * 
	 * @return starter card
	 */
	public Card getStarter() {
		return starter;
	}
	
	/**
	 * Returns current value of the play/sequence stack
	 * 
	 * @return play/sequence stack rank value total
	 */
	public int pointTotal() {
		return playPointTotal;
	}
	
	/** 
	 * Resets play stack point total (> 31)
	 */
	public void resetTotal() {
		this.playPointTotal = 0;
	}
	
	/**
	 * Used to add a cards Rank value to play/sequence stack total
	 * 
	 * @param n - Intended to be value return from card Rank enum
	 */
	public void addToTotal(int n) {
		this.playPointTotal += n;
	}
	
	/**
	 * Return the total Cribbage values of the played cards
	 * 
	 * @return int this.playPointTotal
	 */
	public int getTotal() {
		return this.playPointTotal;
	}
	
	/**
	 * Resets play stack if no players can play
	 */
	public void resetSequence() {
		sequenceCards.clear();
	}
	
	/**
	 * Used to trigger game end - if either player score >= 61
	 * 
	 * @return true if above condition met
	 */
	public boolean gameOver() {
		// change to 61 in final
		return player1.getScore() >= 61 || player2.getScore() >= 61;
	}
	
	/**
	 * This function checks if the player can play any cards
	 * without the total going over 31.
	 * 
	 * @param player
	 * @return true if able to play a card
	 */
	public boolean handCheck(Player player) {
		boolean playable = false;
		ArrayList<Card> cards = player.getHand();
		for (int i=0; i < cards.size(); i++) {
			if (cards.get(i).getRank().getValue() + playPointTotal <= 31) {
				playable = true;
			}
		}
		return playable;
	}
	
	/**
	 * Checks the current state of the sequence, and returns the associated
	 * point values for any pairs. (ie.
	 * 2 pair: 2 points,
	 * 3 pair: 6 points
	 * 4 pair: 12 points )
	 * 
	 * @return value of any pairs, 0 if none or less than 2 cards in sequence stack
	 */
	public int checkPairs() {
		if (sequenceCards.size() < 2) {
			return 0;
		}
		
		int matches = 0;
//		Card lastPlayed = sequenceCards.getLast();
		Card lastPlayed = sequenceCards.get(sequenceCards.size() - 1);
		for (int i = sequenceCards.size() - 2; i >= 0; i--) {
			if (sequenceCards.get(i).getRank().equals(lastPlayed.getRank()) ) {
				matches++;
			}
			else {
				break;
			}
		}
		
		if (matches == 1) {
			// pair
			return 2;
		}
		else if (matches == 2) {
			// pair royal
			return 6;
		}
		else if (matches == 3) {
			return 12;
		}
		else {
			return 0;
		}
	}
	
	/**
	 * A run requires at least 3 cards. This function takes the last three cards,
	 * creates a sub-ArrayList of these last three, sorts them by rank using
	 * a COMPARATOR, then checks to see if they're in a 'run'. If a card is 
	 * out of order, changes the validity of a check (valid) to false.
	 * - If the first 3 are a run, sets the return to an integer, before
	 * decrementing to check the last 4, then 5, etc. So long as the runs
	 * remain valid, it'll set the return value to the that run.
	 * 
	 * @return the longest streak of successful runs
	 */
	public int checkRuns() {
		if (sequenceCards.size() < 3) {
			return 0;
		}
		
		int maxRun = 0;
		for (int i = sequenceCards.size()-3; i >= 0; i--) {
			boolean valid = true;
			ArrayList<Card> check = new ArrayList<Card>(sequenceCards.subList(i, sequenceCards.size()));
			check.sort(Comparator.comparingInt(card -> card.getRank().ordinal()));
			for (int j = 0; j < check.size()-1; j++) {
				if (check.get(j+1).getRank().ordinal() != check.get(j).getRank().ordinal() + 1) {
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
	
	/**
	 * Parent function that returns an int of the total score for
	 * the show.
	 * 
	 * For scoring played cards and starter for a player, uses
	 * the handAndStarter to judge them together for the following
	 * point checkers: cards that add up to 15 (score15), pairs (scorePairs),
	 * and runs (scoreRuns).
	 * 
	 * @param player to determine score
	 * @param isCrib to determine if scoring the crib for dealer (true if so)
	 * @return total score for all show checks
	 */
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
	
	/**
	 * This function incrementally checks and adds 2 points for each of the 
	 * following that totals 15 (by cribbage Rank value):
	 * - Every 2 cards
	 * - Every 3 cards
	 * - Every 4 cards
	 * - and finally, the whole hand if 5 cards
	 * 
	 * (public for testing purposes)
	 * 
	 * @param cards
	 * @return total points for each variation
	 */
	public int score15(ArrayList<Card> cards) {
		int points = 0;
	    int n = cards.size();

	    // 2 cards
	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            int sum = cards.get(i).getRank().getValue() + cards.get(j).getRank().getValue();
	            if (sum == 15) {
	            	points += 2;
	            }
	        }
	    }

	    // 3 cards
	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            for (int k = j + 1; k < n; k++) {
	                int sum = cards.get(i).getRank().getValue()
	                        + cards.get(j).getRank().getValue()
	                        + cards.get(k).getRank().getValue();
	                if (sum == 15) {
	                	points += 2;
	                }
	            }
	        }
	    }

	    // 4 cards
	    for (int i = 0; i < n; i++) {
	        for (int j = i + 1; j < n; j++) {
	            for (int k = j + 1; k < n; k++) {
	                for (int l = k + 1; l < n; l++) {
	                    int sum = cards.get(i).getRank().getValue()
	                            + cards.get(j).getRank().getValue()
	                            + cards.get(k).getRank().getValue()
	                            + cards.get(l).getRank().getValue();
	                    if (sum == 15) {
	                    	points += 2;
	                    }
	                }
	            }
	        }
	    }

	    // 4-cards and starter
	    if (n >= 5) {
	        int sum = 0;
	        for (Card card : cards) {
	            sum += card.getRank().getValue();
	        }
	        if (sum == 15) {
	        	points += 2;
	        }
	    }

	    return points;
	}
	
	/**
	 * Increments through every possible pair of cards in hand,
	 * adding 2 points for each that share a Rank.
	 * 
	 * (public for testing purposes)
	 * 
	 * @param cards
	 * @return
	 */
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
	
	/**
	 * Similar to the sequence run check, this function scores if a hand is a
	 * run by creating and sorting a copy (preserving ENCAPSULATION) by using 
	 * a COMPARATOR. For each card in the list, it checks to see if the next card
	 * is one bigger, changes the cursor to that next card, and checks if the one
	 * after that is one bigger - going to the end. For each successful check, it
	 * increments the run counter. If one card is the same as the next, it ignores
	 * that card, and moves to the next (doubles are ignored). If any two comparisons
	 * are not a valid run contributor - the loop drops, checks to see how many
	 * successful runs there were, setting max if over 3, then does the full check
	 * on the next card. This is meant to see if the full hand is a run, while 
	 * also catching any middle or end runs.
	 * 
	 * @param cards hand / cards to be scored for a run
	 * @return Largest successful run >= 3, or 0 if none
	 */
	public int scoreRuns(ArrayList<Card> cards) {
	   ArrayList<Card> check = new ArrayList<Card>(cards);
	   check.sort(Comparator.comparingInt(card -> card.getRank().ordinal()));
	   
	   int maxRun = 0;
	   for (int i=0; i < check.size(); i++) {
		   int run = 1;
		   int cur = check.get(i).getRank().ordinal();
		   int j = i + 1;
		   while ( j < check.size()) {
			   // skip samesies
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

	/**
	 * This function checks if all 4 cards are same SUIT (Flush) and returns the score.
	 * Per game rules, generally assumes the "cards" are a 4 card hand - but can work
	 * with more. If all 4 cards in hand are a flush, AND match the starter's SUIT, returns 
	 * an extra point.
	 * 
	 * The funny bits about the isCrib has to do with show scoring for the crib.
	 * The crib only scores points if all 4 cards are same SUIT AND the starter
	 * card. So thats why the function checks if isCrib is flagged as, if true, 
	 * it represents crib scoring.
	 * 
	 * If any are not the same, exits with a 0.
	 * 
	 * (public for testing purposes)
	 * 
	 * @param cards   generally a 4 card hand
	 * @param starter starter card
	 * @param isCrib  true if scoring crib
	 * @return total  points (flush)
	 */
	public int scoreFlush(ArrayList<Card> cards, Card starter, boolean isCrib) {
		if (cards.size() < 3) {
			return 0;
		}
		for (int i = 0; i < cards.size()-1; i++) {
			if (cards.get(i).getSuit() != cards.get(i+1).getSuit()) {
				return 0;
			}
		}
		
		if (isCrib) {
			if (starter.getSuit() == cards.get(0).getSuit()) {
				return 5;
			} else {
				return 0;
			}
		}
		
		if (starter.getSuit() == cards.get(0).getSuit()) {
			return 5;
		} else {
			return 4;
		}
	}
	
	/**
	 * This function represents nob scoring.
	 * If a jack is in a players hand and is same SUIT as the
	 * starter card - player gets a point.
	 * 
	 * (public for testing purposes)
	 * 
	 * @param cards
	 * @param starter
	 * @return nob score (1 / 0)
	 */
	public int scoreNob(ArrayList<Card> cards, Card starter) {
		for (Card card : cards) {
			if (card.getRank() == Rank.JACK) {
				if (card.getSuit() == starter.getSuit()) {
					return 1;
				}
			}
		}
		return 0;
	}
	
}
