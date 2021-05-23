package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the Board Class.
 */
public class BoardTest {

    /**
     * Tests if board is created correctly by checking its dimensions and its content.
     */
    @Test
    void testBoardCreation() {
        BasicSquare basicSquare = new BasicSquare();
        Board tBoard = new Board(new Square[][]{new Square[]{basicSquare}});
        assertThat(tBoard.getHeight()).isEqualTo(1);
        assertThat(tBoard.getWidth()).isEqualTo(1);
        assertThat(tBoard.invariant()).isEqualTo(true);
        assertThat(tBoard.squareAt(0, 0)).isEqualTo(basicSquare);
    }

    /**
     * Boundary Test for the withinBorders method.
     *
     * @param x      the x value of the square to test for this test
     * @param y      the y value of the square to test for this test
     * @param result the result we expect
     */
    @ParameterizedTest(name = "x={0}, y={1}, result={2}")
    @CsvSource({
        //X >= 0
        "0,0,true", "-1,1,false",
        //X < 4
        "4,2,false", "3,3,true",
        //Y >= 0
        "0,0,true", "1,-1,false",
        //Y < 4
        "2,4,false", "3,3,true"
    })
    void testBoardCreation(int x, int y, boolean result) {
        BasicSquare basicSquare = new BasicSquare();
        Board tBoard = new Board(new Square[][]{
            new Square[]{basicSquare, basicSquare, basicSquare, basicSquare},
            new Square[]{basicSquare, basicSquare, basicSquare, basicSquare},
            new Square[]{basicSquare, basicSquare, basicSquare, basicSquare},
            new Square[]{basicSquare, basicSquare, basicSquare, basicSquare}});
        assertThat(tBoard.withinBorders(x, y)).isEqualTo(result);
    }

}
