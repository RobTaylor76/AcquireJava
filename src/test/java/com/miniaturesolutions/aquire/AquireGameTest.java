package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.miniaturesolutions.aquire.Corporations.Status;

public class AquireGameTest {

	AquireGame game;
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
	public void getStockMarket() {

		//no active corporations... no available corporations
		//never show unincorporated

		Map<Corporations,StockQuote> stockMarket = game.getStockMarket();
		assertEquals("Should be empty market",0,stockMarket.size());

		//need to create a corporation that is active...
		Corporation corp = game.getCorporation(Corporations.AMERICAN);
		corp.setStatus(Status.ACTIVE);

		stockMarket = game.getStockMarket();
		assertEquals("Should 1 entry",1,stockMarket.size());

		StockQuote quote = stockMarket.get(Corporations.AMERICAN);
		assertNotNull("Should have a live quote",quote);

		assertEquals("Should have correct no of available shares",
				corp.getRemainingShareCount(),
				quote.getAvailableShares());

		assertEquals("Should have correct value for the shares",
				corp.getCurrentStockPrice(),
				quote.getStockPrice());

        assertEquals("Should have correctcorporation",
                corp.getCorporation(),
                quote.getCorporation());

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

	@Test
	public void willTileCauseMerge() {

		game.placeTile(new Tile(1,0));
		game.placeTile(new Tile(0,1));

		game.placeTile(new Tile(8,10));
		game.placeTile(new Tile(7,11));

		List<Tile> validMerges = new LinkedList<>();
		validMerges.add(new Tile(0,0));
		validMerges.add(new Tile(2,0));
		validMerges.add(new Tile(1,1));
		validMerges.add(new Tile(0,2));

		validMerges.add(new Tile(8,11));
		validMerges.add(new Tile(7,10));
		validMerges.add(new Tile(6,11));
		validMerges.add(new Tile(8,9));

		List<Tile> noMerges = new LinkedList<>();
		noMerges.add(new Tile(0,3));
		noMerges.add(new Tile(1,2));
		noMerges.add(new Tile(2,1));

		for(Tile tile: validMerges) {
			assertTrue("should trigger merge " + tile.toString(), game.willTileCauseMerge(tile));
		}

		for(Tile tile: noMerges) {
			assertFalse("should not trigger merge "+ tile.toString(), game.willTileCauseMerge(tile));
		}
	}	
}
