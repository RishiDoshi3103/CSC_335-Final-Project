package ui;

import controller.GameController; 
import model.Card;
import model.Hand;
import Player.Player;
import Player.ComputerPlayer; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class GameView extends JFrame {
    
    private GameController controller;

    // UI components for game display
    private JPanel playerPanel;
    private JPanel opponentPanel;
    private JPanel cribPanel;
    private JPanel scorePanel;
    private JLabel playerScoreLabel;
    private JLabel opponentScoreLabel;
    private JButton discardButton;
    private JButton playCardButton;
    
    // Mode selection components
    private JButton playAgainstComputerButton;
    private JButton playOverNetworkButton;
    private JPanel modeSelectionPanel;

    // Constructor
    public GameView() {
        // Set vs Computer or vs Player over Network
        initializeModeSelectionUI();
    }

    // Initialize the mode selection UI (offering choice to play locally or over the network)
    private void initializeModeSelectionUI() {
        // Set up JFrame properties
        setTitle("Cribbage Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the mode selection panel
        modeSelectionPanel = new JPanel();
        modeSelectionPanel.setLayout(new GridLayout(2, 1));

        // Buttons for selecting game mode
        playAgainstComputerButton = new JButton("Play Against Computer");
        playOverNetworkButton = new JButton("Play Over Network");

        // Add action listeners to the buttons
        playAgainstComputerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameAgainstComputer();
            }
        });

        playOverNetworkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameOverNetwork();
            }
        });

        // Add buttons to the mode selection panel
        modeSelectionPanel.add(playAgainstComputerButton);
        modeSelectionPanel.add(playOverNetworkButton);

        // Add mode selection panel to the frame
        add(modeSelectionPanel, BorderLayout.CENTER);

        // Make the UI visible with mode selection first
        setVisible(true);
    }

    // Initialize the game components once a mode is selected
    private void initializeGameComponents() {
        // Remove mode selection panel after a choice is made
        remove(modeSelectionPanel);

        // Create main panels for game display
        playerPanel = new JPanel();
        opponentPanel = new JPanel();
        cribPanel = new JPanel();
        scorePanel = new JPanel();

        // Set layouts for the panels
        playerPanel.setLayout(new FlowLayout());
        opponentPanel.setLayout(new FlowLayout());
        cribPanel.setLayout(new FlowLayout());
        scorePanel.setLayout(new BoxLayout(scorePanel, BoxLayout.Y_AXIS));
        
        
        // Labels to show scores
        playerScoreLabel = new JLabel("Player Score: 0");
        opponentScoreLabel = new JLabel("Opponent Score: 0");
        
        // Set alignment for labels
        playerScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        opponentScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add padding or margins for better spacing
        playerScoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Top, Left, Bottom, Right padding
        opponentScoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add score labels to score panel
        scorePanel.add(playerScoreLabel);
        scorePanel.add(Box.createVerticalStrut(10));  // Add vertical space between labels
        scorePanel.add(opponentScoreLabel);
        
        scorePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Top, Left, Bottom, Right padding

        // Buttons for player actions
        discardButton = new JButton("Discard");
        playCardButton = new JButton("Play Card");

        // Add action listeners for buttons
        discardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDiscard();
            }
        });

        playCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePlayCard();
            }
        });

        // Layout setup for JFrame
        add(playerPanel, BorderLayout.SOUTH);
        add(opponentPanel, BorderLayout.NORTH);
        add(cribPanel, BorderLayout.CENTER);
        add(scorePanel, BorderLayout.EAST);

        // Buttons for Play Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(discardButton);
        buttonPanel.add(playCardButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Revalidate and repaint the frame to refresh
        revalidate();
        repaint();
    }

    // Set the game controller
    public void setController(GameController controller) {
        this.controller = controller;
    }

    // Method to update the UI with the current game state
    public void updateView(Player player, Player opponent, Hand playerHand, Hand opponentHand, int playerScore, int opponentScore) {
        // Update player and opponent hands
        updatePlayerHand(playerHand);
        updateOpponentHand(opponentHand);

        // Update the crib (if needed)
        updateCrib();

        // Update the score labels
        updateScores(playerScore, opponentScore);

        // Update the opponent label
        if (opponent instanceof ComputerPlayer) {
            updateOpponentLabel("Computer");
        } else {
            updateOpponentLabel("Network Player");
        }
    }

    // Update the player's hand in the view
    private void updatePlayerHand(Hand hand) {
        playerPanel.removeAll();  // Clear previous hand
        List<Card> cards = hand.getCards();
        for (Card card : cards) {
            JLabel cardLabel = new JLabel(new ImageIcon(getClass().getResource("/cards_images/" + card.toString() + ".png")));
            playerPanel.add(cardLabel);
        }
        playerPanel.revalidate();
        playerPanel.repaint();
    }

    // Update the opponent's hand in the view
    private void updateOpponentHand(Hand hand) {
        opponentPanel.removeAll();  // Clear previous hand
        List<Card> cards = hand.getCards();
        for (Card card : cards) {
            JLabel cardLabel = new JLabel(new ImageIcon(getClass().getResource("/cards_images/" + card.toString() + ".png")));
            opponentPanel.add(cardLabel);
        }
        opponentPanel.revalidate();
        opponentPanel.repaint();
    }

    // Update the crib in the view
    private void updateCrib() {
        // Once we start getting the rest of the implementation going,
    	// this will need to update to a view of the current crib. 
    }

    // Update the scores on the UI
    private void updateScores(int playerScore, int opponentScore) {
        playerScoreLabel.setText("Player Score: " + playerScore);
        opponentScoreLabel.setText("Opponent Score: " + opponentScore);
    }

    // Handle discard action (when the player discards cards to the crib)
    private void handleDiscard() {
        // Send discard action to the controller
        controller.discardSelectedCards();
    }

    // Handle play card action (when the player plays a card to the pile)
    private void handlePlayCard() {
        // Send play card action to the controller
        controller.playCardSelected();
    }

    // Method to show the network status (if playing over the network)
    public void networkStatus(String status) {
        // Optionally display network-related information (e.g., "Waiting for opponent")
        JOptionPane.showMessageDialog(this, status, "Network Status", JOptionPane.INFORMATION_MESSAGE);
    }

    // Main method to launch the viewer (for testing purposes)
    public static void main(String[] args) {
        GameView gameView = new GameView();
        gameView.setVisible(true);
    }

    // Method to start the game against the computer
    private void startGameAgainstComputer() {
        // Remove mode selection and initialize the game components
        initializeGameComponents();
        
        // Optionally, update the UI to show the opponent is the computer
        updateOpponentLabel("Computer");
    }

    // Method to start the game over the network
    private void startGameOverNetwork() {
        // Show a dialog to ask whether the user wants to host or join a game
        int option = JOptionPane.showOptionDialog(this,
            "Do you want to host a game or join an existing one?",
            "Network Game",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            new String[]{"Host Game", "Join Game"},
            "Host Game");

        if (option == 0) {
            // Host the game
            controller.startServer();
        } else {
            // Join the game (ask for IP address)
            String ip = JOptionPane.showInputDialog(this, "Enter IP address of the host:");
            controller.connectToServer(ip);
        }

        // Remove mode selection and initialize the game components
        initializeGameComponents();

        // Optionally, update the UI to show the opponent is a network player
        updateOpponentLabel("Network Player");
    }

    // Update opponent label for the UI
    private void updateOpponentLabel(String opponentType) {
        opponentScoreLabel.setText(opponentType + " Score: 0");
    }
}

