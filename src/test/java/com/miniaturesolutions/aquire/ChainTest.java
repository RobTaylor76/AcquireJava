package com.miniaturesolutions.aquire;

import org.junit.Test;

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

        assertEquals("Correct chain length", testChain.length(), 1);

        /* lets assume you won't add duplicates */
        testChain.addTile(testTile);
        assertEquals("Correct chain length", testChain.length(), 2);
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

        assertEquals("Correct chain length", result.length(), (testChain1.length() + testChain2.length()));
    }
}