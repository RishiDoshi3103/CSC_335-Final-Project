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
				if (scanner.nextInt() == 1) {
					return 1;
				}
				else if (scanner.nextInt() == 2) {
					return 2;
				}
				else if (scanner.nextInt() == 3) {
					return 3;
				}
			}
			System.out.println("Please enter valid input:");
		}
	}

	public static int getStrat(String name) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Computer Player '" + name + "' Strategy Options:");
			System.out.println("Enter 1 for Easy Mode");
			System.out.println("Enter 2 for Hard Mode");
			if (scanner.hasNextInt()) {
				if (scanner.nextInt() == 1) {
					return 1;
				}
				else if (scanner.nextInt() == 2) {
					return 2;
				}
			}
			System.out.println("Please enter valid input:");
		}
	}
}
