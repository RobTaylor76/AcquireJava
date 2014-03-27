package com.miniaturesolutions.aquire;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: rob
 * Date: 27/03/14
 * Time: 16:40
 * To change this template use File | Settings | File Templates.
 */
public class TileTest {

    @Test
    public void testTile() {
        // Create a Tile... assume zero based index...    0,0 = 1A   1,0 = 1B , 0,1 = 2A
        // (int column, int row)
        Tile testTile = new Tile(0,0);

        assertEquals("should have correct column",testTile.getColumn(),0);
        assertEquals("should have correct row",testTile.getRow(),0);

        testTile = new Tile(1,0);

        assertEquals("should have correct column",testTile.getColumn(),1);
        assertEquals("should have correct row",testTile.getRow(),0);

        testTile = new Tile(0,1);

        assertEquals("should have correct column",testTile.getColumn(),0);
        assertEquals("should have correct row",testTile.getRow(),1);

    }

    @Test
    public void testTileName() {
        // Create a Tile... assume zero based index...    0,0 = 1A   1,0 = 1B , 0,1 = 2A
        // (int column, int row)
        Tile testTile = new Tile(0,0);

        String tileName = testTile.toString();
        assertTrue("Correct Tile Name", tileName.equalsIgnoreCase("1A"));

        assertTrue("Correct Tile Name", Tile.getTileName(0,0).equalsIgnoreCase("1A"));

        testTile = new Tile(1,0);

        tileName = testTile.toString();
        assertTrue("Correct Tile Name", tileName.equalsIgnoreCase("1B"));

        tileName =   Tile.getTileName(1,0);
        assertTrue("Correct Tile Name", tileName.equalsIgnoreCase("1B"));

        testTile = new Tile(0,1);

        tileName =  testTile.toString();
        assertTrue("Correct Tile Name", tileName.equalsIgnoreCase("2A"));

        tileName =   Tile.getTileName(0,1);
        assertTrue("Correct Tile Name", tileName.equalsIgnoreCase("2A"));

        tileName =   Tile.getTileName(8,11);
        assertTrue("Correct Tile Name", tileName.equalsIgnoreCase("12I"));
     }
}
