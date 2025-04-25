package ui;

import java.util.ArrayList;
import java.util.Scanner;

import Player.ComputerPlayer;
import Player.HumanPlayer;
import Player.Player;
import model.Card;
import model.Game;

public class TextViewer {
	private Scanner scanner = new Scanner(System.in);
	
	public void showHand(Player player) {
		System.out.println(player.getName() + ": ");
		for (int i=1; i < player.getHand().size()+1; i++) {
			System.out.println(i + ". "+ player.getHand().get(i-1).toString());
		}
	}
	
	public int discard(Player player, boolean dealer) {
		if (player instanceof HumanPlayer) {
			while (true) {
				System.out.print(player.getName() + " - choose card to discard: ");
				if (scanner.hasNextInt()) {
					int selection = scanner.nextInt();
					if (selection >= 1 && selection <= player.getHand().size()) {
						return selection;
					}
					else {
						System.out.println("Invalid - Please select a valid card number.");
					}
				} 
				else {
					System.out.println("\"Invalid - Please select a valid card number.");
					scanner.next();
				} 
			}
		}
		else {
			return ((ComputerPlayer)player).discardIndex(dealer);
		}
	}
	
	public void showCrib(ArrayList<Card> crib) {
		System.out.println("\nCrib: ");
		for (Card card : crib) {
			System.out.println(card.toString());
		}
		System.out.print("\n");
	}
	
	public void showPlayTotal(int val) {
		System.out.println("Play Stack Total: " + val);
	}
	
	public int playCard(Player player, int total, ArrayList<Card> sequence, int stackSum) {
		ArrayList<Card> cards = player.getHand();
		
		System.out.print(player.getName() + "'s Turn - Play a card:");
		
		boolean playable = false;
		for (int i=0; i < cards.size(); i++) {
			if (cards.get(i).getRank().getValue() + total <= 31) {
				playable = true;
			}
		}
		
		if (playable == false) {
			return -1;
		}
		
		if (player instanceof HumanPlayer) {
			while (true) {
				int selection = scanner.nextInt();
				
				if (selection >= 1 && selection <= cards.size()) {
					Card card = cards.get(selection - 1);
					if (card.getRank().getValue() + total <= 31) {
						return selection;
					} else {
						System.out.println(card.toString() + " exceeds 31, choose a different card.");
					}
				} else {
					System.out.println("Invalid input.");
				}
			}
		}
		else {
			return ((ComputerPlayer) player).getPlayIndex(sequence, stackSum);
		}
	}
	
	public void showTurn(Player player, Card card, int total) {
		System.out.println(player.getName() + " plays " + card.toString() + " | Play Stack Total: " + total);
	}
	
	public void sayGo(Player player) {
		System.out.println(player.getName() + " says 'Go'..");
	}
	
	public void goReward(Player player) {
		System.out.println(player.getName() + " scores 1 point for other player's 'Go'!");
	}
	public void showLastCardPoint(Player player) {
        System.out.println(player.getName() + " scores 1 point for last card.");
    }
	
	public void special31(Player player) {
		System.out.println(player.getName() + " scores 2 points for 31!");
	}
	
	public boolean playAgain() {
		while (true) {
			System.out.print("Play Again (y/n): ");
			String input = scanner.nextLine().trim().toLowerCase();
			if (input.startsWith("y")) {
				return true;
			}
			else if (input.startsWith("n")) {
				return false;
			}
			else {
				System.out.println("Please enter y-(yes) or n-(no).");
			}
		}
	
		
	}
}
