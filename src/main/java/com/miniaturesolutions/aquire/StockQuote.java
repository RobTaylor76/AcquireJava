package com.miniaturesolutions.aquire;

public class StockQuote {

    private final int availableShareCount;
    private final int stockPrice;
    private final Corporations corporation;

    public StockQuote(Corporations coporation, int availableShareCount, int stockPrice) {
		this.availableShareCount = availableShareCount;
        this.stockPrice = stockPrice;
        this.corporation = coporation;
	}

	/**
	 * Howint many shares are available?
	 * @return
	 */
	public int getAvailableShares() {
		return availableShareCount;
	}

    /**
     * What is the current price of the Stock
     * @return
     */
    public int getStockPrice() {
        return stockPrice;
    }

    public Corporations getCorporation() {
        return corporation;
    }
}
