package com.miniaturesolutions.aquire;

public final class ShareHolderBonus {

  final private int minority;
  final private int majority;

  public ShareHolderBonus(final int majority, final int minority) {
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
