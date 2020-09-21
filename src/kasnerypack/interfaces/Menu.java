package kasnerypack.interfaces;

/**
 * @param <Type> type to choose.
 * @author kasnery.
 */
public interface Menu<Type> extends Animation {
    /**
     * method adds selection to menu.
     *
     * @param key       to press to select.
     * @param message   to print on screen.
     * @param returnVal type to return.
     */
    void addSelection(String key, String message, Type returnVal);

    /**
     * @return status of menu.
     */
    Type getStatus();

    /**
     * method adds a subMenu to menu.
     *
     * @param key     to press to activate subMenu.
     * @param message to print to enter subMenu.
     * @param subMenu menu.
     */
    void addSubMenu(String key, String message, Menu<Type> subMenu);

    /**
     * method resets stop.
     */
    void reset();
}
