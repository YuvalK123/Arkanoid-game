package kasnerypack.shapes;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import kasnerypack.game.GameLevel;
import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.Collidable;

import java.awt.Color;

/**
 * @author kasnery
 */
public class Paddle implements Collidable {
    private Collidable paddle;
    private KeyboardSensor keyboard;
    private int speed;
    private DrawSurface surface;

    /**
     * Constructor method creates paddle, gui and keyboard fields according to args.
     *
     * @param rec   paddle rectangle.
     * @param k     keyBoard of paddle.
     * @param color of paddle.
     * @param s     drawsurface.
     * @param spd   speed of paddle.
     */
    public Paddle(Rectangle rec, KeyboardSensor k, Color color, int spd, DrawSurface s) {
        this.paddle = new Block(rec, -1);
        this.paddle.getCollisionRectangle().setColor(color);
        this.keyboard = k;
        this.speed = spd;
        this.surface = s;
    }

    /**
     * Method moves paddle to the left, unless its at its edge.
     */
    public void moveLeft() {
        //Line right = this.paddle.getCollisionRectangle().recSides()[3];
        Point paddlePoint = getCollisionRectangle().getUpperLeft();
        double x = paddlePoint.getX() - this.speed;
        if (x <= 25) {
            x = 25;
        }

        Point tmp = new Point(x, paddlePoint.getY());
        Rectangle rec = new Rectangle(tmp, getCollisionRectangle().getWidth(),
                getCollisionRectangle().getHeight(), this.paddle.getCollisionRectangle().getColor());
        this.paddle = new Block(rec, -1);
    }

    /**
     * Method moves paddle to the right, unless its at its edge.
     */
    public void moveRight() {
        Line left = this.paddle.getCollisionRectangle().recSides()[1];
        Point paddlePoint = this.paddle.getCollisionRectangle().getUpperLeft();
        double x = paddlePoint.getX() + this.speed;
        if (left.start().getX() >= this.surface.getWidth() - 25) {
            x = this.surface.getWidth() - 25 - getCollisionRectangle().getWidth();
        }
        Point tmp = new Point(x, paddlePoint.getY());
        Rectangle rec = new Rectangle(tmp,
                this.paddle.getCollisionRectangle().getWidth(),
                this.paddle.getCollisionRectangle().getHeight(), this.paddle.getCollisionRectangle().getColor());
        this.paddle = new Block(rec, -1);
    }

    /**
     * paddle resets paddle's x axis to param x.
     *
     * @param x point to set paddle axis to.
     */
    public void setPaddleXAxis(double x) {
        Point left = this.paddle.getCollisionRectangle().getUpperLeft();
        Rectangle paddleRec = this.paddle.getCollisionRectangle();
        Rectangle tmp = new Rectangle(new Point(x, left.getY()), paddleRec.getWidth(), paddleRec.getHeight());
        this.paddle = new Paddle(tmp, this.keyboard, paddleRec.getColor(), this.speed, this.surface);
    }

    /**
     * drawOn method draws paddle on surface.
     *
     * @param d surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        this.paddle.getCollisionRectangle().drawRec(d);
        d.setColor(Color.BLACK);
        Point upperLeft = this.getCollisionRectangle().getUpperLeft();
        int width = (int) this.getCollisionRectangle().getWidth(),
                height = (int) this.getCollisionRectangle().getHeight();
        d.drawRectangle((int) upperLeft.getX(), (int) upperLeft.getY(), width, height);

    }

    /**
     * Method animates paddle, according to pressed key.
     */
    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
    }

    /**
     * @return paddle's rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.paddle.getCollisionRectangle();
    }

    /**
     * Method changes velocity according to region of hit.
     *
     * @param hitter          ball that hit paddle.
     * @param collisionPoint  of hit.
     * @param currentVelocity of object.
     * @return velocity after hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (collisionPoint == null) {
            return currentVelocity;
        }
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        double velocity = Math.sqrt((dx * dx) + (dy * dy));
        int region = checkRegion(collisionPoint);
        if (region == 1) {
            return Velocity.fromAngleAndSpeed(300, velocity);
        }
        if (region == 2) {
            return Velocity.fromAngleAndSpeed(330, velocity);
        }
        if (region == 3) {
            return new Velocity(currentVelocity.getDx(), currentVelocity.getDy() * -1);
        }
        if (region == 4) {
            return Velocity.fromAngleAndSpeed(30, velocity);
        }
        if (region == 5) {
            return Velocity.fromAngleAndSpeed(60, velocity);
        }
        return new Velocity(currentVelocity.getDx() * -1, currentVelocity.getDy());

    }

    /**
     * Method adds paddle to game.
     *
     * @param g game to add paddle to.
     */
    // Add this paddle to the game.
    public void addToGame(GameLevel g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

    /**
     * @return Line array of different sections of upper side of paddle.
     */
    private Line[] paddleParts() {
        Line[] lines = new Line[5];
        double upPartsLength = (this.paddle.getCollisionRectangle().getWidth() / 5);
        Point tmp = new Point(
                paddle.getCollisionRectangle().getUpperLeft().getX() + upPartsLength,
                paddle.getCollisionRectangle().getUpperLeft().getY());
        lines[0] = new Line(paddle.getCollisionRectangle().getUpperLeft(), tmp);
        lines[1] = new Line(tmp, new Point(tmp.getX() + upPartsLength, tmp.getY()));
        lines[2] = new Line(lines[1].end(), new Point(lines[1].end().getX() + upPartsLength,
                tmp.getY()));
        lines[3] = new Line(lines[2].end(), new Point(lines[2].end().getX() + upPartsLength,
                tmp.getY()));
        lines[4] = new Line(lines[3].end(), new Point(lines[3].end().getX() + upPartsLength,
                tmp.getY()));
        return lines;
    }

    /**
     * Method checks in which area of upper paddle side it hit.
     *
     * @param collisionPoint of hit.
     * @return region section of hit.
     */
    private int checkRegion(Point collisionPoint) {
        Line[] parts = paddleParts();

        if (collisionPoint.inLine(parts[0])) {
            return 1;
        }
        if (collisionPoint.inLine(parts[1])) {
            return 2;
        }
        if (collisionPoint.inLine(parts[2])) {
            return 3;
        }
        if (collisionPoint.inLine(parts[3])) {
            return 4;
        }
        if (collisionPoint.inLine(parts[4])) {
            return 5;
        }
        return 0;
    }
}
