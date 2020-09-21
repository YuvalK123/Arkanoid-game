package kasnerypack.litseners;

import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;
import kasnerypack.generalbehaviors.Counter;
import kasnerypack.interfaces.HitListener;

/**
 * @author kasnery.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * constructor method for listener.
     */
    public ScoreTrackingListener() {

        this.currentScore = new Counter();
    }

    /**
     * constructor method for listener.
     *
     * @param score counter to reset this listener by.
     */
    public ScoreTrackingListener(Counter score) {

        this.currentScore = score;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
        if (beingHit.getHitPoints().equals("0")) {
            this.currentScore.increase(10);
        }
    }

    /**
     * method returns score.
     *
     * @return game's score.
     */
    public Counter getScore() {
        return this.currentScore;
    }

}
