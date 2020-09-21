package kasnerypack.interfaces;

import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;

/**
 * @author kasnery.
 */
public interface HitListener {

    /**
     * method is called whenever the beingHit object is hit.
     *
     * @param beingHit block that was hit.
     * @param hitter   ball that hit the block.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
