package com.miniaturesolutions.aquire;

import java.util.HashMap;
import java.util.Map;

/**
 * Main Game context
 * @author rob
 *
 */
public class AquireGame {

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
	}
	
	public Board getBoard() {
		return board;
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

}
