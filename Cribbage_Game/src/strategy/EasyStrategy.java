package strategy;

import model.Card;
import model.Hand;

import java.util.*;

/**
 * EasyStrategy: Discards high-value cards randomly, but tries not to break pairs/runs.
 */
public class EasyStrategy implements DiscardStrategy {
    private final Random random = new Random();

    @Override
    public List<Card> selectDiscard(Hand hand) {
        List<Card> copy = new ArrayList<>(hand.getCards());
        copy.sort((a, b) -> Integer.compare(b.getCribValue(), a.getCribValue())); // High to low value

        List<Card> pool = new ArrayList<>();
        for (Card c : copy) {
            if (!formsPairOrRun(c, copy)) {
                pool.add(c);
            }
        }

        if (pool.size() < 2) {
            // fallback if not enough good discard candidates
            pool = copy;
        }

        Collections.shuffle(pool, random);
        return List.of(pool.get(0), pool.get(1));
    }

    private boolean formsPairOrRun(Card c, List<Card> hand) {
        int val = c.getRank().getValue();
        for (Card other : hand) {
            if (other != c) {
                int diff = Math.abs(val - other.getRank().getValue());
                if (diff == 1 || diff == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
