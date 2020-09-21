package kasnerypack.shapes;

/**
 * @author kasnery
 */
public class Line {

    private Point start;
    private Point end;
    private double slope;
    private double b;

    /**
     * constructor calculates points and line equation with points args.
     *
     * @param start point.
     * @param end   point.
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        slopeCal();
        this.b = start.getY() - this.slope * start.getX();
    }

    /**
     * constructor calculates points and line equation with double args.
     *
     * @param x1 value of start point x.
     * @param y1 value of start point y.
     * @param x2 value of end point x.
     * @param y2 value of end point y.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        slopeCal();
        this.b = start.getY() - (this.slope * start.getX());
    }

    /**
     * method calculates line's slope.
     */
    private void slopeCal() {
        double numerator, denominator;
        numerator = this.start.getY() - this.end.getY();
        denominator = this.start.getX() - this.end.getX();
        if (denominator == 0) {
            return;
        } else {
            this.slope = (numerator / denominator);
        }
    }

    /**
     * method calculates line's length by points.
     *
     * @return length of line.
     */
    public double length() {
        return this.start.distance(end);
    }

    /**
     * method calculates line's middle point.
     *
     * @return mid point.
     */
    // Returns the middle point of the line
    public Point middle() {
        double x = (this.start.getX() + this.end.getX()) / 2.0;
        double y = (this.start.getY() + this.end.getY()) / 2.0;
        Point mid = new Point(x, y);
        return mid;
    }

    /**
     * getter method of start point.
     *
     * @return start point.
     */
    public Point start() {
        return this.start;
    }

    /**
     * getter method of end point.
     *
     * @return end point.
     */
    // Returns the end point of the line
    public Point end() {
        return this.end;
    }

    /**
     * @return slope of line.
     */
    private double getSlope() {
        return this.slope;
    }

    /**
     * @return b value of line.
     */
    //return line's b point.
    private double getB() {
        return this.b;
    }

    /**
     * method checks if lines intersect.
     *
     * @param other line to check with.
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        return intersectionWith(other) != null;
    }

    /**
     * method calculates the intersection point of 2 lines, if exist.
     *
     * @param other line to check if intersects with.
     * @return intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        double minThisX, minOtherX, maxThisX, maxOtherX, x, y, a;
        if (this.start().xPointEq(this.end()) == 0) {
            if (other.start().xPointEq(other.end()) == 0) {
                return null;
            }

            x = this.start().getX();
            y = other.getSlope() * x + other.getB();
            Point tmp = new Point(x, y);
            if (tmp.inLine(other) && tmp.inLine(this)) {
                return tmp;
            }
        } else if (other.start().xPointEq(other.end()) == 0) {
            if ((this.start().xPointEq(this.end()) == 0)) {
                return null;
            }
            x = other.start().getX();
            y = this.getSlope() * x + this.getB();
            Point tmp = new Point(x, y);
            if (tmp.inLine(other) && tmp.inLine(this)) {
                return tmp;
            }
        }
        //checks for same slope.
        /*a = this.slope - other.getSlope();
        if (a == 0) {
            return null;
        }*/
        x = (other.getB() - this.b) / (this.slope - other.getSlope());
        y = this.slope * x + this.b;
        Point tmp = new Point(x, y);
        if (tmp.inLine(this) && tmp.inLine(other)) {
            return tmp;
        }
        return null;

    }

    /**
     * method check if lines are equal.
     *
     * @param other line to compare with.
     * @return true if same lines, false otherwise.
     */
    // equals true return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        if ((this.slope == other.getSlope()) && (this.b == other.getB())) {
            if (this.start.equals(other.start()) && this.end.equals(other.end())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param rect rectangle to check intersection with.
     * @return closest intersection point to start of movement.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Point[] tmp = arrInterectionPoints(rect.recSides());
        if (tmp == null) {
            return null;
        }
        if (tmp.length == 1) {
            return tmp[0];
        }
        Point x = tmp[0];
        if (this.start().distance(x) == 0) {
            return x;
        }
        for (int i = 0; i < tmp.length - 1; i++) {
            if (this.start().distance(x) > this.start().distance(tmp[i + 1])) {
                x = tmp[i + 1];
            }
        }
        return x;
    }

    /**
     * @param l line array to check intersection with.
     * @return kasnerypack.shapes.Point array of intersection points.
     */
    public Point[] arrInterectionPoints(Line[] l) {
        Point[] sidePoints = new Point[4];
        Point tmp;
        int k = 0;
        for (int i = 0; i < l.length; ++i) {
            tmp = l[i].intersectionWith(this);
            if (tmp != null) {
                sidePoints[k] = tmp;
                k++;
            }
        }
        if (k == 0) {
            return null;
        }
        Point[] arr = new Point[k];
        for (int i = 0; i < k; i++) {
            arr[i] = sidePoints[i];
        }
        return arr;
    }


}