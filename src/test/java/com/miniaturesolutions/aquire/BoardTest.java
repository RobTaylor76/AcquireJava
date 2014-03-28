package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

//import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class BoardTest {

	@Test
	public void createAGameBoard() {
		Board board = new Board();
		List<Tile> tiles = board.getAvailableTiles();
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
    public void resolveMerges() {
        Board board = new Board();
    	Chain chain1 = new Chain();
    	chain1.setCorporation(Corporation.UNINCORPORATED);
    	Chain chain2 = new Chain();
       	chain2.setCorporation(Corporation.AMERICAN);
       	
       	Chain winner = board.whoWinsMerge(chain1,chain2);    	
       	assertEquals("AMERICAN should win", chain2, winner);
        
       	chain1.setCorporation(Corporation.SACKSON);
       	
       	//chain with most tiles wins
       	for(int i=0; i<3; i++) {
       		chain1.addTile(new Tile(i,0));
       		chain2.addTile(new Tile(0,i));
       	}

       	winner = board.whoWinsMerge(chain1,chain2);    	
       	assertNull("no winner as both have same number of tiles", winner);  
       	
       	chain1.addTile(new Tile(4,0));
       	winner = board.whoWinsMerge(chain1,chain2);    	
       	assertEquals("SACKSON should win", chain1, winner);   	
    	
    }

    @Test
    public void willTileCauseMerge() {
        Board board = new Board();

        board.placeTile(new Tile(1,0));
        board.placeTile(new Tile(0,1));

        board.placeTile(new Tile(8,10));
        board.placeTile(new Tile(7,11));

        List<Tile> validMerges = new LinkedList<>();
        validMerges.add(new Tile(0,0));
        validMerges.add(new Tile(2,0));
        validMerges.add(new Tile(1,1));
        validMerges.add(new Tile(0,2));

        validMerges.add(new Tile(8,11));
        validMerges.add(new Tile(7,10));
        validMerges.add(new Tile(6,11));
        validMerges.add(new Tile(8,9));

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
