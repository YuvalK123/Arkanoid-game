package kasnerypack.generalbehaviors;

import kasnerypack.game.filesreaders.ColorsParser;
import kasnerypack.game.sprites.ImageSprite;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kasnery.
 */
public class DeafultBlockVals {
    private int height = 0;
    private int width = 0;
    private int hitPoints = 0;
    private Map<Integer, ImageSprite> fill;
    private Color stroke = null;
    private String line;

    /**
     * constructor method.
     *
     * @param line representing default values.
     */
    public DeafultBlockVals(String line) {
        this.line = line;
        this.fill = new TreeMap<>();
        generateSpecs();
    }

    /**
     * method generate specs from line.
     */
    private void generateSpecs() {
        Pattern patt = Pattern.compile("[^ ]*:[^ ]*");
        Matcher matcher = patt.matcher(this.line);
        while (matcher.find()) {
            try {
                String[] params = this.line.substring(matcher.start(), matcher.end()).split(":");
                if (params[0].toLowerCase().contains("fill")) {
                    filler(params[0], params[1]);
                }
                switch (params[0].trim().toLowerCase()) {
                    case ("width"):
                        try {
                            this.width = Integer.valueOf(params[1]);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case ("height"):
                        try {
                            this.height = Integer.valueOf(params[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case ("stroke"):
                        String inputUrl = params[1].substring(params[1].indexOf("(") + 1, params[1].indexOf(")") + 1)
                                .trim();
                        this.stroke = ColorsParser.colorFromString(inputUrl);
                        break;
                    case ("hit_points"):
                        try {
                            this.hitPoints = Integer.valueOf(params[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    default:
                        break;
                }
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * method adds a filler to fill map.
     *
     * @param key for image or color.
     * @param val of filler.
     */
    private void filler(String key, String val) {
        String inputUrl = val.substring(val.indexOf("(") + 1, val.indexOf(")") + 1).trim().toLowerCase();
        String tmp = "(\\(.*?\\))", url = inputUrl.substring(0, inputUrl.indexOf(")"));
        Pattern patt = Pattern.compile(tmp);
        Matcher matcher = patt.matcher(val);
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
        this.fill.put(mapKey, imageSprite);
    }

    //getters

    /**
     * @return stroke default of blocks.
     */
    public Color getStroke() {
        return stroke;
    }

    /**
     * @return fills of blocks.
     */
    public Map<Integer, ImageSprite> getFill() {
        return fill;
    }

    /**
     * @return height of blocks.
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return default hitPoints.
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @return default width.
     */
    public int getWidth() {
        return width;
    }
}
