package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class ComputerLV1 extends Player {
	public ComputerLV1(char playerSymbol){
		super(playerSymbol);
	}
	
	public int nextColumn(Stack<Character>[] gameBoard) {
		int column = this.randomColumn(validColumns(gameBoard));
		this.addDisc(gameBoard, column);
		return column;
	}
	
	public Set<Integer> validColumns(Stack<Character>[] gameBoard){
		Set<Integer> validColumnsSet = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.length; i++){
			if (gameBoard[i].size() < 6){
				validColumnsSet.add(i);
			}
		}
		return validColumnsSet;
	}
	protected int randomColumn(Set<Integer> validColumnsSet){
		Integer[] validColumnsArray = (Integer[]) validColumnsSet.toArray();
		Random randomizer = new Random();
		return validColumnsArray[randomizer.nextInt(validColumnsSet.size())];
	}
	
	protected Boolean addDisc(Stack<Character>[] gameBoard, int column){
		return this.discs.add(column*6 + gameBoard[column].size());
	}
}
