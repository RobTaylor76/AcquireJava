package com.miniaturesolutions.aquire;

import java.util.List;

public interface AquireAdviser {
  /**
   * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
   * @param tile
   * @return
   */
  boolean willTileCauseMerger(Tile tile);

  /**
   * Get the current stock market condition... Active corporations only
   * @return
   */
  List<StockQuote> getStockMarket();

  /**
   * List the available corporations... the ones that aren't active
   *
   * @return List of stock market quotes for the available corporations
   */
  List<StockQuote> availableCorporations();


}
