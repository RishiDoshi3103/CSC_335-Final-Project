package model;

import Player.Player;
import strategy.DiscardStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    private final Player player1;
    private final Player player2;
    private Deck deck;
    private final List<Card> crib = new ArrayList<>();
    private final List<Card> pile = new ArrayList<>();
    private int runningCount = 0;
    private boolean discardPhase = true;

    public Game(Player player1, Player player2) {
        this.player1 = Objects.requireNonNull(player1);
        this.player2 = Objects.requireNonNull(player2);
    }

    public void startNewRound() {
        deck = new Deck();
        deck.shuffle();
        player1.resetPoints();
        player2.resetPoints();
        player1.clearHand();
        player2.clearHand();
        crib.clear();
        pile.clear();
        runningCount = 0;
        discardPhase = true;
        for (int i = 0; i < 6; i++) {
            player1.getHand().add(deck.draw());
            player2.getHand().add(deck.draw());
        }
    }

    public void discardToCrib(List<Card> p1Discard, DiscardStrategy p2Strategy) {
        if (!discardPhase) return;
        for (Card c : p1Discard) {
            player1.getHand().remove(c);
            crib.add(c);
        }
        List<Card> p2Discard = player2.discardToCrib(p2Strategy);
        crib.addAll(p2Discard);
        discardPhase = false;
    }

    public void playTurn(Card p1Card) {
        if (discardPhase) return;
        if (p1Card != null) {
            pile.add(p1Card);
            runningCount += p1Card.getCribValue();
            awardPegging(player1);
        }
        Card p2Card = player2.playCard(runningCount);
        if (p2Card != null) {
            pile.add(p2Card);
            runningCount += p2Card.getCribValue();
            awardPegging(player2);
        } else {
            player1.addPoints(1);
            resetPegging();
            return;
        }
        if (player1.getHand().getCards().isEmpty() &&
            player2.getHand().getCards().isEmpty()) {
            startNewRound();
        }
    }

    private void awardPegging(Player p) {
        if (runningCount == 15 || runningCount == 31) {
            p.addPoints(2);
            if (runningCount == 31) resetPegging();
        }
    }

    private void resetPegging() {
        pile.clear();
        runningCount = 0;
    }

    public Player getPlayer1()       { return player1; }
    public Player getPlayer2()       { return player2; }
    public List<Card> getCrib()      { return List.copyOf(crib); }
    public List<Card> getPile()      { return List.copyOf(pile); }
    public int getRunningCount()     { return runningCount; }
    public boolean isDiscardPhase()  { return discardPhase; }
}