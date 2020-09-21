package kasnerypack.game.filesreaders;

import kasnerypack.game.gamelevels.LevelFromFile;
import kasnerypack.game.sprites.Block;
import kasnerypack.game.sprites.ImageSprite;
import kasnerypack.generalbehaviors.Velocity;
import kasnerypack.interfaces.LevelInformation;
import kasnerypack.shapes.Point;
import kasnerypack.shapes.Rectangle;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kasnery.
 */
public class LevelSpecificationReader {
    /**
     * @param reader object that reads the file.
     * @return list of levelInformation
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        List<LevelInformation> list = new LinkedList<>();
        BufferedReader bufferedReader = null;
        LevelFromFile level = null;
        String string;
        try {
            bufferedReader = new BufferedReader(reader);
            while ((string = bufferedReader.readLine()) != null) {
                if (string.trim().toLowerCase().startsWith("start_level")) {
                    level = readLevel(bufferedReader);
                    if (level != null) {
                        list.add(level);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * method reads level from file and returns level.
     *
     * @param bufferedReader reader.
     * @return level with all specs.
     * @throws Exception if fails to read.
     */
    private LevelFromFile readLevel(BufferedReader bufferedReader) throws Exception {
        String name = null, key, val, string;
        int pSpeed = 0, pWidth = 0, rowHeight = -1, blocksNum = 0, startX = -1, startY = -1;
        List<Velocity> velocities = new ArrayList<>();
        List<Block> blocks = new ArrayList<>();
        BlocksFromSymbolsFactory factory = null;
        Pattern patt;
        Matcher matcher;
        ImageSprite bg = null;
        while ((string = bufferedReader.readLine()) != null) {
            if (string.trim().toUpperCase().equals("END_LEVEL")) {
                break;
            }
            if (string.trim().toUpperCase().equals("START_BLOCKS")) {
                blocks = createBlocks(bufferedReader, factory, startX, startY, rowHeight);
            }
            try {
                String[] params = string.split(":");
                key = params[0].trim().toLowerCase();
                val = params[1].trim();
            } catch (Exception e) {
                continue;
            }
            switch (key) {
                case ("level_name"):
                    name = val;
                    break;
                case ("ball_velocities"):
                    patt = Pattern.compile("[-0-9]+,[0-9]+");
                    matcher = patt.matcher(val);
                    int speed, angle;
                    while (matcher.find()) {
                        try {
                            String matched = val.substring(matcher.start(), matcher.end());
                            String[] spVel = matched.split(",");
                            angle = Integer.valueOf(spVel[0]);
                            speed = Integer.valueOf(spVel[1]);
                            velocities.add(Velocity.fromAngleAndSpeed(angle, speed));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case ("background"):

                    try {
                        if (val.toLowerCase().contains("color")) {
                            String value = string.substring(string.indexOf("(") + 1, string.indexOf(")") + 1);
                            bg = new ImageSprite(ColorsParser.colorFromString(value));
                            bg.setByBlock(new Block(new Rectangle(new Point(0, 0), 1000, 1000), -1));
                        } else if (val.toLowerCase().contains("image")) {
                            String value = string.substring(string.indexOf("(") + 1, string.indexOf(")"));
                            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(value);
                            bg = new ImageSprite(ImageIO.read(is));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("paddle_speed"):
                    try {
                        pSpeed = Integer.valueOf(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("paddle_width"):
                    try {
                        pWidth = Integer.valueOf(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("block_definitions"):
                    InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(val);
                    //this.getClass().getResourceAsStream("/" + val);
                    InputStreamReader r = new InputStreamReader(inputStream);
                    try {
                        factory = BlocksDefinitionReader.fromReader(r);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("blocks_start_x"):
                    try {
                        startX = Integer.valueOf(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("blocks_start_y"):
                    try {
                        startY = Integer.valueOf(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("row_height"):
                    try {
                        rowHeight = Integer.valueOf(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case ("num_blocks"):
                    try {
                        blocksNum = Integer.valueOf(val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        return new LevelFromFile(velocities, blocks, name, pSpeed, pWidth, bg, blocksNum);
    }

    /**
     * method creates blocks for level.
     *
     * @param reader    bufferedReader to read file.
     * @param factory   class of blocks.
     * @param x         start point.
     * @param y         start point.
     * @param rowHeight to jump row by.
     * @return list of blocks for level.
     */
    private List<Block> createBlocks(BufferedReader reader, BlocksFromSymbolsFactory factory,
                                     int x, int y, int rowHeight) {
        if (x == -1) {
            x = 50;
        }
        if (y == -1) {
            y = 100;
        }
        if (rowHeight == -1) {
            rowHeight = 50;
        }
        if (factory == null) {
            System.out.println("please load blocks 1st");
            return null;
        }
        List<Block> blocks = new LinkedList<>();
        int startX = x;
        String string;

        try {
            while ((string = reader.readLine()) != null) {
                if (string.trim().toUpperCase().equals("END_BLOCKS")) {
                    break;
                }
                for (int i = 0; i < string.length(); i++) {
                    Block block = factory.getBlock(Character.toString(string.charAt(i)), x, y);
                    if (block == null) {
                        continue;
                    }
                    if (!block.getHitPoints().equals("0")) {
                        blocks.add(block);
                    }
                    x = x + (int) block.getCollisionRectangle().getWidth();
                }
                y = y + rowHeight;
                x = startX;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blocks;

    }
}
