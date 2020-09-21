package kasnerypack.interfaces;

/**
 * @author kasnery.
 */
public interface HitNotifier {
    /**
     * method adds a HitLitsener to hit events.
     *
     * @param hl hitListener to be added to the hit events.
     */
    void addHitListener(HitListener hl);

    /**
     * method removes a HitLitsener from the hit events.
     *
     * @param hl hitListener to be removed from the hit events.
     */
    void removeHitListener(HitListener hl);
}
