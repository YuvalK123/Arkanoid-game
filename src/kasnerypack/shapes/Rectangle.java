package kasnerypack.shapes;

import biuoop.DrawSurface;
import kasnerypack.game.sprites.ImageSprite;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kasnery
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private ImageSprite bg;
    private Color stroke = null;

    /**
     * constructor Method Creates a new rectangle with location and width/height.
     *
     * @param upperLeft point of rectangle.
     * @param width     of rectangle.
     * @param height    of rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * constructor method Create a new rectangle with location and width/height and color.
     *
     * @param upperLeft point of rectangle.
     * @param width     of rectangle.
     * @param height    of rectangle.
     * @param colour    of rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height, Color colour) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        this.bg = new ImageSprite(colour, upperLeft, new Point(width, height));
    }

    /**
     * intersectionPoints method checks intersection point of rectangle with line.
     *
     * @param line to check intersection with.
     * @return a (possibly empty) List of intersection points with the specified line.
     */
    public List intersectionPoints(Line line) {
        List<kasnerypack.shapes.Point> list = new LinkedList<>();
        Line[] edges = recSides();
        kasnerypack.shapes.Point[] tmp = line.arrInterectionPoints(edges);
        if (tmp == null) {
            return list;
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] == null) {
                break;
            }
            list.add(tmp[i]);

        }

        return list;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * @return the height of the rectangle
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * @return the UpperLeft point of the rectangle
     */
    public kasnerypack.shapes.Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * @return color of rectangle.
     */
    public Color getColor() {

        return this.bg.getColor();
    }

    /**
     * function sets color of rectangle according to arg.
     *
     * @param colour of rectangle.
     */
    public void setColor(Color colour) {

        this.bg = new ImageSprite(colour, this.getUpperLeft(), new Point(getWidth(), getHeight()));
    }

    /**
     * @return line array of rectangle sides.
     */
    public Line[] recSides() {
        Line[] arr = new Line[4];
        kasnerypack.shapes.Point rightUp = new kasnerypack.shapes.Point(this.upperLeft.getX() + width,
                this.upperLeft.getY());
        kasnerypack.shapes.Point rightDown = new kasnerypack.shapes.Point(this.upperLeft.getX() + width,
                this.upperLeft.getY() + height);
        kasnerypack.shapes.Point leftDown = new kasnerypack.shapes.Point(this.upperLeft.getX(),
                this.upperLeft.getY() + height);
        arr[0] = new Line(this.upperLeft, rightUp);
        arr[1] = new Line(rightUp, rightDown);
        arr[2] = new Line(rightDown, leftDown);
        arr[3] = new Line(leftDown, this.upperLeft);
        return arr;
    }

    /**
     * drawRec method draws rectangle on surface.
     *
     * @param surface to draw rectangle on.
     */
    public void drawRec(DrawSurface surface) {
        if (this.bg != null) {
            this.bg.drawOn(surface);
        } else {
            surface.setColor(Color.BLACK);
            surface.fillRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                    (int) this.getWidth(), (int) this.getHeight());
        }
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle((int) this.upperLeft.getX(), (int) this.upperLeft.getY(),
                    (int) this.getWidth(), (int) this.getHeight());
        }
    }

    /**
     * method sets imageSprite to block.
     *
     * @param image to rectangle.
     */
    public void setImage(ImageSprite image) {
        this.bg = image;
    }

    /**
     * @return bg of rectangle.
     */
    public ImageSprite getBg() {
        return this.bg;
    }

}

