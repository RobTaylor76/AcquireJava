package com.miniaturesolutions.aquire;

/**
 * Aquire board tile
 */
public class Tile {

    final private int column;
    final private int row;
    final private String tileName;

    /**
     * Create a Tile... assume zero based index...    0,0 = 1A   1,0 = 1B , 0,1 = 2A
     * @param column
     * @param row
     */
    public Tile(int column, int row) {
        this.column = column;
        this.row = row;
        this.tileName = Tile.getTileName(column, row);
    }

    public String toString() {
        return tileName;
    }

    /**
     * What column is this tile for
     * @return  zero based index
     */
    public int getColumn() {
        return column;
    }

    /**
     * What row is this tile for
     * @return  zero based index
     */
    public int getRow() {
        return row;
    }

    public static String getTileName(int column, int row) {
        return String.valueOf(row+1) + String.valueOf(Character.toChars((column + 65)));
    }
}
