package com.miniaturesolutions.aquire;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

public class Corporation implements Comparable<Corporation> {

	private int availableShares;
	
    private List<Tile> tiles = new LinkedList<>();
    
	private Status status;
	final private NamedCorporation namedCorporation;
	/** 
	 * Creates a corporation based on the defined corporations
	 * @param namedCorporation
	 */
	public Corporation(NamedCorporation namedCorporation) {
		this.namedCorporation = namedCorporation;
		this.availableShares = namedCorporation.getTotalShareCount();
		this.status = Status.DORMANT;
	}

	public int getRemainingShareCount() {
		return this.availableShares;
	}

	/**
	 * Is the corporation DORMANT, ACTIVE or DEFUNCT?
	 * @param status DORMANT, ACTIVE or DEFUNCT
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Is the corporation DORMANT, ACTIVE or DEFUNCT?
	 * @return active DORMANT, ACTIVE or DEFUNCT
	 */
	public Status getStatus() {
		return this.status;
	}

	/**
	 * How much are the shares currently worth?
	 * @return the value of the shares
	 */
	public int getCurrentStockPrice() {
		return namedCorporation.getTier().getSharePrice(getTileCount());
	}

    /**
     * Safe if size is 11 or more
     * @return
     */
	public boolean isSafe() {
		return (getTileCount() > 10);
	}

    /**
     * How many tiles in chain
     * @return
     */
    public int getTileCount() {
        return tiles.size();
    }
	
	/**
	 * Add a tile to the corporation
	 * @param tile
	 */
	public void addTile(Tile tile) {
		tiles.add(tile);
		this.status = Status.ACTIVE;
	}

	/**
	 * What corporation does this represent?
	 * @return
	 */
	public NamedCorporation getCorporationName() {
		return namedCorporation;
	}

	/** 
	 * Merge the 2 corporations... 
	 * loser corporation will become defunt
	 * all it's tiles will be merged into winner 
	 * @param loser
	 */
	public void merge(Corporation loser) {
        for(Tile tile: loser.tiles) {
            addTile(tile);
        }
        loser.defuntCompany();
	}

	public void defuntCompany() {
		//loser corporation... needs to ask it's share holders to sell/keep or trade their shares?
		setStatus(Status.DEFUNCT);
		tiles.clear();
	}
	
	@Override
	/**
	 * CompareTo work of the currentTileCount
	 */
	public int compareTo(Corporation compare) {
		return Integer.compare(this.getTileCount(), compare.getTileCount());
	}
}
