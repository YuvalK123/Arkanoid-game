package kasnerypack.game.gamelevels;

import kasnerypack.game.sprites.Block;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.interfaces.Sprite;

import java.util.List;

/**
 * @author kasnery.
 */
public class LevelFromFile implements LevelInformation {
    private List<Velocity> ballVelocities;
    private List<Block> blocks;
    private String levelName;
    private int paddleSpeed;
    private int paddleWidth;
    private Sprite bg;
    private int blocksToDestroy;

    /**
     * constructor method of all values needed to set level by.
     *
     * @param velocities    of balls.
     * @param blocks        of level.
     * @param name          of level.
     * @param paddleSpeed   to set paddle speed by.
     * @param paddleWidth   to set paddle width by..
     * @param bg            background of level.
     * @param blocksDestroy int of how many blocks to destroy to finish level.
     */
    public LevelFromFile(List<Velocity> velocities, List<Block> blocks, String name, int paddleSpeed,
                         int paddleWidth, Sprite bg, int blocksDestroy) {
        this.ballVelocities = velocities;
        this.blocks = blocks;
        this.levelName = name;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.bg = bg;
        this.blocksToDestroy = blocksDestroy;

    }

    /**
     * @return number of balls in level.
     */
    @Override
    public int numberOfBalls() {
        return this.ballVelocities.size();
    }

    /**
     * method initializes velocity of each ball.
     *
     * @return list of velocities.
     */
    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }

    /**
     * @return speed of paddle.
     */
    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * @return paddle width.
     */
    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * @return name of level.
     */
    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * method creates and returns background.
     *
     * @return a sprite with the background of the level
     */
    @Override
    public Sprite getBackground() {
        return this.bg;
    }

    /**
     * method creates blocks for game.
     *
     * @return list of blocks.
     */
    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * @return number of block to remove so level is cleared.
     */
    @Override
    public int numberOfBlocksToRemove() {
        return this.blocksToDestroy;
    }
}
