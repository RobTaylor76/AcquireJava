package com.miniaturesolutions.aquire;

public class StockQuote {
    private final int availableShareCount;
    private final int stockPrice;
    private final Corporation corporation;
	private final int corporationTileCount;

    public StockQuote(CorporationImpl corporation) {
		this.availableShareCount = corporation.getRemainingShareCount();
        this.stockPrice = corporation.getCurrentStockPrice();
        this.corporation = corporation.getCorporation();
        this.corporationTileCount = corporation.getTileCount();
	}

	/**
	 * How many shares are available?
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

    public Corporation getCorporation() {
        return corporation;
    }

    /**
     * how many board tiles are in the corporation?
     * @return
     */
	public Object getCorporationTileCount() {
		return corporationTileCount;
	}
    
    @Override
    public boolean equals(Object o) {
    	if (!(o instanceof StockQuote)) return false;
    	StockQuote quote = (StockQuote)o;
    	return (quote.corporation == this.corporation) &&
    			(quote.availableShareCount == this.availableShareCount) &&
    			(quote.stockPrice == this.stockPrice);
    											
    }
    
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + availableShareCount;
    	result = 31 * result + corporation.ordinal();
       	result = 31 * result + stockPrice;
    	return result;
    }

}
