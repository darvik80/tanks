package xyz.tanks.game;

import xyz.tanks.game.level.Level;
import xyz.tanks.io.Input;

import java.awt.*;

public abstract class Entity {
    public final EntityType type;

    protected float x;
    protected float y;

    protected Entity(EntityType type, float x, float y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    protected abstract void update(Input input);
    protected abstract void render(Graphics2D g);
}
