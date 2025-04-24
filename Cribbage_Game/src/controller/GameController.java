package controller;

import Player.Player;
import model.Card;
import model.Game;
import model.Rank;
import ui.GuiViewer;
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
	private GuiViewer view;
	
	/** 
	 * Each instance of the game requires a Game model - which contains
	 * game state, which the controller acts on. It also accepts a viewer
	 * that should primarily focus on communicating and accepting input
	 * from the user.
	 * 
	 * @param model Game Object
	 * @param view  Requisite Viewer
	 */
	public GameController(Game model, GuiViewer view) {
		this.model = model;
		this.view = view;
	}
	
	/**
	 * This function represents the main game loop. 
	 * - It starts with a loop that checks if either player in the Game 
	 * has a score at or over 61, and ends if either meets this condition.
	 * 
	 * - It then begins the 'Rounds' as Game.startRound() switches dealers,
	 * clears player's hands/played cards, clears the crib, resets the sequence 
	 * stack (and associated stack point total), sets a new and shuffled deck, 
	 * and deals 6 cards to each player.
	 * 
	 * - It prompts each player to then discard two cards via the promptDiscard
	 * function. The chosen cards are immediately added to the Game crib.
	 * 
	 * - A starter card is then drawn and assigned from the deck.
	 * 
	 * - Then the game plays out through the playPhase(), allowing players to
	 * play cards, before doing a final round of scoring. 
	 * 
	 * Note: Any print lines and TextViewer calls should be replaced by GUI,
	 * or increased-complexity viewer. These are meant to show game states
	 * via a text based viewer as a rough draft, and testing purposes.
	 */
	public void startGame() {
		while (!model.gameOver()) {
			model.startRound();
			view.logMessage("-- Starting Round -- ");
			
			promptDiscard(model.getPlayer1(), model.getDealer()==model.getPlayer1());
			promptDiscard(model.getPlayer2(), model.getDealer()==model.getPlayer2());
			
			view.showCrib(model.showCrib());
			
			view.logMessage("--- Starter Card ---");
			starterCard();
			
			view.logMessage("--- Play Phase ---");
			playPhase();
			
			view.logMessage(model.getPlayer1().getName() + " Score: " + model.getPlayer1().getScore());
			view.logMessage(model.getPlayer2().getName() + " Score: " + model.getPlayer2().getScore());
		}
	}
	
	/**
	 * This function prompts a player, through the viewer, to discard two cards
	 * based on index representation of the cards in their hand.
	 * 
	 * @param player Desired player to discard
	 */
	private void promptDiscard(Player player, Boolean dealer) {
		for (int i=0; i < 2; i++) {
			view.showHand(player);
			int choice = view.discard(player, dealer);
			Card card = model.cribCard(player.discard(choice - 1));
			view.logMessage(player.getName() + " added " + card + " to crib.");
		}
	}
	
	/**
	 * This basic function draws and assigns a starter card. If it is a jack, the
	 * Game.drawStarter() function automatically gives the current dealer 2 points.
	 * Viewer should reflect this.
	 */
	private void starterCard() {
		Card starter = model.drawStarter();
		view.logMessage("Starter Card: " + starter.toString());
		if (starter.getRank().equals(Rank.JACK)) {
			view.logMessage("Dealer: " + model.getDealer().getName() + ", +2 points.");
			view.logMessage(model.getDealer().getName() + " total: " + model.getDealer().getScore());
		}
	}
	
	/**
	 * This complicated function represents the core card play loop.
	 * - It begins by determining who the dealer is through the Game
	 * state, setting who should take the first turn. Then, while each
	 * player has cards to play, it runs:
	 * - It checks if either player can actually play any cards in
	 * their hand that wont cause the play stack to go over 31 points.
	 * - If neither can play, it allocates 1 point to the player that
	 * played the last card, before reseting the play stack points
	 * and sequence (for determining viable runs).
	 * - If the active turn player can play, it displays their hand
	 * via the viewer and the play stack point total.
	 * - It prompts the user to choose a card in their hand to play,
	 * assigning that specific card to add it to the sequence, and its' 
	 * rank value to the play stack point total. It also adds that card
	 * to the players played cards, and sets the player as the last player
	 * to play a card.
	 * - It then runs a quick active point check for pairs or runs in the
	 * sequence through the Game state functions checkPairs() and checkRuns().
	 * - If either return an integer value, it adds those 'peg' points to the 
	 * active turn player's points and displays them through the viewer.
	 * - It also checks if the play stack point total reaches 15, or 31 (proceeds
	 * to reset the play stack and stack points in this case).
	 * - If the active player cannot play a card, but has cards still in their
	 * hand, it checks the opponents hand.
	 * - If the opponent cannot play, but also has cards in their hand, it 
	 * triggers a 'Go', point allocation, and reset.
	 * - The end of the loop switches the active turn to the opponent, when
	 * appropriate.
	 * - Finally, it does a last played check, and runs the score allocations
	 * for the show / crib.
	 * 
	 */
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
		    	        view.logMessage(lastPlayerToPlay.getName() + " gets 1 point for last card.");
		    	        lastPlayerToPlay.addPoints(1);
		    	    }
		    	    model.resetTotal();
		    	    model.resetSequence();
		    	    continue;
		    	} else if (model.handCheck(activeTurn)) {
		    		view.showHand(activeTurn);
		    		view.showPlayTotal(model.pointTotal());
		    		int choice = view.playCard(activeTurn, model.pointTotal(), model.getSequence(), model.getTotal());
		    		Card card = activeTurn.discard(choice - 1);
		    		model.addToTotal(card.getRank().getValue());
		    		model.addToSequence(card); // Sequence Scoring
		    		activeTurn.addToPlayed(card); // Hand scoring at end
		    		lastPlayerToPlay = activeTurn;
		    		
		    		view.logMessage(activeTurn.getName() + " plays " + card + " | Total: " + model.pointTotal());
		    		
		    		int pairScore = model.checkPairs();
		    		if (pairScore > 0) {
		    			view.logMessage(activeTurn.getName() + " scored " + pairScore + " from pairs!");
		    			activeTurn.addPoints(pairScore);
		    		}
		    		int runScore = model.checkRuns();
		    		if (runScore > 0) {
		    			view.logMessage(activeTurn.getName() + " scored " + runScore + " from a run!");
		    			activeTurn.addPoints(runScore);
		    		}
		    		
		    		if (model.pointTotal() == 15) {
		    			view.logMessage("15! " + activeTurn.getName() + " gets 1 point.");
		    		}
		    		
		    		if (model.pointTotal() == 31) {
		    			view.logMessage("31 Reached! " + activeTurn.getName() + " gets 2 points.");
		    			activeTurn.addPoints(2);
		    			model.resetTotal();
		    			model.resetSequence();
		    		}
		    	} else {
		    		if (!activeTurn.getHand().isEmpty()) {
		    			view.logMessage(activeTurn.getName() + " Says Go..");
		    			if (!model.handCheck(opponent) && !opponent.getHand().isEmpty()) {
		    				view.logMessage(opponent.getName() + " pegs 1 for Go");
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
		    	view.logMessage(lastPlayerToPlay.getName() + " gets 1 point for last card.");
		    	lastPlayerToPlay.addPoints(1);
		    }
		    
		    int score1 = model.scoreHand(activeTurn, false);
		    if (score1 > 0) {
		    	view.logMessage(activeTurn.getName() + " scored " + score1 + " from show!");
		    	activeTurn.addPoints(score1);
		    }
		    int score2 = model.scoreHand(opponent, false);
		    if (score2 > 0) {
		    	view.logMessage(opponent.getName() + " scored " + score2 + " from show!");
		    	opponent.addPoints(score2);
		    }
		    int scoreCrib = model.scoreHand(model.getDealer(), true);
		    if (scoreCrib > 0) {
		    	view.logMessage("(Dealer) " + model.getDealer().getName() + " scored " + scoreCrib + " from show!");
		    	model.getDealer().addPoints(scoreCrib);
		    }
	}
	
	/**
	 * This function creates a game loop for multiple games (wins / losses).
	 * 
	 * Wins favor whoever gets 61, or highest score. Ties are a wash for 
	 * both players.
	 */
	public void playCribbage() {
		boolean playAgain = true;
		
		while(playAgain) {
			// reset player scores
			model.getPlayer1().resetScore();
			model.getPlayer2().resetScore();
			
			startGame();
			
			if (model.getPlayer1().getScore() >= 61 && model.getPlayer2().getScore() < 61) {
				view.logMessage(model.getPlayer1().getName() + " wins!");
				model.getPlayer1().recordWin();
				model.getPlayer2().recordLoss();
			}
			else if (model.getPlayer2().getScore() >= 61 && model.getPlayer1().getScore() < 61) {
				view.logMessage(model.getPlayer2().getName() + "wins!");
				model.getPlayer1().recordLoss();
				model.getPlayer2().recordWin();
			}
			else if (model.getPlayer1().getScore() > model.getPlayer2().getScore()){
				view.logMessage(model.getPlayer1().getName() + " wins!");
				model.getPlayer1().recordWin();
				model.getPlayer2().recordLoss();
			}
			else if (model.getPlayer2().getScore() > model.getPlayer1().getScore()) {
				view.logMessage(model.getPlayer2().getName() + "wins!");
				model.getPlayer1().recordLoss();
				model.getPlayer2().recordWin();
			}
			else {
				view.logMessage("Tie!");
			}
			view.logMessage(model.getPlayer1().getName() + ": Wins(" + model.getPlayer1().getWins() + ") | Losses (" + model.getPlayer1().getLosses()+")");
			view.logMessage(model.getPlayer2().getName() + ": Wins(" + model.getPlayer2().getWins() + ") | Losses (" + model.getPlayer2().getLosses()+")");
			playAgain = view.playAgain();
			
		}
	}
}
		   

