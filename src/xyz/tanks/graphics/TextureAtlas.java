package xyz.tanks.graphics;

import xyz.tanks.utils.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TextureAtlas {
    private BufferedImage image;

    public TextureAtlas(String imageName) {
        image = ResourceLoader.loadImage(imageName);
    }

    public BufferedImage cut(int x, int y, int w, int h) {
        return image.getSubimage(x, y, w, h);
    }

    public BufferedImage makeColorTransparent(BufferedImage original, int red, int green, int blue) {
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
