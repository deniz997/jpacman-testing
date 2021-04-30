package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen 
 *
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        // Remove the following placeholder:
        assertThat(unit).isNotNull();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        BasicSquare bsquare = new BasicSquare();
        unit.occupy(bsquare);
        assertThat(unit.hasSquare()).isEqualTo(true);
        assertThat(unit.getSquare()).isEqualTo(bsquare);
        assertThat(bsquare.getOccupants()).contains(unit).isEqualTo(true);
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        Square testSquare = new BasicSquare();
        Square otherSquare = new BasicSquare();
        unit.occupy(testSquare);
        unit.occupy(otherSquare);

        assertThat(unit.hasSquare()).isEqualTo(true);
        assertThat(unit.getSquare()).isEqualTo(otherSquare);
        assertThat(otherSquare.getOccupants().contains(unit)).isEqualTo(true);
    }
}
