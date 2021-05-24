package nl.tudelft.jpacman.board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test cases for the Unit class.
 */
public class UnitTest {
    private BasicUnit unit;

    /**
     * Initializes the unit class before each test.
     */
    @BeforeEach
    public void initUnit() {
        unit = new BasicUnit();
        Square square = new BasicSquare();
        unit.occupy(square);
    }

    /**
     * Tests if the squaresAheadOf method returns the units own square if amount is set to 0.
     */
    @Test
    public void testSquaresAheadOfAmount0() {
        Square square = new BasicSquare();
        unit.setDirection(Direction.EAST);
        unit.getSquare().link(square, Direction.EAST);

        assertThat(unit.squaresAheadOf(0)).isEqualTo(unit.getSquare());
    }

    /**
     * Tests if the squaresAheadOf method returns the square in front of him if amount is set to 1.
     */
    @Test
    public void testSquaresAheadOfAmount1() {
        Square square = new BasicSquare();
        Square wrongSquare = new BasicSquare();
        unit.setDirection(Direction.EAST);
        unit.getSquare().link(square, Direction.EAST);
        unit.getSquare().link(wrongSquare, Direction.NORTH);
        unit.getSquare().link(wrongSquare, Direction.WEST);
        unit.getSquare().link(wrongSquare, Direction.SOUTH);

        assertThat(unit.squaresAheadOf(1)).isEqualTo(square);
    }

    /**
     * Tests if the squaresAheadOf method returns null if amount is set to 2,
     * but there are no 2 squares in front.
     * In actual map the edge square references itself.
     * If you set the amount to 3 here, then there will be a NullPointer exception!
     */
    @Test
    public void testSquaresAheadOfAmount2WithMapEdge() {
        Square square = new BasicSquare();
        Square wrongSquare = new BasicSquare();
        unit.setDirection(Direction.EAST);
        unit.getSquare().link(square, Direction.EAST);
        unit.getSquare().link(wrongSquare, Direction.NORTH);
        unit.getSquare().link(wrongSquare, Direction.WEST);
        unit.getSquare().link(wrongSquare, Direction.SOUTH);

        assertThat(unit.squaresAheadOf(2)).isNull();
    }
}
