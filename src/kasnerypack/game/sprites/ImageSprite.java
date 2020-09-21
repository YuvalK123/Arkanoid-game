package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.interfaces.Sprite;
import kasnerypack.shapes.Point;

import java.awt.Color;
import java.awt.Image;

/**
 * @author kasnery.
 */
public class ImageSprite implements Sprite {
    private Image image;
    private Point point;
    private Point size;
    private Color color;

    /**
     * constructor method for images.
     *
     * @param im image to set by.
     */
    public ImageSprite(Image im) {
        this.image = im;
        this.color = null;
    }

    /**
     * @param color to set color to.
     * @param point to set point to.
     * @param size  of image.
     */
    public ImageSprite(Color color, Point point, Point size) {
        this.size = size;
        this.point = point;
        this.color = color;
    }

    /**
     * constructor method resets color by block relations and color.
     *
     * @param block to reset by,
     * @param color to set color to.
     */
    public ImageSprite(Block block, Color color) {
        this.size = new Point(block.getCollisionRectangle().getWidth(), block.getCollisionRectangle().getHeight());
        this.color = color;
    }

    /**
     * constructor method to set image by color only.
     *
     * @param color to set color.
     */
    public ImageSprite(Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * method sets image size and point by block relations.
     *
     * @param block to set by.
     */
    public void setByBlock(Block block) {
        this.point = block.getCollisionRectangle().getUpperLeft();
        this.size = new Point(block.getCollisionRectangle().getWidth(), block.getCollisionRectangle().getHeight());
    }

    /**
     * @return this color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * method copies deep copies of this.
     *
     * @return new copied of imageSprite.
     */
    public ImageSprite copy() {
        ImageSprite copy = null;
        if (this.image != null) {
            copy = new ImageSprite(this.image);
        }
        if (this.color != null && this.point != null && this.size != null) {
            copy = new ImageSprite(this.color, this.point, this.size);
        }
        if (this.color != null) {
            copy = new ImageSprite(this.color);
        }
        return copy;
    }

    /**
     * @param d surface to draw on.
     *          drawOn method draw the sprite to the screen.
     */
    @Override
    public void drawOn(DrawSurface d) {
        if (this.point == null) {
            this.point = new Point(0, 0);
        }
        if (this.image != null) {
            d.drawImage((int) this.point.getX(), (int) this.point.getY(), this.image);
        } else {
            if (this.color == null) {
                this.color = Color.BLACK;
            }
            d.setColor(this.getColor());
            d.fillRectangle((int) this.point.getX(), (int) this.point.getY(), (int) this.size.getX(),
                    (int) this.size.getY());
        }
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }


}
