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

    /**
     * Constructor for a multi level game.
     * @param player the player playing the game
     * @param level the level we are currently playing
     * @param pointCalculator the point calculator which will calculate the points
     */
    public MultiLevelGame(Player player, Level level, PointCalculator pointCalculator) {
        super(pointCalculator);

        assert player != null;
        assert level != null;

        this.player = player;
        this.level = level;
        this.level.registerPlayer(this.player);
    }

    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    @Override
    public Level getLevel() {
        return level;
    }
}
