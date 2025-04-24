package ui;

import Player.Player;
import Player.HumanPlayer;
import Player.ComputerPlayer;
import model.Card;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GuiViewer {
	private JFrame frame;
	private JTextArea log;
	private JPanel cardPanel;
	private JLabel playTotalLabel;

	private int selectedIndex = -1;
	private boolean waitingForInput = false;

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

	public int discard(Player player, boolean dealer) {
		if (player instanceof HumanPlayer) {
			selectedIndex = -1;
			waitForUserInput();
			return selectedIndex + 1;
		} else {
			return ((ComputerPlayer) player).discardIndex(dealer);
		}
	}

	public void showCrib(ArrayList<Card> crib) {
		logMessage("\nCrib:");
		for (Card c : crib) {
			logMessage(c.toString());
		}
	}

	public void showPlayTotal(int val) {
		playTotalLabel.setText("Play Stack Total: " + val);
	}

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
		});

		cardPanel.add(new JLabel("Play again?"));
		cardPanel.add(yes);
		cardPanel.add(no);
		cardPanel.revalidate();
		cardPanel.repaint();

		waitForUserInput();
		return selectedIndex == 1;
	}

	public void logMessage(String message) {
		log.append(message + "\n");
		log.setCaretPosition(log.getDocument().getLength()); // auto-scroll
	}
}


