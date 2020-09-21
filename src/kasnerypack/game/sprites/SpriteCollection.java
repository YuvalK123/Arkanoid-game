package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kasnery
 */
public class SpriteCollection {
    private LinkedList<Sprite> sprites;

    /**
     * constructor method. allocates memory for sprites field.
     */
    public SpriteCollection() {
        this.sprites = new LinkedList<>();
    }

    /**
     * addSprite method adds a sprite to sprite collection.
     *
     * @param s to add to sprites.
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * method call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * method call drawOn(d) on all sprites.
     *
     * @param d surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }

    /**
     * method removes sprite s from list.
     *
     * @param s sprite to remove from list.
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
}
