package player;

import model.Card;
import strategy.Strategy;

import java.util.List;

public class ComputerPlayer extends Player {
    private final Strategy strategy;

    public ComputerPlayer(String name, Strategy strategy) {
        super(name);
        this.strategy = strategy;
    }

    @Override
    public List<Card> discardToCrib() {
        return strategy.selectDiscard(hand);
    }

    public Card selectCardToPlay(int currentTotal) {
        return strategy.selectCardToPlay(hand, currentTotal);
    }

    @Override
    public boolean isHuman() {
        return false;
    }
}
