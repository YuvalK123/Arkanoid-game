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
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author kasnery.
 */
public class LevelFour implements LevelInformation {
    private DrawSurface surface;

    /**
     * constructor method.
     *
     * @param s surface of gui.
     */
    public LevelFour(DrawSurface s) {
        this.surface = s;
    }

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {

        int num = this.numberOfBalls(), angle = -30, range = 30;
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
        return 20;
    }

    @Override
    public int paddleWidth() {
        return 150;
    }

    @Override
    public String levelName() {
        return "Its raining rain";
    }

    @Override
    public Sprite getBackground() {
        SpriteCollection sprites = new SpriteCollection();
        BackGround bg = new BackGround(sprites);
        int width = this.surface.getWidth(), height = this.surface.getHeight();

        Rectangle rec = new Rectangle(new Point(0, 0), width, height);
        rec.setColor(new Color(50, 156, 234));
        Block block = new Block(rec, -1);
        sprites.addSprite(block);
        createClouds(600, 430, sprites);
        createClouds(250, 350, sprites);
        createClouds(440, 140, sprites);
        createClouds(120, 200, sprites);
        return bg;
    }

    /**
     * method creates clouds for background.
     *
     * @param left   side of cloud.
     * @param height of lowest center of circle in cloud.
     * @param spriteCollection collection of sprites to add clouds to.
     */
    private void createClouds(int left, int height, SpriteCollection spriteCollection) {
        Sprite[] sprites = new Sprite[21];
        int i = 0;
        Circle circle;
        Color color;
        Point point;
        Sprite[] rain = createRain(new Point(left, height - 10), new Point(left + 70, height - 10));
        for (Sprite s : rain) {
            if (s == null) {
                break;
            }
            sprites[i] = s;
            i++;
        }


        color = new Color(195, 195, 199);
        point = new Point(left + 10, height + 10);
        circle = new Circle(point, 30, color);
        sprites[i] = circle;
        i++;

        point = new Point(left + 40, height);
        circle = new Circle(point, 30, color);
        sprites[i] = circle;
        i++;

        point = new Point(left, height);
        circle = new Circle(point, 30, color);
        sprites[i] = circle;
        i++;

        point = new Point(left + 10, height - 35);
        circle = new Circle(point, 39, color);
        sprites[i] = circle;
        i++;

        color = new Color(146, 146, 150);
        point = new Point(left + 20, height - 20);
        circle = new Circle(point, 25, color);
        sprites[i] = circle;
        i++;

        color = new Color(109, 109, 114);
        point = new Point(left + 70, height - 30);
        circle = new Circle(point, 30, color);
        sprites[i] = circle;
        i++;

        point = new Point(left + 50, height - 10);
        circle = new Circle(point, 20, color);
        sprites[i] = circle;
        i++;


        point = new Point(left + 40, height - 35);
        circle = new Circle(point, 20, color);
        sprites[i] = circle;
        i++;

        point = new Point(left + 73, height - 11);
        circle = new Circle(point, 35, color);
        sprites[i] = circle;
        i++;

        color = new Color(146, 146, 150);
        point = new Point(left + 40, height - 30);
        circle = new Circle(point, 30, color);
        sprites[i] = circle;
        i++;

        for (Sprite s : sprites) {
            if (s == null) {
                break;
            }
            spriteCollection.addSprite(s);
        }
    }

    /**
     * method creates sprite array of lines.
     *
     * @param left  point to stick rain to.
     * @param right point to stick rain to.
     * @return sprite array of rain sprites.
     */
    private Sprite[] createRain(Point left, Point right) {
        Sprite[] sprites = new Sprite[11];
        Line line;
        Color color = new Color(138, 180, 180);
        double x = right.getX() + 20, y = right.getY() - 10;
        int i = 0;
        while (x > left.getX() - 20) {
            line = new Line(x, y, x - 50, this.surface.getHeight());
            LineSprite lineSprite = new LineSprite(line, color);
            sprites[i] = lineSprite;
            i++;
            x = x - 10;
        }

        return sprites;
    }

    @Override
    public List<Block> blocks() {
        List<Block> list = new LinkedList<>();
        int lines = 7, height = 25;
        Random rand = new Random();
        Rectangle rec = new Rectangle(new Point(0, 150), 63.5, height);
        Color[] colors = {};
        //rec.setColor(Color.WHITE);
        Block block = new Block(rec, 3);
        block.setStroke(Color.BLACK);
        block.setColors(colors);
        for (int i = 0; i < lines; i++) {
            Block[] blocks = block.createBlockLine(11, this.surface.getWidth());
            list.addAll(Arrays.asList(blocks));
            rec = new Rectangle(new Point(0, 150 + (height * (i + 1))), 63.5, height);
            //rec.setColor(new Color(rand.nextInt(0xFFFFFF)));
            block = new Block(rec, 3);
            block.setColors(colors);
            block.setStroke(Color.BLACK);
        }

        return list;
    }

    @Override
    public int numberOfBlocksToRemove() {

        return this.blocks().size();
    }
}
