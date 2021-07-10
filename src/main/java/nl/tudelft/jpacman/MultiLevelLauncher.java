package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.points.PointCalculatorLoader;

/**
 * A launcher that can handle multiple levels.
 */
public class MultiLevelLauncher extends Launcher {
    private MultiLevelGame multiGame;

    /**
     * Constructor initializing game.
     */
    public MultiLevelLauncher() {
        multiGame = makeGame();
    }

    @Override
    public MultiLevelGame getGame() {
        return multiGame;
    }

    /**
     * Creates a new game using the level from {@link #makeLevel()}.
     *
     * @return a new Game.
     */
    @Override
    public MultiLevelGame makeGame() {
        GameFactory gf = getGameFactory();
        Level level = makeLevel();
        multiGame = gf.createSinglePlayerGameWithMultipleLevels(
            level, new PointCalculatorLoader().load());
        return multiGame;
    }
}
