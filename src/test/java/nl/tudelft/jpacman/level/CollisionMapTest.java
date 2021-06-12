package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test-Suites for testing player collisions with all combinations.
 */

public abstract class CollisionMapTest {

    private CollisionMap collisionMap;

    @Mock
    private PointCalculator pointCalculator;

    @Mock
    private Player player;
    @Mock
    private Ghost ghost;
    @Mock
    private Pellet pellet;

    /**
     * Checks if player dies after the collision of player and ghost.
     */
    @Test
    void checkIfPlayerAndGhostCollisionResultsPlayerDeath() {
        collisionMap.collide(player, ghost);
        playerDies();
    }

    /**
     * Checks if player dies after the collision of ghost and player.
     */
    @Test
    void checkIfGhostAndPlayerCollisionResultsPlayerDeath() {
        collisionMap.collide(ghost, player);
        playerDies();
    }

    /**
     * Checks if point is gained after the collision of player and pellet.
     */
    @Test
    void checkIfPlayerAndPelletCollisionResultsPointCollection() {
        collisionMap.collide(player, pellet);
        gainPoint();
    }

    /**
     * Checks if nothing happens after the collision of player and player.
     */
    @Test
    void checkIfPlayerAndPlayerCollisionResultsInNothing() {
        collisionMap.collide(player, player);
        nothingHappens();
    }

    /**
     * Checks if nothing happens after the collision of ghost and ghost.
     */
    @Test
    void checkIfGhostAndGhostCollisionResultsInNothing() {
        collisionMap.collide(ghost, ghost);
        nothingHappens();
    }

    /**
     * Checks if nothing happens after the collision of ghost and pellet.
     */
    @Test
    void checkIfGhostAndPelletCollisionResultsInNothing() {
        collisionMap.collide(ghost, pellet);
        nothingHappens();
    }

    /**
     * Checks if nothing happens after the collision of pellet and ghost.
     */
    @Test
    void checkIfPelletAndGhostCollisionResultsInNothing() {
        collisionMap.collide(pellet, ghost);
        nothingHappens();
    }

    /**
     * Checks if point is gained after the collision of pellet and player.
     */
    @Test
    void checkIfPelletAndPlayerCollisionResultsInNothing() {
        collisionMap.collide(pellet, player);
        gainPoint();
    }

    /**
     * Checks if nothing happens after the collision of pellet and pellet.
     */
    @Test
    void checkIfPelletAndPelletCollisionResultsInNothing() {
        collisionMap.collide(pellet, pellet);
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

    /**
     * Sets collision map.
     * @param collMap -> can be an instance of PlayerCollision or DefaultPlayerInteractionMap.
     */
    void setCollisionMap(CollisionMap collMap) {
        this.collisionMap = collMap;
    }

    /**
     * Gets point calculator.
     * @return pointCalculator to access private point calculator object.
     */
    PointCalculator getPointCalculator() {
        return this.pointCalculator;
    }


}
