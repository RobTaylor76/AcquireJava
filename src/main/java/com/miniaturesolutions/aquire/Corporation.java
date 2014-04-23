package com.miniaturesolutions.aquire;

import java.util.List;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

public interface Corporation extends Comparable<Corporation> {

  int getRemainingShareCount();

  /**
   * Is the corporation DORMANT, ACTIVE or DEFUNCT?
   * @param status DORMANT, ACTIVE or DEFUNCT
   */
  void setStatus(Status newStatus);

  /**
   * Is the corporation DORMANT, ACTIVE or DEFUNCT?
   * @return active DORMANT, ACTIVE or DEFUNCT
   */
  Status getStatus();

  /**
   * How much are the shares currently worth?
   * @return the value of the shares
   */
  int getCurrentStockPrice();

  /**
   * Safe if size is 11 or more
   * @return
   */
  boolean isSafe();

  /**
   * How many tiles in chain
   * @return
   */
  int getTileCount();

  /**
   * Add a tile to the corporation
   * @param tile
   */
  void addTile(Tile tile);

  /**
   * What corporation does this represent?
   * @return
   */
  NamedCorporation getCorporationName();

  /**
   * Merge the 2 corporations...
   * loser corporation will become defunt
   * all it's tiles will be merged into winner
   * @param loser
   */
  void merge(Corporation loser);

  void defuntCompany();

  /**
   * CompareTo work of the currentTileCount
   */
  int compareTo(Corporation compare);

  /**
   * Return the list of tiles the corporation has
   *
   * @return List of tiles
   */
  List<Tile> getTiles();
}
