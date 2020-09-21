package kasnerypack.animation;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Animation;

/**
 * @author kasnery.
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * constructor method.
     */
    public PauseScreen() {
        this.stop = false;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    /**
     * method check if animation should stop.
     *
     * @return true if should stop. false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
