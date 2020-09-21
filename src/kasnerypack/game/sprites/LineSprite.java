package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Line;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class LineSprite implements Sprite {
    private Line line;
    private Color color;

    /**
     * constructor method.
     *
     * @param l line to draw.
     * @param c color of line.
     */
    public LineSprite(Line l, Color c) {
        this.line = l;
        this.color = c;
    }

    /**
     * @param d surface to draw on.
     *          drawOn method draw the sprite to the screen.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        int x1 = (int) this.line.start().getX(), x2 = (int) this.line.end().getX();
        int y1 = (int) this.line.start().getY(), y2 = (int) this.line.end().getY();
        d.drawLine(x1, y1, x2, y2);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        return;
    }
}
