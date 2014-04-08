package com.miniaturesolutions.aquire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

/**
 * Main Game context
 * @author rob
 *
 */
public class AquireGame {

	final private Adviser adviser;
	final private AquireBoard board;
    final private Map<NamedCorporation,Corporation> corporationMap = new HashMap<>();
	private PlayerStrategy player;
    
	/**
	 * 
	 * @param factory
	 */
	public AquireGame(AquireFactory factory) {
		this.board = factory.createBoard();
		
		List<Corporation> corporations = factory.createCorporations();
        for(Corporation corp : corporations) {
            corporationMap.put(corp.getCorporationName(),corp);
        }
        adviser = new Adviser(board, corporationMap);	
	}

	/**
	 * Which of the 2 Corporations is the winner in a merger?
	 * @param corp1
	 * @param corp2
	 * @return winner or null if a tie
	 */
    public Corporation whoWinsMerge(Corporation corp1, Corporation corp2) {
		Corporation winner = null; // if no clear winner then we need to make a choice, just return null for now

		if (corp1.getCorporationName() == NamedCorporation.UNINCORPORATED) {
			winner = corp2;
		}
		if (corp2.getCorporationName() == NamedCorporation.UNINCORPORATED) {
			winner = corp1;
		}
		
		if (corp1.getTileCount() > corp2.getTileCount()) {
			winner = corp1;
		} else if (corp2.getTileCount() > corp1.getTileCount()) {
			winner = corp2;
		}

		return winner;
	 }
    
    
    /**
     * Exposes a readonly query interface for clients
     * @return
     */
	public Adviser getAdviser() {
		return adviser;
	}

	/**
	 * Add a player to the game
	 * @param player
	 */
	public void addPlayer(PlayerStrategy player) {
		this.player = player;
	}

	/**
	 * Starts the game playing
	 */
	public void playGame() {
		
		List<Tile> validTiles = new LinkedList<>();
		
		Tile placedTile = player.placeTile(validTiles);
		
		List<Entry<Tile, Corporation>> affectedTiles = board.getAffectedTiles(placedTile);
		
		if (affectedTiles.size() == 0) {
			
			
		} else {
			NamedCorporation mergerCheck = NamedCorporation.UNINCORPORATED;
			boolean mergerOccuring = false;
			for(Entry<Tile, Corporation> affectedTile: affectedTiles) {
				Corporation corporation = affectedTile.getValue();
				NamedCorporation corporationName = corporation.getCorporationName();
				if (corporationName != NamedCorporation.UNINCORPORATED && 
						mergerCheck != NamedCorporation.UNINCORPORATED) {
					//need to resolve the merger....
					mergerOccuring = true;
				}
				mergerCheck = corporationName;
			}
			if (mergerOccuring) {
				List<StockQuote> mergerCorporations = new ArrayList<>();

				for(Entry<Tile, Corporation> corp : affectedTiles) {
					Corporation corporation = corp.getValue();
					if (corporation.getCorporationName() != NamedCorporation.UNINCORPORATED) {
						mergerCorporations.add(new StockQuote(corporation));
					}
				}
				
				player.resolveMerger(mergerCorporations);
				
			} else if(mergerCheck == NamedCorporation.UNINCORPORATED) {
			//if all tiles unincorporated then form corporation 
				player.selectCorporationToForm(adviser.availableCorporations());
			}
		}
		
		
		//else if a merger needs resolving...
		//List<StockQuote> valueOfMergingCorporations = new LinkedList<>();
		//player.resolveMerger(valueOfMergingCorporations);
		
		player.purchaseShares(adviser.getStockMarket(), 0);
	}
	

}
