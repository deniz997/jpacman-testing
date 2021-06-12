package nl.tudelft.jpacman.level;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
 */

public class DefaultPlayerInteractionMapTest extends CollisionMapTest {

    /**
     *
     */
    @BeforeEach
    void setup() {
        initMocks(this);
        this.collisionMap = new DefaultPlayerInteractionMap(this.pointCalculator);
    }
}
