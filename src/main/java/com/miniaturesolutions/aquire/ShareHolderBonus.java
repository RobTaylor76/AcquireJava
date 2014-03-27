package com.miniaturesolutions.aquire;

public class ShareHolderBonus {
	
	final private int minority;
	final private int majority;

	public ShareHolderBonus(int majority, int minority) {
		this.minority = minority;
		this.majority = majority;
	}

	public int getMinority() {
		return minority;
	}

	public int getMajority() {
		return majority;
	}
}
