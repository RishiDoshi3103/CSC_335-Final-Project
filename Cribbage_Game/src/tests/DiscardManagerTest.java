package tests;

import strategy.DiscardManager;
import strategy.EasyStrategy;
import strategy.HardStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class DiscardManagerTest {

    @Test
    public void testSelectStrategyReturnsNonNull() {
        DiscardManager manager = new DiscardManager(
                List.of(new EasyStrategy(), new HardStrategy())
        );

        assertNotNull(manager.selectStrategy(), "DiscardManager should select a strategy.");
    }
}
