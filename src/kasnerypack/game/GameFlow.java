package kasnerypack.game;

import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import kasnerypack.animation.AnimationRunner;
import kasnerypack.animation.EndScreen;
import kasnerypack.animation.HighScoresAnimation;
import kasnerypack.animation.KeyPressStoppableAnimation;
import kasnerypack.interfaces.LevelInformation;

import java.io.File;
import java.util.List;

/**
 * @author kasnery.
 */
public class GameFlow {
    private LivesIndicator lives;
    private KeyboardSensor keyBoard;
    private AnimationRunner animationRunner;
    private ScoreIndicator score;
    private Boolean won;
    private DialogManager dialog;
    private HighScoresTable table;

    /**
     * constructor method of gameFlow.
     *
     * @param runner animationRunner to run animation by.
     * @param key    keyboardSensor of gui.
     * @param dialog manager of gui.
     * @param table of high scores.
     */
    public GameFlow(AnimationRunner runner, KeyboardSensor key, DialogManager dialog, HighScoresTable table) {
        this.lives = new LivesIndicator(7);
        this.score = new ScoreIndicator();
        this.animationRunner = runner;
        this.keyBoard = key;
        this.won = true;
        this.dialog = dialog;
        this.table = table;
    }

    /**
     * method runs levels by list it recieves.
     *
     * @param levels list to run.
     */
    public void runLevels(List<LevelInformation> levels) {
        for (LevelInformation levelInfo : levels) {
            int currentLives = this.lives.getLives();
            GameLevel level = new GameLevel(levelInfo, this.keyBoard, this.animationRunner, this.score,
                    this.lives);
            level.initialize();
            KeyPressStoppableAnimation k = new KeyPressStoppableAnimation(this.keyBoard, "p", level);
            while (this.lives.getLives() > 0) {
                level.playOneTurn();
                if (this.lives.getLives() == currentLives) {
                    break;
                }
                currentLives--;
            }
            if (this.lives.getLives() == 0) {
                this.won = false;
                break;
            }
        }

        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyBoard, KeyboardSensor.SPACE_KEY,
                new EndScreen(this.won, this.score.getValue())));
    }

    /**
     * runs and draws scores on high scores table.
     *
     * @param file with details of scores.
     */
    public void runScores(File file) {
        String name = this.dialog.showQuestionDialog("Name", "What is your name?", "");
        while (name.equals("uninitializedValue")) {
            name = this.dialog.showQuestionDialog("Name", "What is your name?", "");
        }
        ScoreInfo scoreInfo = new ScoreInfo(name, this.score.getValue());
        if (scoreInfo != null) {
            table.add(scoreInfo);
            try {
                table.save(file);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyBoard, KeyboardSensor.SPACE_KEY,
                new HighScoresAnimation(table)));
    }

}

