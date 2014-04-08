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

	
	private AquireBoard boardImpl;
	
	@Before
	public void doSetup() {
    	boardImpl = new BoardImpl();
	}
	
	@Test
	public void createAGameBoard() {
		List<Tile> tiles = boardImpl.getAvailableTiles();
		assertEquals("Should have 108 tiles", 108, tiles.size());
	}

    @Test
    public void storeTilesOnBoard() {

        Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);

        assertFalse("tile not on board ", boardImpl.isTilePlaced(testTile1.displayedAs()));
        assertFalse("tile not on board ", boardImpl.isTilePlaced(testTile2.displayedAs()));

        boardImpl.placeTile(testTile1);
        boardImpl.placeTile(testTile2);

        assertTrue("tile on board ", boardImpl.isTilePlaced(testTile1.displayedAs()));
        assertTrue("tile on board ", boardImpl.isTilePlaced(testTile2.displayedAs()));
        
        Entry<Tile,Corporation> placedTile;
        
        placedTile =  boardImpl.getTile("XX");
        assertNull("tile doesnt exist ", placedTile);
        
        placedTile =  boardImpl.getTile(testTile1.displayedAs());

        assertEquals("tile matches ", testTile1, placedTile.getKey());
        assertEquals("corporation matches ", NamedCorporation.UNINCORPORATED, placedTile.getValue().getCorporationName());

        placedTile =  boardImpl.getTile(testTile2.displayedAs());
        
        assertEquals("tile matches ", testTile2, placedTile.getKey());
    }
    
    @Test
    public void getBoardState() {

        
    	Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);
        Tile testTile3 = new Tile(2,2);
        
        boardImpl.placeTile(testTile1);
        boardImpl.placeTile(testTile2);

        
    	Map<Tile,NamedCorporation> state = boardImpl.getState();

    	assertEquals("unincorpoarted tiles", NamedCorporation.UNINCORPORATED,state.get(testTile1));
    	assertEquals("unincorpoarted tiles", NamedCorporation.UNINCORPORATED,state.get(testTile2));
    	assertNull("not on board!!!",state.get(testTile3)); 	
    }
    
	
    @Test
    public void getMergeTiles() {
		boardImpl.placeTile(new Tile(1,0));
		boardImpl.placeTile(new Tile(0,1));

		boardImpl.placeTile(new Tile(8,10));
		boardImpl.placeTile(new Tile(7,11));  

        List<Tile> testTiles = new LinkedList<>();
        testTiles.add(new Tile(0,0)) ;
        testTiles.add(new Tile(1,1)) ;

        for(Tile tile :testTiles) {
            List<Entry<Tile,Corporation>> tileList = boardImpl.getAffectedTiles(tile);
            assertEquals("should have 2 tiles", 2, tileList.size());

            Set<Tile> tiles = new HashSet<>();
            for(Entry<Tile,Corporation> entry :tileList) {
                tiles.add(entry.getKey());
            }
            assertTrue("Contains the correct tile", tiles.contains(new Tile(1,0)));
            assertTrue("Contains the correct tile", tiles.contains(new Tile(0,1)));
            assertFalse("doesnt contain other tiles", tiles.contains(new Tile(8,10)));
        }

        List<Entry<Tile,Corporation>> tileList = boardImpl.getAffectedTiles(new Tile(8,11));
        assertEquals("should have 2 tiles", 2, tileList.size());

        Set<Tile> tiles = new HashSet<>();
        for(Entry<Tile,Corporation> entry :tileList) {
            tiles.add(entry.getKey());
        }
        assertTrue("Contains the correct tile", tiles.contains(new Tile(8,10)));
        assertTrue("Contains the correct tile", tiles.contains(new Tile(7,11)));
        assertFalse("doesnt contain other tiles", tiles.contains(new Tile(1,0)));

    }
    
	@Test
	public void willTileCauseMerge() {
		
		boardImpl.placeTile(new Tile(1,0));
		boardImpl.placeTile(new Tile(0,1));

		boardImpl.placeTile(new Tile(8,10));
		boardImpl.placeTile(new Tile(7,11));  
		
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
        noMerges.add(new Tile(1,11));
        noMerges.add(new Tile(8,1));
        noMerges.add(new Tile(5,1));
        noMerges.add(new Tile(5,0));

		for(Tile tile: validMerges) {
			assertTrue("should trigger merge " + tile.toString(), boardImpl.willTileCauseMerger(tile));
		}

		for(Tile tile: noMerges) {
			assertFalse("should not trigger merge "+ tile.toString(), boardImpl.willTileCauseMerger(tile));
		}
	}	

    
}
