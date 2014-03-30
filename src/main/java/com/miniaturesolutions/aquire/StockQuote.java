package com.miniaturesolutions.aquire;

public class StockQuote {

	final int availableShareCount;
	public StockQuote(int availableShareCount) {
		this.availableShareCount = availableShareCount;
	}

	/**
	 * How many shares are available?
	 * @return
	 */
	public int getAvailableShares() {
		return availableShareCount;
	}

}
