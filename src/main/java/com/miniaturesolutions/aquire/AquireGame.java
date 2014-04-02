package com.miniaturesolutions.aquire;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.Corporation.Status;

/**
 * Main Game context
 * @author rob
 *
 */
public class AquireGame {

	final private Adviser adviser;
	final private Board board;
    final private Map<Corporation,CorporationImpl> corporationMap = new HashMap<>();
	private PlayerStrategy player;
    
	/**
	 * 
	 */
	public AquireGame() {
		this(new Board());
	}
	
	/**
	 * 
	 * @param board
	 */
	public AquireGame(Board board) {
		this.board = board;
        for(Corporation def : Corporation.values()) {
            if (def == Corporation.UNINCORPORATED) {
                continue;
            }
        	CorporationImpl corp = new CorporationImpl(def);
            corporationMap.put(def,corp);
        }
        adviser = new Adviser(board, corporationMap);	
	}

	/**
	 * Get the CorporationImpl based on it's definition...
	 * Limits the list the enum
	 * @param def
	 * @return
	 */
	public CorporationImpl getCorporation(Corporation def) {
		// TODO Auto-generated method stub
		return corporationMap.get(def);
	}

	/**
	 * Which of the 2 Corporations is the winner in a merger?
	 * @param corp1
	 * @param corp2
	 * @return winner or null if a tie
	 */
    public CorporationImpl whoWinsMerge(CorporationImpl corp1, CorporationImpl corp2) {
		CorporationImpl winner = null; // if no clear winner then we need to make a choice, just return null for now

		if (corp1.getCorporation() == Corporation.UNINCORPORATED) {
			winner = corp2;
		}
		if (corp2.getCorporation() == Corporation.UNINCORPORATED) {
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
		
		List<Entry<Tile, Corporation>> affectedTiles = board.getAffectedMergerTiles(placedTile);
		
		List<StockQuote> availableCorporations = adviser.availableCorporations(); 
		
		player.selectCorporationToForm(availableCorporations);
	}
	

}
