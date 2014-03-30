package com.miniaturesolutions.aquire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class AquireRulesTest {

    @Test 
    public void resolveMerges() {
    	Chain chain1 = new Chain();
    	chain1.setCorporation(Corporations.UNINCORPORATED);
    	Chain chain2 = new Chain();
       	chain2.setCorporation(Corporations.AMERICAN);
       	
       	Chain winner = AquireRules.whoWinsMerge(chain1,chain2);    	
       	assertEquals("AMERICAN should win", chain2, winner);
        
       	chain1.setCorporation(Corporations.SACKSON);
       	
       	//chain with most tiles wins
       	for(int i=0; i<3; i++) {
       		chain1.addTile(new Tile(i,0));
       		chain2.addTile(new Tile(0,i));
       	}

       	winner = AquireRules.whoWinsMerge(chain1,chain2);    	
       	assertNull("no winner as both have same number of tiles", winner);  
       	
       	chain1.addTile(new Tile(4,0));
       	winner = AquireRules.whoWinsMerge(chain1,chain2);    	
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
            assertTrue("should trigger merge " + tile.toString(), AquireRules.willTileCauseMerge(board, tile));
        }

        for(Tile tile: noMerges) {
            assertFalse("should not trigger merge "+ tile.toString(), AquireRules.willTileCauseMerge(board,tile));
        }
    }
}
