package xyz.tanks.game;

import xyz.tanks.display.Display;
import xyz.tanks.game.level.Level;
import xyz.tanks.graphics.Sprite;
import xyz.tanks.graphics.SpriteSheet;
import xyz.tanks.graphics.TextureAtlas;
import xyz.tanks.io.Input;
import xyz.tanks.utils.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable {
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final String TITLE = "Tanks";
    public static final String ATLAS_FILE_NAME = "texture_atlas.png";
    public static final int CLEAR_COLOR = 0xFF000000;
    public static final int NUM_BUFFERS = 2;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
    public static final long IDLE_TIME = 1;

    private boolean running;
    private Thread gameThread;
    private Input input;
    private Graphics2D graphics;

    private TextureAtlas atlas;

    private Player player;
    private Level level;


    public Game() {
        running = false;
        Display.create(
                WIDTH,
                HEIGHT,
                TITLE,
                CLEAR_COLOR,
                NUM_BUFFERS
        );

        input = new Input();
        Display.addInputListener(input);
        graphics = Display.getGraphics();

        atlas = new TextureAtlas(ATLAS_FILE_NAME);
        level = new Level(atlas);

        player = new Player(100, 100, 2, atlas, level);
    }

    public synchronized void start() {
        if (this.running) {
            return;
        }

        this.gameThread = new Thread(this);

        this.running = true;
        this.gameThread.start();
    }

    public synchronized void stop() {
        if (!this.running) {
            return;
        }

        this.running = false;
        try {
            this.gameThread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        cleanup();
    }

    // calculate
    private void update() {
        level.update();
        player.update(input);
    }

    //render scene
    private void render() {
        Display.clear();

        level.render(graphics, false);
        player.render(graphics);
        level.render(graphics, true);

        Display.swapBuffers();
    }

    private void cleanup() {
        Display.destroy();
    }

    @Override
    public void run() {
        int fps = 0;
        int upd = 0;
        int updl = 0;

        float delta = 0f;

        long count = 0;

        long lastTime = Time.get();
        while(this.running) {
            long now = Time.get();
            long elapsedTime = now - lastTime;

            count += elapsedTime;

            lastTime = now;

            boolean render = false;
            delta += elapsedTime / UPDATE_INTERVAL;
            while (delta > 1) {
                update();
                delta--;

                if (render) {
                    updl++;
                } else {
                    render = true;
                }
                upd++;
            }

            if (render) {
                render();
                fps++;
            } else {
                try {
                    Thread.sleep(IDLE_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            if (count >= Time.SECOND) {
                Display.setTitle(TITLE+" || fps: " + fps + " | upd: " + upd + " | updl: " + updl);

                fps = 0; upd = 0; updl = 0; count -= Time.SECOND;
            }
        }
    }
}
