package com.miniaturesolutions.aquire;

import java.util.List;

public class TestAquireFactory implements AquireFactory {

	private AquireBoard board;
	private List<Corporation> corporations;
	private AquireAdviser adviser;
	private AquireFactory factory;
	
	public TestAquireFactory() {
		this.factory = new AquireFactoryImpl();
		this.board = factory.createBoard();
		this.corporations = factory.createCorporations();
		this.adviser = factory.createAdviser(board, corporations);
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
		return 	board;
	}
	/**
	 * Get the current AquireBoard
	 * @return
	 */
	protected AquireBoard getAquireBoard() {
		return board;
	}
	
	/**
	 * Set the current AquireBoard 
	 * 
	 * will create a new adviser pointing at the new board...
	 * 
	 * @param boardImpl
	 */
	protected void setAquireBoard(AquireBoard boardImpl) {
		this.board = boardImpl;
		this.adviser = factory.createAdviser(board, corporations);
	}
	
	/**
	 * Will return the adviser created in the constructor...
	 * (ignores the params passed in!!!)
	 * 
	 * use setAdviser if you wish to have it return one...
	 */
	@Override
	public AquireAdviser createAdviser(AquireBoard board,
			List<Corporation> corporations) {
		// TODO Auto-generated method stub
		return adviser;
	}

	/**
	 * Allows the user to override the adviser implementation for tests
	 * @param adviser
	 */
	protected void setAdviser(AquireAdviser adviser) {
		this.adviser = adviser;
	}

}
