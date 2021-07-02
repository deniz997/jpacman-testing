package nl.tudelft.jpacman.integration.player.movement;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test if Moving The Player works as intended in the design document.
 */
public class PlayerMovementTest {
    private Launcher launcher;

    /**
     * Start a launcher, which can display the user interface.
     */
    @BeforeEach
    public void before() {
        launcher = new Launcher().withMapFile("/player_movements_map_test.txt");
        launcher.launch();
        getGame().start();
    }

    /**
     * Scenario S2.1: The player consumes
     * Given the game has started,
     *  and  my Pacman is next to a square containing a pellet;
     * When  I press an arrow key towards that square;
     * Then  my Pacman can move to that square,
     *  and  I earn the points for the pellet,
     *  and  the pellet disappears from that square.
     */
    @Test
    public void playerConsumesTest() {
        //Given the game has started
        assertThat(getGame().isInProgress()).isTrue();

        Player p = getGame().getPlayers().get(0);
        assertThat(p.getScore()).isEqualTo(0);
        Square squareWithPellet = p.getSquare().getSquareAt(Direction.WEST);

        //and  my Pacman is next to a square containing a pellet;
        assertThat(squareWithPellet.getOccupants().size()).isGreaterThanOrEqualTo(1);
        assertThat(squareWithPellet.getOccupants().get(0)).isInstanceOf(Pellet.class);

        Pellet pellet = (Pellet) squareWithPellet.getOccupants().get(0);

        //When  I press an arrow key towards that square;
        getGame().move(p, Direction.WEST);

        //Then  my Pacman can move to that square,
        assertThat(p.getSquare()).isEqualTo(squareWithPellet);

        // and  I earn the points for the pellet,
        assertThat(p.getScore()).isEqualTo(pellet.getValue());

        //and  the pellet disappears from that square
        assertThat(squareWithPellet.getOccupants()).doesNotContain(pellet);
    }

    /**
     * Close the user interface.
     */
    @AfterEach
    public void after() {
        launcher.dispose();
    }

    private Game getGame() {
        return launcher.getGame();
    }
}
