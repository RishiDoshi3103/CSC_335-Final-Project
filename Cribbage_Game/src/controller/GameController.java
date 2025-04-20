package controller;

import model.Card;
import model.Deck;
import Player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Orchestrates dealing, discards, pegging, and basic scoring.
 */
public class GameController {
    private final Player localPlayer;
    private final Player opponent;
    private Deck deck;
    private final List<Card> crib = new ArrayList<>();
    private final List<Card> pile = new ArrayList<>();
    private int runningCount = 0;
    private boolean discardPhase = true;

    public GameController(Player local, Player opponent) {
        this.localPlayer = Objects.requireNonNull(local);
        this.opponent    = Objects.requireNonNull(opponent);
    }

    public Player getLocalPlayer() { return localPlayer; }
    public Player getOpponent()    { return opponent;    }

    public void startNewGame() {
        deck = new Deck();
        deck.shuffle();
        localPlayer.resetPoints();
        opponent.resetPoints();
        localPlayer.clearHand();
        opponent.clearHand();
        crib.clear();
        pile.clear();
        runningCount = 0;
        discardPhase = true;
        for (int i = 0; i < 6; i++) {
            localPlayer.getHand().add(deck.draw());
            opponent.getHand().add(deck.draw());
        }
    }

    public void discardToCrib(List<Card> humanDiscard) {
        if (!discardPhase) return;
        humanDiscard.forEach(c -> {
            localPlayer.getHand().remove(c);
            crib.add(c);
        });
        List<Card> aiDiscard = opponent.discardToCrib(null);
        crib.addAll(aiDiscard);
        discardPhase = false;
    }

    public void playTurn(Card humanCard) {
        if (discardPhase) return;
        if (humanCard != null) {
            pile.add(humanCard);
            runningCount += humanCard.getCribValue();
            awardPeg(localPlayer);
        }
        Card ai = opponent.playCard(runningCount);
        if (ai != null) {
            pile.add(ai);
            runningCount += ai.getCribValue();
            awardPeg(opponent);
        } else {
            localPlayer.addPoints(1);
            resetPegging();
            return;
        }
        if (localPlayer.getHand().getCards().isEmpty() &&
            opponent.getHand().getCards().isEmpty()) {
            startNewGame();
        }
    }

    private void awardPeg(Player p) {
        if (runningCount == 15 || runningCount == 31) {
            p.addPoints(2);
            if (runningCount == 31) resetPegging();
        }
    }

    private void resetPegging() {
        pile.clear();
        runningCount = 0;
    }
}