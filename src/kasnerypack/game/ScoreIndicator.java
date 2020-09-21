package kasnerypack.game;

import biuoop.DrawSurface;
import kasnerypack.generalbehaviors.Counter;
import kasnerypack.interfaces.Sprite;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class ScoreIndicator implements Sprite {
    private Counter scoreCounter;

    /**
     * constructor method resets field.
     */
    public ScoreIndicator() {
        this.scoreCounter = new Counter();
    }

    /**
     * method updates the score.
     *
     * @param tmp to update score to.
     */
    public void updateScore(ScoreIndicator tmp) {
        this.scoreCounter.resetValueTo(tmp.getValue());
    }

    /**
     * @param d surface to draw on.
     *          drawOn method draw the sprite to the screen.
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(d.getWidth() / 2 - 60, 20, "Score:" + this.scoreCounter.getValue(), 20);
    }

    /**
     * method updates score that the game has been won, and updates score accordingly.
     */
    public void blocksEmptied() {
        this.scoreCounter.increase(100);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        return;
    }

    /**
     * @return score.
     */
    public int getValue() {
        return this.scoreCounter.getValue();
    }

    /**
     * @return scoreCounter of indicator.
     */
    public Counter getScoreCounter() {
        return this.scoreCounter;
    }
}
