package kasnerypack.game.gamelevels;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class LevelName implements Sprite {
    private String name;

    /**
     * constructor method.
     *
     * @param s level name.
     */
    public LevelName(String s) {
        this.name = s;
    }

    /**
     * drawOn method draw the sprite to the screen.
     *
     * @param d surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 + 150, 20, "Level Name: " + this.name, 15);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        return;
    }
}
