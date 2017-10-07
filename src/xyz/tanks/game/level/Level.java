package xyz.tanks.game.level;

import xyz.tanks.game.Game;
import xyz.tanks.graphics.TextureAtlas;
import xyz.tanks.utils.Utils;

import java.awt.*;

public class Level {
    public static final int TILE_SCALE = 8;
    public static final int TILE_IN_GAME_SCALE = 2;
    public static final int SCALED_TILE_SIZE = TILE_SCALE * TILE_IN_GAME_SCALE;
    public static final int TILE_IN_WIDTH = Game.WIDTH / SCALED_TILE_SIZE;
    public static final int TILE_IN_HEIGHT = Game.HEIGHT / SCALED_TILE_SIZE;

    private int[][] tileMap;
    private Tile[] tiles;

    public Level(TextureAtlas atlas) {
        tiles = new Tile[TileType.values().length];
        tiles[TileType.BRICK.getIndex()] = new Tile(atlas.cut(32 * TILE_SCALE, 0 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.BRICK);
        tiles[TileType.METAL.getIndex()] =  new Tile(atlas.cut(32 * TILE_SCALE, 2 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.METAL);
        tiles[TileType.WATER.getIndex()] =  new Tile(atlas.cut(32 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.WATER);
        tiles[TileType.GRASS.getIndex()] = new Tile(atlas.cut(34 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.GRASS);
        tiles[TileType.ICE.getIndex()] = new Tile(atlas.cut(36 * TILE_SCALE, 4 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.ICE);
        tiles[TileType.EMPTY.getIndex()] = new Tile(atlas.cut(36 * TILE_SCALE, 6 * TILE_SCALE, TILE_SCALE, TILE_SCALE),
                TILE_IN_GAME_SCALE, TileType.EMPTY);

        tileMap = Utils.levelParser("res/level1.lvl");

    }

    public void update() {

    }

    public void render(Graphics2D g, boolean foreground) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j++) {
                Tile tile =  tiles[tileMap[i][j]];
                if (tile != null) {
                    if (tile.getType().getIsForeground() == foreground) {
                        tile.render(g, j * SCALED_TILE_SIZE, i * SCALED_TILE_SIZE);
                    }
                }
            }
        }

    }

    public boolean collision(Rectangle rectangle) {
        Point[] points = new Point[4];
        points[0] = new Point(rectangle.x / SCALED_TILE_SIZE, rectangle.y / SCALED_TILE_SIZE);
        points[1] = new Point((rectangle.x+rectangle.width) / SCALED_TILE_SIZE, rectangle.y / SCALED_TILE_SIZE);
        points[2] = new Point((rectangle.x+rectangle.width) / SCALED_TILE_SIZE, (rectangle.y+rectangle.height) / SCALED_TILE_SIZE);
        points[3] = new Point(rectangle.x / SCALED_TILE_SIZE, (rectangle.y+rectangle.height) / SCALED_TILE_SIZE);

        for (Point point : points) {
            if (point.y < tileMap.length) {
                if (point.x < tileMap[point.y].length) {
                    Tile tile = tiles[tileMap[point.y][point.x]];

                    switch (tile.getType()) {
                        case ICE:
                        case BRICK:
                            return true;
                    }
                }
            }
        }

        return false;
    }
}
