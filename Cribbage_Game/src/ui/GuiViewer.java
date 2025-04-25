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
 * The GuiViewer class provides a graphical interface for the Cribbage game.
 * It displays game messages, hands, the crib, and provides controls for
 * user input during discard and play phases.
 *
 * This class also implements the Observer interface to receive game updates.
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
	 * Waits until the user interacts with the interface.
	 * Used for synchronous input handling in the discard/play phases.
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
	 * Displays the player's current hand in the GUI.
	 *
	 * @param player the player whose hand is shown
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
	 *
	 * @param player the player discarding
	 * @param dealer true if the player is the dealer
	 * @return the index (1-based) of the discarded card
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
	 * Displays the crib in the log.
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
	 * Updates the play stack total display in the GUI.
	 *
	 * @param val the current point total
	 */
	public void showPlayTotal(int val) {
		playTotalLabel.setText("Play Stack Total: " + val);
	}

	/**
	 * Handles the play card phase for a human or computer player.
	 * Only allows legal card options (total <= 31).
	 *
	 * @param player the current player
	 * @param total current play total
	 * @param sequence cards played so far
	 * @param stackSum total stack value
	 * @return index (1-based) of the selected card
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

	public void showTurn(Player player, Card card, int total) {
		logMessage(player.getName() + " plays " + card + " | Total: " + total);
	}

	public void sayGo(Player player) {
		logMessage(player.getName() + " says 'Go'..");
	}

	public void goReward(Player player) {
		logMessage(player.getName() + " scores 1 point for opponent's 'Go'.");
	}

	public void showLastCardPoint(Player player) {
		logMessage(player.getName() + " scores 1 point for last card.");
	}

	public void special31(Player player) {
		logMessage(player.getName() + " scores 2 points for 31!");
	}

	/**
	 * Prompts the user to decide whether to play another game.
	 * This method blocks until the user selects Yes or No.
	 *
	 * @return true if "Yes" is selected, false if "No"
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
			SwingUtilities.getWindowAncestor(cardPanel).dispose();
			System.exit(0);
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
	 * Adds a message to the game log area.
	 *
	 * @param message the message to display
	 */
	public void logMessage(String message) {
		log.append(message + "\n");
		log.setCaretPosition(log.getDocument().getLength());
	}

	/**
	 * Receives updates from the game controller and logs them.
	 *
	 * @param message the message to log
	 */
	@Override
	public void update(String message) {
		logMessage(message + "\n");
	}
}
