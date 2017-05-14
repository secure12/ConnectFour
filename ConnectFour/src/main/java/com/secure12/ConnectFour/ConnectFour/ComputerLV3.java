package com.secure12.ConnectFour.ConnectFour;

import java.util.List;
import java.util.Set;

public class ComputerLV3 extends ComputerLV2 {
	public ComputerLV3(char playerSymbol){
		super(playerSymbol);
	}
	
	@Override
	public int nextColumn(String[] gameBoard) {
		int column = critical(gameBoard);
		if (column != -1)
			return column;
		
	}
	
	protected int alphaBeta(String[] gameBoard, List<Integer> validColumns, int depth, int alpha, int beta, Boolean myTurn){
		if (depth == 0){
			return this.heuristics_1(gameBoard);
		}
		if (myTurn){
			int v = -1;
			for (int column: validColumns){
				int index = column*6 + gameBoard[column].length();
				this.discs.add(index);
				gameBoard[column] += this.getPlayerSymbol();
				if (gameBoard[column].length() == 6){
					validColumns.remove(column);
				}
				v = Math.max(v, this.alphaBeta(gameBoard, validColumns, depth-1, alpha, beta, false));
				if (!validColumns.contains(column)){
					validColumns.
				}
			}
		}
	}
	
	protected int heuristics_1(String[] gameBoard){
		int h = 0;
		Set<Integer> discs = this.getDiscs();
		for (int disc: discs){
			if (disc%6 != 5 && discs.contains(disc+1)){
				h++;
			}
			int strat = disc%6 - disc/6;
			if (strat < 3 
				&& strat > -4 
				&& disc%6 != 5 
				&& discs.contains(disc+7)){
				h++;
			}
			if (disc/6 != 6 && discs.contains(disc+6)){
				h++;
			}
			strat = disc%6 + disc/6;
			if (strat > 2
				&& strat < 9
				&& disc%6 != 0
				&& discs.contains(disc+5)){
				h++;
			}
		}
		return h;
	}
}
