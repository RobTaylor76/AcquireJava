package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;

import org.junit.Test;

public class CorportationTest {

	@Test
	public void createCorporation() {
		
		for(Corporations def : Corporations.values()) {
			Corporation newCorp = new Corporation(def);
			assertEquals("Should have correct number of shares", def.getTotalShareCount(), 
																	newCorp.getRemainingShareCount());
		}		
	}
}
