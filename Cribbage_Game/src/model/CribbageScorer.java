package model;

import player.Player;

import java.util.*;

public class CribbageScorer {

    public static int scoreHand(List<Card> hand, Card starter, boolean isCrib) {
        List<Card> fullHand = new ArrayList<>(hand);
        fullHand.add(starter);

        int total = 0;
        total += scoreFifteens(fullHand);
        total += scorePairs(fullHand);
        total += scoreRuns(fullHand);
        total += scoreFlush(hand, starter, isCrib);
        total += scoreNobs(hand, starter);

        return total;
    }

    private static int scoreFifteens(List<Card> cards) {
        int points = 0;
        int n = cards.size();
        for (int i = 1; i < (1 << n); i++) {
            int sum = 0;
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    sum += Math.min(cards.get(j).getRank().getValue(), 10);
                }
            }
            if (sum == 15) points += 2;
        }
        return points;
    }

    private static int scorePairs(List<Card> cards) {
        int points = 0;
        for (int i = 0; i < cards.size(); i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank()) {
                    points += 2;
                }
            }
        }
        return points;
    }

    private static int scoreRuns(List<Card> cards) {
        int maxRun = 0;
        int points = 0;

        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator.comparingInt(c -> c.getRank().ordinal()));

        for (int len = 5; len >= 3; len--) {
            for (int i = 0; i <= sorted.size() - len; i++) {
                List<Card> subset = sorted.subList(i, i + len);
                if (isRun(subset)) {
                    points += len;
                    return points; // only longest run counts
                }
            }
        }
        return points;
    }

    private static boolean isRun(List<Card> cards) {
        for (int i = 1; i < cards.size(); i++) {
            if (cards.get(i).getRank().ordinal() != cards.get(i - 1).getRank().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    private static int scoreFlush(List<Card> hand, Card starter, boolean isCrib) {
        boolean allSameSuit = hand.stream().allMatch(c -> c.getSuit() == hand.get(0).getSuit());
        if (allSameSuit) {
            if (starter.getSuit() == hand.get(0).getSuit()) {
                return 5;
            } else {
                return isCrib ? 0 : 4;
            }
        }
        return 0;
    }

    private static int scoreNobs(List<Card> hand, Card starter) {
        for (Card c : hand) {
            if (c.getRank() == Rank.JACK && c.getSuit() == starter.getSuit()) {
                return 1;
            }
        }
        return 0;
    }
}
