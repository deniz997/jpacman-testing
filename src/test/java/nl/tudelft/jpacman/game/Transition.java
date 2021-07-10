package nl.tudelft.jpacman.game;


/**
 * n is the number of remaining pellets.
 * Start -> start button
 * Stop -> stop button
 * a1 -> Arrow keys [Player moves towards an empty square]
 * a2 -> Arrow keys [Player moves towards a ghost]
 * a3 -> Arrow keys [Player moves towards a pellet & n!=1]
 * a4 -> Arrow keys [Player moves towards a pellet & n==1]
 * g1 -> Ghost moves automatically [Movement to a square occupied by a player]
 * g2 -> Ghost moves automatically [Movement to a square not occupied by a player]
 */

public enum Transition {
    start,
    stop,
    a1,
    a2,
    a3,
    a4,
    g1,
    g2
}
