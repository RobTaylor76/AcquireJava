package com.miniaturesolutions.aquire;

import java.util.List;

public class TestAquireFactory implements AquireFactory {

	private Board board;
	private List<Corporation> corporations;
	
	public TestAquireFactory() {
		AquireFactory factory = new AquireFactoryImpl();
		board = factory.createBoard();
		corporations = factory.createCorporations();
		
	}
	/**
	 * Will return the same corporations every time...
	 */
	@Override
	public List<Corporation> createCorporations() {
		return corporations;
	}

	/**
	 * will return the same board every time
	 */
	@Override
	public Board createBoard() {
		return 	board;
	}

}
