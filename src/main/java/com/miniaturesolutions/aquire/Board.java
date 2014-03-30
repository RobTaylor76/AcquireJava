package com.miniaturesolutions.aquire;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {

    private List<Tile> availableTiles = new ArrayList<>();
    private Map<String,Tile> placedTiles = new HashMap<>();

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
     * Get mutable array representing the game state...
     * @return
     */
	public Map<Tile, Corporations> getState() {
		Map<Tile, Corporations> state = new HashMap<>();
		
		for(Entry<String,Tile> tileName: placedTiles.entrySet()) {
			Tile tile = tileName.getValue();
			//TODO need to be able to map tiles to the corporation they belong to
			state.put(tile, Corporations.UNINCORPORATED); 
		}
		return state;
	}

}
