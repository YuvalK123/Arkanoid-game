package kasnerypack.animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import kasnerypack.interfaces.Animation;

/**
 * @author kasnery.
 */
public class KeyPressStoppableAnimation implements Animation {
    private Boolean stop;
    private KeyboardSensor keyboard;
    private Animation animation;
    private String key;
    private boolean isAlreadyPressed;

    /**
     * constructor method.
     *
     * @param sensor    keyboard sensor.
     * @param key       key to stop when pressed on.
     * @param animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.stop = false;
        this.isAlreadyPressed = true;
        this.keyboard = sensor;
        this.animation = animation;
        this.key = key;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        if (this.keyboard.isPressed(this.key)) {
            if (!this.isAlreadyPressed) {
                this.stop = true;
            }
        } else {
            this.isAlreadyPressed = false;
            this.stop = false;
        }
        this.animation.doOneFrame(d);
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
