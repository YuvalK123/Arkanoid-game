package kasnerypack.game.gamelevels;

import biuoop.DrawSurface;
import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.Circle;
import kasnerypack.game.sprites.SpriteCollection;
import kasnerypack.game.sprites.BackGround;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author kasnery.
 */
public class LevelThree implements LevelInformation {
    private DrawSurface surface;

    /**
     * constructor method.
     *
     * @param s drawSurface to draw on.
     */
    public LevelThree(DrawSurface s) {
        this.surface = s;
    }

    @Override
    public int numberOfBalls() {
        return 6;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        int num = this.numberOfBalls(), angle = -20, range = 10;
        List<Velocity> list = new LinkedList<>();
        Velocity tmp;
        for (int i = 0; i < num; i++) {
            tmp = Velocity.fromAngleAndSpeed(angle + (range * i), 10);
            list.add(tmp);
        }
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 15;
    }

    @Override
    public int paddleWidth() {
        return 115;
    }

    @Override
    public String levelName() {
        return "Business at dawn";
    }

    @Override
    public Sprite getBackground() {
        SpriteCollection sprites = new SpriteCollection();
        BackGround bg = new BackGround(sprites);
        Color color = new Color(255, 250, 175);

        int width = this.surface.getWidth(), height = this.surface.getHeight();
        Rectangle rec = new Rectangle(new Point(0, 0), width, height);
        rec.setColor(color);
        Block block = new Block(rec, -1);
        sprites.addSprite(block);
        color = new Color(255, 241, 128);

        rec = new Rectangle(new Point(0, 200), width, height);
        rec.setColor(color);
        block = new Block(rec, -1);
        sprites.addSprite(block);

        color = new Color(210, 163, 44);
        rec = new Rectangle(new Point(0, 450), width, height);
        rec.setColor(color);
        block = new Block(rec, -1);
        sprites.addSprite(block);

        color = new Color(255, 183, 52);
        rec = new Rectangle(new Point(0, 550), width, height);
        rec.setColor(color);
        block = new Block(rec, -1);
        sprites.addSprite(block);

        Point point = new Point(190, 217);
        Circle circle = new Circle(point, 16, Color.RED);
        sprites.addSprite(circle);
        circle = new Circle(point, 10, Color.orange);
        sprites.addSprite(circle);
        circle = new Circle(point, 5, Color.YELLOW);
        sprites.addSprite(circle);
        circle = new Circle(point, 2, Color.WHITE);
        sprites.addSprite(circle);

        rec = new Rectangle(new Point(182, 230), 15, 100);
        block = new Block(rec, -1);
        block.setSingleColor(new Color(93, 93, 97));
        sprites.addSprite(block);

        rec = new Rectangle(new Point(170, 330), 40, 40);
        block = new Block(rec, -1);
        block.setSingleColor(new Color(44, 44, 50));
        sprites.addSprite(block);

        rec = new Rectangle(new Point(120, 370), 140, 300);
        block = new Block(rec, -1);
        block.setSingleColor(Color.BLACK);
        sprites.addSprite(block);

        Sprite[] windows = createWindows(120, 260, 380);
        for (Sprite s : windows) {
            if (s != null) {
                sprites.addSprite(s);
            }
        }

        return bg;
    }

    /**
     * method creates window blocks for background.
     *
     * @param left  side of building.
     * @param right side of building.
     * @param up    level of building
     * @return sprites array of windows.
     */
    private Sprite[] createWindows(double left, double right, int up) {
        Sprite[] sprites = new Sprite[100];
        int newLeft = (int) left + 10, newRight = (int) right - 30, newUp = up + 5;
        int width = 25, height = 35;
        Color white = Color.WHITE;
        Point pointRight, pointLeft, midPoint, midPoint2;
        Rectangle rec;
        Block windowA, windowB, windowC, windowD;
        for (int i = 0; i < 20; i = i + 4) {
            if (i % 4 == 0 && i != 0) {
                newUp = newUp + height + 10;
            }
            pointLeft = new Point(newLeft, newUp);
            rec = new Rectangle(pointLeft, width, height);
            windowA = new Block(rec, -1);
            windowA.setSingleColor(white);
            sprites[i] = windowA;

            pointRight = new Point(newRight - 5, newUp);
            rec = new Rectangle(pointRight, width, height);
            windowB = new Block(rec, -1);
            windowB.setSingleColor(white);
            sprites[i + 1] = windowB;

            midPoint = new Point(newLeft + width + 7.5, newUp);
            rec = new Rectangle(midPoint, width, height);
            windowC = new Block(rec, -1);
            windowC.setSingleColor(white);
            sprites[i + 2] = windowC;

            midPoint2 = new Point(newLeft + (2 * width) + 14, newUp);
            rec = new Rectangle(midPoint2, width, height);
            windowD = new Block(rec, -1);
            windowD.setSingleColor(white);
            sprites[i + 3] = windowD;

        }
        return sprites;
    }


    @Override
    public List<Block> blocks() {
        List<Block> list = new LinkedList<>();
        int lines = 5, height = 25, times = 7;
        Random rand = new Random();
        Rectangle rec = new Rectangle(new Point(0, 200), 63.5, height);
        Block block = new Block(rec, 1);
        block.setSingleColor(Color.WHITE);
        block.setStroke(Color.BLACK);
        for (int i = 0; i < lines; i++) {
            Block[] blocks = block.createBlockLine(times, this.surface.getWidth());
            list.addAll(Arrays.asList(blocks));
            rec = new Rectangle(new Point(0, 200 + (height * (i + 1))), 63.5, height,
                    new Color(rand.nextInt(0xFFFFFF)));
            block = new Block(rec, 1);
            block.setStroke(Color.BLACK);
            times--;
        }


        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.blocks().size();
    }
}
