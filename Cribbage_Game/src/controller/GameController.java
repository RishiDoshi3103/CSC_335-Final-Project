package controller;

import Player.Player;
import model.Card;
import model.Game;
import model.Rank;
import ui.TextViewer;

/**
 * 
 *  Score Reference
 *  Scoring is seemingly broken into four different phases: the pre-play that accounts
 *  for only the start card, play where cards are considered at each turn, the show at
 *  the end for full hand analysis, and the crib at the end for the dealer. Dealer 
 *  switches off each round.
 *  
 *  Pre-Play:
 *   - If the start card is a Jack, the dealer gets............................2 point
 *   
 *  During Play: 
 *   - If one player says "go", the other player gets..........................1 point
 * 	 - Playing the last card of a round that doesn't go over 31................1 point
 *   - Playing a card that causes the total to equal 15 or 31..................2 points
 *   - Playing the same RANK as the last played card (pair)....................2 points
 *   - Playing a third same RANK card as the last two (pair royal).............6 points
 *   - Playing a fourth same RANK card as the last three (Double pair royal)...12 points
 *   - Playing the third (or more) card of a consecutive run of RANKs 
 *     (i.e, 2♡, 3♧, 4♤.. etc), grants points equal to the total cards in
 *     that run (3 consecutive cards gives 3 points. 4 grants 4 points, etc)...3+ points
 *     ** Note: they don't have to be in direct order (2, 4, 3 counts, so 
 *     long as there isn't another number in between them that wouldn't  
 *     qualify: 2, 8, 4, 3 doesn't count) **
 *   
 *   The show: (Scores based on individual hands and start card - includes dealer)
 *   - Any combination of cards adding up to 15 (can reuse cards)..............2 points
 *   - A pair (two of the same RANK)...........................................2 points
 *   - Three of a kind (three of the same RANK)................................6 points
 *   - Four of a kind (four of the same RANK)..................................12 points
 *   - The longest consecutive run of RANKS (similar to play) grants
 *     points equal to the total cards in that run.............................3+ points
 *     ** Note: smaller runs in a bigger run do not count, only the longest
 *     consecutive streak is counted.
 *   - If all 4 cards in hand are same SUIT (Flush)............................4 points
 *   - If all 4 cards in hand AND the start card are same SUIT (Flush).........5 points
 *   - If a Jack is in players hand and is same SUIT as start card.............1 point
 *   
 *   The Crib:
 *   - Same as the show, except can only get a flush if all 4 in the crib, and the
 *   start cards are same SUIT.
 */


public class GameController {
	private Game model;
	private TextViewer view;
	
	public GameController(Game model, TextViewer view) {
		this.model = model;
		this.view = view;
	}
	
	public void startGame() {
		while (!model.gameOver()) {
			model.startRound();
			System.out.println("--- Starting Round ---");
			
			promptDiscard(model.getPlayer1());
			promptDiscard(model.getPlayer2());
			
			view.showCrib(model.showCrib());
			
			//view.showHand(model.getPlayer1());
			//view.showHand(model.getPlayer2());
			
			System.out.println("--- Starter Card ---");
			starterCard();
			
			System.out.println("--- Play Phase ---");
			playPhase();
			
			System.out.println(model.getPlayer1().getName() + " Score: " + model.getPlayer1().getScore());
			System.out.println(model.getPlayer2().getName() + " Score: " + model.getPlayer2().getScore());
		}
	}
	
	private void promptDiscard(Player player) {
		for (int i=0; i < 2; i++) {
			view.showHand(player);
			int choice = view.discard(player);
			Card card = model.cribCard(player.discard(choice - 1));
			System.out.println(player.getName() + " added " + card + " to crib.");
		}
	}
	
	private void starterCard() {
		Card starter = model.drawStarter();
		System.out.println("Starter Card: " + starter.toString());
		if (starter.getRank().equals(Rank.JACK)) {
			System.out.println("Dealer: " + model.getDealer().getName() + ", +2 points.");
			System.out.println(model.getDealer().getName() + " total: " + model.getDealer().getScore());
		}
	}
	
