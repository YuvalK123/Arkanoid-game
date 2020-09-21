package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;

/**
 * @author kasnery.
 */
public class EmptyCircle implements Sprite {
    private Circle circle;

    /**
     * constructor method.
     *
     * @param c circle to draw by.
     */
    public EmptyCircle(Circle c) {
        this.circle = c;
    }

    /**
     * @param d surface to draw on.
     *          drawOn method draw the sprite to the screen.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.circle.getColor());
        d.drawCircle((int) this.circle.getPoint().getX(), (int) this.circle.getPoint().getY(), this.circle.getSize());
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
