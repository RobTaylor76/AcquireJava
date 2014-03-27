package com.miniaturesolutions.aquire;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rob
 * Date: 27/03/14
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class Chain {
    /**
     * Add a tile to the chain
     * @param testTile
     */
    private List<Tile> tiles = new LinkedList<>();

    public void addTile(Tile testTile) {
        tiles.add(testTile);
    }

    public int length() {
        return tiles.size();
    }


    protected void addAllTiles(Chain chain) {
        this.tiles.addAll(chain.tiles);
    }

    /** return a new chain representing the merged chains **/
    public Chain merge(Chain chainToMerge) {

        Chain mergedChain = new Chain();

        mergedChain.addAllTiles(this);
        mergedChain.addAllTiles(chainToMerge);

        return mergedChain;
    }
}
