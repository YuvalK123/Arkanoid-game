package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.game.GameEnviornment;
import kasnerypack.game.GameLevel;
import kasnerypack.generalbehaviors.CollisionInfo;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;

import java.awt.Color;
import java.util.Random;

/**
 * @author kasnery.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity v;
    private GameEnviornment environment;

    /**
     * constructor method - resets lower boundaries, and assume value from args to fields.
     *
     * @param center point of ball.
     * @param r      radius/size of ball.
     * @param color  of ball.
     */
    public Ball(Point center, int r, Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
    }

    /**
     * @return center's x value.
     */
    // accessors
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     * @return center's y value.
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     * @return ball's radius/size.
     */
    public int getSize() {
        return this.radius;
    }

    /**
     * @return ball's velocity.
     */
    public Velocity getVelocity() {
        return this.v;
    }

    /**
     * @return ball's color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /**
     * method setVelocity sets velocity by a velocity arg.
     *
     * @param tmpVel velocity to be set.
     */
    //setters
    public void setVelocity(Velocity tmpVel) {
        this.v = tmpVel;
    }

    /**
     * method setVelocity sets velocity by speed of axises.
     *
     * @param dx speed value for x axis.
     * @param dy speed value for y axis.
     */
    public void setVelocity(double dx, double dy) {

        this.v = new Velocity(dx, dy);
    }


    /**
     * method setCenter places new placement of ball's center.
     *
     * @param x value of center.
     * @param y value of center.
     */
    public void setCenter(double x, double y) {
        this.center = new Point(x, y);
    }

    /**
     * method checkBounds checks in rectangle side it ay .
     *
     * @param collision collision Info.
     * @return string that describes in which situation it is.
     */
    private String checkBounds(CollisionInfo collision) {
        Point collidePoint = collision.collisionPoint();
        Line[] collSides = collision.collisionObject().getCollisionRectangle().recSides();
        if (collidePoint.inLine(collSides[1])) {
            return "Right";
        }
        if (collidePoint.inLine(collSides[3])) {
            return "Left";
        }
        if (collidePoint.inLine(collSides[0])) {
            return "Up";
        }
        if (collidePoint.inLine(collSides[2])) {
            return "Down";
        }

        return null;

    }

    /**
     * method moveOneStep checks if collided, and if not - moves placements according to ball's velocity.
     */
    public void moveOneStep() {
        if (!collisionMove()) {
            this.center = this.getVelocity().applyToPoint(this.center);
        }

    }

    /**
     * Method checks if balls is gonna collide, and if it will collide the next step it will change the placement,
     * and velocity accordingly.
     *
     * @return true if collided, false otherwise.
     */
    private boolean collisionMove() {
        Line trajectory = new Line(this.center, getVelocity().applyToPoint(this.center));
        CollisionInfo collide = this.environment.getClosestCollision(trajectory);
        double twoSqr = Math.sqrt(2);
        boolean flag = false;
        if (collide != null) {
            //right collision
            if (checkBounds(collide).equals("Right")) {
                setCenter(collide.collisionPoint().getX() + getSize() + twoSqr,
                        collide.collisionPoint().getY());
                flag = true;
            } else if (checkBounds(collide).equals("Left")) {
                setCenter(collide.collisionPoint().getX() - getSize() - twoSqr, collide.collisionPoint().getY());
                flag = true;
            }
            //upper collision
            if (checkBounds(collide).equals("Up")) {
                setCenter(collide.collisionPoint().getX(), collide.collisionPoint().getY() - getSize() - twoSqr);
                flag = true;
            } else if (checkBounds(collide).equals("Down")) {
                setCenter(collide.collisionPoint().getX(), collide.collisionPoint().getY() + getSize() + twoSqr);
                flag = true;
            }
            setVelocity(collide.collisionObject().hit(this, collide.collisionPoint(), this.getVelocity()));
        }
        return flag;
    }

    /**
     * method creates balls array, while setting default boundaries and random velocity.
     *
     * @param args   sizes of balls.
     * @param width  bounds to bounce.
     * @param height bounds to bounce.
     * @return balls array.
     */
    public static Ball[] createBallsArr(String[] args, int width, int height) {
        Random rand = new Random();
        Ball[] balls = new Ball[args.length];
        for (int i = 0; i < args.length; i++) {
            balls[i] = new Ball(new Point(rand.nextInt(width), rand.nextInt(height)),
                    Integer.valueOf(args[i]), new Color((int) (Math.random() * 0x1000000)));
            balls[i].setVelocity(Velocity.fromAngleAndSpeed(rand.nextInt(360),
                    (49 / balls[i].getSize()) + 3));
        }
        return balls;
    }

    /**
     * method draws ball on surface.
     *
     * @param surface to draw ball on.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawCircle((int) this.center.getX(), (int) this.center.getY(), this.getSize() + 1);
        surface.setColor(this.getColor());
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.getSize());
    }

    /**
     * method activates this ball's animation method.
     */
    @Override
    public void timePassed() {
        if (this.environment != null) {
            moveOneStep();
        }
    }

    /**
     * Method adds this ball to game sprite and sets ball's enviorment.
     *
     * @param game to add ball to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        this.environment = game.getGameEnviorment();
    }

    /**
     * method removes ball from game.
     *
     * @param game to remove ball from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
    }
}