package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import player.Player;

public class Game {

    private Player player1;
    private Player player2;
    private Player dealer;
    private List<Card> playSequence = new ArrayList<>();
    private List<Card> crib = new ArrayList<>();
    private Card starter;

    public void setPlayers(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
        this.dealer = p1; // P1 starts as dealer by default
    }

    public void startRound() {
        player1.resetHand();;
        player2.resetHand();
        crib.clear();
        playSequence.clear();

        List<Card> deck = createDeck();
        Collections.shuffle(deck);

        // Deal 6 cards each
        for (int i = 0; i < 6; i++) {
            player1.addCard(deck.remove(0));
            player2.addCard(deck.remove(0));
        }

        // Cut starter card (not used in crib)
        starter = deck.remove(0);
    }

    public void discardToCrib(Player player, List<Card> cards) {
        crib.addAll(cards);
    }

    public void setStarterCard() {
        // Already set in startRound, but method provided for compatibility
    }

    public Card getStarterCard() {
        return starter;
    }

    public void resetPlaySequence() {
        playSequence.clear();
    }

    public boolean canPlay(Player player) {
        for (Card card : player.getHand()) {
            if (getPlayTotal() + card.getRank().getValue() <= 31) {
                return true;
            }
        }
        return false;
    }

    public boolean canPlayCard(Card card) {
        return getPlayTotal() + card.getRank().getValue() <= 31;
    }

    public boolean isRoundOver() {
        return player1.getHand().isEmpty() && player2.getHand().isEmpty();
    }

    public boolean gameOver() {
        return player1.getScore() >= 121 || player2.getScore() >= 121;
    }

    public void playCard(Player player, Card card) {
        playSequence.add(card);
    }

    public int checkPairs() {
        int points = 0;
        if (playSequence.size() >= 2) {
            Card last = playSequence.get(playSequence.size() - 1);
            Card secondLast = playSequence.get(playSequence.size() - 2);
            if (last.getRank() == secondLast.getRank()) {
                points = 2;
                if (playSequence.size() >= 3 &&
                    playSequence.get(playSequence.size() - 3).getRank() == last.getRank()) {
                    points = 6;
                    if (playSequence.size() >= 4 &&
                        playSequence.get(playSequence.size() - 4).getRank() == last.getRank()) {
                        points = 12;
                    }
                }
            }
        }
        return points;
    }

    public int checkRuns() {
        int maxRun = 0;
        for (int i = playSequence.size(); i >= 3; i--) {
            List<Card> subList = playSequence.subList(playSequence.size() - i, playSequence.size());
            if (isRun(subList)) {
                maxRun = i;
                break;
            }
        }
        return maxRun;
    }

    private boolean isRun(List<Card> cards) {
        List<Integer> values = new ArrayList<>();
        for (Card c : cards) {
            values.add(c.getRank().getValue());
        }
        Collections.sort(values);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) {
                return false;
            }
        }
        return true;
    }

    public int checkFifteenOrThirtyOne() {
        int total = getPlayTotal();
        return (total == 15 || total == 31) ? 2 : 0;
    }

    public List<Card> getCrib() {
        return crib;
    }

    public int getPlayTotal() {
        return playSequence.stream().mapToInt(c -> c.getRank().getValue()).sum();
    }

    public int scoreHand(Player player, boolean isCrib) {
        int score = 0;
        List<Card> hand = new ArrayList<>(player.getHand());
        hand.add(starter); // include starter for scoring

        // Simple scoring: pairs and 15s
        for (int i = 0; i < hand.size(); i++) {
            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getRank() == hand.get(j).getRank()) {
                    score += 2;
                }
                if (hand.get(i).getRank().getValue() + hand.get(j).getRank().getValue() == 15) {
                    score += 2;
                }
            }
        }

        // Run check (naive: score only one best run of 3+)
        for (int i = 5; i >= 3; i--) {
            List<List<Card>> combos = getCombinations(hand, i);
            for (List<Card> combo : combos) {
                if (isRun(combo)) {
                    score += combo.size();
                    break;
                }
            }
        }

        return score;
    }

    private List<List<Card>> getCombinations(List<Card> hand, int size) {
        List<List<Card>> result = new ArrayList<>();
        combineRecursive(result, new ArrayList<>(), hand, size, 0);
        return result;
    }

    private void combineRecursive(List<List<Card>> result, List<Card> current, List<Card> hand, int size, int start) {
        if (current.size() == size) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < hand.size(); i++) {
            current.add(hand.get(i));
            combineRecursive(result, current, hand, size, i + 1);
            current.remove(current.size() - 1);
        }
    }

    public Player getDealer() {
        return dealer;
    }

    public Player getNonDealer() {
        return dealer == player1 ? player2 : player1;
    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                deck.add(new Card(rank, suit));
            }
        }
        return deck;
    }
}
