package com.miniaturesolutions.aquire;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

public class AquireFactoryTest {
	@Test
	public void buildInterface() {
		AquireFactory factory = mock(AquireFactory.class);
		
		List<Corporation> corporations = factory.createCorporations();
		AquireBoard boardImpl = factory.createBoard();
	}
	
	@Test
	public void testFactory() {
		AquireFactory factory = new AquireFactoryImpl();
		
		AquireBoard board = factory.createBoard();
		
		assertNotNull("have a board",board);
	
		List<Corporation> corporations = factory.createCorporations();
		
		assertEquals("should have 7", 7, corporations.size());
		for(Corporation corp: corporations) {
			assertFalse("should not be unincorporated", corp.getCorporationName() == NamedCorporation.UNINCORPORATED);
		}
	}

}
