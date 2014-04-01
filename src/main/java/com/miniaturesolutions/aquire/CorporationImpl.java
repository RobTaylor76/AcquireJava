package com.miniaturesolutions.aquire;

import com.miniaturesolutions.aquire.Corporations.Status;

public class CorporationImpl {

	private int availableShares;
	private Chain chain = new Chain();
	private Status status;
	final private Corporations corporation;
	/** 
	 * Creates a corporation based on the defined corporations
	 * @param corporation
	 */
	public CorporationImpl(Corporations corporation) {
		this.corporation = corporation;
		this.availableShares = corporation.getTotalShareCount();
	}

	public int getRemainingShareCount() {
		return this.availableShares;
	}

	public Chain getChain() {
		return chain;
	}

	/**
	 * Is the corporation DORMANT, ACTIVE or DEFUNCT?
	 * @param active DORMANT, ACTIVE or DEFUNCT
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
		return corporation.getTier().getSharePrice(getTileCount());
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
        return chain.getTileCount();
    }
	
	/**
	 * Add a tile to the corporation
	 * @param tile
	 */
	public void addTile(Tile tile) {
		chain.addTile(tile);
	}

	/**
	 * What corporation does this represent?
	 * @return
	 */
	public Corporations getCorporation() {
		return corporation;
	}
}
