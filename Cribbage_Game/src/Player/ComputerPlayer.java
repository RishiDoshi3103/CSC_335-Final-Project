package Player;

import model.Card;
import strategy.DiscardStrategy;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Computer player that uses strategies to discard and play.
 */
public class ComputerPlayer extends Player {
    private final List<DiscardStrategy> strategies;
    private final Random random = new Random();

    public ComputerPlayer(String name, List<DiscardStrategy> strategies) {
        super(Objects.requireNonNull(name));
        if (strategies == null || strategies.isEmpty()) {
            throw new IllegalArgumentException("Must provide at least one strategy.");
        }
        this.strategies = List.copyOf(strategies);
    }

    @Override
    public List<Card> discardToCrib(DiscardStrategy ignored) {
        // Randomly select a discard strategy each time
        DiscardStrategy strategy = strategies.get(random.nextInt(strategies.size()));
        List<Card> toDiscard = strategy.selectDiscard(getHand());
        toDiscard.forEach(getHand()::remove);
        return toDiscard;
    }

    @Override
    public Card playCard(int runningCount) {
        for (Card c : getHand().getCards()) {
            int sum = c.getCribValue() + runningCount;
            if (sum == 15 || sum == 31) {
                getHand().remove(c);
                return c;
            }
        }
        return getHand().getCards().stream()
                .filter(c -> c.getCribValue() + runningCount <= 31)
                .min((a, b) -> Integer.compare(a.getCribValue(), b.getCribValue()))
                .map(c -> {
                    getHand().remove(c);
                    return c;
                })
                .orElse(null);
    }
}
