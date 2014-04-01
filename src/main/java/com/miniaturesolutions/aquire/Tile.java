package com.miniaturesolutions.aquire;

/**
 * Aquire board tile
 */
public class Tile {

    final private int column;
    final private int row;
    final private String tileName;
    //private Chain chain;

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

    /**
     * Assign ownership of the chain - only to be called by Chain
     * @param chain
     */
 //   protected void setChain(Chain chain) {
 //       this.chain = chain;
 //   }

    /**
     * What chain does this tile belong to?
     * @return   chain or null
     */
//    public Chain getChain() {
//        return chain;
//    }

    /**
     *  The nice representation of the Tile position...
     *    assume zero based index...    0,0 = 1A   1,0 = 1B , 0,1 = 2A
     * @return
     */
    public String displayedAs() {
        return tileName;
    }

    public String toString() {
        return tileName;
    }
    
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof Tile)) return false;
    	Tile tile = (Tile)o;
    	return tile.tileName.equalsIgnoreCase(this.tileName);
    }
    
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + row;
    	result = 31 * result + column;
    	return result;
    }
    

}
