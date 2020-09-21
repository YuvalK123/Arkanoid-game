package kasnerypack.litseners;

import kasnerypack.game.GameLevel;
import kasnerypack.generalbehaviors.Counter;
import kasnerypack.interfaces.HitListener;
import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;

/**
 * @author kasnery.
 */
public class BlockRemover implements HitListener {
    private GameLevel game;
    private Counter remainingBlocks;

    /**
     * constructor method.
     *
     * @param game          to modify in.
     * @param removedBlocks from game
     */
    public BlockRemover(GameLevel game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    // Blocks that are hit and reach 0 hit-points should be removed
    // from the game. Remember to remove this listener from the block
    // that is being removed from the game.
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints().equals("0")) {
            beingHit.removeFromGame(this.game);
            beingHit.removeHitListener(this);
            this.remainingBlocks.decrease(1);
        }
    }
}
