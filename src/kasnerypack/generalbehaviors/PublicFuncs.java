package kasnerypack.generalbehaviors;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import kasnerypack.animation.AnimationRunner;
import kasnerypack.animation.HighScoresAnimation;
import kasnerypack.animation.KeyPressStoppableAnimation;
import kasnerypack.animation.MenuAnimation;
import kasnerypack.game.GameFlow;
import kasnerypack.game.HighScoresTable;
import kasnerypack.game.filesreaders.LevelSpecificationReader;
import kasnerypack.game.gamelevels.LevelFour;
import kasnerypack.game.gamelevels.LevelOne;
import kasnerypack.game.gamelevels.LevelThree;
import kasnerypack.game.gamelevels.LevelTwo;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.interfaces.Menu;
import kasnerypack.interfaces.Task;
import kasnerypack.shapes.Point;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.LinkedList;
import java.util.List;

/**
 * @author kasnery
 */
public class PublicFuncs {
    /**
     * doublesEq method checks if 2 doubles are equal.
     *
     * @param a 1st double.
     * @param b 2nd double.
     * @return true if equal. false otherwise.
     */
    public static boolean doublesEq(double a, double b) {
        Double x = new Double(a);
        Double y = new Double(b);
        if (x.equals(y)) {
            return true;
        }
        return false;
    }

    /**
     * printPArr method prints point array.
     *
     * @param tmp point array.
     */
    public void printPArr(Point[] tmp) {
        if (tmp == null) {
            return;
        }
        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i] == null) {
                break;
            }
            System.out.print("point " + i + ": ");
            System.out.println("x value is " + (int) tmp[i].getX() + " y value is " + (int) tmp[i].getY());
        }
    }

    /**
     * method makes a list of levels to run.
     *
     * @param args    string array of strings symbolizes levels to run.
     * @param surface to make levels by.
     * @return list of LevelInformation.
     */
    public static List<LevelInformation> easyLevel(String[] args, DrawSurface surface) {
        List<LevelInformation> list = new LinkedList<>();
        int tmp;
        List<Integer> intArgs = new LinkedList<>();
        for (int i = 0; i < args.length; i++) {
            try {
                tmp = Integer.valueOf(args[i]);
            } catch (Exception e) {
                continue;
            }
            intArgs.add(tmp);
        }

        if (intArgs.size() == 0) {
            for (int i = 1; i <= 4; i++) {
                intArgs.add(i);
            }
        }
        for (Integer levelValue : intArgs) {

            switch (levelValue) {
                case (1):
                    list.add(new LevelOne(surface));
                    break;
                case (2):
                    list.add(new LevelTwo(surface));
                    break;
                case (3):
                    list.add(new LevelThree(surface));
                    break;
                case (4):
                    list.add(new LevelFour(surface));
                    break;
                default:
            }
        }
        if (list.size() == 0) {
            list.add(new LevelOne(surface));
            list.add(new LevelTwo(surface));
            list.add(new LevelThree(surface));
            list.add(new LevelFour(surface));
        }
        return list;
    }

    /**
     * * method creates menu starting the program.
     *
     * @param runner            animationRunner.
     * @param gui               of program.
     * @param table             highScoresTable of game.
     * @param taskMenuAnimation play menu.
     * @return starting the program.
     */
    public static Menu<Task<Void>> initiallizeProgram(AnimationRunner runner, GUI gui,
                                                      HighScoresTable table,
                                                      MenuAnimation<Task<Void>> taskMenuAnimation) {
        Menu<Task<Void>> menu = new MenuAnimation<>("menu", gui.getKeyboardSensor(), runner);
        HighScoresAnimation highScoresAnimation = new HighScoresAnimation(table);
        Task<Void> subTask = new Task<Void>() {
            @Override
            public Void run() {
                Task<Void> status = null;
                runner.run(taskMenuAnimation);
                status = taskMenuAnimation.getStatus();
                if (status != null) {
                    status.run();
                }
                return null;
            }
        };
        Task<Void> hiScore = new Task<Void>() {
            @Override
            public Void run() {
                runner.run(new KeyPressStoppableAnimation(gui.getKeyboardSensor(), KeyboardSensor.SPACE_KEY,
                        highScoresAnimation));
                return null;
            }
        };
        Task<Void> exit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }
        };
        menu.addSelection("s", "Play game", subTask);
        menu.addSelection("h", "scores", hiScore);
        menu.addSelection("q", "exit", exit);
        return menu;
    }

    /**
     * method sets a play menu by path.
     *
     * @param reader to read file from.
     * @param runner to run game on.
     * @param gui    of game.
     * @param table  highScoreTable of game.
     * @param k      keyboard of gui.
     * @param file   of table.
     * @return a play menu.
     */
    public static MenuAnimation<Task<Void>> levelsByPath(InputStreamReader reader, AnimationRunner runner,
                                                         GUI gui, HighScoresTable table, KeyboardSensor k, File file) {
        LineNumberReader lineNumberReader = null;
        GameFlow game = new GameFlow(runner, k, gui.getDialogManager(), table);
        MenuAnimation<Task<Void>> menu = new MenuAnimation<>("levels choice", k, runner);
        try {
            lineNumberReader = new LineNumberReader(reader);
            String string, key = null, message = null;
            while ((string = lineNumberReader.readLine()) != null) {
                if (string.length() == 0) {
                    continue;
                }
                if (lineNumberReader.getLineNumber() % 2 == 0) {
                    try {
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(string);
                        if (is == null) {
                            break;
                        }
                        LevelSpecificationReader specificationReader = new LevelSpecificationReader();
                        List<LevelInformation> levelInformations = specificationReader.fromReader(
                                new InputStreamReader(is));
                        Task<Void> task = new Task<Void>() {
                            @Override
                            public Void run() {
                                game.runLevels(levelInformations);
                                game.runScores(file);
                                return null;
                            }
                        };
                        menu.addSelection(key, message, task);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (lineNumberReader.getLineNumber() % 2 == 1) {
                    String[] params = string.split(":");
                    key = params[0];
                    message = params[1];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (lineNumberReader != null) {
                    lineNumberReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return menu;
    }
}
