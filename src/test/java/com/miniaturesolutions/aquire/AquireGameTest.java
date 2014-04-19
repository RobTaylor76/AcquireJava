package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

import static org.mockito.Mockito.*;

public class AquireGameTest {

	private AquireGame game;
	private AquireAdviser adviser;
	private AquireFactory factory;
	private AquireBoard board;
	private PlayerStrategy player;
	
	@Before
	public void doSetup() {
		TestAquireFactory factoryImpl = new TestAquireFactory();
		factory = factoryImpl;
		board = mock(AquireBoard.class);
		factoryImpl.setAquireBoard(board);
		game = new AquireGame(factory);
		adviser = game;
		player = mock(PlayerStrategy.class);
		game.addPlayer(player);
	}

	@Test
	public void getAdviser() {
		AquireAdviser adviserImpl = game.getAdviser();
		assertNotNull("should create an adviser", adviserImpl);
	}


//	@Test 
//	public void resolveMerges() {
//
//		Corporation corp1 = new Corporation(NamedCorporation.UNINCORPORATED);
//		Corporation corp2 = new Corporation(NamedCorporation.AMERICAN);
//
//
//		Corporation winner = game.whoWinsMerge(corp1,corp2);
//		assertEquals("AMERICAN should win", corp2, winner);
//
//        winner = game.whoWinsMerge(corp2,corp1);
//        assertEquals("AMERICAN should win", corp2, winner);
//
//		corp1 = new Corporation(NamedCorporation.SACKSON);
//
//		//chain with most tiles wins
//		for(int i=0; i<3; i++) {
//			corp1.addTile(new Tile(i,0));
//			corp2.addTile(new Tile(0,i));
//		}
//
//		winner = game.whoWinsMerge(corp1,corp2);    	
//		assertNull("no winner as both have same number of tiles", winner);  
//
//		corp1.addTile(new Tile(4,0));
//		winner = game.whoWinsMerge(corp1,corp2);      	
//		assertEquals("SACKSON should win", corp1, winner);
//
//        winner = game.whoWinsMerge(corp2,corp1);
//        assertEquals("SACKSON should win", corp1, winner);
//    }

	@Test
	public void playerPlacesIsolatedTile() {
				
		PlayerStrategy player = mock(PlayerStrategy.class);
		
		game.addPlayer(player);
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		when(board.getAffectedCorporations(eq(tileToPlace))).thenReturn(Collections.<Corporation>emptyList());

		AquireAdviser adviser = game.getAdviser();
		
		List<StockQuote> stockMarket = adviser.getStockMarket();
		
		game.playGame();
		verify(board).getAffectedCorporations(eq(tileToPlace));
		verify(board).placeTile(eq(tileToPlace), any(Corporation.class));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place
		
		verify(player).purchaseShares(eq(stockMarket),any(int.class));
		verify(player,never()).selectCorporationToForm(any(List.class));
	}

	
	@Test
	public void playerPlacesTileThatFormsCorporation() {
				
		PlayerStrategy player = mock(PlayerStrategy.class);
		
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation newCorp = corporations.get(0);
		assertTrue("should be active corp", Status.DORMANT == newCorp.getStatus());
		
		game.addPlayer(player);
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		
		List<Corporation> affectedCorporations = new LinkedList<>();
		affectedCorporations.add(new Corporation(NamedCorporation.UNINCORPORATED));
		when(board.getAffectedCorporations(eq(tileToPlace))).thenReturn(affectedCorporations);
		when(player.selectCorporationToForm(any(List.class))).thenReturn(newCorp.getCorporationName());

		AquireAdviser adviser = game.getAdviser();
		
//		List<StockQuote> stockMarket = adviser.getStockMarket();
		
		List<StockQuote> formationChoices = adviser.availableCorporations();
		
		game.playGame();
		verify(board).getAffectedCorporations(eq(tileToPlace));
		verify(board).placeTile(eq(tileToPlace), any(Corporation.class));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place
				
		verify(player).selectCorporationToForm(eq(formationChoices));
		verify(player).purchaseShares(any(List.class),any(int.class));

		assertTrue("should be active corp", Status.ACTIVE == newCorp.getStatus());
	}
	
	
	@Test
	public void playerPlacesTileThatCausesAmbiguousMerger() {
		
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation activeCorp1 = corporations.get(0);
		activeCorp1.setStatus(Status.ACTIVE);
		activeCorp1.addTile(new Tile(1,1));

		Corporation activeCorp2 = corporations.get(1);
		activeCorp2.setStatus(Status.ACTIVE);
		activeCorp2.addTile(new Tile(1,1));
		activeCorp2.addTile(new Tile(1,1));

		Corporation activeCorp3 = corporations.get(2);
		activeCorp3.setStatus(Status.ACTIVE);
		activeCorp3.addTile(new Tile(1,1));
		activeCorp3.addTile(new Tile(1,1));    //should be ultimate winner and 1/2 are chosen from...
		
		Corporation activeCorp4 = new Corporation(NamedCorporation.UNINCORPORATED);
		activeCorp4.setStatus(Status.ACTIVE);
		activeCorp4.addTile(new Tile(1,1));
		activeCorp4.addTile(new Tile(1,1));
		
		assertEquals("corporations are same size", activeCorp2.getTileCount(), activeCorp3.getTileCount());
		
		AquireAdviser adviser = game.getAdviser();
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		List<Corporation> affectedCorporations = new LinkedList<>();
		affectedCorporations.add(activeCorp1);
		affectedCorporations.add(activeCorp2);
		affectedCorporations.add(activeCorp3);
		affectedCorporations.add(activeCorp4);
		affectedCorporations.add(new Corporation(NamedCorporation.UNINCORPORATED));
		
		when(board.getAffectedCorporations(eq(tileToPlace))).thenReturn(affectedCorporations);
		
		List<StockQuote> mergerCorporations = new ArrayList<>();
		
		mergerCorporations.add(new StockQuote(activeCorp2));
		mergerCorporations.add(new StockQuote(activeCorp3));
		
//		List<StockQuote> formationChoices = adviser.availableCorporations();
//		List<StockQuote> stockMarket = adviser.getStockMarket();


		when(player.resolveMerger(any(List.class))).thenReturn(activeCorp2.getCorporationName());

		
		game.playGame();
		verify(board).getAffectedCorporations(eq(tileToPlace));
		verify(board).placeTile(eq(tileToPlace), eq(activeCorp2));
		
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place		

		verify(player).resolveMerger(eq(mergerCorporations));
//		verify(player).purchaseShares(eq(stockMarket),any(int.class));
		verify(player).purchaseShares(any(List.class),any(int.class));
		
		assertEquals("corp one should have no tiles", 0, activeCorp1.getTileCount());
		assertEquals("corp 2 should have 8 tiles", 8, activeCorp2.getTileCount());
		assertEquals("corp 3 should have 0 tiles", 0, activeCorp3.getTileCount());

	}
	
