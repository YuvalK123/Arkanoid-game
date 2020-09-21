package kasnerypack.litseners;

import kasnerypack.interfaces.HitListener;
import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;

/**
 * @author kasnery.
 */
public class PrintingHitListener implements HitListener {
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        String i = beingHit.getHitPoints();
        if (i == "X") {
            System.out.println("A Block with " + i + " points was hit.");
        } else if (Integer.valueOf(i) != -1) {
            System.out.println("A Block with " + i + " points was hit.");
        }
    }
}
