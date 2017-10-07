package xyz.tanks.graphics;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage sheet;
    private int spriteCount = 0;
    private int scale;
    private int spritesInWidth;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale) {
        this.sheet = sheet;
        this.spriteCount = spriteCount;
        this.scale = scale;
        this.spritesInWidth = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int idx) {
        idx = idx % spriteCount;
        int x = idx % spritesInWidth * scale;
        int  y = idx / spritesInWidth * scale;

        return sheet.getSubimage(x, y, scale, scale);
    }
}
