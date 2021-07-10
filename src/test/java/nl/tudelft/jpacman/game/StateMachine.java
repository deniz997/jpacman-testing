package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.tools.TestObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */

public class StateMachine {
    private Game game;
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
        game = launcher.getGame();
        game.start();
        game.getLevel().addObserver(testObserver);
        currentState = State.game_launched;
        player = game.getPlayers().get(0);
    }

    /**
     * Test the path from the State Gui launched to the State Game running
     * with transitions start and a3.
     */
    @Test
    public void test_transitions_start_a3() {
        before("/state_machine_test.txt");
        assertThat(currentState).isEqualTo(State.game_launched);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.a3);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
    }

    /**
     * Test the path from the State Gui launched to the State Game won
     * with transitions start and a4.
     */
    @Test
    public void test_transitions_start_a4() {
        before("/state_machine_test_won.txt");
        assertThat(currentState).isEqualTo(State.game_launched);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.a4);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_won);
    }

    /**
     * Test the path from the State Gui launched to the State Game running through
     * the path game running, game paused and back to game running again
     * with transitions start, stop and start.
     */
    @Test
    public void test_transitions_start_stop_start() {
        before("/state_machine_test.txt");
        assertThat(currentState).isEqualTo(State.game_launched);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.stop);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_paused);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
    }

    /**
     * Test the path from the State Gui launched to the State Game over
     * with transitions start and a2.
     */
    @Test
    public void test_transitions_start_a2() {
        before("/state_machine_test.txt");
        assertThat(currentState).isEqualTo(State.game_launched);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.a2);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_over);
    }

    /**
     * Test the path from the State Gui launched to the State Game over
     * with transitions start and g1.
     */
    @Test
    public void test_transitions_start_g1() {
        before("/state_machine_test_loss.txt");
        assertThat(currentState).isEqualTo(State.game_launched);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.g1);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_over);
    }

    /**
     * Test the path from the State Gui launched to the State Game running
     * with transitions start and g2.
     */
    @Test
    public void test_transitions_start_g2() {
        before("/state_machine_test_player_safe.txt");
        assertThat(currentState).isEqualTo(State.game_launched);
        trigger(Transition.start);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
        trigger(Transition.g2);
        currentState = inspectCurrentState();
        assertThat(currentState).isEqualTo(State.game_running);
    }

    /**
     * We need to find out if we are running, paused, won or lost.
     *
     * @return state -> we are currently in
     */
    public State inspectCurrentState() {
        if (game.isInProgress()) {
            return State.game_running;
        } else if (testObserver.isObservedLoss()) {
            return State.game_over;
        } else if (testObserver.isObservedWin()) {
            return State.game_won;
        } else {
            return State.game_paused;
        }
    }

    /**
     * Trigger transitions based on current state.
     * @param transition -> transition type
     */

    public void trigger(Transition transition) {
        final long sleepTime = 500L;
        if (currentState == State.game_launched && transition == Transition.start) {
            game.start();
        } else if (currentState == State.game_running && transition == Transition.stop) {
            game.stop();
        } else if (currentState == State.game_running && transition == Transition.a1) {
            game.move(player, Direction.NORTH);
        } else if (currentState == State.game_running && transition == Transition.a2) {
            game.move(player, Direction.EAST);
        } else if (currentState == State.game_running && transition == Transition.a3) {
            game.move(player, Direction.WEST);
        } else if (currentState == State.game_running && transition == Transition.a4) {
            game.move(player, Direction.WEST);
        } else if (currentState == State.game_running && transition == Transition.g1) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                currentState = State.error;
            }
        } else if (currentState == State.game_running && transition == Transition.g2) {
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                currentState = State.error;
            }
        } else if (currentState == State.game_paused && transition == Transition.start) {
            game.start();
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
     * @param launcher the launcher we want to test.
     */
    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }
}
