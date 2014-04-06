package com.miniaturesolutions.aquire;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {

    private List<Tile> availableTiles = new ArrayList<>();
    private Map<String,Entry<Tile,Corporation>> placedTiles = new HashMap<>();

    /**
     * Create a new game board
     */
	public Board() {
        buildAvailableTiles();
	}

    /**
     *  Get list of tiles that have not been issued
     * @return
     */
	public List<Tile> getAvailableTiles() {
		return availableTiles;
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
     * 
     * will manage this via a callback ... dont call me... i'll call you! 
     * the player strategy will return the tile it wishes to place. 
     * @param tile
     */
    public void placeTile(Tile tile) {
        placedTiles.put(tile.toString(), 
        		new AbstractMap.SimpleEntry<>(tile,new Corporation(NamedCorporation.UNINCORPORATED)));
    }

    /**
     * Get the tile from the board
     * @param tileName
     */
    public Entry<Tile, Corporation> getTile(String tileName){
        return placedTiles.get(tileName);
    }

    /**
     * Get mutable array representing the game state...
     * @return
     */
	public Map<Tile, NamedCorporation> getState() {
		Map<Tile, NamedCorporation> state = new HashMap<>();
		
		for(String key: placedTiles.keySet()) {
			Entry<Tile,Corporation> placedTile = placedTiles.get(key);
			state.put(placedTile.getKey(), placedTile.getValue().getCorporation()); 
		}
		return state;
	}

	/**
	 *  Get the tiles that would be involved in a merge by placing the tile
	 * @param tile
	 * @return A List of tiles and the corporation they belong to
	 */
	public List<Entry<Tile, Corporation>> getAffectedMergerTiles(Tile tile) {
		List<Entry<Tile,Corporation>> list = new LinkedList<>();

		addTileToList(tile.getColumn() > 0,  tile.getColumn()-1, tile.getRow(),		list); 
		addTileToList(tile.getColumn() < 8,  tile.getColumn()+1, tile.getRow(),		list);
		addTileToList(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow()-1, 	list);
		addTileToList(tile.getRow()    < 10, tile.getColumn(),   tile.getRow()+1,	list);
		

		return list;
	}

	/**
	 * If the condition holds and a tile has been placed in the column/row add it to the list
	 * @param condition
	 * @param column
	 * @param row
	 * @param list
	 */
	private void addTileToList(boolean condition, int column, int row, 
													List<Entry<Tile,Corporation>> list) {
        if (condition) {
        	Entry<Tile,Corporation> tile = getTile(Tile.getTileName(column, row));
        	if (tile != null) {
        		list.add(tile);
        	}
        }
	}
	/**
     * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
     * @param tile
     * @return
     */
    public boolean willTileCauseMerger(Tile tile) {
    	return  checkIfTileExists(tile.getColumn() > 0,  tile.getColumn()-1, tile.getRow()) ||
    			checkIfTileExists(tile.getColumn() < 8,  tile.getColumn()+1, tile.getRow()) ||
    			checkIfTileExists(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow()-1) ||
    			checkIfTileExists(tile.getRow()    < 11, tile.getColumn(),   tile.getRow()+1);
    }

    private boolean checkIfTileExists(boolean condition, int column, int row) {
        boolean tilePresent = false;
        if (condition) {
            tilePresent = isTilePlaced(Tile.getTileName(column, row));              
        }
        return tilePresent;
    }
}
