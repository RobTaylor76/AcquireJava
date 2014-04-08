package com.miniaturesolutions.aquire;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

public class Adviser {

	final private AquireBoard boardImpl;
	final private Map<NamedCorporation, Corporation> corporationMap;
	
	final Status[] activeStatuses =  {Status.ACTIVE};
	final Status[] availableStatuses =  {Status.DEFUNCT, Status.DORMANT};
	
	/**
	 * A adviser for the Game... can hand to clients as readonly object for querying
     * @param corporationMap
     */

	  public Adviser(AquireBoard boardImpl, Map<NamedCorporation, Corporation> corporationMap) {
			this.boardImpl = boardImpl;
			this.corporationMap = corporationMap;
	}

	/**
     * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
     * @param tile
     * @return
     */
    public boolean willTileCauseMerger(Tile tile) {
    	return  boardImpl.willTileCauseMerger(tile);
    }

    /** 
     * Get the current stock market condition... Active corporations only
     * @return
     */
	public List<StockQuote> getStockMarket() {
		return filterCorporationsByState(activeStatuses);
	}

	/**
	 * List the available corporations... the ones that aren't active
	 * 
	 * @return List of stock market quotes for the available corporations
	 */
	public List<StockQuote> availableCorporations() {	
		return filterCorporationsByState(availableStatuses);
	}
		
	private List<StockQuote> filterCorporationsByState(Status[] statuses) {
		List<StockQuote> filteredCorporations = new LinkedList<>();
		
		for(Entry<NamedCorporation, Corporation> entry : corporationMap.entrySet()) {
            Corporation corp = entry.getValue();
            for(Status status: statuses) {
            	if (status == corp.getStatus()) {
                    StockQuote quote = new StockQuote(corp);
                    filteredCorporations.add(quote);
    			}       	
            }
		}
		return filteredCorporations;
	}
}
