package com.miniaturesolutions.aquire;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: rob
 * Date: 27/03/14
 * Time: 17:25
 * To change this template use File | Settings | File Templates.
 */
public class ChainTest {

    @Test
    public void testChainCreation() {
        Chain testChain = new Chain();
        Tile testTile = new Tile(0,0);

        testChain.addTile(testTile);
        assertEquals("Correct chain getTileCount", 1, testChain.getTileCount());

 //       assertEquals("tile has been added to the chain", testChain, testTile.getChain());

        /* lets assume you won't add duplicates */
        testChain.addTile(testTile);
        assertEquals("Correct chain getTileCount", 2, testChain.getTileCount());
    }

    @Test
    public void mergeChains() {

        Chain testChain1 = new Chain();
        Tile testTile1 = new Tile(0,0);
        testChain1.addTile(testTile1);

        Chain testChain2 = new Chain();
        Tile testTile2 = new Tile(1,1);
        testChain2.addTile(testTile2);

        testChain1.merge(testChain2);

        assertEquals("Correct chain getTileCount", 2, testChain1.getTileCount());

//        assertEquals("should be a member of this chain", result, testTile1.getChain());
//        assertEquals("should be a member of this chain", result, testTile2.getChain());

    }
    
    @Test
    public void clearChain() {
        Chain testChain = new Chain();
        testChain.addTile(new Tile(0,0));
        assertEquals("Correct chain getTileCount", 1, testChain.getTileCount());
        testChain.clear();
        assertEquals("Correct chain getTileCount", 0, testChain.getTileCount());        
        
    }
}
