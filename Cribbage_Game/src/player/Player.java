package player;

import model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Player {
    protected final String name;
    protected final List<Card> hand;
    protected final List<Card> playedCards;
    protected int score;
    protected int wins;
    protected int losses;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.playedCards = new ArrayList<>();
        this.score = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(new ArrayList<>(hand));
    }

    public int getHandSize() {
        return hand.size();
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public void resetHand() {
        hand.clear();
        playedCards.clear();
    }

    public int getScore() {
        return score;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void resetScore() {
        score = 0;
    }

    public void reset() {
        resetScore();
        resetHand();
    }

    public void recordWin() {
        wins++;
    }

    public void recordLoss() {
        losses++;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void addToPlayed(Card card) {
        playedCards.add(card);
    }

    public List<Card> getPlayedCards() {
        return Collections.unmodifiableList(new ArrayList<>(playedCards));
    }

    public abstract List<Card> discardToCrib();

    public abstract boolean isHuman();
    
    
}
