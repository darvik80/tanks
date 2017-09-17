package xyz.tanks.game.level;

public enum TileType {
    EMPTY(0, false), BRICK(1, false), METAL(2, false), WATER(3, true), GRASS(4, true), ICE(5, false);

    private int index;
    private boolean isForeground;

    TileType(int index, boolean isForeground) {
        this.index = index; this.isForeground = isForeground;
    }

    public boolean getIsForeground() {
        return isForeground;
    }

    public int getIndex() {
        return index;
    }
}
