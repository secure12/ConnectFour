package com.secure12.ConnectFour.ConnectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class ComputerLV3 extends ComputerLV2 {
	
	private int depth;
	private int mode;
	
	public ComputerLV3(char playerSymbol, int mode){
		super(playerSymbol);
		this.depth = 4;
		this.mode = mode;
	}
	
	@Override
	public int nextColumn(List<Stack<Character>> gameBoard) {
		int col = critical(gameBoard);
		if (col != -1)
			return col;
		int v = Integer.MIN_VALUE;
		int bestColumn = 0;
		List<Integer> validColumns = new ArrayList<Integer>();
		List<Integer> temp = new ArrayList<Integer>();
		for (int column: this.validColumns(gameBoard)){
			insert(validColumns, column);
			insert(temp, column);
		}
		for (int column: temp){
			int index = column*6 + gameBoard.get(column).size();
			this.discs.add(index);
			gameBoard.get(column).push(this.getPlayerSymbol());
			if (gameBoard.get(column).size() == 6){
				validColumns.remove((Integer)index);
			}
			int h = this.alphaBeta(gameBoard, validColumns, this.depth-1, Integer.MIN_VALUE+1, Integer.MAX_VALUE, false);
			gameBoard.get(column).pop();
			this.discs.remove(index);
			if (!validColumns.contains(column)){
				insert(validColumns, column);
			}
			if (v < h){
				v = h;
				bestColumn = column;
			}
		}
		return bestColumn;
	}
	
	protected int alphaBeta(List<Stack<Character>> gameBoard, List<Integer> validColumns, int depth, int alpha, int beta, Boolean myTurn){
		if (depth == 0){
			switch(this.mode){
				case 1:
					return this.heuristics_1();
				case 2:
					return this.heuristics_2(gameBoard);
			}
		}
		Player player = (myTurn) ? this : this.oppo;
		char playerSymbol = player.getPlayerSymbol();
		int v = (myTurn) ? Integer.MIN_VALUE+1 : Integer.MAX_VALUE;
		List<Integer> temp = new ArrayList<Integer>();
		for (int column: validColumns){
			temp.add(column);
		}
		for (int column: temp){
			int index = column*6 + gameBoard.get(column).size();
			player.discs.add(index);
			gameBoard.get(column).push(playerSymbol);
			if (gameBoard.get(column).size() == 6){
				validColumns.remove((Integer)column);
			}
			int h = this.alphaBeta(gameBoard, validColumns, depth-1, alpha, beta, !myTurn);
			v = (myTurn) ? Math.max(v, h) : Math.min(v,  h);
			if (!validColumns.contains(column)){
				insert(validColumns, column);
			}
			gameBoard.get(column).pop();
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
		for (int i = index-1; i >= 0; i--){
			if (!validColumns.contains(ideal.charAt(i) - '0')){
				index--;
			}
		}
		validColumns.add(index, column);
	}
	
	protected int heuristics_1(){
		int h = 0;
		Set<Integer> discs = this.getDiscs();
		for (int column = 0; column < 7; column++)
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
	
	protected int heuristics_2(List<Stack<Character>> gameBoard){
		int h = 0;
		Set<Integer> validColumnsSet = validColumns(gameBoard);
		if (validColumnsSet.size() == 1){
			int column = validColumnsSet.iterator().next();
			return column;
		}
		else{
			for (int column: validColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this)){
						h += 3;
					}
				}
			}
			for (int column: validColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this.oppo)){
						h -= 2;
					}
				}
			}
		}
		return h;
	}
}
