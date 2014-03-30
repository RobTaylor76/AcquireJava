package com.miniaturesolutions.aquire;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

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
        assertEquals("Correct chain getTileCount", testChain.getTileCount(), 1);

 //       assertEquals("tile has been added to the chain", testChain, testTile.getChain());

        /* lets assume you won't add duplicates */
        testChain.addTile(testTile);
        assertEquals("Correct chain getTileCount", testChain.getTileCount(), 2);
    }

    @Test
    public void mergeChains() {

        Chain testChain1 = new Chain();
        Tile testTile1 = new Tile(0,0);
        testChain1.addTile(testTile1);

        Chain testChain2 = new Chain();
        Tile testTile2 = new Tile(1,1);
        testChain2.addTile(testTile2);

        Chain result =  testChain1.merge(testChain2);

        assertEquals("Correct chain getTileCount", result.getTileCount(), (testChain1.getTileCount() + testChain2.getTileCount()));

//        assertEquals("should be a member of this chain", result, testTile1.getChain());
//        assertEquals("should be a member of this chain", result, testTile2.getChain());

    }
    
    @Test
    public void chainOwnerShip() {
    	Chain testChain = new Chain();
    	
    	testChain.setCorporation(Corporations.UNINCORPORATED);
    	
    	assertEquals("Should be unincorporated",testChain.getCorporation(),Corporations.UNINCORPORATED);
    }
}
