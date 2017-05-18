package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.List;
public class ComputerLV1 extends Player {
	
	public ComputerLV1(char playerSymbol){
		super(playerSymbol);
	}
	
	public int nextColumn(List<Stack<Character>> gameBoard) {
		return this.randomColumn(validColumns(gameBoard));
	}
	
	public Set<Integer> validColumns(List<Stack<Character>> gameBoard){
		Set<Integer> validColumnsSet = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.size(); i++){
			if (gameBoard.get(i).size() < 6){
				validColumnsSet.add(i);
			}
		}
		return validColumnsSet;
	}
	
	protected int randomColumn(Set<Integer> validColumnsSet){
		Integer[] validColumnsArray = validColumnsSet.toArray(new Integer[validColumnsSet.size()]);
		Random randomizer = new Random();
		return validColumnsArray[randomizer.nextInt(validColumnsSet.size())];
	}
}
