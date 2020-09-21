package kasnerypack.game;

/**
 * @author kasnery.
 */
public class ScoreInfo implements Comparable<ScoreInfo> {
    private String name;
    private int score;

    /**
     * constructor method.
     *
     * @param name  of user.
     * @param score of user.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * @return name of user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return score of user.
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int compareTo(ScoreInfo o) {
        if (this.score < o.score) {
            return 1;
        } else {
            return -1;
        }
    }
}
