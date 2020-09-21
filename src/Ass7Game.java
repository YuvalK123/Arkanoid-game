import biuoop.GUI;
import kasnerypack.animation.AnimationRunner;
import kasnerypack.animation.MenuAnimation;
import kasnerypack.game.HighScoresTable;
import kasnerypack.generalbehaviors.PublicFuncs;
import kasnerypack.interfaces.Menu;
import kasnerypack.interfaces.Task;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author kasnery.
 */
public class Ass7Game {
    /**
     * method starts game.
     *
     * @param args from user.
     */
    public static void main(String[] args) {
        GUI gui = new GUI("Assignment 7", 850, 600);
        File file = new File("highscores.txt");
        HighScoresTable table = HighScoresTable.loadFromFile(file);
        AnimationRunner runner = new AnimationRunner(gui, 60);
        MenuAnimation<Task<Void>> playMenu = null;
        String link = "level_sets.txt";
        if (args.length > 0) {
            link = args[0];
        }
        while (true) {
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(link);
                if (is != null) {
                    InputStreamReader isr = new InputStreamReader(is);
                    playMenu = PublicFuncs.levelsByPath(isr, runner, gui, table, gui.getKeyboardSensor(), file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Menu<Task<Void>> menu = PublicFuncs.initiallizeProgram(runner, gui, table, playMenu);
            runner.run(menu);
            menu.getStatus().run();
        }
    }
}
