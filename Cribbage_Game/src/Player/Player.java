package Player;

import model.Card;
import model.Hand;
import strategy.DiscardStrategy;

import java.util.List;

/**
 * Abstract base class for Players (Human and Computer).
 * Defines the required actions for gameplay.
 */
public abstract class Player {
    private final String name;
    private final Hand hand = new Hand();
    private int points = 0;

    public Player(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int pointsToAdd) {
        if (pointsToAdd < 0) throw new IllegalArgumentException("Points must be non-negative");
        points += pointsToAdd;
    }

    public void resetPoints() {
        points = 0;
    }

    public void clearHand() {
        hand.clear();
    }

    /**
     * Player must implement how they discard two cards to the crib.
     */
    public abstract List<Card> discardToCrib(DiscardStrategy strategy);

    /**
     * Player must implement how they play a card during pegging phase.
     */
    public abstract Card playCard(int runningCount);
}
