package kasnerypack.game;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class LivesIndicator implements Sprite {
    private int remainingLives;

    /**
     * constructor method.
     *
     * @param starter lives.
     */
    public LivesIndicator(int starter) {
        this.remainingLives = starter;
    }

    /**
     * @param d surface to draw on.
     *          drawOn method draw the sprite to the screen.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(40, 15, "Lives: " + this.remainingLives, 15);
    }

    /**
     * method returns how many lives left.
     *
     * @return remaining lives.
     */
    public int getLives() {
        return this.remainingLives;
    }

    /**
     * method sets remaining lives.
     *
     * @param lives to set number of lives to.
     */
    public void setLives(int lives) {
        this.remainingLives = lives;
    }

    /**
     * method decrease lives by amount of.
     *
     * @param i lives to decrease by.
     */
    public void decreaseLivesBy(int i) {
        this.remainingLives = this.remainingLives - i;
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        return;
    }
}
