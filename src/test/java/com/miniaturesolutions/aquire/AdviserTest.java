package com.miniaturesolutions.aquire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.miniaturesolutions.aquire.NamedCorporation.Status;

public class AdviserTest {
	
	private AquireGame game;
	private Adviser adviser;
	private AquireFactory factory;
	
	@Before
	public void setUp() {
		factory = new TestAquireFactory();
		game = new AquireGame(factory);
		adviser = game.getAdviser();
	}

	@Test
	public void getStockMarket() {

		adviser = game.getAdviser();
		
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
                corp.getCorporation(),
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
			assertFalse("dont list the active corporation", quote.getCorporation() == corp.getCorporation());
		}
	}
	
	@Test
	public void willTileCauseMerge() {
		Tile tile = new Tile(0,0);
		adviser.willTileCauseMerger(tile);

		assertFalse("should not trigger merge "+ tile.toString(), adviser.willTileCauseMerger(tile));

	}	


}
