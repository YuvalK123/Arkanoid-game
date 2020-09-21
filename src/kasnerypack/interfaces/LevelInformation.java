package kasnerypack.interfaces;

import kasnerypack.game.sprites.Block;
import kasnerypack.generalbehaviors.Velocity;

import java.util.List;

/**
 * @author kasnery.
 */
public interface LevelInformation {
    /**
     * @return number of balls in level.
     */
    int numberOfBalls();

    /**
     * method initializes velocity of each ball.
     *
     * @return list of velocities.
     */
    List<Velocity> initialBallVelocities();

    /**
     * @return speed of paddle.
     */
    int paddleSpeed();

    /**
     * @return paddle width.
     */
    int paddleWidth();
    // the level name will be displayed at the top of the screen.

    /**
     * @return name of level.
     */
    String levelName();

    /**
     * method creates and returns background.
     *
     * @return a sprite with the background of the level
     */
    Sprite getBackground();

    /**
     * method creates blocks for game.
     *
     * @return list of blocks.
     */
    List<Block> blocks();

    /**
     * @return number of block to remove so level is cleared.
     */
    int numberOfBlocksToRemove();
}
