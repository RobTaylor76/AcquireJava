package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class AquireGameTest {

	private AquireGame game;
	private AquireFactory factory;
	
	@Before
	public void doSetup() {
		factory = new TestAquireFactory();
		game = new AquireGame(factory);
	}

	@Test
	public void getAdviser() {
		Adviser adviser = game.getAdviser();
		assertNotNull("should create an adviser", adviser);
	}


	@Test 
	public void resolveMerges() {

		CorporationImpl corp1 = new CorporationImpl(Corporation.UNINCORPORATED);
		CorporationImpl corp2 = new CorporationImpl(Corporation.AMERICAN);


		CorporationImpl winner = game.whoWinsMerge(corp1,corp2);
		assertEquals("AMERICAN should win", corp2, winner);

        winner = game.whoWinsMerge(corp2,corp1);
        assertEquals("AMERICAN should win", corp2, winner);

		corp1 = new CorporationImpl(Corporation.SACKSON);

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
	public void playerPlacesTile() {
		
		Board board = factory.createBoard();
		
		board.placeTile(new Tile(1,0));
		board.placeTile(new Tile(0,1));

		Adviser adviser = game.getAdviser();
		
		PlayerStrategy player = mock(PlayerStrategy.class);
		
		game.addPlayer(player);
		
		
		Tile tileToPlace = new Tile(0,0);
		
		when(player.placeTile(any(List.class))).thenReturn(tileToPlace);
		
		List<Tile> tiles = new LinkedList<>();

		
		List<StockQuote> formationChoices = adviser.availableCorporations();
		
		game.playGame();
		verify(player).placeTile(any(List.class));
		verify(player).selectCorporationToForm(eq(formationChoices));
		
	}

}
