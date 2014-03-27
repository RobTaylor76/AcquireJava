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
		NONE(0),
		BRONZE(0),
		SILVER(100),
		GOLD(200);
		
		final private int sharePriceUplift;

		private Tier(int sharePriceUplift) {
			this.sharePriceUplift = sharePriceUplift;
		}
		public int  getSharePrice(int noOfActiveShares) {
			int price = 0;
			if (noOfActiveShares < 7) {
				price = noOfActiveShares * 100;
			} else if (noOfActiveShares < 11) {
				price = 600;
			} else if (noOfActiveShares < 21) {
				price = 700;
			} else if (noOfActiveShares < 31) {
				price = 800;
			} else if (noOfActiveShares < 41) {
				price = 900;
			} else {
				price = 1000;
			}
			return price + sharePriceUplift;
		}
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
