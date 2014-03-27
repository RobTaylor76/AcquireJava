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
			if (tier == Tier.NONE) {
				continue;
			}
			
			int tierUplift = 0;
			if (tier == Tier.SILVER) {
				tierUplift = 100;
			} 
			if (tier == Tier.GOLD) {
				tierUplift = 200;
			} 
			for(int noOfTiles=2; noOfTiles< 7; noOfTiles++ ) { 
				assertEquals((noOfTiles*100) + tierUplift, tier.getSharePrice(noOfTiles));
			}
			for (int noOfTiles=6; noOfTiles< 11; noOfTiles++) {
				assertEquals(600 + tierUplift, tier.getSharePrice(noOfTiles));
			}
			for (int noOfTiles=11; noOfTiles< 21; noOfTiles++) {
				assertEquals(700 + tierUplift, tier.getSharePrice(noOfTiles));
			}	
			for (int noOfTiles=21; noOfTiles< 31; noOfTiles++) {
				assertEquals(800 + tierUplift, tier.getSharePrice(noOfTiles));
			}	
			for (int noOfTiles=31; noOfTiles< 41; noOfTiles++) {
				assertEquals(900 + tierUplift, tier.getSharePrice(noOfTiles));
			}	
			for (int noOfTiles=41; noOfTiles< 100; noOfTiles++) {
				assertEquals(1000 + tierUplift, tier.getSharePrice(noOfTiles));
			}				
		}

	
	}

	@Test 
	public void tieredShareHolderBonus() {
	
		//Tier tier = Tier.BRONZE;
		for (Tier tier : Tier.values()) {
			if (tier == Tier.NONE) {
				continue;
			}
			
			int majorityUplift = 0;
			int minorityUplift = 0;
			if (tier == Tier.SILVER) {
				majorityUplift = 1000;
				minorityUplift = 500;
			} 
			if (tier == Tier.GOLD) {
				majorityUplift = 2000;
				minorityUplift = 1000;
			} 
			
			ShareHolderBonus bonus;
			for(int noOfTiles=2; noOfTiles< 7; noOfTiles++ ) { 
				bonus = tier.getShareHolderBonus(noOfTiles);
				assertEquals((noOfTiles*1000) + majorityUplift, bonus.getMajority());
				assertEquals((noOfTiles * 500) + minorityUplift, bonus.getMinority());
			}
			for (int noOfTiles=6; noOfTiles< 11; noOfTiles++) {
				bonus = tier.getShareHolderBonus(noOfTiles);
				assertEquals(6000 + majorityUplift, bonus.getMajority());
				assertEquals(3000 + minorityUplift, bonus.getMinority());
			}
			for (int noOfTiles=11; noOfTiles< 21; noOfTiles++) {
				bonus = tier.getShareHolderBonus(noOfTiles);
				assertEquals(7000 + majorityUplift, bonus.getMajority());
				assertEquals(3500 + minorityUplift, bonus.getMinority());
			}	
			for (int noOfTiles=21; noOfTiles< 31; noOfTiles++) {
				bonus = tier.getShareHolderBonus(noOfTiles);
				assertEquals(8000 + majorityUplift, bonus.getMajority());
				assertEquals(4000 + minorityUplift, bonus.getMinority());
			}	
			for (int noOfTiles=31; noOfTiles< 41; noOfTiles++) {
				bonus = tier.getShareHolderBonus(noOfTiles);
				assertEquals(9000 + majorityUplift, bonus.getMajority());
				assertEquals(4500 + minorityUplift, bonus.getMinority());
			}	
			for (int noOfTiles=41; noOfTiles< 100; noOfTiles++) {
				bonus = tier.getShareHolderBonus(noOfTiles);
				assertEquals(10000 + majorityUplift, bonus.getMajority());
				assertEquals(5000 + minorityUplift, bonus.getMinority());
			}				
		}

	
	}

}
