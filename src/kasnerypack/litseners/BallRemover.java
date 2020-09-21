package kasnerypack.litseners;

import kasnerypack.game.GameLevel;
import kasnerypack.generalbehaviors.Counter;
import kasnerypack.interfaces.HitListener;
import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;

/**
 * @author @kasnery.
 */
public class BallRemover implements HitListener {
    private GameLevel game;
    private Counter balls;

    /**
     * constructor method for class.
     *
     * @param g game to remove ball from.
     */
    public BallRemover(GameLevel g) {
        this.game = g;
    }

    /**
     * method updates balls num.
     *
     * @param counter new balls counter.
     */
    public void ballsUpdate(Counter counter) {
        this.balls = counter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        if (this.balls != null) {
            this.balls.decrease(1);
        }
    }
}
