package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.points.PointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import nl.tudelft.jpacman.tools.Tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test if the ghost Inky is behaving correctly.
 */
public class InkyTest {
    private GhostMapParser ghostMapParser;
    private Player player;

    /**
     * Initializes the GhostMapParser for every test.
     */
    @BeforeEach
    public void initTests() {
        PacManSprites pacManSprites = new PacManSprites();
        GhostFactory ghostFactory = new GhostFactory(pacManSprites);
        PointCalculator pointCalculator = new DefaultPointCalculator();
        LevelFactory levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);
        BoardFactory boardFactory = new BoardFactory(pacManSprites);
        PlayerFactory playerFactory = new PlayerFactory(pacManSprites);
        player = playerFactory.createPacMan();
        ghostMapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
    }

    /**
     * Tests if Inky moves closer to the player
     * if Blinky is between him and the player.
     */
    @Test
    public void testIfInkyMovesToThePlayerIfBlinkyBetweenThem() {
        char[][] map = new char[][]{
            "###############".toCharArray(),
            "#   P    B   I#".toCharArray(),
            "###############".toCharArray()
        };
        Level level = ghostMapParser.parseMap(Tools.rotateMap(map));

        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        Optional<Direction> dir = inky.nextAiMove();
        assertThat(dir).isPresent();
        assertThat(dir.get()).isEqualTo(Direction.WEST);
    }

    /**
     * Tests if Inky moves away from the player
     * if Blinky is behind him and the player is in front of him
     * (moving to a point far ahead of the player).
     */
    @Test
    public void testIfInkyMovesAwayFromThePlayerIfBlinkyBehindInky() {
        char[][] map = new char[][]{
            "###############".toCharArray(),
            "#             #".toCharArray(),
            "#         P I #".toCharArray(),
            "#            B#".toCharArray(),
            "###############".toCharArray()
        };
        Level level = ghostMapParser.parseMap(Tools.rotateMap(map));

        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        Optional<Direction> dir = inky.nextAiMove();
        assertThat(dir).isPresent();
        assertThat(dir.get()).isEqualTo(Direction.NORTH);
    }

    /**
     * Tests if Inky does not move if there is no nearest player.
     */
    @Test
    public void testIfInkyDoesNotMoveIfNoPlayer() {
        char[][] map = new char[][]{
            "###############".toCharArray(),
            "#        B   I#".toCharArray(),
            "###############".toCharArray()
        };
        Level level = ghostMapParser.parseMap(Tools.rotateMap(map));

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        Optional<Direction> dir = inky.nextAiMove();
        assertThat(dir).isEmpty();
    }

    /**
     * Tests if Inky does not move if there is no nearest Blinky.
     */
    @Test
    public void testIfInkyDoesNotMoveIfNoBlinky() {
        char[][] map = new char[][]{
            "###############".toCharArray(),
            "#     P      I#".toCharArray(),
            "###############".toCharArray()
        };
        Level level = ghostMapParser.parseMap(Tools.rotateMap(map));

        level.registerPlayer(player);
        player.setDirection(Direction.EAST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        Optional<Direction> dir = inky.nextAiMove();
        assertThat(dir).isEmpty();
    }

    /**
     * Tests if Inky does not move
     * if there is no direct path (ignoring terrain) between Blinky and the Player.
     */
    @Test
    public void testIfInkyDoesNotMoveIfNoPathIgnoringTerrainBetweenPlayerAndBlinky() {
        char[][] map = new char[][]{
            "###############".toCharArray(),
            "P       B    I#".toCharArray(),
            "###############".toCharArray()
        };
        Level level = ghostMapParser.parseMap(Tools.rotateMap(map));

        level.registerPlayer(player);
        player.setDirection(Direction.WEST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        Optional<Direction> dir = inky.nextAiMove();
        assertThat(dir).isPresent();
        assertThat(dir.get()).isEqualTo(Direction.WEST);
    }

    /**
     * Tests if Inky does not move
     * if there is no path between Blinky and the Player.
     */
    @Test
    public void testIfInkyDoesNotMoveIfNoPathBetweenPlayerAndBlinky() {
        char[][] map = new char[][]{
            "###############".toCharArray(),
            "# P  #  B #  I#".toCharArray(),
            "###############".toCharArray()
        };
        Level level = ghostMapParser.parseMap(Tools.rotateMap(map));

        level.registerPlayer(player);
        player.setDirection(Direction.WEST);

        Inky inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());
        assertThat(inky).isNotNull();
        Optional<Direction> dir = inky.nextAiMove();
        assertThat(dir).isEmpty();
    }
}
