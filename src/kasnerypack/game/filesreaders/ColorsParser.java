package kasnerypack.game.filesreaders;

import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kasnery.
 */
public class ColorsParser {
    /**
     * static method that parse the val of string into a color.
     *
     * @param s string to convert to color object.
     * @return color of string.
     */
    public static java.awt.Color colorFromString(String s) {
        Pattern pattern = Pattern.compile("RGB *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            return new Color(Integer.valueOf(matcher.group(1)),  // r
                    Integer.valueOf(matcher.group(2)),  // g
                    Integer.valueOf(matcher.group(3))); // b
        }
        pattern = Pattern.compile("[a-zA-Z]+");
        matcher = pattern.matcher(s);
        Color c = null;
        if (matcher.find()) {
            String me = s.substring(matcher.start(), matcher.end()).toLowerCase();
            c = myColor(me);
        }
        if (c == null) {
            c = Color.BLACK;
        }
        return c;
    }

    /**
     * method returns color of known java Color object.
     *
     * @param col color string.
     * @return color represented by col.
     */
    private static Color myColor(String col) {
        Color color;
        switch (col.toLowerCase()) {
            case "black":
                color = Color.BLACK;
                break;
            case "blue":
                color = Color.BLUE;
                break;
            case "cyan":
                color = Color.CYAN;
                break;
            case "darkgray":
                color = Color.DARK_GRAY;
                break;
            case "gray":
                color = Color.GRAY;
                break;
            case "green":
                color = Color.GREEN;
                break;

            case "yellow":
                color = Color.YELLOW;
                break;
            case "lightgray":
                color = Color.LIGHT_GRAY;
                break;
            case "magneta":
                color = Color.MAGENTA;
                break;
            case "orange":
                color = Color.ORANGE;
                break;
            case "pink":
                color = Color.PINK;
                break;
            case "red":
                color = Color.RED;
                break;
            case "white":
                color = Color.WHITE;
                break;
            default:
                color = Color.BLACK;
        }
        return color;
    }
}
