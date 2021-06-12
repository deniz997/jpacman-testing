package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests default player interactions via CollisionMapTest.
 */

public class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    /**
     * Setup mocks and inits collision map as DefaultPlayerInteractionMap.
     */
    @BeforeEach
    void setup() {
        initMocks(this);
        setCollisionMap(new DefaultPlayerInteractionMap(getPointCalculator()));
    }
}
