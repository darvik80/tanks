package xyz.tanks.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static BufferedImage resize(BufferedImage image, int width, int height) {
        BufferedImage result = new BufferedImage(width, height, image.getType());
        result.getGraphics().drawImage(image, 0, 0, width, height, null);

        return result;
    }

    public static int[][] levelParser(String filePath) {
        int[][] result = null;
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
            String line = null;

            while ((line = reader.readLine()) != null) {
                int[]row = new int[line.length()];
                int idx = 0;
                for (char ch : line.toCharArray()) {
                    row[idx++] = (ch-'0');
                }

                rows.add(row);
            }

            result = new int[rows.size()][rows.get(0).length];
            for (int i = 0; i < rows.size(); i++) {
                result[i] = rows.get(i);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static BufferedImage makeColorTransparent(BufferedImage original, int red, int green, int blue) {
        // read original image with no transparency
        // get dimensions of image
        int width = original.getWidth();
        int height = original.getHeight();

        // create a new image that will have transparent colors
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        // make color transparent
        int oldRGB = new Color(red, green, blue).getRGB();
        int newRGB = new Color(red, green, blue, 0x00).getRGB();
        int currRGB;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                currRGB = original.getRGB(x, y);

                if (oldRGB == currRGB) {
                    result.setRGB(x, y, newRGB);
                } else {
                    result.setRGB(x, y, currRGB);
                }
            }
        }

        return result;
    }
}
