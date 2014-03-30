package com.miniaturesolutions.aquire;

/**
 * The corporations that are available in the game
 * @author rob
 *
 */
public enum Corporations {
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
	
	Corporations(Tier tier, int totalNoOfShares) {
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
		
		/**
		 * What is the current share price based on the number of tiles in 
		 * the corporation?
		 * @param noOfTiles
		 * @return the current share price
		 */
		public int  getSharePrice(int noOfTiles) {
			int price = 0;
			if (noOfTiles < 7) {
				price = noOfTiles * 100;
			} else if (noOfTiles < 11) {
				price = 600;
			} else if (noOfTiles < 21) {
				price = 700;
			} else if (noOfTiles < 31) {
				price = 800;
			} else if (noOfTiles < 41) {
				price = 900;
			} else {
				price = 1000;
			}
			return price + sharePriceUplift;
		}

		/**
		 * What is the shareholder bonus based on the number of tiles
		 * @param noOfTiles
		 * @return
		 */
		public ShareHolderBonus getShareHolderBonus(int noOfTiles) {

			int majority = getSharePrice(noOfTiles) * 10;
			int minority = majority / 2;
		
			return new ShareHolderBonus(majority,minority);
		}
	}

	/**
	 *  What tier is associated with the Corporation
	 * @return
	 */
	public Tier getTier() {
		return this.tier;
	}

	/** 
	 * How many shares are available
	 * @return
	 */
	public int getTotalShareCount() {
		return totalNoOfShares;
	}

}