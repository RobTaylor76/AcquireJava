package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

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
}
