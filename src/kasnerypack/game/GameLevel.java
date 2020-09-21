package kasnerypack.game;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import kasnerypack.animation.AnimationRunner;
import kasnerypack.animation.CountdownAnimation;
import kasnerypack.animation.KeyPressStoppableAnimation;
import kasnerypack.animation.PauseScreen;
import kasnerypack.game.gamelevels.LevelName;
import kasnerypack.game.sprites.Ball;
import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.SpriteCollection;
import kasnerypack.generalbehaviors.Counter;
import kasnerypack.interfaces.Sprite;
import kasnerypack.interfaces.Animation;
import kasnerypack.interfaces.Collidable;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.interfaces.HitListener;
import kasnerypack.litseners.BallRemover;
import kasnerypack.litseners.BlockRemover;
import kasnerypack.litseners.ScoreTrackingListener;
import kasnerypack.shapes.Paddle;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.awt.Color;
import java.util.List;

/**
 * @author kasnery
 */
public class GameLevel implements Animation {
    private LevelInformation levelInformation;
    private SpriteCollection sprites;
    private GameEnviornment environment;
    private Counter blockCounter;
    private Counter ballsCounter;
    private Paddle paddle;
    private ScoreIndicator score;
    private AnimationRunner runner;
    private Boolean running;
    private KeyboardSensor keyboard;
    private LivesIndicator lives;

    /**
     * constructor method allocates memory for sprite collection, game enviorment and gui.
     *
     * @param information LevelInformation to play.
     * @param k keyBoardSensor of gui.
     * @param run AnimationRunner.
     * @param s scoreIndicator of game.
     * @param livesIndicator lives of game.
     */
    public GameLevel(LevelInformation information, KeyboardSensor k, AnimationRunner run, ScoreIndicator s,
                     LivesIndicator livesIndicator) {
        this.levelInformation = information;
        this.sprites = new SpriteCollection();
        this.environment = new GameEnviornment();
        this.blockCounter = new Counter();
        this.blockCounter.resetValueTo(information.numberOfBlocksToRemove());
        this.ballsCounter = new Counter();
        this.runner = run;
        this.keyboard = k;
        this.score = s;
        this.lives = livesIndicator;

    }

    /**
     * method adds a collidable to sprite collection and collidable List.
     *
     * @param c collidable to add to game.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Method adds given sprite to spriteCollection.
     *
     * @param s sprite to add to spriteCollection.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * ballremover with deathRegion block.
     */
    private void ballRemoverUpdate() {
        DrawSurface surface = this.runner.getSurface();
        //down
        Rectangle deathRegion = new Rectangle(new Point(-10, surface.getHeight() + 10),
                surface.getWidth() + 10, 10);
        Block x = new Block(deathRegion, -1);
        this.addCollidable(x);
        BallRemover baller = new BallRemover(this);
        baller.ballsUpdate(this.ballsCounter);
        x.addHitListener(baller);
    }

    /**
     * method Initialize a new game: create the Blocks and Ball Paddle and add them to the game.
     */
    public void initialize() {
        //listeners
        ballRemoverUpdate();
        DrawSurface surface = this.runner.getSurface();
        BlockRemover print = new BlockRemover(this, this.blockCounter);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(this.score.getScoreCounter());
        HitListener[] listeners = {print, scoreListener};
        //bg
        addSprite(this.levelInformation.getBackground());
        //sides
        this.environment.addEdgesColl(surface, this);
        addSprite(this.lives);
        addSprite(new LevelName(this.levelInformation.levelName()));
        //blocks
        addLevelBlocks(listeners);
        addSprite(this.score);
        initializePaddle();
    }

    /**
     * method add blocks to game.
     *
     * @param listeners to add to blocks.
     */
    private void addLevelBlocks(HitListener[] listeners) {
        List<Block> blocks = this.levelInformation.blocks();
        for (Block b : blocks) {
            b.addListeners(listeners);
            b.addToGame(this);
        }
    }

    /**
     * method initialize paddle for each level.
     */
    private void initializePaddle() {
        DrawSurface surface = this.runner.getSurface();
        int halfPaddleWidth = this.levelInformation.paddleWidth(),
                speed = this.levelInformation.paddleSpeed();
        double midX = surface.getWidth() / 2 - halfPaddleWidth;
        double y = surface.getHeight() * 0.9;
        this.paddle = new Paddle(new Rectangle((new Point(midX, y)), this.levelInformation.paddleWidth(), 35),
                this.keyboard, Color.PINK, speed, surface);
        this.paddle.addToGame(this);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void playOneTurn() {
        createBallsOnTopOfPaddle();
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        while (this.running) {
            KeyPressStoppableAnimation k = new KeyPressStoppableAnimation(this.keyboard, "p",
                    new PauseScreen());
            this.runner.run(this);
        }

    }

    /**
     * method creates balls and reset paddle.
     */
    private void createBallsOnTopOfPaddle() {
        DrawSurface surface = this.runner.getSurface();
        double midX = (surface.getWidth() - this.levelInformation.paddleWidth()) / 2;
        this.paddle.setPaddleXAxis(midX);
        createBallsForGame();
    }

    /**
     * method creates balls for game.
     */
    private void createBallsForGame() {
        DrawSurface surface = this.runner.getSurface();
        int num = this.levelInformation.numberOfBalls();
        this.ballsCounter.resetValueTo(num);
        int x = surface.getWidth() / 2 - 2;
        double y = surface.getHeight() * 0.85;
        Point point = new Point(x, y);
        Ball ball;
        for (int i = 0; i < num; i++) {
            ball = new Ball(point, 5, Color.WHITE);
            ball.setVelocity(this.levelInformation.initialBallVelocities().get(i));
            ball.addToGame(this);
        }
    }

    /**
     * method remove collidable c from game.
     *
     * @param c collidable to remove from game.
     */
    public void removeCollidable(Collidable c) {
        getGameEnviorment().removeCollidable(c);
    }

    /**
     * method remove sprite s from game.
     *
     * @param s sprite to remove from game.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * @return Game's GameEnviorment field
     */
    public GameEnviornment getGameEnviorment() {
        return this.environment;
    }

    /**
     * @param d drawface to do frame on.
     */
    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        Sleeper sleeper = new Sleeper();
        this.sprites.notifyAllTimePassed();
        if (this.blockCounter.getValue() <= 0) {
            this.score.blocksEmptied();
            this.sprites.drawAllOn(d);
            sleeper.sleepFor(40);
            this.running = false;
        }
        if (this.ballsCounter.getValue() == 0) {
            this.lives.decreaseLivesBy(1);
            this.running = false;
        }
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
        }
    }

    /**
     * method check if animation should stop.
     *
     * @return true if should stop. false otherwise.
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}
