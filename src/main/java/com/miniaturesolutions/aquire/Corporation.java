package com.miniaturesolutions.aquire;

/**
 * The corporations that are available in the game
 * @author rob
 *
 */
public enum Corporation {
	UNINCORPORATED(Tier.NONE, 0),
	WORLDWIDE(Tier.BRONZE, 25), 
	SACKSON(Tier.BRONZE, 25), 
	FESTIVAL(Tier.SILVER, 25), 
	IMPERIAL(Tier.SILVER, 25), 
	AMERICAN(Tier.SILVER, 25), 
	CONTINENTAL(Tier.GOLD, 25), 
	TOWER(Tier.GOLD, 25);	


final private Tier tier;
final private int totalNoOfShares;
	
	Corporation(Tier tier, int totalNoOfShares) {
		this.tier = tier;
		this.totalNoOfShares = totalNoOfShares;
	}
	
	/** 
	 * Used to drive the corporations stock valuations
	 * @author rob
	 *
	 */
	public enum Tier {
		NONE,
		BRONZE,
		SILVER,
		GOLD
	}

	public Tier getTier() {
		return this.tier;
	}

	public boolean isActive() {
		return false;
	}

	/** 
	 * How many shares are available
	 * @return
	 */
	public int getTotalShareCount() {
		return totalNoOfShares;
	}

}
