package ui;

import java.util.ArrayList;
import java.util.Scanner;

import Player.ComputerPlayer;
import Player.HumanPlayer;
import Player.Player;
import model.Card;
import model.Game;

/**
 * The TextViewer class provides a console-based interface for the Cribbage game.
 * It serves as an alternative to the Swing-based GUI (`GuiViewer`).
 * This class handles:
 * - Displaying the game state in the terminal
 * - Prompting human players for input
 * - Simulating interactions with computer players
 */
public class TextViewer {
    
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the player's current hand to the terminal.
     *
     * @param player the player whose hand is displayed
     */
    public void showHand(Player player) {
        System.out.println(player.getName() + ": ");
        for (int i = 1; i <= player.getHand().size(); i++) {
            System.out.println(i + ". " + player.getHand().get(i - 1));
        }
    }

    /**
     * Handles the discard phase for the player.
     * Prompts human players for a valid card index.
     * Delegates to computer logic if player is AI.
     *
     * @param player the player discarding a card
     * @param dealer true if the player is the dealer
     * @return the 1-based index of the card to discard
     */
    public int discard(Player player, boolean dealer) {
        if (player instanceof HumanPlayer) {
            while (true) {
                System.out.print(player.getName() + " - choose card to discard: ");
                if (scanner.hasNextInt()) {
                    int selection = scanner.nextInt();
                    if (selection >= 1 && selection <= player.getHand().size()) {
                        return selection;
                    } else {
                        System.out.println("Invalid - Please select a valid card number.");
                    }
                } else {
                    System.out.println("Invalid - Please enter a number.");
                    scanner.next();
                }
            }
        } else {
            return ((ComputerPlayer) player).discardIndex(dealer);
        }
    }

    /**
     * Displays the contents of the crib in the terminal.
     *
     * @param crib the list of cards in the crib
     */
    public void showCrib(ArrayList<Card> crib) {
        System.out.println("\nCrib:");
        for (Card card : crib) {
            System.out.println(card);
        }
        System.out.print("\n");
    }

    /**
     * Displays the current total of the play stack.
     *
     * @param val the point total of the play sequence
     */
    public void showPlayTotal(int val) {
        System.out.println("Play Stack Total: " + val);
    }

    /**
     * Handles the play phase for a player.
     * Human players are prompted for legal moves (≤ 31 total).
     * AI players choose based on strategy.
     *
     * @param player the player whose turn it is
     * @param total the current point total of the play stack
     * @param sequence the sequence of cards played so far
     * @param stackSum the total point sum of the current stack
     * @return the 1-based index of the chosen card, or -1 if the player can't play
     */
    public int playCard(Player player, int total, ArrayList<Card> sequence, int stackSum) {
        ArrayList<Card> cards = player.getHand();
        System.out.print(player.getName() + "'s Turn - Play a card:");

        boolean playable = false;
        for (Card card : cards) {
            if (card.getRank().getValue() + total <= 31) {
                playable = true;
                break;
            }
        }

        if (!playable) return -1;

        if (player instanceof HumanPlayer) {
            while (true) {
                int selection = scanner.nextInt();
                if (selection >= 1 && selection <= cards.size()) {
                    Card card = cards.get(selection - 1);
                    if (card.getRank().getValue() + total <= 31) {
                        return selection;
                    } else {
                        System.out.println(card + " exceeds 31, choose a different card.");
                    }
                } else {
                    System.out.println("Invalid input.");
                }
            }
        } else {
            return ((ComputerPlayer) player).getPlayIndex(sequence, stackSum);
        }
    }

    /**
     * Logs a message showing what card was played and the new play total.
     *
     * @param player the player who played the card
     * @param card the card played
     * @param total the new total of the play stack
     */
    public void showTurn(Player player, Card card, int total) {
        System.out.println(player.getName() + " plays " + card + " | Play Stack Total: " + total);
    }

    /**
     * Logs when a player says "Go".
     *
     * @param player the player who cannot play
     */
    public void sayGo(Player player) {
        System.out.println(player.getName() + " says 'Go'..");
    }

    /**
     * Logs when a player receives a point because the opponent said "Go".
     *
     * @param player the player who receives the point
     */
    public void goReward(Player player) {
        System.out.println(player.getName() + " scores 1 point for other player's 'Go'!");
    }

    /**
     * Logs when a player scores a point for playing the last card.
     *
     * @param player the player who gets the point
     */
    public void showLastCardPoint(Player player) {
        System.out.println(player.getName() + " scores 1 point for last card.");
    }

    /**
     * Logs when a player scores 2 points for reaching a play total of 31.
     *
     * @param player the player who reached 31
     */
    public void special31(Player player) {
        System.out.println(player.getName() + " scores 2 points for 31!");
    }

    /**
     * Prompts the player whether they want to play another game.
     *
     * @return true if the player says yes, false if no
     */
    public boolean playAgain() {
        while (true) {
            System.out.print("Play Again (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.startsWith("y")) {
                return true;
            } else if (input.startsWith("n")) {
                return false;
            } else {
                System.out.println("Please enter y-(yes) or n-(no).");
            }
        }
    }
}
