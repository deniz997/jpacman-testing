package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test-Suites for testing player collisions with all combinations.
 */

public class PlayerCollisionsTest {

    private PlayerCollisions playerCollisions;

    @Mock
    private PointCalculator pointCalculator;

    @Mock
    private Player player;
    @Mock
    private Ghost ghost;
    @Mock
    private Pellet pellet;

    /**
     * Setup the Mock Objects for the Player Collisions before each test.
     */

    @BeforeEach
    void setup() {
        initMocks(this);
        playerCollisions = new PlayerCollisions(pointCalculator);
    }

    /**
     * Checks if player dies after the collision of player and ghost.
     */
    @Test
    void checkIfPlayerAndGhostCollisionResultsPlayerDeath() {
        playerCollisions.collide(player, ghost);
        playerDies();
    }

    /**
     * Checks if player dies after the collision of ghost and player.
     */
    @Test
    void checkIfGhostAndPlayerCollisionResultsPlayerDeath() {
        playerCollisions.collide(ghost, player);
        playerDies();
    }

    /**
     * Checks if point is gained after the collision of player and pellet.
     */
    @Test
    void checkIfPlayerAndPelletCollisionResultsPointCollection() {
        playerCollisions.collide(player, pellet);
        gainPoint();
    }

    /**
     * Checks if nothing happens after the collision of player and player.
     */
    @Test
    void checkIfPlayerAndPlayerCollisionResultsInNothing() {
        playerCollisions.collide(player, player);
        nothingHappens();
    }

    /**
     * Checks if nothing happens after the collision of ghost and ghost.
     */
    @Test
    void checkIfGhostAndGhostCollisionResultsInNothing() {
        playerCollisions.collide(ghost, ghost);
        nothingHappens();
    }

    /**
     * Checks if nothing happens after the collision of ghost and pellet.
     */
    @Test
    void checkIfGhostAndPelletCollisionResultsInNothing() {
        playerCollisions.collide(ghost, pellet);
        nothingHappens();
    }

    /**
     * Checks if nothing happens after the collision of pellet and ghost.
     */
    @Test
    void checkIfPelletAndGhostCollisionResultsInNothing() {
        playerCollisions.collide(pellet, ghost);
        nothingHappens();
    }

    /**
     * Checks if point is gained after the collision of pellet and player.
     */
    @Test
    void checkIfPelletAndPlayerCollisionResultsInNothing() {
        playerCollisions.collide(pellet, player);
        gainPoint();
    }

    /**
     * Checks if nothing happens after the collision of pellet and pellet.
     */
    @Test
    void checkIfPelletAndPelletCollisionResultsInNothing() {
        playerCollisions.collide(pellet, pellet);
        nothingHappens();
    }

    /**
     * Defines nothing happening as never calling the functions.
     */
    void nothingHappens() {
        verify(pointCalculator, times(0)).consumedAPellet(player, pellet);
        verify(pointCalculator, times(0)).collidedWithAGhost(player, ghost);
        verify(pellet, times(0)).leaveSquare();
    }

    /**
     * Defines death of the player by tracking the calling of the necessary functions.
     */
    void playerDies() {
        verify(pointCalculator, times(1)).collidedWithAGhost(player, ghost);
        verify(player, times(1)).setAlive(false);
    }

    /**
     * Defines gaining point by tracking the calling of the necessary functions.
     */
    void gainPoint() {
        verify(pointCalculator, times(1)).consumedAPellet(player, pellet);
        verify(pellet, times(1)).leaveSquare();
    }

}
