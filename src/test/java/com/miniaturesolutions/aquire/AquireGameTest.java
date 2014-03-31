package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class AquireGameTest {

	private AquireGame game;
	@Before
	public void doSetup() {
		game = new AquireGame();
	}

	@Test
	public void getCorporations() {
		for (Corporations def: Corporations.values()) {
			Corporation corp = game.getCorporation(def);
			assertNotNull("should exist", corp);
		}
	}

	@Test
	public void getAdviser() {
		Adviser adviser = game.getAdviser();
		assertNotNull("should create an adviser", adviser);
	}


	@Test 
	public void resolveMerges() {

		Corporation corp1 = new Corporation(Corporations.UNINCORPORATED);
		Corporation corp2 = new Corporation(Corporations.AMERICAN);


		Corporation winner = game.whoWinsMerge(corp1,corp2);    	
		assertEquals("AMERICAN should win", corp2, winner);

		corp1 = new Corporation(Corporations.SACKSON);

		//chain with most tiles wins
		for(int i=0; i<3; i++) {
			corp1.addTile(new Tile(i,0));
			corp2.addTile(new Tile(0,i));
		}

		winner = game.whoWinsMerge(corp1,corp2);    	
		assertNull("no winner as both have same number of tiles", winner);  

		corp1.addTile(new Tile(4,0));
		winner = game.whoWinsMerge(corp1,corp2);      	
		assertEquals("SACKSON should win", corp1, winner);   	

	}

}
