package com.miniaturesolutions.aquire;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class BoardImpl implements AquireBoard {

  private List<Tile> availableTiles = new ArrayList<>();
  private Map<String, Entry<Tile, Corporation>> placedTiles = new HashMap<>();

  /**
   * Create a new game board
   */
  public BoardImpl() {
    buildAvailableTiles();
  }

  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#getAvailableTiles()
   */
  @Override
  public List<Tile> getAvailableTiles() {
    return availableTiles;
  }


  private void buildAvailableTiles() {
    for (int column = 0; column < 9; column++) {
      for (int row = 0; row < 12; row++) {
        availableTiles.add(new Tile(column, row));
      }
    }
  }

  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#isTilePlaced(java.lang.String)
   */
  @Override
  public boolean isTilePlaced(final String tileName) {
    return placedTiles.containsKey(tileName);
  }

  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#placeTile(com.miniaturesolutions.aquire.Tile)
   */
  @Override
  public void placeTile(final Tile tile, final Corporation corp) {
    placedTiles.put(tile.toString(),
        new AbstractMap.SimpleEntry<>(tile, corp));
  }

  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#getTile(java.lang.String)
   */
  @Override
  public Entry<Tile, Corporation> getTile(final String tileName) {
    return placedTiles.get(tileName);
  }

  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#getState()
   */
  @Override
  public Map<Tile, NamedCorporation> getState() {
    Map<Tile, NamedCorporation> state = new HashMap<>();

    for (String key: placedTiles.keySet()) {
      Entry<Tile, Corporation> placedTile = placedTiles.get(key);
      state.put(placedTile.getKey(), placedTile.getValue().getCorporationName());
    }
    return state;
  }

  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#getAffectedTiles(com.miniaturesolutions.aquire.Tile)
   */
  @Override
  public List<Entry<Tile, Corporation>> getAffectedTiles(final Tile tile) {
    List<Entry<Tile, Corporation>> list = new LinkedList<>();

    addTileToList(tile.getColumn() > 0,  tile.getColumn() - 1, tile.getRow(), list);
    addTileToList(tile.getColumn() < 8,  tile.getColumn() + 1, tile.getRow(), list);
    addTileToList(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow() - 1, list);
    addTileToList(tile.getRow()    < 10, tile.getColumn(),   tile.getRow() + 1, list);


    return list;
  }

  /**
   * If the condition holds and a tile has been placed in the column/row add it to the list
   * @param condition
   * @param column
   * @param row
   * @param list
   */
  private void addTileToList(final boolean condition, final int column, final int row,
      final List<Entry<Tile, Corporation>> list) {
    if (condition) {
      Entry<Tile, Corporation> tile = getTile(Tile.getTileName(column, row));
      if (tile != null) {
        list.add(tile);
      }
    }
  }
  /* (non-Javadoc)
   * @see com.miniaturesolutions.aquire.AquireBoard#willTileCauseMerger(com.miniaturesolutions.aquire.Tile)
   */
  @Override
  public boolean willTileCauseMerger(final Tile tile) {
    return  checkIfTileExists(tile.getColumn() > 0,  tile.getColumn() - 1, tile.getRow())
      || checkIfTileExists(tile.getColumn() < 8,  tile.getColumn() + 1, tile.getRow())
      || checkIfTileExists(tile.getRow()    > 0,  tile.getColumn(),   tile.getRow() - 1)
      || checkIfTileExists(tile.getRow()    < 11, tile.getColumn(),   tile.getRow() + 1);
  }

  private boolean checkIfTileExists(final boolean condition, final int column, final int row) {
    boolean tilePresent = false;
    if (condition) {
      tilePresent = isTilePlaced(Tile.getTileName(column, row));
    }
    return tilePresent;
  }

  @Override
  public List<Corporation> getAffectedCorporations(final Tile tile) {

    List<Corporation> affctedCorporations = new LinkedList<>();
    List<Entry<Tile, Corporation>> tiles = getAffectedTiles(tile);

    for (Entry<Tile, Corporation> affectedTile: tiles) {
      affctedCorporations.add(affectedTile.getValue());
    }
    return affctedCorporations;
  }
}
