package com.miniaturesolutions.aquire;

import java.util.ArrayList;
import java.util.List;

public class Board {
	/**
	 * No matter how many games are played, the tiles donot change!
	 */
	private static final List<Tile> allTiles = new ArrayList<>();
	
	static {
		for (int column = 0; column < 9; column ++) {
			for (int row = 0; row < 12; row ++) {
				allTiles.add(new Tile(column,row));
			}	
		}		
	}

	public Board() {
		buildAvaliableTiles();
	}
	
	private void buildAvaliableTiles() {
		availableTiles.addAll(allTiles);
	}

	List<Tile> availableTiles = new ArrayList<>();
	
	public List<Tile> getAvailableTiles() {
		return availableTiles;
	}

	
}
