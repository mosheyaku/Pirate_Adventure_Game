package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "images/pirate_positions.png";
    public static final String LEVEL_ATLAS = "images/background_elements.png";
    public static final String LEVEL_ONE_DATA = "images/level_one_data.png";
    public static final String MENU_BUTTONS = "images/button_templates.png";
    public static final String MENU_BACKGROUND = "images/menu_background.png";




    public static BufferedImage getPositionsAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] getLevelData() {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getPositionsAtlas(LEVEL_ONE_DATA);
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color color = new Color(img.getRGB(j, i));
                int value = color.getRed();
                if (value >= 12 * 4)
                    value = 0;
                levelData[i][j] = value;
            }
        }
        return levelData;
    }
}
