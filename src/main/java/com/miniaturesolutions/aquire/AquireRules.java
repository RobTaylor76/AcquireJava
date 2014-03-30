package com.miniaturesolutions.aquire;

public class AquireRules {
	
    /**
     * Will placing this tile cause a merge... checks adjacent squares but not diagonals...
     * @param tile
     * @return
     */
    public static boolean willTileCauseMerge(Board board, Tile tile) {
    	return  checkIfTileExists(board, tile.getColumn() > 0,  tile.getColumn()-1, tile.getRow()) ||
    			checkIfTileExists(board,tile.getColumn() < 9,  tile.getColumn()+1, tile.getRow()) ||
    			checkIfTileExists(board,tile.getRow()    > 0,  tile.getColumn(),   tile.getRow()-1) ||
    			checkIfTileExists(board,tile.getRow()    < 10, tile.getColumn(),   tile.getRow()+1);
    }

    /**
     *  Which of the 2 Chains is the winner in a merger?
     * @param chain1
     * @param chain2
     * @return winner or null if a tie
     */
	public static Chain whoWinsMerge(Chain chain1, Chain chain2) {
		Chain winner = null; // if no clear winner then we need to make a choice, just return null for now

		if (chain1.getCorporation() == Corporations.UNINCORPORATED) {
			winner = chain2;
		}
		if (chain2.getCorporation() == Corporations.UNINCORPORATED) {
			winner = chain1;
		}
		
		if (chain1.getTileCount() > chain2.getTileCount()) {
			winner = chain1;
		} else if (chain2.getTileCount() > chain1.getTileCount()) {
			winner = chain2;
		}

		return winner;
	}

    private static boolean checkIfTileExists(Board board, boolean condition, int column, int row) {
        boolean tilePresent = false;
        if (condition) {
            tilePresent = board.isTilePlaced(Tile.getTileName(column, row));              
        }
        return tilePresent;
    }


}