	@Test
	public void playerPlacesTileThatCausesUnAmbiguousMerger() {
		
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation activeCorp1 = corporations.get(0);
		activeCorp1.setStatus(Status.ACTIVE);
		activeCorp1.addTile(new Tile(1,1));
		activeCorp1.addTile(new Tile(2,1));
		
		Corporation activeCorp2 = corporations.get(1);
		activeCorp2.setStatus(Status.ACTIVE);
		activeCorp2.addTile(new Tile(1,1));
		
		assertNotEquals("corporations are not the same size", activeCorp1.getTileCount(), activeCorp2.getTileCount());
		
		AquireAdviser adviser = game.getAdviser();
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		List<Corporation> affectedCorporations = new LinkedList<>();
		affectedCorporations.add(activeCorp1);
		affectedCorporations.add(activeCorp2);;
		
		when(board.getAffectedCorporations(eq(tileToPlace))).thenReturn(affectedCorporations);
		
		List<StockQuote> mergerCorporations = new ArrayList<>();
		
		for(Corporation corp : affectedCorporations) {
			mergerCorporations.add(new StockQuote(corp));
		}
		
		List<StockQuote> formationChoices = adviser.availableCorporations();
		//List<StockQuote> stockMarket = adviser.getStockMarket();
		
		game.playGame();
		verify(board).getAffectedCorporations(eq(tileToPlace));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place		

		verify(player, never()).resolveMerger(eq(mergerCorporations));
		verify(player).purchaseShares(any(List.class),any(int.class));
		
		assertEquals("corp one should have 3 tiles", 4, activeCorp1.getTileCount());
		assertEquals("corp 2 should have 0 tiles", 0, activeCorp2.getTileCount());
		
		assertEquals("corp 1 active", Status.ACTIVE, activeCorp1.getStatus());
		assertEquals("corp 2 defunct", Status.DEFUNCT, activeCorp2.getStatus());
	}

	@Test
	public void getStockMarket() {
		
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation corp = corporations.get(0);
		
		
		//no active corporations... no available corporations
		//never show unincorporated

		List<StockQuote> stockMarket = adviser.getStockMarket();
		assertEquals("Should be empty market",0,stockMarket.size());

		//need to create a corporation that is active...
		corp.setStatus(Status.ACTIVE);

		stockMarket = adviser.getStockMarket();
		assertEquals("Should 1 entry",1,stockMarket.size());

		StockQuote quote = stockMarket.get(0);
		assertNotNull("Should have a live quote",quote);

        assertEquals("Should have correct value for the shares",
                corp.getCurrentStockPrice(),
                quote.getStockPrice());

        assertEquals("Should have correct corporation",
                corp.getCorporationName(),
                quote.getCorporation());


        assertEquals("Should have correct no of avail shares",
                corp.getRemainingShareCount(),
                quote.getAvailableShares());
        
        assertEquals("Should have correct tile count",
                corp.getTileCount(),
                quote.getCorporationTileCount());

		corp.setStatus(Status.DEFUNCT);
		
		stockMarket = adviser.getStockMarket();
		assertEquals("Should be empty market",0,stockMarket.size());

		corp.setStatus(Status.DORMANT);
		
		stockMarket = adviser.getStockMarket();
		assertEquals("Should be empty market",0,stockMarket.size());

	
	}
	
	@Test
	public void getAvailableCorporations() {
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation corp = corporations.get(0);
		corp.setStatus(Status.ACTIVE);
				
		List<StockQuote> availableCorporations = adviser.availableCorporations();
		assertEquals("should only have 6 corporations as 1 is active", 6, availableCorporations.size());
		
		for(StockQuote quote: availableCorporations) {
			assertFalse("dont list the active corporation", quote.getCorporation() == corp.getCorporationName());
		}
	}
	
	@Test
	public void willTileCauseMerge() {
		Tile tile = new Tile(0,0);
		adviser.willTileCauseMerger(tile);

		assertFalse("should not trigger merge "+ tile.toString(), adviser.willTileCauseMerger(tile));

	}
}
