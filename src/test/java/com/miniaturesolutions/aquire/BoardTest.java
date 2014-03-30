package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class BoardTest {

	@Test
	public void createAGameBoard() {
		Board board = new Board();
		List<Tile> tiles = board.getAvailableTiles();
		assertEquals("Should have 108 tiles", 108, tiles.size());
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
    public void getBoardState() {
    	
    	Board board = new Board();
        
    	Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);
        Tile testTile3 = new Tile(2,2);
        
        board.placeTile(testTile1);
        board.placeTile(testTile2);

        
    	Map<Tile,Corporations> state = board.getState();

    	assertEquals("unincorpoarted tiles", Corporations.UNINCORPORATED,state.get(testTile1));
    	assertEquals("unincorpoarted tiles", Corporations.UNINCORPORATED,state.get(testTile2));
    	assertNull("not on board!!!",state.get(testTile3));
    	
    }



}
