package kasnerypack.game.sprites;

import biuoop.DrawSurface;
import kasnerypack.game.GameLevel;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.Collidable;
import kasnerypack.interfaces.HitListener;
import kasnerypack.interfaces.HitNotifier;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author kasnery
 */
public class Block implements Collidable, HitNotifier {
    private Rectangle rec;
    private int count;
    private Color stroke = null;
    private List<HitListener> hitListeners;
    private ImageSprite bg;
    private Map<Integer, ImageSprite> fills;

    /**
     * constructor method implements fields according to args.
     *
     * @param rec rectangle of block.
     * @param num counts of hits.
     */
    public Block(Rectangle rec, int num) {
        this.rec = rec;
        this.count = num;
        this.hitListeners = new LinkedList<>();
        this.fills = new TreeMap<>();
    }

    /**
     * method notifies all litseners that the object was hit.
     *
     * @param hitter ball that hit.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * method degrades count of counter, and returns new velocity according to section of hit.
     *
     * @param collisionPoint  of hit.
     * @param currentVelocity of object that hit.
     * @param hitter          ball that hit the block.
     * @return velocity of object after got hit with this.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        if (this.count > 0) {
            this.count--;
        }
        if (this.count > 0) {
            if (this.fills.containsKey(this.count)) {
                this.bg = this.fills.get(this.count);
                this.bg.setByBlock(this);
            }
            this.rec.setImage(this.bg);
        }
        double dx = currentVelocity.getDx(), dy = currentVelocity.getDy();
        Line[] sides = this.rec.recSides();
        if ((collisionPoint.inLine(sides[1]))
                || (collisionPoint.inLine(sides[3]))) {
            dx *= -1;
        }
        if ((collisionPoint.inLine(sides[0]))
                || (collisionPoint.inLine(sides[2]))) {
            dy *= -1;
        }
        this.notifyHit(hitter);
        return new Velocity(dx, dy);
    }

    /**
     * @return block rectangle.
     */
    public Rectangle getCollisionRectangle() {
        return this.rec;
    }

    /**
     * method draws block and text on it.
     *
     * @param d surface to draw on.
     */
    @Override
    public void drawOn(DrawSurface d) {
        this.rec.drawRec(d);
        if (this.stroke != null) {
            d.setColor(this.stroke);
            Point p = this.getCollisionRectangle().getUpperLeft();
            d.drawRectangle((int) p.getX(), (int) p.getY(), (int) this.getCollisionRectangle().getWidth(),
                    (int) this.getCollisionRectangle().getHeight());
        }
    }

    /**
     * Method returns X if count is 0, and other if count is positive.
     *
     * @return text to draw on block.
     */
    public String getHitPoints() {
        return Integer.toString(this.count);
    }

    /**
     * interface method. blocks don't move, so method does nothing.
     */
    @Override
    public void timePassed() {
        return;
    }

    /**
     * method adds block to game spriteCollection and collidable collection.
     *
     * @param game to add block to.
     */
    public void addToGame(GameLevel game) {
        game.addSprite(this);
        game.addCollidable(this);
    }

    /**
     * @param times        of blocks in line.
     * @param surfaceWidth to stick the block's right side to.
     * @return block array of blocks in line.
     */
    public Block[] createBlockLine(int times, int surfaceWidth) {
        if (times <= 0) {
            return null;
        }
        Block[] blockLine = new Block[times];
        ImageSprite[] bgs = new ImageSprite[this.fills.size()];
        for (int i = 0; i < bgs.length; i++) {
            bgs[i] = this.fills.get(this.count);
        }
        double width = this.rec.getWidth(), height = this.rec.getHeight();
        Rectangle tmp = new Rectangle(new Point((double) surfaceWidth - width - 50,
                this.rec.getUpperLeft().getY()), width, height);
        for (int i = 0; i < times; i++) {
            blockLine[i] = new Block(tmp, this.count);
            blockLine[i].setSingleColor(this.rec.getColor());
            if (this.stroke != null) {
                blockLine[i].setStroke(this.stroke);
            }
            tmp = new Rectangle(new Point(tmp.getUpperLeft().getX() - width,
                    this.rec.getUpperLeft().getY()), width, height);
        }
        return blockLine;
    }

    /**
     * method add listeners to given block.
     *
     * @param listeners to add to block.
     */
    public void addListeners(HitListener[] listeners) {
        for (HitListener l : listeners) {
            this.addHitListener(l);
        }
    }

    /**
     * adds block array to game.
     *
     * @param game   to add blocks to.
     * @param blocks array.
     */
    public void addArrToGame(GameLevel game, Block[] blocks) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].addToGame(game);
        }
    }

    /**
     * method removes block from game.
     *
     * @param game to remove block from.
     */
    public void removeFromGame(GameLevel game) {
        game.removeSprite(this);
        game.removeCollidable(this);
    }

    /**
     * method adds a HitLitsener to hit events.
     *
     * @param hl hitListener to be added to the hit events.
     */
    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * method removes a HitLitsener from the hit events.
     *
     * @param hl hitListener to be removed from the hit events.
     */
    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * method resets block's rectangle color.
     *
     * @param c color to reset by.
     */
    public void setSingleColor(Color c) {
        this.rec.setColor(c);
        this.fills.put(this.count, new ImageSprite(this, c));
        this.bg = new ImageSprite(this, c);
    }

    /**
     * method sets colors to block.
     *
     * @param c colors array.
     */
    public void setColors(Color[] c) {
        Map<Integer, ImageSprite> map = new TreeMap<>();
        ImageSprite[] colors = new ImageSprite[this.count];
        if (c.length == 0) {
            Random rand = new Random();
            for (int i = 0; i < this.count; i++) {
                colors[i + 1] = new ImageSprite(this, new Color(rand.nextInt(0xFFFFFF)));
                colors[i + 1].setByBlock(this);
                this.fills.put(i, colors[i]);
            }
            this.bg = this.fills.get(1);
            return;

        }
        int i = 1;
        for (Color color : c) {
            map.put(i, new ImageSprite(this, color));
            i++;
        }
        this.fills = map;
        this.bg = fills.get(this.count);
        if (this.bg == null) {
            System.out.println("et");
        }
        this.rec.setImage(this.bg);
    }

    /**
     * method sets imageSprites field.
     *
     * @param map of imageSprite to set block by.
     */
    public void setSprites(Map<Integer, ImageSprite> map) {
        Map<Integer, ImageSprite> tmpMap = new TreeMap<>();
        for (Integer key : map.keySet()) {
            tmpMap.put(key, map.get(key).copy());
            tmpMap.get(key).setByBlock(this);
        }
        this.fills = tmpMap;
        this.bg = this.fills.get(this.count);
        Integer tmp = 0;
        if (this.bg == null) {
            for (Integer i : this.fills.keySet()) {
                if (tmp < i) {
                    tmp = i;
                }
            }
            this.bg = this.fills.get(tmp);
        }
        this.rec.setImage(this.bg);
        /*this.sprites.addAll(Arrays.asList(imageSprites));
        Random rand = new Random();
        for (int i = imageSprites.length; i < this.count; i++) {
            this.sprites.add(new ImageSprite(this, new Color(rand.nextInt(0xFFFFFF))));
        }
        this.bg = this.sprites.get(0);
        this.rec.setImage(this.bg);*/
    }

    /**
     * method sets stroke to block.
     *
     * @param s color of stroke.
     */
    public void setStroke(Color s) {
        this.stroke = s;
    }
}
