package kasnerypack.animation;

import biuoop.DrawSurface;
import kasnerypack.game.HighScoresTable;
import kasnerypack.game.sprites.BackGround;
import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.LineSprite;
import kasnerypack.game.sprites.SpriteCollection;
import kasnerypack.interfaces.Animation;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;

/**
 * @author kasnery.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable table;
    private boolean stop;

    /**
     * constructor method with HighScoresTable arg.
     *
     * @param table HighScoresTable to to animate.
     */
    public HighScoresAnimation(HighScoresTable table) {
        this.table = table;
        this.stop = false;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        int width = d.getWidth(), height = d.getHeight();
        SpriteCollection sprites = new SpriteCollection();
        BackGround bg = new BackGround(sprites);
        Block block = new Block(new Rectangle(new Point(0, 0), width, height), -1);
        block.setSingleColor(Color.WHITE);
        sprites.addSprite(block);
        Line line = new Line(new Point(100, 150), new Point(700, 150));
        LineSprite lineSprite = new LineSprite(line, Color.BLUE);
        sprites.addSprite(lineSprite);
        line = new Line(new Point(400, 150), new Point(400, 500));
        lineSprite = new LineSprite(line, Color.BLUE);
        sprites.addSprite(lineSprite);
        bg.drawOn(d);
        d.setColor(Color.RED);
        d.drawText(240, 550, "press space to return", 25);
        d.setColor(Color.BLACK);
        d.drawText(150, 130, "name", 15);
        d.drawText(450, 130, "scores", 15);
        this.table.drawUsers(d, new Point(150, 200), new Point(450, 200));
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
