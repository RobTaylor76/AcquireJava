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
		player = mock(PlayerStrategy.class);
		game.addPlayer(player);
	}

	@Test
	public void getAdviser() {
		AquireAdviser adviserImpl = game.getAdviser();
		assertNotNull("should create an adviser", adviserImpl);
	}


	@Test 
	public void resolveMerges() {

		Corporation corp1 = new Corporation(NamedCorporation.UNINCORPORATED);
		Corporation corp2 = new Corporation(NamedCorporation.AMERICAN);


		Corporation winner = game.whoWinsMerge(corp1,corp2);
		assertEquals("AMERICAN should win", corp2, winner);

        winner = game.whoWinsMerge(corp2,corp1);
        assertEquals("AMERICAN should win", corp2, winner);

		corp1 = new Corporation(NamedCorporation.SACKSON);

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

        winner = game.whoWinsMerge(corp2,corp1);
        assertEquals("SACKSON should win", corp1, winner);
    }

	@Test
	public void playerPlacesIsolatedTile() {
				
		PlayerStrategy player = mock(PlayerStrategy.class);
		
		game.addPlayer(player);
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		when(board.getAffectedTiles(eq(tileToPlace))).thenReturn(Collections.<Entry<Tile,Corporation>>emptyList());

		AquireAdviser adviser = game.getAdviser();
		
		List<StockQuote> stockMarket = adviser.getStockMarket();
		
		game.playGame();
		verify(board).getAffectedTiles(eq(tileToPlace));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place
		
		verify(player).purchaseShares(eq(stockMarket),any(int.class));
		verify(player,never()).selectCorporationToForm(any(List.class));
	}

	
	@Test
	public void playerPlacesTileThatFormsCorporation() {
				
		PlayerStrategy player = mock(PlayerStrategy.class);
		
		game.addPlayer(player);
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		List<Entry<Tile,Corporation>> affectedTiles = new LinkedList<>();
		affectedTiles.add(new AbstractMap.SimpleEntry<>(new Tile(2,1),new Corporation(NamedCorporation.UNINCORPORATED)));
		
		when(board.getAffectedTiles(eq(tileToPlace))).thenReturn(affectedTiles);

		AquireAdviser adviser = game.getAdviser();
		
		List<StockQuote> stockMarket = adviser.getStockMarket();
		
		List<StockQuote> formationChoices = adviser.availableCorporations();
		
		game.playGame();
		verify(board).getAffectedTiles(eq(tileToPlace));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place
				
		verify(player).selectCorporationToForm(eq(formationChoices));
		verify(player).purchaseShares(eq(stockMarket),any(int.class));

	}
	
	
	@Test
	public void playerPlacesTileThatCausesAmbiguousMerger() {
		
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation activeCorp1 = corporations.get(0);
		activeCorp1.setStatus(Status.ACTIVE);
		Chain chain1 = activeCorp1.getChain();
		chain1.addTile(new Tile(1,1));

		Corporation activeCorp2 = corporations.get(1);
		activeCorp2.setStatus(Status.ACTIVE);
		Chain chain2 = activeCorp2.getChain();
		chain2.addTile(new Tile(1,1));
		
		assertEquals("corporations are same size", activeCorp1.getTileCount(), activeCorp2.getTileCount());
		
		AquireAdviser adviser = game.getAdviser();
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		List<Entry<Tile,Corporation>> affectedTiles = new LinkedList<>();
		affectedTiles.add(new AbstractMap.SimpleEntry<>(new Tile(2,1),activeCorp1));
		affectedTiles.add(new AbstractMap.SimpleEntry<>(new Tile(2,2),activeCorp2));
		
		when(board.getAffectedTiles(eq(tileToPlace))).thenReturn(affectedTiles);
		
		List<StockQuote> mergerCorporations = new ArrayList<>();
		
		for(Entry<Tile, Corporation> corp : affectedTiles) {
			mergerCorporations.add(new StockQuote(corp.getValue()));
		}
		
		List<StockQuote> formationChoices = adviser.availableCorporations();
		List<StockQuote> stockMarket = adviser.getStockMarket();
		
		game.playGame();
		verify(board).getAffectedTiles(eq(tileToPlace));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place		

		verify(player).resolveMerger(eq(mergerCorporations));
		verify(player).purchaseShares(eq(stockMarket),any(int.class));
	}
	
	@Test
	public void playerPlacesTileThatCausesUnAmbiguousMerger() {
		
		List<Corporation> corporations = factory.createCorporations();
		
		Corporation activeCorp1 = corporations.get(0);
		activeCorp1.setStatus(Status.ACTIVE);
		Chain chain1 = activeCorp1.getChain();
		chain1.addTile(new Tile(1,1));
		chain1.addTile(new Tile(2,1));
		
		Corporation activeCorp2 = corporations.get(1);
		activeCorp2.setStatus(Status.ACTIVE);
		Chain chain2 = activeCorp2.getChain();
		chain2.addTile(new Tile(1,1));
		
		assertNotEquals("corporations are not the same size", activeCorp1.getTileCount(), activeCorp2.getTileCount());
		
		AquireAdviser adviser = game.getAdviser();
		
		Tile tileToPlace = new Tile(2,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		List<Entry<Tile,Corporation>> affectedTiles = new LinkedList<>();
		affectedTiles.add(new AbstractMap.SimpleEntry<>(new Tile(2,1),activeCorp1));
		affectedTiles.add(new AbstractMap.SimpleEntry<>(new Tile(2,2),activeCorp2));
		
		when(board.getAffectedTiles(eq(tileToPlace))).thenReturn(affectedTiles);
		
		List<StockQuote> mergerCorporations = new ArrayList<>();
		
		for(Entry<Tile, Corporation> corp : affectedTiles) {
			mergerCorporations.add(new StockQuote(corp.getValue()));
		}
		
		List<StockQuote> formationChoices = adviser.availableCorporations();
		List<StockQuote> stockMarket = adviser.getStockMarket();
		
		game.playGame();
		verify(board).getAffectedTiles(eq(tileToPlace));
		verify(player).placeTile(any(List.class)); //gets a list of tiles to place		

		verify(player, never()).resolveMerger(eq(mergerCorporations));
		verify(player).purchaseShares(eq(stockMarket),any(int.class));
	}


}
