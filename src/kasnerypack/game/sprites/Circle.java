package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Point;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class Circle implements Sprite {
    private Point point;
    private int size;
    private Color color;

    /**
     * constructor method for circle.
     *
     * @param p    center of circle.
     * @param size radius of circle.
     * @param c    color of circle.
     */
    public Circle(Point p, int size, Color c) {
        this.point = p;
        this.size = size;
        this.color = c;
    }

    /**
     * @param d surface to draw on.
     *          drawOn method draw the sprite to the screen.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle((int) this.point.getX(), (int) this.point.getY(), this.size);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }

    /**
     * @return center of circle.
     */
    public Point getPoint() {
        return this.point;
    }

    /**
     * @return size of circle.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return color of circle.
     */
    public Color getColor() {
        return this.color;
    }
}
