package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import org.junit.jupiter.api.BeforeEach;

/**
 *  Test Suite for the multi level launcher state machine.
 */
public class MultiLevelLauncherTest extends StateMachine {
    @Override
    @BeforeEach
    protected void setupLauncher() {
        setLauncher(new MultiLevelLauncher());
    }


}
