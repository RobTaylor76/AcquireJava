package com.miniaturesolutions.aquire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private Map<Corporation,Chain> corporationChainMap = new HashMap<>();
    private List<Tile> availableTiles = new ArrayList<>();
    private Map<String,Tile> placedTiles = new HashMap<>();

    /**
     * Create a new game board
     */
	public Board() {
        buildAvailableTiles();
        for(Corporation corp : Corporation.values()) {
            if (corp != Corporation.UNINCORPORATED) {
                Chain chain = new Chain();
                corporationChainMap.put(corp,chain);
            }
        }
	}

    /**
     *  Get list of tiles that have not been issued
     * @return
     */
	public List<Tile> getAvailableTiles() {
		return availableTiles;
	}

    /**
     *  Find the active chain for the corporation
     *
     * @param corporation
     * @return Chain, will return a empty chain if no active chain
     */
    public Chain getChain(Corporation corporation) {
        return corporationChainMap.get(corporation);
    }

    private void buildAvailableTiles() {

        for (int column = 0; column < 9; column ++) {
            for (int row = 0; row < 12; row ++) {
                availableTiles.add(new Tile(column,row));
            }
        }
    }

    /**
     * Is there a tile on the board?
     * @param tileName
     * @return
     */
    public boolean isTilePlaced(String tileName) {
        return placedTiles.containsKey(tileName);
    }

    /**
     * Simplistic place tile on board... no merging issues/legailty addressed
     * @param tile
     */
    public void placeTile(Tile tile) {
        placedTiles.put(tile.toString(),tile);
    }

    /**
     * Get the tile from the board
     * @param tileName
     */
    public Tile getTile(String tileName){
        return placedTiles.get(tileName);
    }

    /**
     * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
     * @param tile
     * @return
     */
    public boolean willTileCauseMerge(Tile tile) {
        return (checkAbove(tile) || checkBelow(tile) || checkLeft(tile) || checkRight(tile));
    }

    private boolean checkLeft(Tile tile) {
        boolean tilePresent = false;
        if (tile.getColumn() > 0) {
            Tile tmp = new Tile(tile.getColumn() -1, tile.getRow());
            tilePresent = isTilePlaced(tmp.displayedAs());
        }
        return tilePresent;
    }
    private boolean checkRight(Tile tile) {
        boolean tilePresent = false;
        if (tile.getColumn() < 8) {
            Tile tmp = new Tile(tile.getColumn() +1, tile.getRow());
            tilePresent = isTilePlaced(tmp.displayedAs());
        }
        return tilePresent;   }


    private boolean checkAbove(Tile tile) {
        boolean tilePresent = false;
        if (tile.getRow() > 0) {
            Tile tmp = new Tile(tile.getColumn(), tile.getRow()-1);
            tilePresent = isTilePlaced(tmp.displayedAs());
        }
        return tilePresent;
    }
    private boolean checkBelow(Tile tile) {
        boolean tilePresent = false;
        if (tile.getRow() < 10) {
            Tile tmp = new Tile(tile.getColumn(), tile.getRow() +1);
            tilePresent = isTilePlaced(tmp.displayedAs());
        }
        return tilePresent;
    }



}
