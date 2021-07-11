package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;

/**
 * A game that can handle multiple levels.
 */
public class MultiLevelGame extends Game {

    /**
     * The player of this game.
     */
    private final Player player;

    /**
     * The level of this game.
     */
    private final Level level;

    private boolean currentLevelWon;
    private boolean currentLevelLost;

    private Runnable restartGameRunnable;
    private Runnable loadNextLevelRunnable;

    /**
     * Constructor for a multi level game.
     * @param player the player playing the game
     * @param level the level we are playing
     * @param pointCalculator the point calculator which will calculate the points
     */
    public MultiLevelGame(Player player, Level level, PointCalculator pointCalculator) {
        super(pointCalculator);

        assert player != null;
        assert level != null;

        currentLevelWon = false;
        currentLevelLost = false;

        this.player = player;
        this.level = level;
        getLevel().registerPlayer(this.player);
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void start() {
        if (!isInProgress() && currentLevelLost) {
            currentLevelLost = false;
            this.restartGameRunnable.run();
        }
        if (!isInProgress() && currentLevelWon) {
            currentLevelWon = false;
            this.loadNextLevelRunnable.run();
        }

        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void levelWon() {
        currentLevelWon = true;
        super.levelWon();
    }

    @Override
    public void levelLost() {
        currentLevelLost = true;
        super.levelLost();
    }

    /**
     * Register a restart callback, that will be called whenever the game is to be restarted.
     * @param restart the function that should be called
     */
    public void registerRestartRunnable(Runnable restart) {
        restartGameRunnable = restart;
    }

    /**
     * Register a load next level callback,
     * that will be called whenever the game is to be loading the next level.
     * @param next the function that should be called
     */
    public void registerLoadNextLevelRunnable(Runnable next) {
        loadNextLevelRunnable = next;
    }
}
