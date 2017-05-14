package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerLV1 extends Player {
	public ComputerLV1(char playerSymbol){
		super(playerSymbol);
	}
	
	public int nextColumn(String[] gameBoard) {
		int column = this.randomColumn(possibleColumns(gameBoard));
		this.addDisc(gameBoard, column);
		return column;
	}
	
	public Set<Integer> possibleColumns(String[] gameBoard){
		Set<Integer> possibleColumnsSet = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.length; i++){
			if (gameBoard[i].length() < 6){
				possibleColumnsSet.add(i);
			}
		}
		return possibleColumnsSet;
	}
	protected int randomColumn(Set<Integer> possibleColumnsSet){
		Integer[] possibleColumnsArray = (Integer[]) possibleColumnsSet.toArray();
		Random randomizer = new Random();
		return possibleColumnsArray[randomizer.nextInt(possibleColumnsSet.size())];
	}
	
	protected Boolean addDisc(String[] gameBoard, int column){
		return this.discs.add(column*6 + gameBoard[column].length());
	}
}
