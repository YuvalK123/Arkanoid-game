package kasnerypack.game.gamelevels;

import biuoop.DrawSurface;
import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.Circle;
import kasnerypack.game.sprites.LineSprite;
import kasnerypack.game.sprites.SpriteCollection;
import kasnerypack.game.sprites.BackGround;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kasnery.
 */
public class LevelTwo implements LevelInformation {
    private DrawSurface surface;

    /**
     * constructor method.
     *
     * @param s drawSurface of gui.
     */
    public LevelTwo(DrawSurface s) {
        this.surface = s;
    }

    @Override
    public int numberOfBalls() {
        return 10;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        int num = this.numberOfBalls(), angle = -68, range = 15;
        LinkedList<Velocity> list = new LinkedList<>();
        for (int i = 0; i < num; i++) {
            Velocity velocity = Velocity.fromAngleAndSpeed(angle + (range * i), 8);
            list.add(velocity);
        }
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 4;
    }

    @Override
    public int paddleWidth() {
        return (int) (this.surface.getWidth() * 0.7);
    }

    @Override
    public String levelName() {
        return "Sunny side up";
    }

    @Override
    public Sprite getBackground() {
        SpriteCollection sprites = new SpriteCollection();
        BackGround bg = new BackGround(sprites);
        Color yellow = new Color(255, 248, 161);
        int width = this.surface.getWidth(), height = this.surface.getHeight();
        Rectangle rec = new Rectangle(new Point(0, 0), width, height);
        rec.setColor(new Color(216, 250, 255));
        Block block = new Block(rec, -1);
        sprites.addSprite(block);

        Point point = new Point(150, 180);
        Point end = new Point(700, 300);
        Line line = new Line(point, end);
        LineSprite lineSprite = new LineSprite(line, yellow);
        sprites.addSprite(lineSprite);

        end = new Point(50, 300);
        line = new Line(point, end);
        lineSprite = new LineSprite(line, yellow);
        sprites.addSprite(lineSprite);

        for (int i = 1; i < 440; i++) {
            end = new Point(700 - (i * 1.5), 300);
            line = new Line(point, end);
            lineSprite = new LineSprite(line, yellow);
            sprites.addSprite(lineSprite);

        }

        Color c = new Color(255, 254, 169);
        Circle circle = new Circle(point, 80, c);
        sprites.addSprite(circle);

        c = new Color(255, 247, 114);
        circle = new Circle(point, 60, c);
        sprites.addSprite(circle);

        c = new Color(251, 255, 89);
        circle = new Circle(point, 40, c);
        sprites.addSprite(circle);

        Sprite[] birds = createBirds(new Point(150, 300));
        for (Sprite s : birds) {
            if (s == null) {
                break;
            }
            sprites.addSprite(s);
        }
        return bg;
    }

    /**
     * method creates birds for backGround.
     *
     * @param mid point of main bird.
     * @return sprite array of birds.
     */
    private Sprite[] createBirds(Point mid) {
        Sprite[] sprites = new Sprite[50];
        double x = mid.getX(), y = mid.getY();
        double xTmp = x, yTmp = y;
        for (int i = 0; i < 19; i = i + 2) {
            createBird(mid, sprites, i);
            xTmp = xTmp + 100;
            yTmp = yTmp - 15;
            mid = new Point(xTmp, yTmp);
        }
        xTmp = x;
        yTmp = y;
        for (int i = 19; i < 40; i = i + 2) {
            createBird(mid, sprites, i);
            xTmp = xTmp + 100;
            yTmp = yTmp + 40;
            mid = new Point(xTmp, yTmp);
        }

        return sprites;
    }

    /**
     * method creates a single bird.
     *
     * @param mid     point of bird.
     * @param sprites to add bird to.
     * @param index   of sprites to add lines of birds to.
     */
    private void createBird(Point mid, Sprite[] sprites, int index) {
        double x = mid.getX(), y = mid.getY();
        Line line = new Line(new Point(x - 10, y - 30), mid);
        LineSprite lineSprite = new LineSprite(line, Color.BLACK);
        sprites[index] = lineSprite;
        line = new Line(new Point(x + 20, y - 30), mid);
        lineSprite = new LineSprite(line, Color.BLACK);
        sprites[index + 1] = lineSprite;
    }

    @Override
    public List<Block> blocks() {
        int j = 4;
        Rectangle rec = new Rectangle(new Point(0, 300), 63.5, 25);
        Color[] color = {Color.RED, Color.orange, Color.GREEN, Color.BLUE, Color.PINK};
        Block block = new Block(rec, 1);
        Block[] blocks = block.createBlockLine(11, this.surface.getWidth());
        for (int i = 0; i < 11; i++) {
            if (i == 2 || i == 4 || i == 7 || i == 9) {
                j--;
            }
            blocks[i].setSingleColor(color[j]);
            blocks[i].setStroke(Color.BLACK);
        }
        return new ArrayList<>(Arrays.asList(blocks));
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}
