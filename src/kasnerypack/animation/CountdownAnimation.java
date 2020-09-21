package kasnerypack.animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import kasnerypack.game.sprites.SpriteCollection;
import kasnerypack.interfaces.Animation;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class CountdownAnimation implements Animation {
    private SpriteCollection sprites;
    private int counter;
    private Boolean beginCount;
    private long seconds;
    private Boolean stop;

    /**
     * Constructor method with double, int and SpriteCollection.
     *
     * @param numOfSeconds to count.
     * @param countFrom    what num.
     * @param gameScreen   spriteCollection.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.sprites = gameScreen;
        this.seconds = (long) (numOfSeconds * 1000) / (countFrom + 1);
        this.counter = countFrom;
        this.beginCount = true;
        this.stop = false;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        Sleeper sleeper = new Sleeper();
        this.sprites.drawAllOn(d);
        if (this.beginCount) {
            this.beginCount = false;
            return;
        }
        int x = d.getWidth() / 2;
        int y = d.getHeight() / 2;
        if (this.counter == -1) {
            this.stop = true;
        }
        d.setColor(Color.BLACK);
        if (this.counter == 0) {
            d.drawText(x - 460, y + 100, "GO!", x + 20);
        } else if (this.counter > 0) {
            d.drawText(x - 130, y + 160, String.valueOf(this.counter), x + 20);
        }
        d.setColor(Color.WHITE);
        if (this.counter == 0) {
            d.drawText(x - 450, y + 150, "GO!", x);
        } else if (this.counter > 0) {
            d.drawText(x - 120, y + 150, String.valueOf(this.counter), x);
        }
        sleeper.sleepFor(this.seconds);
        this.counter--;
    }

    /**
     * method check if animation should stop.
     *
     * @return true if should stop. false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
