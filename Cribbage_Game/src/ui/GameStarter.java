package ui;

import java.util.Scanner;

public class GameStarter {
	private Scanner scanner = new Scanner(System.in);

	public int GameMode() {
		System.out.println("-- CRIBBAGE --");
		while (true) {
			System.out.println("Options:");
			System.out.println("Enter 1 for Human vs. Human");
			System.out.println("Enter 2 for Human vs. Computer");
			System.out.println("Enter 3 for Computer vs. Computer");
			if (scanner.hasNextInt()) {
				int selection = scanner.nextInt();
				if (selection >= 1 && selection <= 3) {
					return selection;
				}
			}
			System.out.println("Please enter valid input:");
		}
	}

	public String[] getPlayerNames(int mode) {
		String[] names = new String[2];
		scanner.nextLine(); // Clear buffer

		if (mode == 1) {
			System.out.print("Enter name for Player 1 (Human): ");
			names[0] = scanner.nextLine();
			System.out.print("Enter name for Player 2 (Human): ");
			names[1] = scanner.nextLine();
		} else if (mode == 2) {
			System.out.print("Enter name for Player 1 (Human): ");
			names[0] = scanner.nextLine();
			names[1] = "Computer";
		} else {
			names[0] = "Computer1";
			names[1] = "Computer2";
		}
		return names;
	}

	public static int getStrat(String name) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Computer Player '" + name + "' Strategy Options:");
			System.out.println("Enter 1 for Easy Mode");
			System.out.println("Enter 2 for Hard Mode");
			if (scanner.hasNextInt()) {
				int selection = scanner.nextInt();
				if (selection == 1 || selection == 2) {
					return selection;
				}
			}
			System.out.println("Please enter valid input:");
		}
	}
}
