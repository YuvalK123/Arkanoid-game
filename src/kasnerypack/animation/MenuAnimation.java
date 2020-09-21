package kasnerypack.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import kasnerypack.game.sprites.Block;
import kasnerypack.generalbehaviors.Selection;
import kasnerypack.interfaces.Menu;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @param <Type> generic to act by.
 * @author kasnery.
 */
public class MenuAnimation<Type> implements Menu<Type> {
    private boolean stop;
    private Type status;
    private List<Selection<Type>> selections;
    private KeyboardSensor keyboardSensor;
    private String title;
    private Selection<Menu<Type>> subMenu;
    private AnimationRunner runner;

    /**
     * constructor method.
     *
     * @param title  of menu.
     * @param key    keyboard of game.
     * @param runner animation runner.
     */
    public MenuAnimation(String title, KeyboardSensor key, AnimationRunner runner) {
        this.stop = false;
        this.selections = new ArrayList<>();
        this.keyboardSensor = key;
        this.status = null;
        this.title = title;
        this.runner = runner;
    }

    @Override
    public void addSelection(String key, String message, Type returnVal) {
        this.selections.add(new Selection<>(key, message, returnVal));
    }

    @Override
    public Type getStatus() {
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<Type> sub) {
        this.subMenu = new Selection<>(key, message, sub);
    }

    /**
     * method resets stop.
     */
    @Override
    public void reset() {
        this.stop = false;
        this.status = null;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        Block block = new Block(new Rectangle(new Point(0, 0), d.getWidth(), d.getHeight(), Color.WHITE),
                -1);
        block.setSingleColor(Color.WHITE);
        block.drawOn(d);
        d.setColor(Color.BLACK);
        d.drawText(350, 100, this.title, 40);
        for (int i = 0; i < this.selections.size(); i++) {
            d.drawText(150, 150 + (i * 50), this.selections.get(i).getKey(), 30);
            d.drawText(200, 150 + (i * 50), this.selections.get(i).getMessage(), 30);
            if (this.keyboardSensor.isPressed(this.selections.get(i).getKey())) {
                this.status = this.selections.get(i).getType();
            }
            if (this.subMenu != null) {
                d.drawText(150, 150 + (this.selections.size() * 50), this.subMenu.getKey(), 30);
                d.drawText(200, 150 + (this.selections.size() * 50), this.subMenu.getMessage(), 30);
                if (this.keyboardSensor.isPressed(this.subMenu.getKey())) {
                    this.runner.run(this.subMenu.getType());
                    this.status = this.subMenu.getType().getStatus();
                }
            }
            if (this.getStatus() != null) {
                this.stop = true;
            }
        }
    }

    /**
     * method check if animation should stop.
     *
     * @return true if should stop. false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
