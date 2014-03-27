package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import org.junit.Test;

import com.miniaturesolutions.aquire.Corporation.Tier;

public class CorporationTest {
	
	@Test
	public void tieredCorporations() {
		Corporation corp = Corporation.UNINCORPORATED;
		Tier tier = corp.getTier();	
		assertEquals("should be none", tier, Tier.NONE);
		
		corp = Corporation.WORLDWIDE;
		assertEquals("should be BRONZE", Tier.BRONZE, corp.getTier());
		corp = Corporation.SACKSON;		
		assertEquals("should be BRONZE", Tier.BRONZE, corp.getTier());
		
		corp = Corporation.FESTIVAL;		
		assertEquals("should be SILVER", Tier.SILVER, corp.getTier());
		corp = Corporation.IMPERIAL;
		assertEquals("should be SILVER", Tier.SILVER, corp.getTier());
		corp = Corporation.AMERICAN;
		assertEquals("should be SILVER",  Tier.SILVER, corp.getTier());
		
		corp = Corporation.CONTINENTAL;
		assertEquals("should be GOLD",  Tier.GOLD, corp.getTier());
		corp = Corporation.TOWER;
		assertEquals("should be GOLD",  Tier.GOLD, corp.getTier());
	}
	
	@Test
	public void activeCorporation() {
		Corporation corp = Corporation.UNINCORPORATED;
		
		assertFalse("Unicorporated are never active", corp.isActive());
	}

	@Test
	public void availableShares() {
		
		for(Corporation corp: Corporation.values()) {
			int totalShares = corp.getTotalShareCount();
			if (corp == Corporation.UNINCORPORATED) {
				assertEquals("Should have no shares", 0, totalShares);	
			} else {
				assertEquals("Should have 25 shares", 25, totalShares);				
			}
		}
		
	}
	
	@Test 
	public void tieredSharePrices() {
	
		//Tier tier = Tier.BRONZE;
		for (Tier tier : Tier.values()) {
			if (tier == tier.NONE) {
				continue;
			}
			
			int tierUplift = 0;
			if (tier == Tier.SILVER) {
				tierUplift = 100;
			} 
			if (tier == Tier.GOLD) {
				tierUplift = 200;
			} 
			for(int i=2; i< 7; i++ ) { 
				assertEquals((i*100) + tierUplift, tier.getSharePrice(i));
			}
			for (int i=6; i< 11; i++) {
				assertEquals(600 + tierUplift, tier.getSharePrice(i));
			}
			for (int i=11; i< 21; i++) {
				assertEquals(700 + tierUplift, tier.getSharePrice(i));
			}	
			for (int i=21; i< 31; i++) {
				assertEquals(800 + tierUplift, tier.getSharePrice(i));
			}	
			for (int i=31; i< 41; i++) {
				assertEquals(900 + tierUplift, tier.getSharePrice(i));
			}	
			for (int i=41; i< 100; i++) {
				assertEquals(1000 + tierUplift, tier.getSharePrice(i));
			}				
		}

	
	}
}
