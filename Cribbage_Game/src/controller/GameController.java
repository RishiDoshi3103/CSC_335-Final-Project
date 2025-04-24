package controller;

import model.Card;
import model.Game;
import player.ComputerPlayer;
import player.HumanPlayer;
import player.Player;
import strategy.EasyStrategy;
import strategy.HardStrategy;
import ui.GameView;

import java.util.List;

import javax.swing.JOptionPane;

public class GameController {
    private final Game model;
    private final GameView view;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player otherPlayer;
    private Player lastPlayed;

    public GameController(Game model, GameView view) {
        this.model = model;
        this.view = view;
        this.view.setController(this);
    }

    public void startGame(String mode, String name1, String name2) {
        switch (mode) {
            case "Human vs Computer (Easy)" -> {
                player1 = new HumanPlayer(name1);
                player2 = new ComputerPlayer("AI (Easy)", new EasyStrategy());
            }
            case "Human vs Computer (Hard)" -> {
                player1 = new HumanPlayer(name1);
                player2 = new ComputerPlayer("AI (Hard)", new HardStrategy());
            }
            case "Human vs Human" -> {
            	
            }
            case "Human vs Human (Local)" ->  {
                player1 = new HumanPlayer(name1);
                player2 = new HumanPlayer(name2);
            }
            default -> throw new IllegalArgumentException("Unknown mode: " + mode);
        }

        model.setPlayers(player1, player2);
        view.setPlayers(player1, player2);
        nextRound();
    }


    public void nextRound() {
        model.startRound();
        view.showMessage("🎮 New round started.");
        view.updateScores(player1.getScore(), player2.getScore());
        handleDiscardPhase();
    }

    private void handleDiscardPhase() {
        if (player1 instanceof ComputerPlayer cp1) {
            List<Card> discards = cp1.discardToCrib();
            model.discardToCrib(cp1, discards);
            discards.forEach(cp1::removeCard);
            view.showMessage(cp1.getName() + " discarded cards.");
        }

        if (player2 instanceof ComputerPlayer cp2) {
            List<Card> discards = cp2.discardToCrib();
            model.discardToCrib(cp2, discards);
            discards.forEach(cp2::removeCard);
            view.showMessage(cp2.getName() + " discarded cards.");
        }

        if (player1 instanceof HumanPlayer) view.enableDiscard(player1);
        if (player2 instanceof HumanPlayer) view.enableDiscard(player2);

        view.updateHands(player1, player2, false);
    }

    public void confirmDiscard(Player player) {
        List<Card> selected = view.getSelectedCards();
        if (selected.size() != 2) {
            view.showMessage("❗ Please select exactly 2 cards to discard.");
            return;
        }

        model.discardToCrib(player, selected);
        selected.forEach(player::removeCard);
        view.updateHands(player1, player2, false);
        view.disableDiscard();

        if (bothPlayersDiscarded()) {
            startPlayPhase();
        }
    }

    private boolean bothPlayersDiscarded() {
        return player1.getHand().size() == 4 && player2.getHand().size() == 4;
    }

    private void startPlayPhase() {
        model.setStarterCard();
        Card starter = model.getStarterCard();
        view.showStarter(starter);

        if (starter.getRank().getLabel().equals("J")) {
            model.getDealer().addPoints(2);
            view.showMessage(model.getDealer().getName() + " scores 2 for Jack starter!");
        }

        currentPlayer = model.getNonDealer();
        otherPlayer = model.getDealer();
        model.resetPlaySequence();

        view.updateScores(player1.getScore(), player2.getScore());
        view.updateHands(player1, player2, false);
        nextPlay();
    }

    private void nextPlay() {
        if (!model.canPlay(player1) && !model.canPlay(player2)) {
            if (lastPlayed != null) {
                lastPlayed.addPoints(1);
                view.showMessage(lastPlayed.getName() + " scores 1 for last card.");
            }
            model.resetPlaySequence();
            view.updateScores(player1.getScore(), player2.getScore());
            scoreHands();
            return;
        }

        if (!model.canPlay(currentPlayer)) {
            view.showMessage(currentPlayer.getName() + " says 'Go'.");
            swapPlayers();
            nextPlay();
            return;
        }

        if (currentPlayer instanceof ComputerPlayer cp) {
            Card toPlay = cp.selectCardToPlay(model.getPlayTotal());
            if (toPlay != null) {
                playCard(currentPlayer, toPlay);
            } else {
                view.showMessage(cp.getName() + " cannot play.");
                swapPlayers();
                nextPlay();
            }
        } else {
            view.enablePlay(currentPlayer);
        }
    }

    public void confirmPlay(Player player) {
        List<Card> selected = view.getSelectedCards();
        if (selected.size() != 1) {
            view.showMessage("❗ Select exactly 1 card to play.");
            return;
        }

        Card card = selected.get(0);
        if (!model.canPlayCard(card)) {
            view.showMessage("❗ That card cannot be played now.");
            return;
        }

        playCard(player, card);
    }

    private void playCard(Player player, Card card) {
        model.playCard(player, card);
        player.removeCard(card);
        lastPlayed = player;

        view.showPlayedCard(player.getName(), card, model.getPlayTotal());

        int score = model.checkPairs() + model.checkRuns() + model.checkFifteenOrThirtyOne();
        if (score > 0) {
            player.addPoints(score);
            view.showMessage(player.getName() + " scores " + score + " points!");
        }

        if (model.getPlayTotal() == 31) {
            model.resetPlaySequence();
        }

        view.updateScores(player1.getScore(), player2.getScore());
        view.updateHands(player1, player2, false);
        swapPlayers();
        nextPlay();
    }

    private void swapPlayers() {
        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    private void scoreHands() {
        int p1Score = model.scoreHand(player1, false);
        int p2Score = model.scoreHand(player2, false);
        int cribScore = model.scoreHand(model.getDealer(), true);

        if (p1Score > 0) {
            player1.addPoints(p1Score);
            view.showMessage(player1.getName() + " scores " + p1Score + " from hand.");
        }

        if (p2Score > 0) {
            player2.addPoints(p2Score);
            view.showMessage(player2.getName() + " scores " + p2Score + " from hand.");
        }

        if (cribScore > 0) {
            model.getDealer().addPoints(cribScore);
            view.showMessage(model.getDealer().getName() + " scores " + cribScore + " from crib.");
        }

        view.updateScores(player1.getScore(), player2.getScore());

        if (model.gameOver()) {
            Player winner = player1.getScore() >= 121 ? player1 : player2;

            StringBuilder summary = new StringBuilder();
            summary.append("\n🎉 Game Over!\n");
            summary.append(player1.getName()).append(" Score: ").append(player1.getScore()).append("\n");
            summary.append(player2.getName()).append(" Score: ").append(player2.getScore()).append("\n");
            summary.append("🏆 Winner: ").append(winner.getName()).append("\n");

            // Log to game window
            view.showMessage(summary.toString());

            // Show dialog too
            JOptionPane.showMessageDialog(null, summary.toString(), "Game Summary", JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.promptNextRound();
        }


    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
