package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import org.junit.Test;

import com.miniaturesolutions.aquire.Corporation.Status;
import com.miniaturesolutions.aquire.Corporation.Tier;

public class CorportationImplTest {

	@Test
	public void createCorporation() {
		
		for(Corporation def : Corporation.values()) {
			CorporationImpl newCorp = new CorporationImpl(def);
			assertEquals("Should have correct number of shares", def.getTotalShareCount(), 
																	newCorp.getRemainingShareCount());
			assertEquals("should be dormatn", Status.DORMANT, newCorp.getStatus());
		}		
	}
	
	@Test
	public void testSharePrice() {
		CorporationImpl corp = new CorporationImpl(Corporation.AMERICAN);
		Chain corpChain = corp.getChain();
	
		Tier corpTier = Corporation.AMERICAN.getTier();
		
		int expectedPrice = corpTier.getSharePrice(corpChain.getTileCount());
		
		assertEquals("stock price should that of tier", expectedPrice, corp.getCurrentStockPrice());		
	}
	
    @Test
    public void safeCorporation() {
		CorporationImpl corp = new CorporationImpl(Corporation.AMERICAN);

        Tile testTile1 = new Tile(0,0);    	
    	assertFalse("Should not be safe", corp.isSafe());
    	
    	for(int x=1; x < 20; x++) {
    		corp.addTile(testTile1); 
    		if (x < 11) {
    	       	assertFalse("Should not be safe", corp.isSafe());    			
    		} else {
    	       	assertTrue("Should be safe if x >= 11", corp.isSafe());
    		} 		
    	}
    }
    

	
	@Test
	public void defunctTheCorporation() {
		//illegal state if not ACTIVE or if safe!		
	}
	
	@Test
	public void formCorporation() {
		
		//illegal state if not DORMANT or DEFUNT
	}
}
