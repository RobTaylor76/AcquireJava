package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import org.junit.Test;

import com.miniaturesolutions.aquire.NamedCorporation.Status;
import com.miniaturesolutions.aquire.NamedCorporation.Tier;

public class CorporatationTest {

	@Test
	public void createCorporation() {
		
		for(NamedCorporation def : NamedCorporation.values()) {
			Corporation newCorp = new CorporationImpl(def);
			assertEquals("Should have correct number of shares", def.getTotalShareCount(), 
																	newCorp.getRemainingShareCount());
			assertEquals("should be dormatn", Status.DORMANT, newCorp.getStatus());
		}		
	}
	
	@Test
	public void testSharePrice() {
		Corporation corp = new CorporationImpl(NamedCorporation.AMERICAN);
	
		Tier corpTier = NamedCorporation.AMERICAN.getTier();
		
		int expectedPrice = corpTier.getSharePrice(corp.getTileCount());
		
		assertEquals("stock price should that of tier", expectedPrice, corp.getCurrentStockPrice());		
	}
	
    @Test
    public void safeCorporation() {
		Corporation corp = new CorporationImpl(NamedCorporation.AMERICAN);

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
		Corporation corp = new CorporationImpl(NamedCorporation.AMERICAN);
		assertTrue("should be active corp", Status.DORMANT == corp.getStatus());

		corp.addTile(new Tile(1,0));
		
		assertTrue("should be active corp", Status.ACTIVE == corp.getStatus());

		//illegal state if not DORMANT or DEFUNT
	}
	
	@Test 
	public void comparator() {
		
		CorporationImpl corp1 = new CorporationImpl(NamedCorporation.AMERICAN);
		corp1.addTile(new Tile(1,0));
		CorporationImpl corp2 = new CorporationImpl(NamedCorporation.AMERICAN);
		corp2.addTile(new Tile(1,0));
		CorporationImpl corp3 = new CorporationImpl(NamedCorporation.AMERICAN);
		corp3.addTile(new Tile(1,0));
		corp3.addTile(new Tile(1,0));
		
		assertEquals("should be equal", 0, corp1.compareTo(corp2));
		assertEquals("should be less", -1, corp1.compareTo(corp3));
		assertEquals("should be greater than", 1, corp3.compareTo(corp1));
		
	}
}
