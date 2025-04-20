package strategy;

import java.util.List;
import java.util.Random;

/**
 * Manages different discard strategies for ComputerPlayer.
 */
public class DiscardManager {
    private final List<DiscardStrategy> strategies;
    private final Random random = new Random();

    public DiscardManager(List<DiscardStrategy> strategies) {
        this.strategies = strategies;
    }

    public DiscardStrategy selectStrategy() {
        return strategies.get(random.nextInt(strategies.size()));
    }
}
