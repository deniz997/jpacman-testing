package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Tests player collisions via CollisionMapTest.
 */
public class PlayerCollisionsTest extends CollisionMapTest {

    /**
     * Setup mocks and inits collision map as PlayerCollisions.
     */
    @Override
    @BeforeEach
    protected void setup() {
        initMocks(this);
        setCollisionMap(new PlayerCollisions(getPointCalculator()));
    }
}
