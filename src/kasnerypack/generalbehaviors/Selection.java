package kasnerypack.generalbehaviors;

/**
 * @param <Type> of selection.
 * @author kasnery.
 */
public class Selection<Type> {
    private Type type;
    private String key;
    private String message;

    /**
     * constructor method.
     *
     * @param key       to press to select.
     * @param message   to print on screen.
     * @param returnVal type to return.
     */
    public Selection(String key, String message, Type returnVal) {
        this.key = key;
        this.message = message;
        this.type = returnVal;
    }

    /**
     * @return key of selection.
     */
    public String getKey() {
        return key;
    }

    /**
     * @return message of selection.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return type of selection.s
     */
    public Type getType() {
        return type;
    }
}
