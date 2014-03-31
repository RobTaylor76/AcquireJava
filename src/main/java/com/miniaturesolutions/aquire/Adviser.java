package com.miniaturesolutions.aquire;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.Corporations.Status;

public class Adviser {

	final private Board board;
	final private Map<Corporations, Corporation> corporationMap;
	
	/**
	 * A adviser for the Game... can hand to clients as readonly object for querying
	 * @param aquireGame
	 * @param board2
	 * @param corporationMap
	 */

	  public Adviser(Board board, Map<Corporations, Corporation> corporationMap) {

			this.board = board;
			this.corporationMap = corporationMap;
	}

	/**
     * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
     * @param tile
     * @return
     */
    public boolean willTileCauseMerger(Tile tile) {
    	return  checkIfTileExists(tile.getColumn() > 0,  tile.getColumn()-1, tile.getRow()) ||
    			checkIfTileExists(tile.getColumn() < 9,  tile.getColumn()+1, tile.getRow()) ||
    			checkIfTileExists(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow()-1) ||
    			checkIfTileExists(tile.getRow()    < 10, tile.getColumn(),   tile.getRow()+1);
    }

    private boolean checkIfTileExists(boolean condition, int column, int row) {
        boolean tilePresent = false;
        if (condition) {
            tilePresent = board.isTilePlaced(Tile.getTileName(column, row));              
        }
        return tilePresent;
    }

    /** 
     * Get the current stock market condition... Active corporations only
     * @return
     */
	public Map<Corporations, StockQuote> getStockMarket() {
		// TODO Auto-generated method stub
		Map<Corporations, StockQuote> stockMarket = new HashMap<>();
		
		for(Entry<Corporations,Corporation> entry : corporationMap.entrySet()) {
			Corporation corp = entry.getValue();
			if (corp.getStatus() == Status.ACTIVE) {
				StockQuote quote = new StockQuote(corp.getRemainingShareCount());
				stockMarket.put(entry.getKey(), quote);
			}
		}		
		return stockMarket;
	}

}
