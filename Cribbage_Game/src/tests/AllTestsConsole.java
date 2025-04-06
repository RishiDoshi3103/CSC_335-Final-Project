package tests;

import java.util.ArrayList;
import java.util.List;

import Player.ComputerPlayer;
import Player.HumanPlayer;
import model.Card;
import model.Hand;

/**
 * AllTestsConsole runs a series of manual tests for the core classes
 * (Card, Hand, HumanPlayer, ComputerPlayer) and prints the outputs to the console.
 * This is useful for quickly verifying that your methods work as expected
 * outside of a formal JUnit framework.
 */
public class AllTestsConsole {

    public static void main(String[] args) {
        testCard();
        testHand();
        testHumanPlayer();
        testComputerPlayer();
    }

    /**
     * Tests the Card class by creating sample cards and printing their properties.
     */
    static void testCard() {
        System.out.println("----- Card Test -----");
        Card cardA = new Card("A", "Spades");
        System.out.println("Card A: " + cardA);
        System.out.println("Cribbage Value: " + cardA.getCribbageValue());
        System.out.println("Rank Value: " + cardA.getRankValue());

        Card cardJ = new Card("J", "Diamonds");
        System.out.println("Card J: " + cardJ);
        System.out.println("Cribbage Value: " + cardJ.getCribbageValue());
        System.out.println("Rank Value: " + cardJ.getRankValue());
    }

    /**
     * Tests the Hand class by adding, removing, and clearing cards, printing the hand contents.
     */
    static void testHand() {
        System.out.println("\n----- Hand Test -----");
        Hand hand = new Hand();
        Card card1 = new Card("2", "Hearts");
        Card card2 = new Card("3", "Clubs");
        hand.addCard(card1);
        hand.addCard(card2);
        System.out.println("Hand contains: " + hand.getCards());
        hand.getCards().remove(card1);
        System.out.println("After removing card1: " + hand.getCards());
        hand.getCards().clear();
        System.out.println("After clearing: " + hand.getCards());
    }

    /**
     * Tests the HumanPlayer class by simulating a turn, discarding, updating wins,
     * and manipulating the hand.
     */
    static void testHumanPlayer() {
        System.out.println("\n----- HumanPlayer Test -----");
        HumanPlayer hp = new HumanPlayer("Alice");
        hp.playTurn();
        hp.discardCards();
        hp.recordWin();
        System.out.println("Wins: " + hp.getWins());
        hp.addCard(new Card("A", "Spades"));
        hp.addCard(new Card("10", "Hearts"));
        System.out.println("Hand: " + hp.getHand().getCards());
        hp.clearHand();
        System.out.println("Hand after clearing: " + hp.getHand().getCards());
    }

    /**
     * Tests the ComputerPlayer class by simulating a turn, using a dummy discard strategy,
     * and printing the results.
     */
    static void testComputerPlayer() {
        System.out.println("\n----- ComputerPlayer Test -----");
        DummyStrategy dummy = new DummyStrategy();
        ComputerPlayer cp = new ComputerPlayer("Comp", dummy);
        cp.playTurn();
        cp.addCard(new Card("A", "Spades"));
        cp.addCard(new Card("2", "Hearts"));
        cp.addCard(new Card("3", "Diamonds"));
        cp.addCard(new Card("4", "Clubs"));
        cp.discardCards(); // This should invoke dummy strategy.
        System.out.println("Discarded cards (DummyStrategy): " + dummy.lastDiscarded);
        cp.recordLoss();
        System.out.println("Losses: " + cp.getLosses());
    }

    /**
     * A dummy discard strategy for testing purposes.
     * This strategy simply selects the first two cards from the hand.
     */
    static class DummyStrategy implements strategy.DiscardStrategy {
        public List<Card> lastDiscarded = null;

        @Override
        public List<Card> selectDiscard(Hand hand) {
            List<Card> cards = hand.getCards();
            List<Card> discard = new ArrayList<>();
            if (cards.size() >= 2) {
                discard.add(cards.get(0));
                discard.add(cards.get(1));
            }
            lastDiscarded = discard;
            System.out.println("DummyStrategy selected discard: " + discard);
            return discard;
        }
    }
}
