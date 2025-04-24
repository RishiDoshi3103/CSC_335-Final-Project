package ui;

import controller.GameController;
import model.Card;
import player.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private GameController controller;
    private Player player1;
    private Player player2;

    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel starterCardLabel;
    private JTextArea messageArea;

    private JPanel player1HandPanel;
    private JPanel player2HandPanel;
    private JPanel cribPanel;
    private JPanel playArea;

    private JButton confirmButton;
    private JButton playButton;

    private final List<JButton> player1CardButtons = new ArrayList<>();
    private final List<Card> selectedForDiscard = new ArrayList<>();

    private Player currentPlayer;

    public GameView() {
        setTitle("Cribbage Game");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        setVisible(true);
    }

    private void initUI() {
        // Top panel with scores and starter card
        JPanel topPanel = new JPanel(new BorderLayout());

        player1ScoreLabel = new JLabel("P1: 0", SwingConstants.LEFT);
        player1ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(player1ScoreLabel, BorderLayout.WEST);

        starterCardLabel = new JLabel("Starter: ---", SwingConstants.CENTER);
        starterCardLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(starterCardLabel, BorderLayout.CENTER);

        player2ScoreLabel = new JLabel("P2: 0", SwingConstants.RIGHT);
        player2ScoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topPanel.add(player2ScoreLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // Center panel: P2 Hand, Crib, P1 Hand
        JPanel centerPanel = new JPanel(new GridLayout(4, 1));

        player2HandPanel = new JPanel();
        player2HandPanel.setBorder(BorderFactory.createTitledBorder("Player 2"));
        centerPanel.add(player2HandPanel);

        cribPanel = new JPanel();
        cribPanel.setBorder(BorderFactory.createTitledBorder("Crib"));
        centerPanel.add(cribPanel);

        playArea = new JPanel();
        JTextArea playedCardsArea = new JTextArea("Played Cards:");
        playedCardsArea.setFont(new Font("Consolas", Font.BOLD, 16));  // 👈 Easy to read
        playedCardsArea.setBackground(new Color(240, 240, 240));       // 👈 Light background
        playedCardsArea.setForeground(Color.DARK_GRAY);                // 👈 Dark text
        playedCardsArea.setMargin(new Insets(10, 10, 10, 10));          // 👈 Padding
        playedCardsArea.setEditable(false);


        player1HandPanel = new JPanel();
        player1HandPanel.setBorder(BorderFactory.createTitledBorder("Player 1"));
        centerPanel.add(player1HandPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel: messages and buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(messageArea);
        scroll.setPreferredSize(new Dimension(1000, 150));
        bottomPanel.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        confirmButton = new JButton("Confirm Discard");
        confirmButton.addActionListener(e -> {
            if (controller != null) controller.confirmDiscard(currentPlayer);
        });
        confirmButton.setEnabled(false);
        buttonPanel.add(confirmButton);

        playButton = new JButton("Play Card");
        playButton.addActionListener(e -> {
            if (controller != null) controller.confirmPlay(currentPlayer);
        });
        playButton.setEnabled(false);
        buttonPanel.add(playButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setPlayers(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.currentPlayer = p1;
        updateScores(p1.getScore(), p2.getScore());
    }

    public void showStarter(Card starter) {
        starterCardLabel.setText("Starter: " + starter);
    }

    public void updateScores(int p1Score, int p2Score) {
        player1ScoreLabel.setText(player1.getName() + ": " + p1Score);
        player2ScoreLabel.setText(player2.getName() + ": " + p2Score);
    }

    public void showMessage(String msg) {
        messageArea.append(msg + "\n");
    }

    public void updateHands(Player p1, Player p2, boolean revealP2) {
        this.player1 = p1;
        this.player2 = p2;

        player1HandPanel.removeAll();
        player2HandPanel.removeAll();
        player1CardButtons.clear();
        selectedForDiscard.clear();

        for (Card card : p1.getHand()) {
            JButton button = new JButton(card.toString());
            button.setFont(new Font("Monospaced", Font.BOLD, 18)); // 👈 More readable
            button.setToolTipText("Click to select: " + card.toString()); // 👈 Tooltip
            button.setMargin(new Insets(10, 14, 10, 14)); // 👈 Padding for spacing

            button.addActionListener(e -> {
                if (selectedForDiscard.contains(card)) {
                    selectedForDiscard.remove(card);
                    button.setBackground(null);
                } else if (selectedForDiscard.size() < 2) {
                    selectedForDiscard.add(card);
                    button.setBackground(Color.CYAN);
                }
            });

            player1HandPanel.add(button);
            player1CardButtons.add(button);
        }


        for (Card card : p2.getHand()) {
            JButton btn = new JButton(revealP2 ? card.toString() : "??");
            player2HandPanel.add(btn);
        }

        revalidate();
        repaint();
    }

    public void enableDiscard(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        confirmButton.setEnabled(true);
        playButton.setEnabled(false);
    }

    public void enablePlay(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        confirmButton.setEnabled(false);
        playButton.setEnabled(true);
    }

    public void disableDiscard() {
        confirmButton.setEnabled(false);
    }

    public void disableAllActions() {
        confirmButton.setEnabled(false);
        playButton.setEnabled(false);
    }

    public List<Card> getSelectedCards() {
        return new ArrayList<>(selectedForDiscard);
    }

    public void displayCrib(List<Card> crib) {
        cribPanel.removeAll();
        for (Card card : crib) {
            cribPanel.add(new JLabel(card.toString()));
        }
        revalidate();
        repaint();
    }

    public void showPlayedCard(String name, Card card, int total) {
        playArea.add(new JLabel("▶️ " + name + " played " + card + " (Total: " + total + ")"));
        revalidate();
        repaint();
    }

    public void promptNextRound() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Start next round?",
            "Next Round",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION && controller != null) {
            controller.nextRound();
        }
    }

}
