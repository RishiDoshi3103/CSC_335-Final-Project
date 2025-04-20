package strategy;

import model.Card;
import model.Hand;

import java.util.*;

/**
 * HardStrategy: Simulates discards, picks the two cards that leave the strongest hand.
 */
public class HardStrategy implements DiscardStrategy {

    @Override
    public List<Card> selectDiscard(Hand hand) {
        List<Card> cards = hand.getCards();
        List<Card> bestPair = List.of(cards.get(0), cards.get(1));
        double bestScore = Double.NEGATIVE_INFINITY;

        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                List<Card> trial = new ArrayList<>(cards);
                trial.remove(cards.get(j));
                trial.remove(cards.get(i));

                double score = evaluateHand(trial);

                if (score > bestScore) {
                    bestScore = score;
                    bestPair = List.of(cards.get(i), cards.get(j));
                }
            }
        }

        return bestPair;
    }

    private double evaluateHand(List<Card> hand) {
        double score = 0;
        Map<Integer, Integer> count = new HashMap<>();
        for (Card c : hand) {
            count.put(c.getCribValue(), count.getOrDefault(c.getCribValue(), 0) + 1);
            score -= Math.abs(c.getCribValue() - 5); // closer to 5 is good
        }
        for (int cnt : count.values()) {
            if (cnt == 2) score += 5; // Pair bonus
        }
        return score;
    }
}
