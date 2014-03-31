package com.miniaturesolutions.aquire;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.miniaturesolutions.aquire.Corporations.Status;

/**
 * Main Game context
 * @author rob
 *
 */
public class AquireGame {

	final private Adviser adviser;
	final private Board board;
    final private Map<Corporations,Corporation> corporationMap = new HashMap<>();
    
	/**
	 * 
	 */
	public AquireGame() {
		board = new Board();
        for(Corporations def : Corporations.values()) {
        	Corporation corp = new Corporation(def);
            corporationMap.put(def,corp);
        }
        adviser = new Adviser(board, corporationMap);
	}
	
	/**
	 * Get the Corporation based on it's definition...
	 * Limits the list the enum
	 * @param def
	 * @return
	 */
	public Corporation getCorporation(Corporations def) {
		// TODO Auto-generated method stub
		return corporationMap.get(def);
	}


    /**
     *  Put a tile in play
     * @param tile
     */
	public void placeTile(Tile tile) {
		board.placeTile(tile);
	}

	/**
	 * Which of the 2 Corporations is the winner in a merger?
	 * @param corp1
	 * @param corp2
	 * @return winner or null if a tie
	 */
    public Corporation whoWinsMerge(Corporation corp1, Corporation corp2) {
		Corporation winner = null; // if no clear winner then we need to make a choice, just return null for now

		if (corp1.getCorporation() == Corporations.UNINCORPORATED) {
			winner = corp2;
		}
		if (corp2.getCorporation() == Corporations.UNINCORPORATED) {
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

}
