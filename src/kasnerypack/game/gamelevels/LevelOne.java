package kasnerypack.game.gamelevels;

import biuoop.DrawSurface;
import kasnerypack.game.sprites.SpriteCollection;
import kasnerypack.game.sprites.LineSprite;
import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.Circle;
import kasnerypack.game.sprites.EmptyCircle;
import kasnerypack.game.sprites.BackGround;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kasnery.
 */
public class LevelOne implements LevelInformation {
    private DrawSurface surface;

    /**
     * constructor method.
     *
     * @param d surface of level.
     */
    public LevelOne(DrawSurface d) {
        this.surface = d;
    }

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        LinkedList<Velocity> list = new LinkedList<>();
        Velocity tmp = Velocity.fromAngleAndSpeed(0, 8);
        list.add(tmp);
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 10;
    }

    @Override
    public int paddleWidth() {
        return 180;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        SpriteCollection sprites = new SpriteCollection();
        Color colorRec = Color.BLACK, colorShapes = Color.BLUE;
        Point point = new Point(0, 0);
        int width = this.surface.getWidth(), height = this.surface.getHeight();
        Rectangle rec = new Rectangle(point, width, height);
        Block block = new Block(rec, -1);
        block.setSingleColor(colorRec);
        sprites.addSprite(block);
        point = new Point(width / 2 - 1, 263);
        Sprite empty = new EmptyCircle(new Circle(point, 70, colorShapes));
        sprites.addSprite(empty);
        empty = new EmptyCircle(new Circle(point, 110, colorShapes));
        sprites.addSprite(empty);
        empty = new EmptyCircle(new Circle(point, 150, colorShapes));
        sprites.addSprite(empty);
        Line line = new Line(this.surface.getWidth() / 4 + 47, 260,
                this.surface.getWidth() / 1.45, 260);
        LineSprite sLine = new LineSprite(line, colorShapes);
        sprites.addSprite(sLine);
        line = new Line(this.surface.getWidth() / 2 - 1, 115, this.surface.getWidth() / 2 - 1, 410);
        sLine = new LineSprite(line, colorShapes);
        sprites.addSprite(sLine);
        BackGround bg = new BackGround(sprites);
        return bg;
    }

    @Override
    public List<Block> blocks() {
        double x = this.surface.getWidth() / 2 - 32.5;
        Point point = new Point(x, 250);
        Rectangle rec = new Rectangle(point, 65, 25);
        rec.setColor(Color.YELLOW);
        Block block = new Block(rec, 1);
        LinkedList<Block> list = new LinkedList<>();
        list.add(block);
        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }

}
