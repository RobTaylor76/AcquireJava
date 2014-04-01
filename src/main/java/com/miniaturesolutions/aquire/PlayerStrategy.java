package com.miniaturesolutions.aquire;

import java.util.List;

public interface PlayerStrategy {

	/**
	 * The player must choose while tile to place...
	 * 
	 * @param tiles which the player can place
	 * @return the tile to place or null if no valid tile to place...
	 */
	Tile placeTile(List<Tile> validTiles);

	/**
	 * Placing the tile caused a corporation to form. 
	 * Choose which corporation to form
	 * 
	 * A list of the Stock Quotes for available Corporations is provided
	 * 
	 * @param corporations
	 * @return The corporation to form
	 */
	Corporation selectCorporationToForm(List<StockQuote> corporations);
	
	/**
	 * A merge has occurred due to the placement of your tile
	 * 
	 * Select which corporation will survive
	 * 
	 * A list of the Stock Quotes for available Corporations is provided
	 * 
	 * @param corporations
	 * @return which corporation will survive
	 */
	Corporation resolveMerger(List<StockQuote> corporations);

}
