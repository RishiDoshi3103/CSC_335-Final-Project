package strategy;

import model.Card;

import java.util.List;

public interface Strategy {
    List<Card> selectDiscard(List<Card> hand);
    Card selectCardToPlay(List<Card> hand, int currentTotal);
}