	private void playPhase() {
		    Player activeTurn;
		    Player opponent;
		    Player lastPlayerToPlay = null;
		    
		    // Determine who starts the turn based on who is the dealer
		    if (model.getDealer() == model.getPlayer1()) {
		        activeTurn = model.getPlayer2();
		        opponent = model.getPlayer1();
		    } else {
		        activeTurn = model.getPlayer1();
		        opponent = model.getPlayer2();
		    }
		    
		    while (!(model.getPlayer1().getHand().isEmpty() && model.getPlayer2().getHand().isEmpty())) {
		    	// Check if both players can't play (Go-Go situation)
		    	if (!model.handCheck(activeTurn) && !model.handCheck(opponent)) {
		    	    if (lastPlayerToPlay != null) {
		    	        System.out.println(lastPlayerToPlay.getName() + " gets 1 point for last card.");
		    	        lastPlayerToPlay.addPoints(1);
		    	    }
		    	    model.resetTotal();
		    	    model.resetSequence();
		    	    continue;
		    	} else if (model.handCheck(activeTurn)) {
		    		view.showHand(activeTurn);
		    		view.showPlayTotal(model.pointTotal());
		    		int choice = view.playCard(activeTurn, model.pointTotal());
		    		Card card = activeTurn.discard(choice - 1);
		    		model.addToTotal(card.getRank().getValue());
		    		model.addToSequence(card); // Sequence Scoring
		    		activeTurn.addToPlayed(card); // Hand scoring at end
		    		lastPlayerToPlay = activeTurn;
		    		
		    		System.out.println(activeTurn.getName() + " plays " + card + " | Total: " + model.pointTotal());
		    		
		    		int pairScore = model.checkPairs();
		    		if (pairScore > 0) {
		    			System.out.println(activeTurn.getName() + " scored " + pairScore + " from pairs!");
		    			activeTurn.addPoints(pairScore);
		    		}
		    		int runScore = model.checkRuns();
		    		if (runScore > 0) {
		    			System.out.println(activeTurn.getName() + " scored " + runScore + " from a run!");
		    			activeTurn.addPoints(runScore);
		    		}
		    		
		    		if (model.pointTotal() == 15) {
		    			System.out.println("15! " + activeTurn.getName() + " gets 1 point.");
		    		}
		    		
		    		if (model.pointTotal() == 31) {
		    			System.out.println("31 Reached! " + activeTurn.getName() + " gets 2 points.");
		    			activeTurn.addPoints(2);
		    			model.resetTotal();
		    			model.resetSequence();
		    		}
		    	} else {
		    		if (!activeTurn.getHand().isEmpty()) {
		    			System.out.println(activeTurn.getName() + " Says Go..");
		    			if (!model.handCheck(opponent) && !opponent.getHand().isEmpty()) {
		    				System.out.println(opponent.getName() + " pegs 1 for Go");
		    				opponent.addPoints(1);
		    				model.resetTotal();
		    				model.resetSequence();
		    			}
		    		}
		    	}
		    	
		    	Player swap = activeTurn;
		    	activeTurn = opponent;
		    	opponent = swap;
		    }
		    
		    if (model.pointTotal() != 0 && model.pointTotal() < 31 && lastPlayerToPlay != null) {
		    	System.out.println(lastPlayerToPlay.getName() + " gets 1 point for last card.");
		    	lastPlayerToPlay.addPoints(1);
		    }
		    
		    int score1 = model.scoreHand(activeTurn, false);
		    if (score1 > 0) {
		    	System.out.println(activeTurn.getName() + " scored " + score1 + " from show!");
		    	activeTurn.addPoints(score1);
		    }
		    int score2 = model.scoreHand(opponent, false);
		    if (score2 > 0) {
		    	System.out.println(opponent.getName() + " scored " + score2 + " from show!");
		    	opponent.addPoints(score2);
		    }
		    int scoreCrib = model.scoreHand(model.getDealer(), true);
		    if (scoreCrib > 0) {
		    	System.out.println("(Dealer) " + model.getDealer().getName() + " scored " + scoreCrib + " from show!");
		    	model.getDealer().addPoints(scoreCrib);
		    }
	}
}
		   

