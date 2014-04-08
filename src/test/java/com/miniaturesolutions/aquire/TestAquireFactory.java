package com.miniaturesolutions.aquire;

import java.util.List;

public class TestAquireFactory implements AquireFactory {

	private AquireBoard boardImpl;
	private List<Corporation> corporations;
	
	public TestAquireFactory() {
		AquireFactory factory = new AquireFactoryImpl();
		boardImpl = factory.createBoard();
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
	public AquireBoard createBoard() {
		return 	boardImpl;
	}
	/**
	 * Get the current AquireBoard
	 * @return
	 */
	protected AquireBoard getAquireBoard() {
		return boardImpl;
	}
	
	/**
	 * Set the current AquireBoard 
	 * @param boardImpl
	 */
	protected void setAquireBoard(AquireBoard boardImpl) {
		this.boardImpl = boardImpl;
	}

}
