package main;

import model.Game;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.Player;
import strategy.EasyStrategy;
import strategy.HardStrategy;
import ui.GameView;
import controller.GameController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] modes = {
                "Human vs Computer (Easy)",
                "Human vs Computer (Hard)",
                "Human vs Human (Local)"
            };

            String mode = (String) JOptionPane.showInputDialog(
                null,
                "Select game mode:",
                "Cribbage Setup",
                JOptionPane.PLAIN_MESSAGE,
                null,
                modes,
                modes[0]
            );

            if (mode == null) return; // user cancelled

            Player player1;
            Player player2;

            String name1 = JOptionPane.showInputDialog("Enter name for Player 1:");
            if (name1 == null || name1.trim().isEmpty()) name1 = "Player 1";

            switch (mode) {
                case "Human vs Computer (Easy)" -> {
                    player1 = new HumanPlayer(name1);
                    player2 = new ComputerPlayer("Computer (Easy)", new EasyStrategy());
                }
                case "Human vs Computer (Hard)" -> {
                    player1 = new HumanPlayer(name1);
                    player2 = new ComputerPlayer("Computer (Hard)", new HardStrategy());
                }
                case "Human vs Human (Local)" -> {
                    String name2 = JOptionPane.showInputDialog("Enter name for Player 2:");
                    if (name2 == null || name2.trim().isEmpty()) name2 = "Player 2";
                    player1 = new HumanPlayer(name1);
                    player2 = new HumanPlayer(name2);
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Invalid selection.");
                    return;
                }
            }

            Game model = new Game();
            GameView view = new GameView();
            GameController controller = new GameController(model, view);
            view.setController(controller);

            String name2;
            if (mode.equals("Human vs Human")) {
                name2 = JOptionPane.showInputDialog("Enter Player 2 Name:");
            } else {
                name2 = "Computer";
            }

            controller.startGame(mode, name1, name2);  // ✅ fixed

        });
    }
}
