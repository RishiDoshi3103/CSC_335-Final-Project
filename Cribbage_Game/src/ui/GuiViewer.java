package ui;

import Player.Player;
import Player.HumanPlayer;
import Player.ComputerPlayer;
import model.Card;
import observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This GUI viewer class was created with assistance by AI, but edited to fit
 * our specific program requirements following the OBSERVER design pattern.
 */
public class GuiViewer extends JLabel implements Observer {
	private JFrame frame;
	private JTextArea log;
	private JPanel cardPanel;
	private JLabel playTotalLabel;

	private int selectedIndex = -1;
	private boolean waitingForInput = false;

	/**
 	* Constructs the GUI components and sets up the main game window.
 	*/
	public GuiViewer() {
		frame = new JFrame("Cribbage");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);
		frame.setLayout(new BorderLayout());

		log = new JTextArea();
		log.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(log);

		cardPanel = new JPanel();
		cardPanel.setLayout(new FlowLayout());

		playTotalLabel = new JLabel("Play Stack Total: 0");

		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(playTotalLabel, BorderLayout.WEST);

		frame.add(scrollPane, BorderLayout.CENTER);
		frame.add(cardPanel, BorderLayout.NORTH);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	/**
 	* Blocks the thread and waits for user input via a card button.
 	* This method uses synchronization to pause execution until a button click triggers a notify.
 	*/
	private void waitForUserInput() {
		synchronized (this) {
			waitingForInput = true;
			while (waitingForInput) {
				try {
					wait();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	/**
 	* Displays the given player's hand in the GUI using buttons.
 	* When a button is clicked, the index of the selected card is stored for input.
 	*
 	* @param player the player whose hand will be displayed
 	*/
	public void showHand(Player player) {
		logMessage(player.getName() + "'s Hand:");
		cardPanel.removeAll();

		ArrayList<Card> hand = player.getHand();
		for (int i = 0; i < hand.size(); i++) {
			JButton cardButton = new JButton(hand.get(i).toString());
			int index = i;
			cardButton.addActionListener(e -> {
				selectedIndex = index;
				synchronized (this) {
					waitingForInput = false;
					notifyAll();
				}
			});
			cardPanel.add(cardButton);
		}

		cardPanel.revalidate();
		cardPanel.repaint();
	}

	/**
	 * Prompts the user or computer player to discard a card.
	 * For a human player, waits for user input via GUI button.
	 * For a computer player, calls its strategy logic.
	 *
	 * @param player the player discarding the card
	 * @param dealer true if the player is the current dealer
	 * @return the 1-based index of the selected card
	 */
	public int discard(Player player, boolean dealer) {
		if (player instanceof HumanPlayer) {
			selectedIndex = -1;
			waitForUserInput();
			return selectedIndex + 1;
		} else {
			return ((ComputerPlayer) player).discardIndex(dealer);
		}
	}

	/**
	 * Displays the cards currently in the crib log area.
	 *
	 * @param crib the list of cards in the crib
	 */
	public void showCrib(ArrayList<Card> crib) {
		logMessage("\nCrib:");
		for (Card c : crib) {
			logMessage(c.toString());
		}
	}

	/**
	 * Updates the "Play Stack Total" label in the GUI.
	 *
	 * @param val the current point total of the play sequence
	 */
	public void showPlayTotal(int val) {
		playTotalLabel.setText("Play Stack Total: " + val);
	}

	/**
	 * Handles the play card phase for a player.
	 * Displays only playable cards (value ≤ 31).
	 * For human players, waits for input. For computers, uses AI strategy.
	 *
	 * @param player the player whose turn it is
	 * @param total the current point total of the play stack
	 * @param sequence the sequence of cards played so far
	 * @param stackSum the total value of the stack
	 * @return the 1-based index of the selected card
	 */
	public int playCard(Player player, int total, ArrayList<Card> sequence, int stackSum) {
		ArrayList<Card> hand = player.getHand();
		cardPanel.removeAll();

		for (int i = 0; i < hand.size(); i++) {
			Card card = hand.get(i);
			if (card.getRank().getValue() + total <= 31) {
				JButton playButton = new JButton(card.toString());
				int index = i;
				playButton.addActionListener(e -> {
					selectedIndex = index;
					synchronized (this) {
						waitingForInput = false;
						notifyAll();
					}
				});
				cardPanel.add(playButton);
			}
		}

		cardPanel.revalidate();
		cardPanel.repaint();

		if (player instanceof HumanPlayer) {
			selectedIndex = -1;
			waitForUserInput();
			return selectedIndex + 1;
		} else {
			return ((ComputerPlayer) player).getPlayIndex(sequence, stackSum);
		}
	}

	/**
	 * Displays a message in the log for a card played during a player's turn.
	 *
	 * @param player the player who played the card
	 * @param card the card that was played
	 * @param total the resulting play stack total
	 */
	public void showTurn(Player player, Card card, int total) {
		logMessage(player.getName() + " plays " + card + " | Total: " + total);
	}

	/**
	 * Displays a "Go" message for a player who cannot play a card.
	 *
	 * @param player the player who says "Go"
	 */
	public void sayGo(Player player) {
		logMessage(player.getName() + " says 'Go'..");
	}

	/**
	 * Displays a message when a player is awarded a point due to opponent's "Go".
	 *
	 * @param player the player who gains the point
	 */
	public void goReward(Player player) {
		logMessage(player.getName() + " scores 1 point for opponent's 'Go'.");
	}

	/**
	 * Displays a message when a player gets a point for the last card played.
	 *
	 * @param player the player who played the last card
	 */
	public void showLastCardPoint(Player player) {
		logMessage(player.getName() + " scores 1 point for last card.");
	}

	/**
	 * Displays a message when a player earns 2 points for reaching a total of 31.
	 *
	 * @param player the player who reached 31
	 */
	public void special31(Player player) {
		logMessage(player.getName() + " scores 2 points for 31!");
	}

	/**
	 * Prompts the user to play another game at the end of a round.
	 * Displays "Yes" and "No" buttons and waits for user input.
	 *
	 * @return true if "Yes" was selected, false if "No"
	 */
	public boolean playAgain() {
		cardPanel.removeAll();
		JButton yes = new JButton("Yes");
		JButton no = new JButton("No");

		yes.addActionListener(e -> {
			selectedIndex = 1;
			synchronized (this) {
				waitingForInput = false;
				notifyAll();
			}
		});

		no.addActionListener(e -> {
			selectedIndex = 0;
			synchronized (this) {
				waitingForInput = false;
				notifyAll();
			}
			// Exit the game if user selects No
			SwingUtilities.getWindowAncestor(cardPanel).dispose(); // Closes window
			System.exit(0); // Ends application
		});

		cardPanel.add(new JLabel("Play again?"));
		cardPanel.add(yes);
		cardPanel.add(no);
		cardPanel.revalidate();
		cardPanel.repaint();

		waitForUserInput();
		return selectedIndex == 1;
	}

	/**
	 * Appends a message to the game's log panel.
	 *
	 * @param message the message to display
	 */
	public void logMessage(String message) {
		log.append(message + "\n");
		log.setCaretPosition(log.getDocument().getLength()); // auto-scroll
	}

	/**
	 * Receives an update from the game controller via the Observer pattern.
	 * Appends the received message to the log.
	 *
	 * @param message the message sent by the Subject
	 */
	@Override
	public void update(String message) {
		logMessage(message + "\n");
	}
}
