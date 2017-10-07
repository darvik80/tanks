package xyz.tanks.graphics;

import xyz.tanks.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite {
    private int idx;

    private BufferedImage[] images = new BufferedImage[2];

    public Sprite(SpriteSheet sheet, float scale) {
        int size = 2;
        images = new BufferedImage[size];
        for (int idx = 0; idx < size; idx++) {
            BufferedImage image = sheet.getSprite(idx);
            images[idx] = Utils.resize(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        }
        this.idx = 1;
    }

    public void next() {
        if (++idx >= images.length) {
            idx = 0;
        }
    }
    public void render(Graphics2D g, float x, float y) {
        g.drawImage(images[idx], (int) x, (int) y,null);
    }

    public Rectangle getRectangle(int x, int y) {
        return new Rectangle(x, y, images[0].getWidth(), images[0].getHeight());
    }
}
