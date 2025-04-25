package ui;

import java.util.Scanner;

/**
 * The GameStarter class is responsible for setting up the initial game configuration.
 * It provides CLI prompts to the user to:
 * - Select the game mode (Human vs Human, Human vs Computer, Computer vs Computer)
 * - Enter player names (for human players)
 * - Choose computer player strategy (Easy or Hard)
 *
 * It handles all user input required before the game starts.
 */
public class GameStarter {

    private Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user to choose a game mode via console.
     *
     * @return an integer representing the selected mode:
     *         1 = Human vs Human,
     *         2 = Human vs Computer,
     *         3 = Computer vs Computer
     */
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

    /**
     * Gets the names of the players based on the selected game mode.
     * Prompts for human player names if applicable.
     *
     * @param mode the selected game mode (1, 2, or 3)
     * @return an array of player names [Player1Name, Player2Name]
     */
    public String[] getPlayerNames(int mode) {
        String[] names = new String[2];
        scanner.nextLine(); // Clear input buffer

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

    /**
     * Prompts the user to choose a strategy for a given computer player.
     *
     * @param name the name of the computer player
     * @return 1 for Easy strategy, 2 for Hard strategy
     */
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
