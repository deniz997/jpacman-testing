package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.MultiLevelLauncher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  Test Suite for the multi level launcher state machine.
 */
public class MultiLevelLauncherTest extends StateMachine {

    @Override
    @BeforeEach
    protected void setupLauncher() {
        setLauncher(new MultiLevelLauncher());
    }

    /**
     * Test the path from the State Game not running to the State Game running after the game is won
     * with transitions start, a4 and start.
     */
    @Test
    public void test_transitions_start_a4_start() {
        before("/state_machine_test_won.txt");
        assertThat(getCurrentState()).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        setCurrentState(inspectCurrentState());
        assertThat(getCurrentState()).isEqualTo(State.game_running);
        trigger(Transition.a4);
        setCurrentState(inspectCurrentState());
        assertThat(getCurrentState()).isEqualTo(State.game_won);
        trigger(Transition.start);
        setCurrentState(inspectCurrentState());
        assertThat(getCurrentState()).isEqualTo(State.game_running);
    }

    /**
     * Test the path from the State Game not running to the State Game running
     * after the game is over with transitions start, a2 and start.
     */
    @Test
    public void test_transitions_start_a2_start() {
        before("/state_machine_test.txt");
        assertThat(getCurrentState()).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        setCurrentState(inspectCurrentState());
        assertThat(getCurrentState()).isEqualTo(State.game_running);
        trigger(Transition.a2);
        setCurrentState(inspectCurrentState());
        assertThat(getCurrentState()).isEqualTo(State.game_over);
        trigger(Transition.start);
        setCurrentState(inspectCurrentState());
        assertThat(getCurrentState()).isEqualTo(State.game_running);
    }
}
