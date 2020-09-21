package kasnerypack.game;

import biuoop.DrawSurface;
import kasnerypack.shapes.Point;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author kasnery.
 */
public class HighScoresTable {
    private List<ScoreInfo> highScores;
    private int size;

    // Create an empty high-scores table with the specified size.
// The size means that the table holds up to size top scores.

    /**
     * constructor method.
     *
     * @param size of table.
     */
    public HighScoresTable(int size) {
        this.size = size;
        this.highScores = new ArrayList<>();
    }

    // Add a high-score.

    /**
     * method adds new user to table.
     *
     * @param score info of user.
     */
    public void add(ScoreInfo score) {
        this.highScores.add(getRank(score.getScore()), score);
        if (this.highScores.size() > this.size) {
            this.highScores = this.highScores.subList(0, this.size - 1);
        }
        this.arrangeScores();
    }

    // Return table size.

    /**
     * @return size of table.
     */
    public int size() {
        return this.size;
    }

    // Return the current high scores.
// The list is sorted such that the highest
// scores come first.

    /**
     * @return list of scoreInfo.
     */
    public List<ScoreInfo> getHighScores() {
        return this.highScores;
    }

    // return the rank of the current score: where will it
// be on the list if added?
// Rank 1 means the score will be highest on the list.
// Rank `size` means the score will be lowest.
// Rank > `size` means the score is too low and will not
// be added to the list.

    /**
     * gets rank of user by score.
     *
     * @param score of user to check.
     * @return rank of user in table.
     */
    public int getRank(int score) {
        int i = 1;
        for (ScoreInfo s : this.highScores) {
            if (i > this.size) {
                break;
            }
            if (score >= s.getScore()) {
                return i;
            }
            i++;

        }
        return 0;
    }

    // Clears the table

    /**
     * method cleares this highScores.
     */
    public void clear() {
        this.highScores.clear();
    }

    // Load table data from file.
// Current table data is cleared.

    /**
     * method loads highscores from file.
     *
     * @param filename to load table by.
     * @throws IOException to throw if failed to read.
     */
    public void load(File filename) throws IOException {
        this.clear();
        BufferedReader reader = null;
        String string, name;
        Integer score = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(filename.getName());
            if (is == null) {
                return;
            }
            InputStreamReader isr = new InputStreamReader(is);
            reader = new BufferedReader(isr);
            while ((string = reader.readLine()) != null) {
                if (!string.trim().startsWith(";")) {
                    name = string.substring(0, string.indexOf(";")).trim();
                } else {
                    name = "failed to load name";
                }
                try {
                    score = Integer.valueOf(string.substring(string.indexOf(";") + 1));
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (score != null) {
                    this.add(new ScoreInfo(name, score));
                }
            }
            arrangeScores();
        } catch (FileNotFoundException ex) {
            System.out.println("file not found meow");
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename to save data to.
     * @throws IOException exception to throw if failed to write.
     */
    public void save(File filename) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            for (ScoreInfo scoreInfo : this.highScores) {
                String line = scoreInfo.getName() + ";" + scoreInfo.getScore();
                writer.write(line);
                writer.newLine();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("file not found");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    // Read a table from file and return it.
// If the file does not exist, or there is a problem with
// reading it, an empty table is returned.

    /**
     * method creates new table from file.
     *
     * @param filename to load table by.
     * @return new HighScoresTable from file.
     */
    public static HighScoresTable loadFromFile(File filename) {
        if (filename == null) {
            return null;
        }
        HighScoresTable table = new HighScoresTable(8);
        try {
            table.load(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }

    /**
     * method arranges scores by value.
     */
    private void arrangeScores() {
        Collections.sort(this.highScores);
    }

    /**
     * method draws users of surface.
     *
     * @param d     DrawSurface to draw on.
     * @param name  point placement of user.
     * @param score point placement of user.
     */
    public void drawUsers(DrawSurface d, Point name, Point score) {
        int i = 0;
        for (ScoreInfo s : this.getHighScores()) {
            d.drawText((int) name.getX(), (int) name.getY() + (50 * i), s.getName(), 20);
            d.drawText((int) score.getX(), (int) score.getY() + (50 * i), String.valueOf(s.getScore()), 20);
            i++;
        }
    }
}
