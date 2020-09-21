package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;

/**
 * @author kasnery.
 */
public class BackGround implements Sprite {
    private SpriteCollection sprites;

    /**
     * constructor method of backGround.
     *
     * @param spriteCollection sprites that make the backGround.
     */
    public BackGround(SpriteCollection spriteCollection) {
        this.sprites = spriteCollection;
    }

    /**
     * drawOn method draw the sprite to the screen.
     *
     * @param d surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        this.sprites.drawAllOn(d);

    }


    @Override
    public void timePassed() {
        return;
    }
}
