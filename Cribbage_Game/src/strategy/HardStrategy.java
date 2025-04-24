package strategy;

import model.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HardStrategy implements Strategy {

    @Override
    public List<Card> selectDiscard(List<Card> hand) {
        List<Card> sorted = new ArrayList<>(hand);
        sorted.sort(Comparator.comparingInt(card -> card.getRank().getValue()));
        return sorted.subList(0, 2); // discard lowest ranked cards
    }

    @Override
    public Card selectCardToPlay(List<Card> hand, int currentTotal) {
        Card bestCard = null;
        int bestValue = -1;

        for (Card card : hand) {
            int value = card.getRank().getValue();
            if (value + currentTotal <= 31 && value > bestValue) {
                bestValue = value;
                bestCard = card;
            }
        }

        return bestCard;
    }
}
