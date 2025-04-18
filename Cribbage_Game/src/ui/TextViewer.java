package ui;

import java.util.ArrayList;
import java.util.Scanner;

import Player.Player;
import model.Card;

public class TextViewer {
	private Scanner scanner = new Scanner(System.in);
	
	public void showHand(Player player) {
		System.out.println(player.getName() + ": ");
		for (int i=1; i < player.getHand().size()+1; i++) {
			System.out.println(i + ". "+ player.getHand().get(i-1).toString());
		}
	}
	
	public int discard(Player player) {
		System.out.print(player.getName() + " - choose card to discard: ");
		int selection = scanner.nextInt();
		return selection; 
	}
	
	public void showCrib(ArrayList<Card> crib) {
		System.out.println("Crib: ");
		for (Card card : crib) {
			System.out.println(card.toString());
		}
	}
}
