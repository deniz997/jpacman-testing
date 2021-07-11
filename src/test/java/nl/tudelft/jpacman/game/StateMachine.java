package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.tools.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * State Machine class that checks the interactions and transitions between states.
 */

public class StateMachine {
    private State currentState;
    private TestObserver testObserver;
    private Player player;

    private Launcher launcher;

    /**
     * Initializes the game with map.
     *
     * @param mapName -> map file path
     */

    public void before(String mapName) {
        testObserver = new TestObserver();
        launcher = launcher.withMapFile(mapName);
        launcher.launch();
        currentState = inspectCurrentState();
        getGame().start();
        getGame().getLevel().addObserver(testObserver);
        player = getGame().getPlayers().get(0);
    }

    /**
     * Test the path from the State Game not running to the State Game running
     * with transitions start and a3.
     */
    @Test
    public void test_transitions_start_a3() {
        before("/state_machine_test.txt");
        assertThat(currentState).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.a3);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
    }

    /**
     * Test the path from the State Game not running to the State Game won
     * with transitions start and a4.
     */
    @Test
    public void test_transitions_start_a4() {
        before("/state_machine_test_won.txt");
        assertThat(currentState).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.a4);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_won);
    }

    /**
     * Test the path from the State Game not running to the State Game running through
     * the path game running, game not running and back to game running again
     * with transitions start, stop and start.
     */
    @Test
    public void test_transitions_start_stop() {
        before("/state_machine_test.txt");
        assertThat(currentState).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.stop);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_not_running);
    }

    /**
     * Test the path from the State Game not running to the State Game over
     * with transitions start and a2.
     */
    @Test
    public void test_transitions_start_a2() {
        before("/state_machine_test.txt");
        assertThat(currentState).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.a2);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_over);
    }

    /**
     * Test the path from the State Game not running to the State Game over
     * with transitions start and g1.
     */
    @Test
    public void test_transitions_start_g1() {
        before("/state_machine_test_loss.txt");
        assertThat(currentState).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.g1);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_over);
    }

    /**
     * Test the path from the State Game not running to the State Game running
     * with transitions start and g2.
     */
    @Test
    public void test_transitions_start_g2() {
        before("/state_machine_test_player_safe.txt");
        assertThat(currentState).isEqualTo(State.game_not_running);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.g2);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
    }

    /**
     * We need to find out if the game is running, not running, won or lost.
     *
     * @return state -> we are currently in
     */
    public State inspectCurrentState() {
        if (getGame().isInProgress()) {
            return State.game_running;
        } else if (testObserver.isObservedLoss()) {
            return State.game_over;
        } else if (testObserver.isObservedWin()) {
            return State.game_won;
        } else {
            return State.game_not_running;
        }
    }

    /**
     * Trigger transitions based on current state.
     *
     * @param transition -> transition type
     */

    public void trigger(Transition transition) {
        if (currentState == State.game_not_running && transition == Transition.start) {
            getGame().start();
        } else if (currentState == State.game_running) {
            gameRunningTrigger(transition);
        }  else if (currentState == State.game_won && transition == Transition.start) {
            getGame().start();
        } else if (currentState == State.game_over && transition == Transition.start) {
            getGame().start();
        }
    }

    /**
     * Trigger transitions based on current state.
     * @param transition -> transition type
     */
    public void gameRunningTrigger(Transition transition) {
        final long sleepTime = 500L;
        if (transition == Transition.stop) {
            getGame().stop();
        } else if (transition == Transition.a1) {
            getGame().move(player, Direction.NORTH);
        } else if (transition == Transition.a2) {
            getGame().move(player, Direction.EAST);
        } else if (transition == Transition.a3) {
            getGame().move(player, Direction.WEST);
        } else if (transition == Transition.a4) {
            getGame().move(player, Direction.WEST);
        } else if (transition == Transition.g1) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                currentState = State.error;
            }
        } else if (transition == Transition.g2) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                currentState = State.error;
            }
        }
    }

    /**
     * Sets up the launcher variable so that the tests run for the correct launcher.
     */
    @BeforeEach
    protected void setupLauncher() {
        launcher = new Launcher();
    }

    /**
     * Sets up the launcher so these tests can run for multiple launchers.
     *
     * @param launcher the launcher we want to test.
     */
    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    /**
     * Gets the current state.
     * @return currentState the state program is in
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state.
     * @param currentState the state program is in
     */
    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
