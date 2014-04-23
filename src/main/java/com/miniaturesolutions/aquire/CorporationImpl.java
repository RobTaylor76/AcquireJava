package com.miniaturesolutions.aquire;

import java.util.LinkedList;
import java.util.List;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

public final class CorporationImpl implements Corporation {

  private int availableShares;

  private List<Tile> tiles = new LinkedList<>();

  private Status status;
  
  final private NamedCorporation namedCorporation;

  /**
   * Creates a corporation based on the defined corporations
   * @param namedCorporation
   */
  public CorporationImpl(final NamedCorporation corporation) {
    this.namedCorporation = corporation;
    this.availableShares = corporation.getTotalShareCount();
    this.status = Status.DORMANT;
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#getRemainingShareCount()
 */
  @Override
  public int getRemainingShareCount() {
    return this.availableShares;
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#setStatus(com.miniaturesolutions.aquire.NamedCorporation.Status)
 */
  @Override
  public void setStatus(final Status newStatus) {
    this.status = newStatus;
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#getStatus()
 */
  @Override
  public Status getStatus() {
    return this.status;
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#getCurrentStockPrice()
 */
  @Override
  public int getCurrentStockPrice() {
    return namedCorporation.getTier().getSharePrice(getTileCount());
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#isSafe()
 */
  @Override
  public boolean isSafe() {
    return (getTileCount() > 10);
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#getTileCount()
 */
  @Override
  public int getTileCount() {
    return tiles.size();
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#addTile(com.miniaturesolutions.aquire.Tile)
 */
  @Override
  public void addTile(final Tile tile) {
    tiles.add(tile);
    this.status = Status.ACTIVE;
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#getCorporationName()
 */
  @Override
  public NamedCorporation getCorporationName() {
    return namedCorporation;
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#merge(com.miniaturesolutions.aquire.CorporationImpl)
 */
  @Override
  public void merge(final Corporation loser) {
    for (Tile tile: loser.getTiles()) {
      addTile(tile);
    }
    loser.defuntCompany();
  }

  /* (non-Javadoc)
 * @see com.miniaturesolutions.aquire.Corporation#defuntCompany()
 */
  @Override
  public void defuntCompany() {
    //loser corporation... needs to ask it's share holders to sell/keep or trade their shares?
    setStatus(Status.DEFUNCT);
    tiles.clear();
  }


  @Override
  /**
   * CompareTo work of the currentTileCount
   */
  public int compareTo(final Corporation compare) {
    return Integer.compare(this.getTileCount(), compare.getTileCount());
  }

  @Override
  public List<Tile> getTiles() {
    return tiles;
  }
}
