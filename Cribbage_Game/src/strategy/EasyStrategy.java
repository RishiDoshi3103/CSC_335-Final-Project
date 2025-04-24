package strategy;

import model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EasyStrategy implements Strategy {

    @Override
    public List<Card> selectDiscard(List<Card> hand) {
        List<Card> temp = new ArrayList<>(hand);
        Collections.shuffle(temp);
        return temp.subList(0, 2);
    }

    @Override
    public Card selectCardToPlay(List<Card> hand, int currentTotal) {
        for (Card card : hand) {
            if (card.getRank().getValue() + currentTotal <= 31) {
                return card;
            }
        }
        return null;
    }
}
