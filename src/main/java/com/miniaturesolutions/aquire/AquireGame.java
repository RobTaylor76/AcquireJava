package com.miniaturesolutions.aquire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

/**
 * Main Game context
 * @author rob
 *
 */
public final class AquireGame implements AquireAdviser {
  final private Status[] activeStatuses =  {Status.ACTIVE};
  final private Status[] availableStatuses =  {Status.DEFUNCT, Status.DORMANT};

  final private AquireBoard board;
  final private List<Corporation> corporations;
  private PlayerStrategy player;

  /**
   *
   * @param factory
   */
  public AquireGame(final AquireFactory factory) {
    this.board = factory.createBoard();
    this.corporations = factory.createCorporations();
  }

  /**
   * Exposes a readonly query interface for clients
   * @return
   */
  public AquireAdviser getAdviser() {
    return this;
  }

  /**
   * Add a player to the game
   * @param player
   */
  public void addPlayer(final PlayerStrategy player) {
    this.player = player;
  }

  /**
   * Starts the game playing
   */
  public void playGame() {

    List<Tile> validTiles = new LinkedList<>();

    Tile placedTile = player.placeTile(validTiles);

    List<Corporation> affectedTiles = board.getAffectedCorporations(placedTile);
    Corporation tilesCorporation;

    if (affectedTiles.size() == 0) {
      tilesCorporation = new CorporationImpl(NamedCorporation.UNINCORPORATED);
    } else {
      tilesCorporation = resolveCorporationMergers(affectedTiles);
    }
    tilesCorporation.addTile(placedTile);
    board.placeTile(placedTile, tilesCorporation);

    player.purchaseShares(getStockMarket(), 0);
  }

  private boolean willAMergerOccur(final List<Corporation> affectedTiles) {
    NamedCorporation mergerCheck = NamedCorporation.UNINCORPORATED;
    boolean mergerOccuring = false;
    for (Corporation corporation: affectedTiles) {
      NamedCorporation corporationName = corporation.getCorporationName();
      if (corporationName != NamedCorporation.UNINCORPORATED
          && mergerCheck != NamedCorporation.UNINCORPORATED) {
        //need to resolve the merger....
        mergerOccuring = true;
          }
      mergerCheck = corporationName;
    }
    return mergerOccuring;
  }

  private Corporation resolveCorporationMergers(final List<Corporation> affectedCorporations) {
    Corporation winner = null;
    boolean mergerOccuring = willAMergerOccur(affectedCorporations);

    NamedCorporation winnerName = NamedCorporation.UNINCORPORATED;
    List<StockQuote> quotations = null;

    if (mergerOccuring) {
      //need to sort the list descending...
      Collections.sort(affectedCorporations, new Comparator<Corporation>() {
        @Override
        public int compare(final Corporation corp1, final Corporation corp2) {
          return (corp1.compareTo(corp2) * -1);
        } });

      List<Corporation> choices = new ArrayList<>();
      //Should have at most 4 tiles...
      int maxTileCount = 0;
      for (Corporation corp : affectedCorporations) {
        int count = corp.getTileCount();
        if (count >= maxTileCount && corp.getCorporationName() != NamedCorporation.UNINCORPORATED) {
          maxTileCount = count;
          choices.add(corp);
        }
      }
      //Either we have 1 clear winner or we can have up to 4 other corporations...
      if (choices.size() == 1) {
        winner = choices.get(0);
      }

      if (winner == null) {
        quotations = getStockQuotes(choices);
        winnerName = player.resolveMerger(quotations);
      } else {
        winnerName = winner.getCorporationName();
      }

    } else { //if(mergerCheck == NamedCorporation.UNINCORPORATED) {
      //if all tiles unincorporated then form corporation
      quotations = availableCorporations();
      winnerName = player.selectCorporationToForm(quotations);
    }

    if (winner == null) { // player had to pick the winner....
      //need to validate that the winner was in the list of options!!!
      for (StockQuote quote: quotations) {
        if (winnerName == quote.getCorporation()) {
          for (Corporation corp: this.corporations) {
            if (corp.getCorporationName() == winnerName) {
              winner = corp;
              break;
            }
          }
        }
      }
      //if (winner == null) {
      //player sent back invalid choice.... exception????? invalid_state?
      //}
    }

    // have multiple losers!!!
    for (Corporation loser: affectedCorporations) {
      if (loser.getCorporationName() != winnerName) {
        // can get corporation to internalise these?
        winner.merge(loser);
      }
    }
    return winner;
    }

    private List<Corporation> filterCorporationsByState(final Status[] statuses) {
      List<Corporation> filteredCorporations = new LinkedList<>();

      for (Corporation corp : corporations) {
        for (Status status: statuses) {
          if (status == corp.getStatus()) {
            filteredCorporations.add(corp);
          }
        }
      }
      return filteredCorporations;
    }


    private List<StockQuote> getStockQuotes(final List<Corporation> corporations) {

      List<StockQuote> stockQuotes = new ArrayList<>();

      for (Corporation corporation : corporations) {
        if (corporation.getCorporationName() != NamedCorporation.UNINCORPORATED) {
          stockQuotes.add(new StockQuote(corporation));
        }
      }

      return stockQuotes;
    }

    @Override
    public boolean willTileCauseMerger(final Tile tile) {
      return  board.willTileCauseMerger(tile);
    }

    @Override
    public List<StockQuote> getStockMarket() {
      return getStockQuotes(filterCorporationsByState(activeStatuses));
    }

    @Override
    public List<StockQuote> availableCorporations() {
      return getStockQuotes(filterCorporationsByState(availableStatuses));
    }
  }
