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

	final private AquireAdviser adviser;
	final private AquireBoard board;
    final private List<Corporation> corporations;
	private PlayerStrategy player;
    
	/**
	 * 
	 * @param factory
	 */
	public AquireGame(AquireFactory factory) {
		this.board = factory.createBoard();
		this.corporations = factory.createCorporations();
        this.adviser = factory.createAdviser(this.board, this.corporations);	
	}

	/**
	 * Which of the 2 Corporations is the winner in a merger?
	 * @param corp1
	 * @param corp2
	 * @return winner or null if a tie
	 */
    protected Corporation whoWinsMerge(Corporation corp1, Corporation corp2) {
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
	public AquireAdviser getAdviser() {
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
		
		List<Corporation> affectedTiles = board.getAffectedCorporations(placedTile);
		Corporation tilesCorporation;
		
		if (affectedTiles.size() == 0) {
			tilesCorporation = new Corporation(NamedCorporation.UNINCORPORATED);
		} else {
			tilesCorporation = resolveCorporationMergers(affectedTiles);
		}
		board.placeTile(placedTile, tilesCorporation);
		
		player.purchaseShares(adviser.getStockMarket(), 0);
	}
	
	private boolean willAMergerOccur(List<Corporation> affectedTiles) {
		NamedCorporation mergerCheck = NamedCorporation.UNINCORPORATED;
		boolean mergerOccuring = false;
		for(Corporation corporation: affectedTiles) {
			NamedCorporation corporationName = corporation.getCorporationName();
			if (corporationName != NamedCorporation.UNINCORPORATED && 
					mergerCheck != NamedCorporation.UNINCORPORATED) {
				//need to resolve the merger....
				mergerOccuring = true;
			}
			mergerCheck = corporationName;
		}		
		return mergerOccuring;
	}
	
	private Corporation resolveCorporationMergers(List<Corporation> affectedCorporations) {
		Corporation winner = null;
		boolean mergerOccuring = willAMergerOccur(affectedCorporations);
		List<NamedCorporation> winnerOptions = new LinkedList<>();

		NamedCorporation winnerName = NamedCorporation.UNINCORPORATED;
		if (mergerOccuring) {
			// #TODO : need to cope with more than 2 corporations merging...
			winner = whoWinsMerge(affectedCorporations.get(0),affectedCorporations.get(1));
			if (winner == null) {
				List<StockQuote> mergerCorporations = new ArrayList<>();

				for(Corporation corporation : affectedCorporations) {
					if (corporation.getCorporationName() != NamedCorporation.UNINCORPORATED) {
						mergerCorporations.add(new StockQuote(corporation));
						winnerOptions.add(corporation.getCorporationName());
					}
				}
				winnerName = player.resolveMerger(mergerCorporations);
			} else {
				winnerName = winner.getCorporationName();
			}

		} else { //if(mergerCheck == NamedCorporation.UNINCORPORATED) {
			//if all tiles unincorporated then form corporation 
			List<StockQuote> choices = adviser.availableCorporations();
			for(StockQuote quote: choices) {
				winnerOptions.add(quote.getCorporation());		
			}
			winnerName = player.selectCorporationToForm(choices);			
		}

		//need to validate that the winner was in the list of options!!!

		if (winner == null) {
			for(Corporation corp: this.corporations) {
				if (corp.getCorporationName() == winnerName) {
					winner = corp;
				} 
			}
		}

		Corporation loser = null;

		for(Corporation corp: affectedCorporations) {
			if (corp.getCorporationName() != winnerName) {
				loser = corp;
				break;
			}
		}	

		Chain winnerChain = winner.getChain();
		Chain loserChain = loser.getChain();
		winnerChain.merge(loserChain);
		loserChain.clear();

		winner.setStatus(Status.ACTIVE);
		loser.setStatus(Status.DEFUNCT);			
		
		return winner;
	}
}
