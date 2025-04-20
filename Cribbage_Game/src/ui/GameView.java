package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * GameView UI Shell for Cribbage - Layout only, no logic connected yet.
 */
public class GameView extends JFrame {

    // Top Options
    private final JPanel optionsPanel = new JPanel();
    private final JLabel infoLabel = new JLabel("(Player 1 Deals First)");
    private final JComboBox<String> p1Type = new JComboBox<>(new String[]{"Human", "Computer"});
    private final JComboBox<String> p2Type = new JComboBox<>(new String[]{"Human", "Computer"});
    private final JButton newGameBtn = new JButton("New Game");

    // Player Hands
    private final JToggleButton[] p1Cards = new JToggleButton[6];
    private final JButton p1ActionBtn = new JButton("P1 Action");

    private final JToggleButton[] p2Cards = new JToggleButton[6];
    private final JButton p2ActionBtn = new JButton("P2 Action");

    // Crib Area
    private final JPanel cribArea = new JPanel(null);
    private final JToggleButton[] cribCards = new JToggleButton[4];

    // Scores and Log
    private final JTextField p1Score = new JTextField("0", 3);
    private final JTextField p2Score = new JTextField("0", 3);
    private final JTextArea logArea = new JTextArea(5, 40);

    public GameView() {
        super("Cribbage Game - UI Shell");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5,5));
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(5,5,5,5));

        buildOptions();
        buildHands();
        buildCrib();
        buildScoreLog();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void buildOptions() {
        optionsPanel.add(infoLabel);
        optionsPanel.add(new JLabel("Player 1:"));
        optionsPanel.add(p1Type);
        optionsPanel.add(new JLabel("Player 2:"));
        optionsPanel.add(p2Type);
        optionsPanel.add(newGameBtn);
        add(optionsPanel, BorderLayout.NORTH);

        // TODO: Connect newGameBtn action later
    }

    private void buildHands() {
        JPanel leftPanel = new JPanel(new GridLayout(7, 1, 2, 2));
        for (int i = 0; i < 6; i++) {
            p1Cards[i] = makeCardButton();
            leftPanel.add(p1Cards[i]);
        }
        p1ActionBtn.setEnabled(false);
        leftPanel.add(p1ActionBtn);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridLayout(7, 1, 2, 2));
        for (int i = 0; i < 6; i++) {
            p2Cards[i] = makeCardButton();
            rightPanel.add(p2Cards[i]);
        }
        p2ActionBtn.setEnabled(false);
        rightPanel.add(p2ActionBtn);
        add(rightPanel, BorderLayout.EAST);

        // TODO: Connect action buttons later
    }

    private void buildCrib() {
        cribArea.setPreferredSize(new Dimension(400, 300));
        cribArea.setBackground(new Color(245, 245, 220));
        add(cribArea, BorderLayout.CENTER);

        for (int i = 0; i < 4; i++) {
            cribCards[i] = makeCardButton();
            cribCards[i].setBounds(160 + (i % 2) * 140, 60 + (i / 2) * 70, 132, 63);
            cribCards[i].setVisible(true);
            cribCards[i].setEnabled(false);
            cribArea.add(cribCards[i]);
        }
    }

    private void buildScoreLog() {
        JPanel bottomPanel = new JPanel();

        p1Score.setEditable(false);
        p1Score.setFont(new Font("Century Gothic", Font.BOLD, 30));
        p1Score.setForeground(Color.RED);
        bottomPanel.add(p1Score);

        JScrollPane scrollPane = new JScrollPane(logArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        logArea.setEditable(false);
        bottomPanel.add(scrollPane);

        p2Score.setEditable(false);
        p2Score.setFont(new Font("Century Gothic", Font.BOLD, 30));
        p2Score.setForeground(Color.RED);
        bottomPanel.add(p2Score);

        add(bottomPanel, BorderLayout.SOUTH);

        // TODO: Update scores and log area later
    }

    private JToggleButton makeCardButton() {
        JToggleButton btn = new JToggleButton("---");
        btn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 40));
        return btn;
    }

    // TODO: Add refresh methods to update UI when game starts
}
