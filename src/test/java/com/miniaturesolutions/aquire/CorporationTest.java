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
}
