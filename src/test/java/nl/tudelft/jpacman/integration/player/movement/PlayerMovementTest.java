package nl.tudelft.jpacman.integration.player.movement;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.level.Pellet;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.tools.TestObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test if Moving The Player works as intended in the design document.
 */
public class PlayerMovementTest {
    private Launcher launcher;

    /**
     * Start a launcher, which can display the user interface.
     * @param mapName the name of the map we want to load for the test
     */
    public void before(String mapName) {
        launcher = new Launcher().withMapFile(mapName);
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
        before("/player_movements_map_test.txt");

        Player p = getGame().getPlayers().get(0);
        assertThat(p.getScore()).isEqualTo(0);
        Square squareWithPellet = p.getSquare().getSquareAt(Direction.WEST);

        //and  my Pacman is next to a square containing a pellet;
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
     * Scenario S2.2: The player moves on empty square
     * Given the game has started,
     *  and  my Pacman is next to an empty square;
     * When  I press an arrow key towards that square;
     * Then  my Pacman can move to that square
     *  and  my points remain the same.
     */
    @Test
    public void playerMovesOnEmptySquareTest() {
        before("/player_movements_map_test.txt");

        //Given the game has started
        assertThat(getGame().isInProgress()).isTrue();

        Player p = getGame().getPlayers().get(0);
        assertThat(p.getScore()).isEqualTo(0);
        Square squareNextToPlayer = p.getSquare().getSquareAt(Direction.NORTH);

        //and  my Pacman is next to an empty square;
        assertThat(squareNextToPlayer.getOccupants().size()).isEqualTo(0);

        //When  I press an arrow key towards that square;
        getGame().move(p, Direction.NORTH);

        //Then  my Pacman can move to that square,
        assertThat(p.getSquare()).isEqualTo(squareNextToPlayer);

        // and  my points remain the same.
        assertThat(p.getScore()).isEqualTo(0);
    }

    /**
     * Scenario S2.3: The move fails
     * Given the game has started,
     *   and my Pacman is next to a cell containing a wall;
     * When  I press an arrow key towards that cell;
     * Then  the move is not conducted.
     */
    @Test
    public void playerMovementFailsTest() {
        before("/player_movements_map_test.txt");

        //Given the game has started
        assertThat(getGame().isInProgress()).isTrue();

        Player p = getGame().getPlayers().get(0);
        Square initialSquare = p.getSquare();
        Square squareNextToPlayer = p.getSquare().getSquareAt(Direction.SOUTH);

        //and my Pacman is next to a cell containing a wall;
        assertThat(squareNextToPlayer.isAccessibleTo(p)).isFalse();

        //When  I press an arrow key towards that square;
        getGame().move(p, Direction.SOUTH);

        //Then  the move is not conducted.
        assertThat(p.getSquare()).isEqualTo(initialSquare);
    }

    /**
     * Scenario S2.4: The player dies
     * Given the game has started,
     *  and  my Pacman is next to a cell containing a ghost;
     * When  I press an arrow key towards that square;
     * Then  my Pacman dies,
     *  and  the game is over.
     */
    @Test
    public void playerLosesTest() {
        before("/player_movements_map_loss_test.txt");

        TestObserver observer = new TestObserver();

        getGame().getLevel().addObserver(observer);

        //Given the game has started
        assertThat(getGame().isInProgress()).isTrue();

        Player p = getGame().getPlayers().get(0);
        Square squareWithGhost = p.getSquare().getSquareAt(Direction.WEST);

        //and  my Pacman is next to a cell containing a ghost;
        assertThat(squareWithGhost.getOccupants().size()).isGreaterThanOrEqualTo(1);
        assertThat(squareWithGhost.getOccupants().get(0)).isInstanceOf(Ghost.class);

        assertThat(observer.isObservedLoss()).isFalse();

        //When  I press an arrow key towards that square;
        getGame().move(p, Direction.WEST);

        //Then  my Pacman dies, and  the game is over.
        assertThat(observer.isObservedLoss()).isTrue();
    }

    /**
     * Scenario S2.5: Player wins, extends S2.1
     * When  I have eaten the last pellet;
     * Then  I win the game.
     */
    @Test
    public void playerWinsTest() {
        before("/player_movements_map_win_test.txt");

        TestObserver observer = new TestObserver();

        getGame().getLevel().addObserver(observer);

        //Given the game has started
        assertThat(getGame().isInProgress()).isTrue();

        Player p = getGame().getPlayers().get(0);
        Square squareWithPellet = p.getSquare().getSquareAt(Direction.WEST);

        //and  my Pacman is next to a square containing a pellet;
        assertThat(squareWithPellet.getOccupants().size()).isGreaterThanOrEqualTo(1);
        assertThat(squareWithPellet.getOccupants().get(0)).isInstanceOf(Pellet.class);

        assertThat(observer.isObservedWin()).isFalse();

        //When  I have eaten the last pellet;
        getGame().move(p, Direction.WEST);

        //Then  I win the game.
        assertThat(observer.isObservedWin()).isTrue();
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
