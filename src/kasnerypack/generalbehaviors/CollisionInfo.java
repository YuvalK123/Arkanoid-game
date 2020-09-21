package kasnerypack.generalbehaviors;

import kasnerypack.interfaces.Collidable;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;

/**
 * @author kasnery
 */
public class CollisionInfo {
    private Collidable collidable;
    private Point collisionPoint;
    private Line trajectory;

    /**
     * constructor implements fields, and finds closest intersection point.
     *
     * @param coll collidable object.
     * @param traj trajectory line.
     */
    public CollisionInfo(Collidable coll, Line traj) {
        this.collidable = coll;
        this.trajectory = traj;
        this.collisionPoint = this.trajectory.closestIntersectionToStartOfLine(
                this.collidable.getCollisionRectangle());
    }

    /**
     * @return collision point.
     */
    // the point at which the collision occurs.
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * @return the collidable object involved in the collision.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}
