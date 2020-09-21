package kasnerypack.game.filesreaders;

import kasnerypack.generalbehaviors.DeafultBlockVals;
import kasnerypack.interfaces.BlockCreator;

import java.io.BufferedReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author kasnery.
 */
class BlocksDefinitionReader {

    /**
     * method read blocksDefinition file and returns blocks accordingly.
     *
     * @param reader to read blocks from file.
     * @return factory of blocks got from file.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, BlockCreator> creators = new TreeMap<>();
        Map<String, Integer> widths = new TreeMap<>();
        BufferedReader bufferedReader = new BufferedReader(reader);
        String string;
        DeafultBlockVals deafultBlockVals = null;
        try {
            while ((string = bufferedReader.readLine()) != null) {
                if (string.startsWith("#") || string.length() == 0) {
                    continue;
                }
                if (string.trim().toLowerCase().startsWith("default")) {
                    deafultBlockVals = new DeafultBlockVals(string);
                } else if (string.trim().toLowerCase().startsWith("bdef")) {
                    BlockReader creator = new BlockReader(string, deafultBlockVals);
                    creator.generateSpecs();
                    creators.put(creator.getSymbol(), creator);
                } else if (string.trim().toLowerCase().startsWith("sdef")) {
                    BlockReader width = new BlockReader(string, deafultBlockVals);
                    width.generateSdef();
                    widths.put(width.getSymbol(), width.generateSdef());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BlocksFromSymbolsFactory(widths, creators);
    }
}
