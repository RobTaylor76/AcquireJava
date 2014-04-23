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

    	Corporation corp = new CorporationImpl(NamedCorporation.CONTINENTAL);
    	
        Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);

        assertFalse("tile not on board ", boardImpl.isTilePlaced(testTile1.displayedAs()));
        assertFalse("tile not on board ", boardImpl.isTilePlaced(testTile2.displayedAs()));

        boardImpl.placeTile(testTile1, corp);
        boardImpl.placeTile(testTile2, corp);

        assertTrue("tile on board ", boardImpl.isTilePlaced(testTile1.displayedAs()));
        assertTrue("tile on board ", boardImpl.isTilePlaced(testTile2.displayedAs()));
        
        Entry<Tile,Corporation> placedTile;
        
        placedTile =  boardImpl.getTile("XX");
        assertNull("tile doesnt exist ", placedTile);
        
        placedTile =  boardImpl.getTile(testTile1.displayedAs());

        assertEquals("tile matches ", testTile1, placedTile.getKey());
        assertEquals("corporation matches ", corp, placedTile.getValue());

        placedTile =  boardImpl.getTile(testTile2.displayedAs());
        
        assertEquals("tile matches ", testTile2, placedTile.getKey());
    }
    
    @Test
    public void getBoardState() {
    	CorporationImpl corp = new CorporationImpl(NamedCorporation.CONTINENTAL);

        
    	Tile testTile1 = new Tile(0,0);
        Tile testTile2 = new Tile(6,6);
        Tile testTile3 = new Tile(2,2);
        
        boardImpl.placeTile(testTile1, corp);
        boardImpl.placeTile(testTile2, corp);

        
    	Map<Tile,NamedCorporation> state = boardImpl.getState();

    	assertEquals("unincorpoarted tiles", NamedCorporation.CONTINENTAL,state.get(testTile1));
    	assertEquals("unincorpoarted tiles", NamedCorporation.CONTINENTAL,state.get(testTile2));
    	assertNull("not on board!!!",state.get(testTile3)); 	
    }
    
	
    @Test
    public void getAffectedTiles() {
    	CorporationImpl corp = new CorporationImpl(NamedCorporation.CONTINENTAL);

    	boardImpl.placeTile(new Tile(1,0), corp);
		boardImpl.placeTile(new Tile(0,1), corp);

		boardImpl.placeTile(new Tile(8,10), corp);
		boardImpl.placeTile(new Tile(7,11), corp);  

        List<Tile> testTiles = new LinkedList<>();
        testTiles.add(new Tile(0,0)) ;
        testTiles.add(new Tile(1,1)) ;

        for(Tile tile :testTiles) {
            List<Entry<Tile, Corporation>> tileList = boardImpl.getAffectedTiles(tile);
            assertEquals("should have 2 tiles", 2, tileList.size());

            Set<Tile> tiles = new HashSet<>();
            for(Entry<Tile, Corporation> entry :tileList) {
                tiles.add(entry.getKey());
            }
            assertTrue("Contains the correct tile", tiles.contains(new Tile(1,0)));
            assertTrue("Contains the correct tile", tiles.contains(new Tile(0,1)));
            assertFalse("doesnt contain other tiles", tiles.contains(new Tile(8,10)));
        }

        List<Entry<Tile, Corporation>> tileList = boardImpl.getAffectedTiles(new Tile(8,11));
        assertEquals("should have 2 tiles", 2, tileList.size());

        Set<Tile> tiles = new HashSet<>();
        for(Entry<Tile, Corporation> entry :tileList) {
            tiles.add(entry.getKey());
        }
        assertTrue("Contains the correct tile", tiles.contains(new Tile(8,10)));
        assertTrue("Contains the correct tile", tiles.contains(new Tile(7,11)));
        assertFalse("doesnt contain other tiles", tiles.contains(new Tile(1,0)));

    }

    @Test
    public void getAffectedCorporations() {
    	Corporation corp1 = new CorporationImpl(NamedCorporation.CONTINENTAL);
		boardImpl.placeTile(new Tile(1,0), corp1);
		
	   	Corporation corp2 = new CorporationImpl(NamedCorporation.IMPERIAL);
		boardImpl.placeTile(new Tile(0,1), corp2);

		List<Corporation> affectedCorporations = boardImpl.getAffectedCorporations(new Tile(0,0));
		
		assertTrue("should contain corp1", affectedCorporations.contains(corp1));
		assertTrue("should contain corp2", affectedCorporations.contains(corp2));    	
    }
    
	@Test
	public void willTileCauseMerge() {
    	Corporation corp = new CorporationImpl(NamedCorporation.CONTINENTAL);

		boardImpl.placeTile(new Tile(1,0), corp);
		boardImpl.placeTile(new Tile(0,1), corp);

		boardImpl.placeTile(new Tile(8,10), corp);
		boardImpl.placeTile(new Tile(7,11), corp);  
		
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
