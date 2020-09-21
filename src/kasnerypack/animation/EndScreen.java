package kasnerypack.animation;

import biuoop.DrawSurface;
import kasnerypack.game.sprites.Block;
import kasnerypack.interfaces.Animation;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class EndScreen implements Animation {
    private Boolean stop;
    private Boolean won;
    private int score;

    /**
     * constructor method.
     *
     * @param won boolean - if won the game.
     * @param s   score.
     */
    public EndScreen(Boolean won, int s) {
        this.stop = false;
        this.won = won;
        this.score = s;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        int width = d.getWidth(), height = d.getHeight();
        String title = "GAME OVER";
        int x = width / 2 - 350;
        String titleScore = "Your score is: " + this.score;
        Rectangle rec = new Rectangle(new Point(0, 0), width, height);
        rec.setColor(Color.BLACK);
        Block bg = new Block(rec, -1);
        if (this.won) {
            title = "YOU WON!";
            x = width / 2 - 310;
        }
        bg.drawOn(d);
        d.setColor(Color.WHITE);
        d.drawText(x, height / 2 - 50, title, 120);
        d.drawText(width / 2 - 325, height / 2 + 100, titleScore, 80);
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
