package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import org.junit.Test;

public class AquireGameTest {

	@Test
	public void getCorporations() {
		
		AquireGame game = new AquireGame();
		
		for (Corporations def: Corporations.values()) {
			Corporation corp = game.getCorporation(def);
			assertNotNull("should exist", corp);
		}
	}
	
	@Test
	public void testBoard() {
		AquireGame game = new AquireGame();
		Board board = game.getBoard();
	}
}
