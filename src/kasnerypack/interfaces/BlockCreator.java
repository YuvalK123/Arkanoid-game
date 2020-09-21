package kasnerypack.interfaces;

import kasnerypack.game.sprites.Block;

/**
 * @author kasnery.
 */
public interface BlockCreator {
    /**
     * Create a block at the specified location.
     *
     * @param xpos x placement.
     * @param ypos y placement.
     * @return block in said placement.
     */
    Block create(int xpos, int ypos);
}
