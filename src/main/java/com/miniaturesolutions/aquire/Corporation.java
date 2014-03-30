package com.miniaturesolutions.aquire;

public class Corporation {

	private int availableShares;
	private Chain chain = new Chain();
	/** 
	 * Creates a corporation based on the defined corporations
	 * @param unincorporated
	 */
	public Corporation(Corporations def) {
		this.availableShares = def.getTotalShareCount();
	}

	public int getRemainingShareCount() {
		return this.availableShares;
	}

	public Chain getChain() {
		return chain;
	}

}
