package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
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

    @BeforeEach
    void setup() {
        initMocks(this);
        playerCollisions = new PlayerCollisions(pointCalculator);
    }

    @Test
    void checkIfPlayerAndGhostCollisionResultsPlayerDeath() {
        playerCollisions.collide(player, ghost);
        playerDies();
    }

    @Test
    void checkIfGhostAndPlayerCollisionResultsPlayerDeath() {
        playerCollisions.collide(ghost, player);
        playerDies();
    }

    @Test
    void checkIfPlayerAndPelletCollisionResultsPointCollection() {
        playerCollisions.collide(player, pellet);
        gainPoint();
    }

    @Test
    void checkIfPlayerAndPlayerCollisionResultsInNothing() {
        playerCollisions.collide(player, player);
        nothingHappens();
    }

    @Test
    void checkIfGhostAndGhostCollisionResultsInNothing() {
        playerCollisions.collide(ghost, ghost);
        nothingHappens();
    }

    @Test
    void checkIfGhostAndPelletCollisionResultsInNothing() {
        playerCollisions.collide(ghost, pellet);
        nothingHappens();
    }

    @Test
    void checkIfPelletAndGhostCollisionResultsInNothing() {
        playerCollisions.collide(pellet, ghost);
        nothingHappens();
    }

    @Test
    void checkIfPelletAndPlayerCollisionResultsInNothing() {
        playerCollisions.collide(pellet, player);
        gainPoint();
    }

    @Test
    void checkIfPelletAndPelletCollisionResultsInNothing() {
        playerCollisions.collide(pellet, pellet);
        nothingHappens();
    }

    void nothingHappens() {
        verify(pointCalculator, times(0)).consumedAPellet(player, pellet);
        verify(pointCalculator, times(0)).collidedWithAGhost(player, ghost);
        verify(pellet, times(0)).leaveSquare();
    }

    void playerDies() {
        verify(pointCalculator, times(1)).collidedWithAGhost(player, ghost);
        verify(player, times(1)).setAlive(false);
    }

    void gainPoint() {
        verify(pointCalculator, times(1)).consumedAPellet(player, pellet);
        verify(pellet, times(1)).leaveSquare();
    }

}
