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
    	return  checkIfTileExists(tile.getColumn() > 0,  tile.getColumn()-1, tile.getRow()) ||
    			checkIfTileExists(tile.getColumn() < 9,  tile.getColumn()+1, tile.getRow()) ||
    			checkIfTileExists(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow()-1) ||
    			checkIfTileExists(tile.getRow()    < 10, tile.getColumn(),   tile.getRow()+1);
    }

    /**
     *  Which of the 2 Chains is the winner in a merger?
     * @param chain1
     * @param chain2
     * @return winner or null if a tie
     */
	public Chain whoWinsMerge(Chain chain1, Chain chain2) {
		Chain winner = null; // if no clear winner then we need to make a choice, just return null for now

		if (chain1.getCorporation() == Corporation.UNINCORPORATED) {
			winner = chain2;
		}
		if (chain2.getCorporation() == Corporation.UNINCORPORATED) {
			winner = chain1;
		}
		
		if (chain1.getTileCount() > chain2.getTileCount()) {
			winner = chain1;
		} else if (chain2.getTileCount() > chain1.getTileCount()) {
			winner = chain2;
		}

		return winner;
	}

    private boolean checkIfTileExists(boolean condition, int column, int row) {
        boolean tilePresent = false;
        if (condition) {
            tilePresent = isTilePlaced(Tile.getTileName(column, row));              
        }
        return tilePresent;
    }
}
