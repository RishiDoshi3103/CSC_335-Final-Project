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
		
	}
	
}
