package kasnerypack.game;

import biuoop.DrawSurface;
import kasnerypack.generalbehaviors.CollisionInfo;
import kasnerypack.interfaces.Collidable;
import kasnerypack.game.sprites.Block;
import kasnerypack.shapes.Line;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kasnery
 */
public class GameEnviornment {
    private List<Collidable> collidables;

    /**
     * constructor method that allocates memory for collidables list.
     */
    public GameEnviornment() {
        this.collidables = new ArrayList<Collidable>();
    }

    /**
     * Method addCollidable adds a collidable to list.
     *
     * @param c collidable to add to list.
     */
    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    /**
     * addEdgesColl method add edges blocks to game.
     *
     * @param surface for edges.
     * @param game    to add edges to.
     */
    public void addEdgesColl(DrawSurface surface, GameLevel game) {
        int space = 25, negTxt = -1;
        //left
        Rectangle tmp = new Rectangle(new Point(0, space), space, surface.getHeight());
        tmp.setColor(Color.GRAY);
        Block x = new Block((tmp), negTxt);
        x.addToGame(game);
        //right
        tmp = new Rectangle(new Point(surface.getWidth() - space, 0),
                space, surface.getHeight());
        tmp.setColor(Color.GRAY);
        x = new Block((tmp), negTxt);
        x.addToGame(game);
        //up
        tmp = new Rectangle(new Point(0, space), surface.getWidth(), space);
        tmp.setColor(Color.GRAY);
        x = new Block((tmp), negTxt);
        x.addToGame(game);
        //block to write score on
        tmp = new Rectangle(new Point(0, 0), surface.getWidth(), space);
        tmp.setColor(Color.WHITE);
        x = new Block((tmp), negTxt);
        x.addToGame(game);
    }

    /**
     * getClosestCollision method finds the closest collision in trajectory.
     *
     * @param trajectory line from current position to next posotion.
     * @return closest collisioninfo type, null if there is no collision.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        ArrayList<CollisionInfo> collidedPoints = new ArrayList<>();
        for (Collidable c : collidables) {
            CollisionInfo tmp = new CollisionInfo(c, trajectory);
            if (tmp.collisionPoint() != null) {
                collidedPoints.add(tmp);
            }
        }
        if (collidedPoints.size() == 0) {
            return null;
        }
        double distA = collidedPoints.get(0).collisionPoint().distance(trajectory.start()), distB;
        CollisionInfo x = collidedPoints.get(0);
        for (int i = 1; i < collidedPoints.size(); i++) {
            distB = collidedPoints.get(i).collisionPoint().distance(trajectory.start());
            if (distA > distB) {
                x = collidedPoints.get(i);
            }
        }
        return x;
    }

    /**
     * method remove collidable from gameEnviorment list.
     *
     * @param c collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        for (int i = 0; i < this.collidables.size(); i++) {
            if (this.collidables.get(i) == c) {
                collidables.remove(i);
                break;
            }
        }
        return;
    }

}
