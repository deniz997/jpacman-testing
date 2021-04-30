package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.Test;

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
}
