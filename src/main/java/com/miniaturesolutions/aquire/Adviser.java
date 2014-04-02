package com.miniaturesolutions.aquire;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.Corporation.Status;

public class Adviser {

	final private Board board;
	final private Map<Corporation, CorporationImpl> corporationMap;
	
	/**
	 * A adviser for the Game... can hand to clients as readonly object for querying
     * @param corporationMap
     */

	  public Adviser(Board board, Map<Corporation, CorporationImpl> corporationMap) {
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
    			checkIfTileExists(tile.getColumn() < 8,  tile.getColumn()+1, tile.getRow()) ||
    			checkIfTileExists(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow()-1) ||
    			checkIfTileExists(tile.getRow()    < 11, tile.getColumn(),   tile.getRow()+1);
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
	public Map<Corporation, StockQuote> getStockMarket() {
		// TODO Auto-generated method stub
		Map<Corporation, StockQuote> stockMarket = new HashMap<>();
		
		for(Entry<Corporation, CorporationImpl> entry : corporationMap.entrySet()) {
            CorporationImpl corp = entry.getValue();
			if (corp.getStatus() == Status.ACTIVE) {
                StockQuote quote = new StockQuote(corp);
                stockMarket.put(entry.getKey(), quote);
			}
		}		
		return stockMarket;
	}

	/**
	 * List the available corporations... the ones that aren't active
	 * 
	 * @return List of stock market quotes for the available corporations
	 */
	public List<StockQuote> availableCorporations() {
		List<StockQuote> availableCorporations = new LinkedList<>();
		
		for(Entry<Corporation, CorporationImpl> entry : corporationMap.entrySet()) {
            CorporationImpl corp = entry.getValue();
	//		if (corp.getStatus() != Status.ACTIVE) {
                StockQuote quote = new StockQuote(corp);
                availableCorporations.add(quote);
	//		}
		}		
		return availableCorporations;
	}
}
