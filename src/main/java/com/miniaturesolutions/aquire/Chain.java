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
    
    private NamedCorporation owner;

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


    /** return a new chain representing the merged chains **/
    public Chain merge(Chain chainToMerge) {

        Chain mergedChain = new Chain();

        mergedChain.addAllTiles(this);
        mergedChain.addAllTiles(chainToMerge);

        return mergedChain;
    }


	/**
	 * Set the chains owning corporation
	 * @param owner
	 */
	public void setCorporation(NamedCorporation owner) {
		this.owner = owner;
	}
	
	/**
	 * Who owns the chain
	 * @return
	 */
	public NamedCorporation getCorporation() {
		return owner;
	}

    protected void addAllTiles(Chain chain) {
        for(Tile tile: chain.tiles) {
            addTile(tile);
        }
    }
}
