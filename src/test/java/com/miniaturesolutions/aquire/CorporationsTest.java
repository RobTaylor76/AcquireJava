package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import org.junit.Test;

import com.miniaturesolutions.aquire.Corporations.Tier;

public class CorporationsTest {
	
	@Test
	public void tieredCorporations() {
		Corporations corp = Corporations.UNINCORPORATED;
		Tier tier = corp.getTier();	
		assertEquals("should be none", tier, Tier.NONE);
		
		corp = Corporations.WORLDWIDE;
		assertEquals("should be BRONZE", Tier.BRONZE, corp.getTier());
		corp = Corporations.SACKSON;		
		assertEquals("should be BRONZE", Tier.BRONZE, corp.getTier());
		
		corp = Corporations.FESTIVAL;		
		assertEquals("should be SILVER", Tier.SILVER, corp.getTier());
		corp = Corporations.IMPERIAL;
		assertEquals("should be SILVER", Tier.SILVER, corp.getTier());
		corp = Corporations.AMERICAN;
		assertEquals("should be SILVER",  Tier.SILVER, corp.getTier());
		
		corp = Corporations.CONTINENTAL;
		assertEquals("should be GOLD",  Tier.GOLD, corp.getTier());
		corp = Corporations.TOWER;
		assertEquals("should be GOLD",  Tier.GOLD, corp.getTier());
	}
	

	@Test
	public void availableShares() {
		
		for(Corporations corp: Corporations.values()) {
			int totalShares = corp.getTotalShareCount();
			if (corp == Corporations.UNINCORPORATED) {
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
