package kasnerypack.interfaces;

import biuoop.DrawSurface;

/**
 * @author kasnery.
 */
public interface Animation {
    /**
     * @param d drawface to do frame on.
     */
    void doOneFrame(DrawSurface d);

    /**
     * method check if animation should stop.
     *
     * @return true if should stop. false otherwise.
     */
    boolean shouldStop();
}
