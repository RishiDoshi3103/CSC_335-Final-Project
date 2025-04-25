package strategy;

import java.util.ArrayList;

import model.Card;

public interface Strategy {

	int playIndex(ArrayList<Card> hand, ArrayList<Card> sequence, int total);

}
