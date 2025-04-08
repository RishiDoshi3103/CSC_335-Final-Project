package controller;

import java.util.ArrayList;
import java.util.List;

import Model.Card;
import Model.Deck;
import Model.Player;

public class Game_Controller {
	/*
	 * IMPORTANT IMPLEMENTATION NOTES:
	 * 		New/Updated methods in other classes that this requires:
	 * 			- Player.play(Card)				Player automatically plays card Card
	 * 			- Player.incrementPoints(int)	Update player's points. Requires new private field.
	 * 			- Player.getPoints()			Returns int value of player's points
	 * 			- Player.resetPoints()			Sets player's points to 0
	 * 
	 * 			- Hand.getMinVal()				Return int value of lowest cribbage-valued card in hand
	 * 
	 * 		
	 * 
	 * 
	 * 
	 * 52 cards (flyweight)
	 * Both players’ points = 0
	 * While (playerAPoints < 61 && playerBPoints < 61) {
	 * 		Dealer (perhaps in the controller/model package)
	 * 			6 car initial (hands)
	 * 				Each discard 2 cards for Crib
	 * 				1 card from deck is starter
	 * 			“go” = false
	 * 			While (Players have cards) {
	 * 				Swap = false
	 * 				If (go) {
	 * 					If (current player can play one card to reach EXACTLY 31)
	 * 						Current player automatically plays this card.
	 * 						Current player gets 2 points
	 * 						Swap = true
	 * 					Else
	 * 						Current player gets 1 point
	 * 					Count = 0
	 * 					“go” = false;
	 * 				If ( (current player’s lowest card) + count > 31)
	 * 					Set “go” = true
	 * 				Else if (swap = false) {
	 * 					Current player turn
	 * 					If (count = 15)
	 * 						Current player gets 2 points
	 * 				}
	 * 				Swap player
	 * 			}
	 * 		End of round - both players are out of cards.
	 * 		Tally hand points
	 * 		Swap dealer.
	 * }
	 * Player with 61+ points wins the game.
	 */
	
	private Player player1;
	private Player player2;
	private Player curPlayer;
	private Player dealer;
	private Player lastWinner;
	
	private int tally;
	private boolean go;
	
	private boolean playAgain;
	
	
	
	public Game_Controller(String nameA, String nameB) {
		this.player1 = new Player(nameA);
		this.player2 = new Player(nameB);
		
		// TODO: Simulate cutting the deck???
		int val1 = 0;
		int val2 = 0;
		while (val1 == val2) {
			val1 = Deck.drawCard().getCribbageValue();
			val2 = Deck.drawCard().getCribbageValue();
		}
		
		// Assign temporary value to initialize play
		if (val1 > val2) {
			// This will cause player1 to deal first
			this.lastWinner = player2;
		}
		else {
			// This will cause player2 to deal first
			this.lastWinner = player1;
		}
		
		// Loop until the players no longer want to play
		while (playAgain) {
			// TODO: Add a way for players to quit
			
			// Set players' points to 0
			player1.resetPoints();
			player2.resetPoints();
			// Set it up opposite; will get changed in setController()
			if (lastWinner == player1) {
				dealer = player1;
			}
			else {
				dealer = player2;
			}
			setController();
		}
	}
	
	
	
	public void setController() {
		while (player1.getPoints() < 61 && player2.getPoints() < 61) {
			// Swap Dealer and first player
			if (dealer == player1) {
				dealer = player2;
				curPlayer = player1;
			}
			else {
				dealer = player1;
				curPlayer = player2;
			}
			
			// 6 cards initial (hands)
			player1.clearHand();
			player2.clearHand();
			for (int i = 0; i < 6; i ++) {
				player1.addCard(Deck.drawCard());
				player2.addCard(Deck.drawCard());
			}
			
			// TODO: Each discard 2 cards for Crib
			
			roundController();
		}
					
	}
	
	
	
	public void roundController() {
		tally = 0;
		// While (Players have cards)
		while (player1.getHand().getCards().size() > 0 || player2.getHand().getCards().size() > 0) {
			if (curPlayer == player2) {
				curPlayer = player1;
			}
			else {
				curPlayer = player2;
			}
			turnController(curPlayer);
		}
		
		// TODO: Count & increment hand points for each player.
	}
	
	
	
	public void turnController(Player curPlayer) {
		// Swap = false;
		go = false;
		// Check if the player can play. If not, return.
		if (curPlayer.getHand().getMinVal() + tally > 31) {
			go = true;
			return;
		}
		// If (go) {
		// 		If (current player can play one card to reach EXACTLY 31)
		// 			Current player automatically plays this card.
		// 			Current player gets 2 points
		// 			Swap = true
		// 		Else
		// 			Current player gets 1 point
		// 		Count = 0
		// 		“go” = false;
		if (go) {
			// Check if the player can hit exactly 31.
			Card canReach31 = null;
			for (Model.Card card : curPlayer.getHand().getCards()) {
				if (card.getCribbageValue() + tally == 31) {
					canReach31 = card;
				}
			}
			
			// If (current player can play one card to reach EXACTLY 31)
			if (canReach31 != null) {
				curPlayer.play(canReach31);
				curPlayer.incrementPoints(2);
			}
			
			else {
				curPlayer.incrementPoints(1);
			}
			tally = 0;
			go = false;
		}
		// Current player turn
		// 		If (count = 15) {
		// 			Current player gets 2 points
		// 		}
		else {
			tally += curPlayer.playerTurn();
			if (tally == 15) {
				curPlayer.incrementPoints(2);
			}
		}
	}
}
