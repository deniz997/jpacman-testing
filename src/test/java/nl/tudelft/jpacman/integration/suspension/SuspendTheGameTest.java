package nl.tudelft.jpacman.integration.suspension;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test if Suspending The Game works - System Test.
 */
public class SuspendTheGameTest {
    private Launcher launcher;

    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher().withMapFile("/suspension_map_test.txt");
        launcher.launch();
        getGame().start();
    }

    /**
     * Close the user interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    /**
     * Scenario S4.1: Suspend the game.
     * Given the game has started;
     * When  the player clicks the "Stop" button;
     * Then  all moves from ghosts and the player are suspended.
     */
    @Test
    public void gameCanBeSuspended() {
        // Check the game is running, given the game has started
        assertThat(getGame().isInProgress()).isTrue();

        // Suspend the game, player clicks the "stop" button
        getGame().stop();

        // Assert that all moves from ghosts and the player are suspended.
        assertThat(getGame().isInProgress()).isFalse();

        // Check if players cannot move anymore
        for (Player p : getGame().getPlayers()) {
            Square sq = p.getSquare();
            getGame().move(p, Direction.NORTH);
            assertThat(p.getSquare()).isEqualTo(sq);
        }
    }

    /**
     * Scenario S4.2: Restart the game.
     * Given the game is suspended;
     * When  the player hits the "Start" button;
     * Then  the game is resumed.
     */
    @Test
    public void gameCanBeResumed() {
        // Suspend the game, player clicks the "stop" button
        getGame().stop();

        // Check the game is running, given the game has suspended
        assertThat(getGame().isInProgress()).isFalse();

        // Start the game, player clicks the "start" button
        getGame().start();

        // Assert that we can move again.
        assertThat(getGame().isInProgress()).isTrue();

        // Check if players we can move again.
        for (Player p : getGame().getPlayers()) {
            Square sq = p.getSquare();
            getGame().move(p, Direction.WEST);
            assertThat(p.getSquare()).isNotEqualTo(sq);
        }
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
