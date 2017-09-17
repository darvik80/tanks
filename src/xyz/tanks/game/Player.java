package xyz.tanks.game;

import xyz.tanks.game.level.Level;
import xyz.tanks.graphics.Sprite;
import xyz.tanks.graphics.SpriteSheet;
import xyz.tanks.graphics.TextureAtlas;
import xyz.tanks.io.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player extends Entity {
    public static final int SPRITE_SCALE = 16;
    public static final int SPRITES_PER_HEADING = 2;

    private enum Heading {
        NORTH(0, 0 * SPRITE_SCALE, 0 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        EAST(1, 6 * SPRITE_SCALE, 0 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        SOUTH(2, 4 * SPRITE_SCALE, 0 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE),
        WEST(3, 2 * SPRITE_SCALE, 0 * SPRITE_SCALE, 2 * SPRITE_SCALE, 1 * SPRITE_SCALE);

        private int index;
        private int x, y, w, h;

        Heading(int index, int x, int y, int w, int h) {
            this.index = index;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        protected BufferedImage texture(TextureAtlas atlas) {
            return atlas.cut(x, y, w, h);
        }
    }

    private Heading heading;
    private Sprite[] sprites;
    private Level level;

    private float x;
    private float y;
    private float scale;

    private double[] speed = new double[100];
    private int speedIndex = 0;

    public Player(float x, float y, float scale, TextureAtlas atlas, Level level) {
        super(EntityType.Player, x, y);

        this.x = x;
        this.y = y;
        this.scale = scale;
        this.level = level;

        heading = Heading.NORTH;

        sprites = new Sprite[Heading.values().length];

        for (Heading h : Heading.values()) {
            SpriteSheet sheet = new SpriteSheet(h.texture(atlas), SPRITES_PER_HEADING, SPRITE_SCALE);
            sprites[h.index] = new Sprite(sheet, scale);
        }

        for (int i = 0; i < speed.length; i++) {
            double delta = (float)(i)/speed.length;
            speed[i] = Math.sin((Math.PI/2)*delta)*3.0f;
        }
    }
    private void increaseSpped() {
        if (++speedIndex >= speed.length) {
            speedIndex = speed.length - 1;
        }
    }
    private void decreaseSpped() {
        if (speedIndex > 0) {
            speedIndex -= Math.min(speedIndex, 10);
        }
    }

    @Override
    protected void update(Input input) {
        float newX = x;
        float newY = y;

        if (input.getKey(KeyEvent.VK_UP)) {
            heading = Heading.NORTH;
            increaseSpped();
        } else if (input.getKey(KeyEvent.VK_DOWN)) {
            heading = Heading.SOUTH;
            increaseSpped();
        } else if (input.getKey(KeyEvent.VK_LEFT)) {
            heading = Heading.WEST;
            increaseSpped();
        } else if (input.getKey(KeyEvent.VK_RIGHT)) {
            heading = Heading.EAST;
            increaseSpped();
        } else {
            decreaseSpped();
        }

        switch (heading) {
            case NORTH:
                newY -= speed[speedIndex];
                break;
            case SOUTH:
                newY += speed[speedIndex];
                break;
            case WEST:
                newX -= speed[speedIndex];
                break;
            case EAST:
                newX += speed[speedIndex];
                break;
        }

        if (newX < 0) {
            newX = 0;
        } else if (newX >= Game.WIDTH - SPRITE_SCALE*scale) {
            newX = Game.WIDTH - SPRITE_SCALE*scale;
        }
        if (newY < 0) {
            newY = 0;
        } else if (newY >= Game.HEIGHT - SPRITE_SCALE*scale) {
            newY = Game.HEIGHT - SPRITE_SCALE*scale;
        }

        if (!level.collision(sprites[heading.index].getRectangle((int)newX, (int)newY))) {
            if (x != newX || y != newY) {
                sprites[heading.index].next();
            }
            x = newX;
            y = newY;
        } else {
            speedIndex = 0;
        }
    }

    @Override
    protected void render(Graphics2D g) {
        sprites[heading.index].render(g, x, y);
    }
}
