package kasnerypack.animation;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
import kasnerypack.interfaces.Animation;

/**
 * @author kasnery.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;

    /**
     * constructor method.
     *
     * @param g      gui.
     * @param frames frames per second.
     */
    public AnimationRunner(GUI g, int frames) {
        this.gui = g;
        this.framesPerSecond = frames;
    }

    /**
     * method runs the animation.
     *
     * @param animation to run.
     */
    public void run(Animation animation) {
        int millisecondsPerFrame = 1000 / this.framesPerSecond;
        Sleeper sleeper = new Sleeper();
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d);
            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }

    /**
     * @return surface of gui.
     */
    public DrawSurface getSurface() {
        return this.gui.getDrawSurface();
    }

}