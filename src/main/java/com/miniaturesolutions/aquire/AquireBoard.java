package com.miniaturesolutions.aquire;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public interface AquireBoard {

  /**
   *  Get list of tiles that have not been issued
   * @return
   */
  List<Tile> getAvailableTiles();

  /**
   * Is there a tile on the board?
   * @param tileName
   * @return
   */
  boolean isTilePlaced(String tileName);

  /**
   * Simplistic place tile on board... no merging issues/legailty addressed
   *
   * will manage this via a callback ... dont call me... i'll call you!
   * the player strategy will return the tile it wishes to place.
   * @param tile
   * @param corp
   */
  void placeTile(Tile tile, Corporation corp);

  /**
   * Get the tile from the board
   * @param tileName
   */
  Entry<Tile, Corporation> getTile(String tileName);

  /**
   * Get mutable array representing the game state...
   * @return
   */
  Map<Tile, NamedCorporation> getState();

  /**
   *  Get the tiles that would be involved in a merge by placing the tile
   * @param tile
   * @return A List of tiles and the corporation they belong to
   */
  List<Entry<Tile, Corporation>> getAffectedTiles(Tile tile);

  /**
   * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
   * @param tile
   * @return
   */
  boolean willTileCauseMerger(Tile tile);

  /**
   * Get the corporations affected by placing a tile
   * @param tile
   * @return
   */
  List<Corporation> getAffectedCorporations(Tile tile);

}
