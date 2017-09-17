package xyz.tanks.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {
    public static final String PATH = "res/";

    public static BufferedImage loadImage(String fileName) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(PATH + fileName));
        }catch (IOException ex) {
            ex.printStackTrace();
        }

        return image;
    }
}
