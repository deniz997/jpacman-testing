package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;

/**
 *
 */

public class StateMachine {
    private Launcher launcher;
    private Game game;
    private State currentState;


    /**
     * Initializes the game with map.
     * @param mapName -> map file path
     */

    public void before(String mapName) {
        launcher = new Launcher().withMapFile(mapName);
        game = launcher.getGame();
        launcher.launch();
        currentState = State.game_launched;
    }

    /**
     * We need to find out if we are running, paused, won or lost.
     */
    public State inspectCurrentState() {
        if (game.isInProgress()) {
            return State.game_running;
        }else if (game.getLevel().isAnyPlayerAlive() && !game.isInProgress()){
            return State.game_over;
        }else if (game.getLevel().remainingPellets()==0 && !game.isInProgress()) {
            return  State.game_won;
        }else {
            return State.game_paused;
        }
    }

}
