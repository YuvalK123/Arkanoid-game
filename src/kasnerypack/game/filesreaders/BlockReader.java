package kasnerypack.game.filesreaders;

import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.ImageSprite;
import kasnerypack.generalbehaviors.DeafultBlockVals;
import kasnerypack.interfaces.BlockCreator;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.InputStream;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kasnery.
 */
public class BlockReader implements BlockCreator {
    private String line;
    private String symbol;
    private Integer width;
    private Integer height;
    private Integer hitPoints;
    private Map<Integer, ImageSprite> fills;
    private Color stroke;
    private DeafultBlockVals deafultBlockVals;

    /**
     * constructor method with line and default vals.
     *
     * @param line         to read block by.
     * @param defaultBlock contains default values.
     */
    public BlockReader(String line, DeafultBlockVals defaultBlock) {
        this.line = line;
        this.deafultBlockVals = defaultBlock;
        this.fills = new TreeMap<>();
    }

    @Override
    public Block create(int xpos, int ypos) {
        Rectangle rec = new Rectangle(new Point(xpos, ypos), this.width.doubleValue(), this.height.doubleValue());
        Block block = new Block(rec, this.hitPoints);
        Map<Integer, ImageSprite> fillers = new TreeMap<>();
        for (Integer integer : this.fills.keySet()) {
            fillers.put(integer, this.fills.get(integer).copy());
        }
        block.setSprites(fillers);
        if (this.stroke != null) {
            block.setStroke(this.stroke);
        }
        return block;
    }

    /**
     * method generates specs for block by line.
     */
    public void generateSpecs() {
        Pattern patt = Pattern.compile("[^ ]*:[^ ]*");
        Matcher matcher = patt.matcher(this.line);
        while (matcher.find()) {
            assumeByString(this.line.substring(matcher.start(), matcher.end()));
        }
        assumeDefaultVals();
        if (!this.line.trim().toLowerCase().startsWith("default")) {
            checkForNulls();
        } else {
            this.symbol = "default";
        }
    }

    /**
     * method assumes filed by seq string.
     *
     * @param seq string of key:val.
     */
    private void assumeByString(String seq) {
        String param = seq.substring(0, seq.indexOf(":"));
        String val = seq.substring(seq.indexOf(":") + 1), string;
        Map<String, ImageSprite> fillsMap = new TreeMap<>();
        if (param.trim().toLowerCase().contains("fill")) {
            try {
                filler(param.trim().toLowerCase(), val);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        switch (param.trim().toLowerCase()) {
            case ("width"):
                try {
                    this.width = Integer.valueOf(val);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ("height"):
                try {
                    this.height = Integer.valueOf(val);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case ("symbol"):
                this.symbol = val;
                break;
            case ("stroke"):
                string = val.substring(val.indexOf("(" + 2), val.indexOf(")"));
                this.stroke = Color.getColor(string);
                break;
            case ("hit_points"):
                try {
                    this.hitPoints = Integer.valueOf(val);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            default:
                break;
        }

    }

    /**
     * method adds a filler to fill map.
     * @param key for image or color.
     * @param val of filler.
     */
    private void filler(String key, String val) {
        String inputUrl = val.substring(val.indexOf("(") + 1, val.indexOf(")") + 1).trim().toLowerCase();
        String url = inputUrl.substring(0, inputUrl.indexOf(")"));
        Integer mapKey = 1;
        ImageSprite imageSprite = null;
        if (val.trim().toLowerCase().contains("image")) {
            try {
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(url);
                if (is == null) {
                    System.out.println("stream got null");
                    imageSprite = new ImageSprite(Color.BLACK);
                } else {
                    imageSprite = new ImageSprite(ImageIO.read(is));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (val.trim().toLowerCase().startsWith("color")) {
            Color color = ColorsParser.colorFromString(inputUrl.toUpperCase());
            if (color == null) {
                System.out.println("block reader failed to read color");
                color = Color.BLACK;
            }
            imageSprite = new ImageSprite(color);
        }
        if (!key.contains("-")) {
            mapKey = 1;
        } else {
            String[] keyVal = key.split("-");
            try {
                mapKey = Integer.valueOf(keyVal[1]);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.fills.put(mapKey, imageSprite);
    }

    /**
     * method checks for unformatted fields.
     */
    private void checkForNulls() {
        if (this.symbol == null) {
            this.symbol = "default";
        }
        if (this.fills.size() == 0) {
            Random rand = new Random();
            if (this.hitPoints != null) {
                for (int i = 0; i < this.hitPoints; i++) {
                    this.fills.put(i + 1, new ImageSprite(new Color(rand.nextInt(0xFFFFFF))));
                }
            } else {
                this.fills.put(1, new ImageSprite(new Color(rand.nextInt(0xFFFFFF))));
            }
        }
        if (this.hitPoints == null) {
            this.hitPoints = this.fills.size();
        }
        if (this.height == null) {
            this.height = 30;
        }
        if (this.width == null) {
            this.width = 30;
        }
    }

    /**
     * method assume default values.
     */
    private void assumeDefaultVals() {
        if (this.deafultBlockVals == null) {
            return;
        }
        if (this.deafultBlockVals.getHitPoints() != 0) {
            this.hitPoints = this.deafultBlockVals.getHitPoints();
        }
        if (this.deafultBlockVals.getHeight() != 0) {
            this.height = this.deafultBlockVals.getHeight();
        }
        if (this.deafultBlockVals.getWidth() != 0) {
            this.width = this.deafultBlockVals.getWidth();
        }
        if (this.deafultBlockVals.getFill().size() != 0) {
            this.fills.putAll(this.deafultBlockVals.getFill());
        }
        if (this.deafultBlockVals.getStroke() != null) {
            this.stroke = this.deafultBlockVals.getStroke();
        }
    }

    /**
     * @return symbol of block.
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * @return integer reflecting width of space.
     */
    public Integer generateSdef() {
        Integer integer = null;
        Pattern patt = Pattern.compile("[^ ]*:[^ ]*");
        Matcher matcher = patt.matcher(this.line);
        while (matcher.find()) {
            String keyVal = this.line.substring(matcher.start(), matcher.end());
            String key = keyVal.trim().substring(0, keyVal.indexOf(":"));
            String val = keyVal.trim().substring(keyVal.indexOf(":") + 1);
            if (key.trim().toLowerCase().equals("symbol")) {
                this.symbol = val.trim();
            } else if (key.trim().toLowerCase().equals("width")) {
                try {
                    integer = Integer.valueOf(val.trim());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (this.symbol == null) {
            this.symbol = "x";
        }
        if (integer == null) {
            System.out.println("failed to assume sdef" + this.symbol);
            integer = 15;
        }
        return integer;
    }
}
