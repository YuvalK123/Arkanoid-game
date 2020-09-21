package kasnerypack.interfaces;

/**
 * @author kasnery.
 * @param <Type> general.
 */
public interface Task<Type> {
    /**
     * method run the type of task.
     *
     * @return type to run.
     */
    Type run();
}
