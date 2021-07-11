package nl.tudelft.jpacman;

import nl.tudelft.jpacman.game.Game;
import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.points.PointCalculatorLoader;

/**
 * A launcher that can handle multiple levels.
 */
public class MultiLevelLauncher extends Launcher {
    private MultiLevelGame multiGame;

    private final String[] levels = {"/board.txt", "/board2.txt", "/board3.txt"};

    private int currentLevel = 0;

    @Override
    public Game getGame() {
        return multiGame;
    }

    @Override
    protected String getLevelMap() {
        return levels[currentLevel];
    }

    /**
     * Creates a new game using the level from {@link #makeLevel()}.
     *
     * @return a new Game.
     */
    @Override
    public Game makeGame() {
        GameFactory gf = getGameFactory();
        multiGame = gf.createSinglePlayerGameWithMultipleLevels(
            makeLevel(), new PointCalculatorLoader().load());
        multiGame.registerRestartRunnable(this::restartGame);
        multiGame.registerLoadNextLevelRunnable(this::loadNextLevel);
        return multiGame;
    }

    private void restartGame() {
        currentLevel = 0;
        dispose();
        launch();
        getGame().start();
    }

    private void loadNextLevel() {
        if (currentLevel + 1 >= levels.length) {
            return;
        }
        currentLevel += 1;
        dispose();
        launch();
        getGame().start();
    }

    /**
     * Starts a multi level pacman game.
     * @param args arguments passed on the command line
     */
    public static void main(String[] args) {
        new MultiLevelLauncher().launch();
    }
}
