package kasnerypack.shapes;

import kasnerypack.generalbehaviors.PublicFuncs;

/**
 * @author kasnery.
 */

public class Point {
    private double x;
    private double y;

    /**
     * kasnerypack.shapes.Point class constructor.
     *
     * @param x value of point.
     * @param y value of point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * method calculates distance between two points.
     *
     * @param other point to compare value.
     * @return distance between points.
     */
    //returns the distance of this pint to the other point.
    public double distance(Point other) {
        double distX = ((this.x - other.getX()) * (this.x - other.getX()));
        double distY = ((this.y - other.getY()) * (this.y - other.getY()));
        return Math.sqrt(distX + distY);
    }

    /**
     * checks if the points are equal.
     *
     * @param other point.
     * @return true if same point, false otherwise.
     */
    public boolean equals(Point other) {
        if (PublicFuncs.doublesEq(other.getX(), this.x) && PublicFuncs.doublesEq(other.getY(), this.y)) {
            return true;
        }
        return false;
    }


    /**
     * getter method - gets X value.
     *
     * @return x value of point.
     */
    //returns fields values
    public double getX() {
        return this.x;
    }

    /**
     * getter method - gets Y value.
     *
     * @return y value of point.
     */
    public double getY() {
        return this.y;
    }

    /**
     * method compares x positions of points.
     *
     * @param other point to check.
     * @return 0 if at same x position, 1 if this x position is bigger, and -1 otherwise.
     */
    public int xPointEq(Point other) {
        if (PublicFuncs.doublesEq(this.getX(), other.getX())) {
            return 0;
        } else if (this.getX() > other.getX()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * method compares x positions of points.
     *
     * @param other point to check.
     * @return 0 if at same x position, 1 if this x position is bigger, and -1 otherwise.
     */
    public int yPointEq(Point other) {
        if (PublicFuncs.doublesEq(this.getY(), other.getY())) {
            return 0;
        } else if (this.getY() > other.getY()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * boolean method inXRange checks if point is in range in the x axis.
     *
     * @param x1 double type 1st value to check.
     * @param x2 double type 1st value to check.
     * @return true if in x range, false otherwise.
     */
    public boolean inXRange(double x1, double x2) {
        double min, max;
        if (x1 < x2) {
            min = x1;
            max = x2;
        } else {
            min = x2;
            max = x1;
        }
        if (this.x >= min && this.x <= max) {
            return true;
        }
        return false;
    }

    /**
     * boolean method inYRange checks if point is in range in the y axis.
     *
     * @param y1 double type 1st value to check.
     * @param y2 double type 1st value to check.
     * @return true if in y range, false otherwise.
     */
    public boolean inYRange(double y1, double y2) {
        double min, max;
        if (y1 < y2) {
            min = y1;
            max = y2;
        } else {
            min = y2;
            max = y1;
        }
        if (this.y >= min && this.y <= max) {
            return true;
        }
        return false;
    }

    /**
     * boolean method inLine checks if point is in line range.
     *
     * @param tmp kasnerypack.shapes.Line to check with.
     * @return true if in line range, false otherwise.
     */
    public boolean inLine(Line tmp) {
        if ((this.inXRange(tmp.start().getX(), tmp.end().getX()))
                && this.inYRange(tmp.start().getY(), tmp.end().getY())) {
            return true;
        }
        return false;
    }
}