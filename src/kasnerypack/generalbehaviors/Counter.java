package kasnerypack.generalbehaviors;

/**
 * @author kasnery.
 */
public class Counter {
    private int counter;

    /**
     * constructor method. resets counter.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * method increases counter by parameter number.
     *
     * @param number to increase counter by.
     */
    public void increase(int number) {
        this.counter = this.counter + number;
    }

    /**
     * subtract number from current count.
     *
     * @param number to decrease counter by.
     */
    public void decrease(int number) {
        this.counter = this.counter - number;
    }

    /**
     * get current count.
     *
     * @return this counter value.
     */
    public int getValue() {
        return this.counter;
    }

    /**
     * method resets value of counter to i value.
     *
     * @param i resets counter to value of i.
     */
    public void resetValueTo(int i) {
        this.counter = i;
    }
}
