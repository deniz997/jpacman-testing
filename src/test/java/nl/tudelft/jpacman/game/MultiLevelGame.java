package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;

/**
 * A game that can handle multiple levels.
 */
public class MultiLevelGame extends Game {

    public MultiLevelGame(PointCalculator pointCalculator) {
        super(pointCalculator);
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public Level getLevel() {
        return null;
    }
}
