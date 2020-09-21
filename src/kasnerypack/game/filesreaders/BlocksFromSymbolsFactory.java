package kasnerypack.game.filesreaders;

import kasnerypack.game.sprites.Block;
import kasnerypack.interfaces.BlockCreator;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import java.util.Map;

/**
 * @author kasnery.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * constructor method.
     *
     * @param widths   sdef map.
     * @param creators bdef by map.
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> widths, Map<String, BlockCreator> creators) {
        this.spacerWidths = widths;
        this.blockCreators = creators;
    }

    /**
     * @param s string to check.
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        if (this.spacerWidths.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * @param s string to check.
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        if (this.blockCreators.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * @param s    string to get block by.
     * @param xpos to put block at.
     * @param ypos to put block at.
     * @return block by string from creators map. The block will be located at position (xpos, ypos).
     */
    public Block getBlock(String s, int xpos, int ypos) {
        Block block = null;
        if (isBlockSymbol(s)) {
            block = this.blockCreators.get(s).create(xpos, ypos);
        } else if (isSpaceSymbol(s)) {
            block = new Block(new Rectangle(new Point(0, 0), this.spacerWidths.get(s), 0), 0);
        }
        return block;
    }


}
