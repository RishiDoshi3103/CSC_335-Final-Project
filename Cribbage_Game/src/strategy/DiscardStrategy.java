package strategy;

import model.Card;
import model.Hand;

import java.util.List;

public interface DiscardStrategy {
    List<Card> selectDiscard(Hand hand);
}