package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class BoardTest {

	@Test
	public void createAGameBoard() {
		
		Board board = new Board();
		
		List<Tile> tiles = board.getAvailableTiles();
		
		assertEquals("Should have 108 tiles", 108, tiles.size());
		
		Tile last = tiles.get(107);
	
		assertEquals("Should have 108 tiles", 108, tiles.size());
	}

    @Test
    public void getChainForCorporation() {
        //can we have multiple chains for unicorporated tiles, if not how do we track placed uninCorporated tiles

        Board board = new Board();

        Chain testChain = board.getChain(Corporation.AMERICAN);

        assertEquals("Should be an empty chain", 0 ,testChain.getTileCount());

        testChain.addTile(new Tile(0,0));

        testChain = board.getChain(Corporation.AMERICAN);

        assertEquals("Should not be an empty chain", 1 ,testChain.getTileCount());

        testChain = board.getChain(Corporation.UNINCORPORATED);

        assertNull("no chain for unincorporated", testChain);

    }

    @Test
    public void storeTilesOnBoard() {
        Board board = new Board();

        Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);

        assertFalse("tile not on board ", board.isTilePlaced(testTile1.displayedAs()));
        assertFalse("tile not on board ", board.isTilePlaced(testTile2.displayedAs()));

        board.placeTile(testTile1);
        board.placeTile(testTile2);

        assertTrue("tile on board ", board.isTilePlaced(testTile1.displayedAs()));
        assertTrue("tile on board ", board.isTilePlaced(testTile2.displayedAs()));

        assertEquals("tile matches ", testTile1, board.getTile(testTile1.displayedAs()));
        assertEquals("tile matches ", testTile2, board.getTile(testTile2.displayedAs()));
    }


    @Test
    public void willTileCauseMerge() {
        Board board = new Board();

        board.placeTile(new Tile(1,0));
        board.placeTile(new Tile(0,1));

        board.placeTile(new Tile(10,8));
        board.placeTile(new Tile(11,7));

        List<Tile> validMerges = new LinkedList<>();
        validMerges.add(new Tile(0,0));
        validMerges.add(new Tile(2,0));
        validMerges.add(new Tile(1,1));
        validMerges.add(new Tile(0,2));

        validMerges.add(new Tile(11,8));
        validMerges.add(new Tile(10,7));
        validMerges.add(new Tile(11,6));
        validMerges.add(new Tile(9,8));

        List<Tile> noMerges = new LinkedList<>();
        noMerges.add(new Tile(0,3));
        noMerges.add(new Tile(1,2));
        noMerges.add(new Tile(2,1));

        for(Tile tile: validMerges) {
            assertTrue("should trigger merge " + tile.toString(), board.willTileCauseMerge(tile));
        }

        for(Tile tile: noMerges) {
            assertFalse("should not trigger merge "+ tile.toString(), board.willTileCauseMerge(tile));
        }
    }
}
