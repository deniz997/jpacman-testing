package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.tools.Tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test-Suite for the Map Parser.
 */
public class MapParserTest {

    private MapParser parser;

    @Mock
    private LevelFactory levelFactoryMock;
    @Mock
    private BoardFactory boardFactoryMock;

    /**
     * Setup the Mock Objects for the Map Parser before each test.
     */
    @BeforeEach
    void setup() {
        initMocks(this);

        parser = new MapParser(levelFactoryMock, boardFactoryMock);
    }

    /**
     * Tests if the map parsers creates a grid with the correct width and height.
     */
    @Test
    void testIfMapHasCorrectDimensions() {
        parser.parseMap(new char[][] {{' '}, {' '}});

        verify(boardFactoryMock, times(1)).createBoard(any());
        verify(boardFactoryMock).createBoard(argThat(
            (Square[][] grid) -> grid.length == 2 && grid[0].length == 1)
        );
    }

    /**
     * Tests if the map parsers calls the ground creation the correct number of times and that the grid contains squares.
     */
    @Test
    void testIfMapCreatesGridOfSquaresWithCreateGround() {
        Square s = mock(Square.class);

        when(boardFactoryMock.createGround()).thenReturn(s);

        parser.parseMap(new char[][] {{' ', ' '}, {' ', ' '}});

        verify(boardFactoryMock).createBoard(argThat(
            (Square[][] grid) -> {
                boolean incorrectSquare = false;
                for (Square[] squares : grid) {
                    for (Square square : squares) {
                        if (square != s) {
                            incorrectSquare = true;
                            break;
                        }
                    }
                }
                return !incorrectSquare;
            }
        ));

        verify(boardFactoryMock, times(4)).createGround();
    }

    /**
     * Tests if the map parsers calls the ground creation correctly on ' '.
     */
    @Test
    void testIfMapCreatesGroundCorrect() {
        parser.parseMap(new char[][] {{' '}});

        verify(boardFactoryMock, times(1)).createGround();
    }

    /**
     * Tests if the map parsers calls the wall creation correctly on '#'.
     */
    @Test
    void testIfMapCreatesWallCorrect() {
        parser.parseMap(new char[][] {{'#'}});

        verify(boardFactoryMock, times(1)).createWall();
    }

    /**
     * Tests if the map parsers calls the pellet creation correctly on '.'.
     */
    @Test
    void testIfMapCreatesPelletCorrect() {
        Square square = mock(Square.class);
        Pellet pellet = mock(Pellet.class);

        when(levelFactoryMock.createPellet()).thenReturn(pellet);
        when(boardFactoryMock.createGround()).thenReturn(square);

        parser.parseMap(new char[][] {{'.'}});

        verify(boardFactoryMock, times(1)).createGround();
        verify(levelFactoryMock, times(1)).createPellet();
        verify(pellet, times(1)).occupy(square);
    }

    /**
     * Tests if the map parsers calls the ghost square creation correctly on 'G'.
     */
    @Test
    void testIfMapCreatesGhostSquareCorrect() {
        Square square = mock(Square.class);
        Ghost ghost = mock(Ghost.class);

        when(boardFactoryMock.createGround()).thenReturn(square);
        when(levelFactoryMock.createGhost()).thenReturn(ghost);

        parser.parseMap(new char[][] {{'G'}});

        verify(boardFactoryMock, times(1)).createGround();
        verify(levelFactoryMock, times(1)).createGhost();
        verify(ghost, times(1)).occupy(square);
        verify(levelFactoryMock, times(1)).createLevel(
            any(),
            argThat((ghosts) -> ghosts.size() == 1 && ghosts.get(0) == ghost),
            any()
        );
    }

    /**
     * Tests if the map parsers calls the player square creation correctly on 'P'.
     */
    @Test
    void testIfMapCreatesPlayerSquareCorrect() {
        Square square = mock(Square.class);

        when(boardFactoryMock.createGround()).thenReturn(square);

        parser.parseMap(new char[][] {{'P'}});

        verify(boardFactoryMock, times(1)).createGround();
        verify(levelFactoryMock, times(1)).createLevel(
            any(),
            any(),
            argThat((playerStarts) -> playerStarts.size() == 1 && playerStarts.get(0) == square)
        );
    }

    /**
     * Tests if the map parser correctly converts the map from a list of strings to char array.
     */
    @Test
    void testIfMapIsCorrectlyConvertedFromStringListToCharArray() {
        Square ground = mock(Square.class);
        Square wall = mock(Square.class);

        when(boardFactoryMock.createGround()).thenReturn(ground);
        when(boardFactoryMock.createWall()).thenReturn(wall);
        when(ground.toString()).thenReturn("Ground Square");
        when(wall.toString()).thenReturn("Wall Square");

        parser.parseMap(new ArrayList<String>() {
            {
                add(" #");
                add("# ");
            }
        });
        final Square[][] expected = new Square[][]{
            {ground, wall},
            {wall, ground},
        };

        verify(boardFactoryMock).createBoard(eq(expected));
    }
}
