package player;

import model.Card;

import java.util.List;

public class HumanPlayer extends Player {

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public List<Card> discardToCrib() {
        throw new UnsupportedOperationException("Human discards via GUI");
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
