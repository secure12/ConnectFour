package com.secure12.ConnectFour.ConnectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ComputerLV3 extends ComputerLV2 {
	private int depth;
	public ComputerLV3(char playerSymbol){
		super(playerSymbol);
		this.depth = 4;
	}
	
	@Override
	public int nextColumn(Stack<Character>[] gameBoard) {
		int col = critical(gameBoard);
		if (col != -1)
			return col;
		int v = Integer.MIN_VALUE;
		int bestColumn = 0;
		List<Integer> validColumns = new ArrayList<Integer>();
		for (int column: this.validColumns(gameBoard)){
			insert(validColumns, column);
		}
		for (int column: validColumns){
			int index = column*6 + gameBoard[column].size();
			this.discs.add(index);
			gameBoard[column].push(this.getPlayerSymbol());
			if (gameBoard[column].size() == 6){
				validColumns.remove(index);
			}
			int temp = this.alphaBeta(gameBoard, validColumns, this.depth-1, Integer.MIN_VALUE+1, Integer.MAX_VALUE, false);
			gameBoard[column].pop();
			this.discs.remove(index);
			if (!validColumns.contains(column)){
				insert(validColumns, column);
			}
			if (v < temp){
				v = temp;
				bestColumn = column;
			}
		}
		return bestColumn;
	}
	
	protected int alphaBeta(Stack<Character>[] gameBoard, List<Integer> validColumns, int depth, int alpha, int beta, Boolean myTurn){
		if (depth == 0){
			return this.heuristics_1(this);
		}
		Player player = (myTurn) ? this : this.oppo;
		char playerSymbol = player.getPlayerSymbol();
		int v = (myTurn) ? Integer.MIN_VALUE+1 : Integer.MAX_VALUE;
		for (int column: validColumns){
			int index = column*6 + gameBoard[column].size();
			player.discs.add(index);
			gameBoard[column].push(playerSymbol);
			if (gameBoard[column].size() == 6){
				validColumns.remove(column);
			}
			int temp = this.alphaBeta(gameBoard, validColumns, depth-1, alpha, beta, !myTurn);
			v = (myTurn) ? Math.max(v, temp) : Math.min(v,  temp);
			if (!validColumns.contains(column)){
				insert(validColumns, column);
			}
			gameBoard[column].pop();
			player.discs.remove(index);
			if (myTurn){
				alpha = Math.max(alpha, v);
			}
			else{
				beta = Math.min(beta, v);
			}
			if (beta <= alpha){
				break;
			}
		}
		return v;
	}
	
	protected void insert(List<Integer> validColumns, int column){
		if (column < 0 || column > 6){
			return;
		}
		final String ideal = "3241506";
		int index = ideal.indexOf('0'+column);
		for (int i = 0; i < index; i++){
			if (validColumns.contains(ideal.charAt(i) - '0')){
				index--;
			}
		}
		validColumns.add(index, column);
	}
	
	protected int heuristics_1(ComputerLV3 player){
		int h = 0;
		Set<Integer> discs = player.getDiscs();
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
