package Player;

import model.Card;
import strategy.DiscardStrategy;

import java.util.List;

/**
 * Human player relies on UI to perform actions.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public List<Card> discardToCrib(DiscardStrategy strategy) {
        throw new UnsupportedOperationException("Human player discards manually via UI");
    }

    @Override
    public Card playCard(int runningCount) {
        throw new UnsupportedOperationException("Human player plays cards manually via UI");
    }
}
