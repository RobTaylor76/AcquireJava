package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	
	private Board board;
	
	@Before
	public void doSetup() {
    	board = new Board();
	}
	
	@Test
	public void createAGameBoard() {
		List<Tile> tiles = board.getAvailableTiles();
		assertEquals("Should have 108 tiles", 108, tiles.size());
	}

    @Test
    public void storeTilesOnBoard() {

        Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);

        assertFalse("tile not on board ", board.isTilePlaced(testTile1.displayedAs()));
        assertFalse("tile not on board ", board.isTilePlaced(testTile2.displayedAs()));

        board.placeTile(testTile1);
        board.placeTile(testTile2);

        assertTrue("tile on board ", board.isTilePlaced(testTile1.displayedAs()));
        assertTrue("tile on board ", board.isTilePlaced(testTile2.displayedAs()));
        
        Entry<Tile,Corporation> placedTile;
        
        placedTile =  board.getTile("XX");
        assertNull("tile doesnt exist ", placedTile);
        
        placedTile =  board.getTile(testTile1.displayedAs());

        assertEquals("tile matches ", testTile1, placedTile.getKey());
        assertEquals("corporation matches ", Corporation.UNINCORPORATED, placedTile.getValue());

        placedTile =  board.getTile(testTile2.displayedAs());
        
        assertEquals("tile matches ", testTile2, placedTile.getKey());
    }
    
    @Test
    public void getBoardState() {

        
    	Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);
        Tile testTile3 = new Tile(2,2);
        
        board.placeTile(testTile1);
        board.placeTile(testTile2);

        
    	Map<Tile,Corporation> state = board.getState();

    	assertEquals("unincorpoarted tiles", Corporation.UNINCORPORATED,state.get(testTile1));
    	assertEquals("unincorpoarted tiles", Corporation.UNINCORPORATED,state.get(testTile2));
    	assertNull("not on board!!!",state.get(testTile3)); 	
    }
    
	
    @Test
    public void getMergeTiles() {
		board.placeTile(new Tile(1,0));
		board.placeTile(new Tile(0,1));

		board.placeTile(new Tile(8,10));
		board.placeTile(new Tile(7,11));  
		
    	List<Entry<Tile,Corporation>> tileList = board.getAffectedMergerTiles(new Tile(0,0));
    	assertEquals("should have 2 tiles", 2, tileList.size());
    	
    	Set<Tile> tiles = new HashSet<>();
    	for(Entry<Tile,Corporation> entry :tileList) {
    		tiles.add(entry.getKey());		
    	}
    	assertTrue("Contains the correct tile", tiles.contains(new Tile(1,0)));
    	assertTrue("Contains the correct tile", tiles.contains(new Tile(0,1)));
    	assertFalse("doesnt contain other tiles", tiles.contains(new Tile(8,10)));
    }
}
