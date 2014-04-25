package com.miniaturesolutions.aquire;


public final class StockQuote {
  private final int availableShareCount;
  private final int stockPrice;
  private final NamedCorporation namedCorporation;
  private final int corporationTileCount;

  public StockQuote(final Corporation corporation) {
    this.availableShareCount = corporation.getRemainingShareCount();
    this.stockPrice = corporation.getCurrentStockPrice();
    this.namedCorporation = corporation.getCorporationName();
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

  public NamedCorporation getCorporation() {
    return namedCorporation;
  }

  /**
   * how many board tiles are in the corporation?
   * @return
   */
  public Object getCorporationTileCount() {
    return corporationTileCount;
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof StockQuote)) {
        return false;
    }
    StockQuote quote = (StockQuote) o;
    return (quote.getCorporation() == this.getCorporation())
      && (quote.getAvailableShares() == this.getAvailableShares())
      && (quote.getStockPrice() == this.getStockPrice());
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + availableShareCount;
    result = 31 * result + namedCorporation.ordinal();
    result = 31 * result + stockPrice;
    return result;
  }
}
