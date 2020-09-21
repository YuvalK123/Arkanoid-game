package kasnerypack.interfaces;

import biuoop.DrawSurface;

/**
 * @author kasnery
 */
public interface Sprite {
    /**
     * @param d surface to draw on.
     * drawOn method draw the sprite to the screen.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
}

