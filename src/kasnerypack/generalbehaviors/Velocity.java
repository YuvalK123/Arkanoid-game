package kasnerypack.generalbehaviors;

import kasnerypack.shapes.Point;

/**
 * @author kasnery.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * constructor method. creates velocity by speed of axises.
     *
     * @param dx speed in x axis.
     * @param dy speed in y axis.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * method Takes a point with position (x,y) and return a new point with position (x+dx, y+dy).
     *
     * @param p point values of moving ball's center.
     * @return new point to move ball to.
     */
    public Point applyToPoint(Point p) {
        double x = p.getX() + this.dx;
        double y = p.getY() + this.dy;
        return new Point(x, y);
    }

    /**
     * getter method of dx.
     *
     * @return dx value.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * getter method of dy.
     *
     * @return dy value.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * creates calculates new velocity by desired angle and speed.
     *
     * @param angle double value of movement.
     * @param speed double value of speed.
     * @return new velocity from arguments.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -1 * Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

}
