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
 
    /**
     * Takes ownership of tile
     * @param tile
     */
    public void addTile(Tile tile) {
        tiles.add(tile);
//        tile.setChain(this);
    }

    /**
     * How many tiles in chain
     * @return
     */
    public int getTileCount() {
        return tiles.size();
    }


    /** adds the chain to merge to the current chain **/
    public void merge(Chain chainToMerge) {

   //     Chain mergedChain = new Chain();

   //     mergedChain.addAllTiles(this);
        this.addAllTiles(chainToMerge);

   //     return mergedChain;
   	}

    protected void addAllTiles(Chain chain) {
        for(Tile tile: chain.tiles) {
            addTile(tile);
        }
    }

	public void clear() {
		tiles.clear();	
	}
}
