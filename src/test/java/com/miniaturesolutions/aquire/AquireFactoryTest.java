package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

public class AquireFactoryTest {
	@Test
	public void buildInterface() {
		AquireFactory factory = mock(AquireFactory.class);
		
		List<CorporationImpl> corporations = factory.createCorporations();
		Board board = factory.createBoard();
	}
	
	@Test
	public void testFactory() {
		AquireFactory factory = new AquireFactoryImpl();
		
		Board board = factory.createBoard();
		
		assertNotNull("have a board",board);
	
		List<CorporationImpl> corporations = factory.createCorporations();
		
		assertEquals("should have 7", 7, corporations.size());
		for(CorporationImpl corp: corporations) {
			assertFalse("should not be unincorporated", corp.getCorporation() == Corporation.UNINCORPORATED);
		}
	}

}
