package kasnerypack.interfaces;

import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.game.sprites.Ball;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

/**
 * @author kasnery
 */
public interface Collidable extends Sprite {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     *
     * @param hitter          ball that hit.
     * @param collisionPoint  of ball with collided object.
     * @param currentVelocity of ball.
     * @return new velocity expected after the hit, based on the force the object inflicted on us).
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
