package com.miniaturesolutions.aquire;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

public class AdviserImpl implements AquireAdviser {

	final private AquireBoard boardImpl;
	final private List<Corporation> corporations;
	
	final Status[] activeStatuses =  {Status.ACTIVE};
	final Status[] availableStatuses =  {Status.DEFUNCT, Status.DORMANT};
	
	/**
	 * A adviser for the Game... can hand to clients as readonly object for querying
     * @param corporations
     */
	public AdviserImpl(AquireBoard boardImpl, List<Corporation> corporations) {
			this.boardImpl = boardImpl;
			this.corporations = corporations;
	}

	/* (non-Javadoc)
	 * @see com.miniaturesolutions.aquire.AquireAdviser#willTileCauseMerger(com.miniaturesolutions.aquire.Tile)
	 */
    @Override
	public boolean willTileCauseMerger(Tile tile) {
    	return  boardImpl.willTileCauseMerger(tile);
    }

    /* (non-Javadoc)
	 * @see com.miniaturesolutions.aquire.AquireAdviser#getStockMarket()
	 */
	@Override
	public List<StockQuote> getStockMarket() {
		return filterCorporationsByState(activeStatuses);
	}

	/* (non-Javadoc)
	 * @see com.miniaturesolutions.aquire.AquireAdviser#availableCorporations()
	 */
	@Override
	public List<StockQuote> availableCorporations() {	
		return filterCorporationsByState(availableStatuses);
	}
		
	private List<StockQuote> filterCorporationsByState(Status[] statuses) {
		List<StockQuote> filteredCorporations = new LinkedList<>();
		
		for(Corporation corp : corporations) {
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
