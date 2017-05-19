package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.List;

/**
 * A simplest computer player class inherited from Player abstract class
 * Make random moves among all valid moves.
 * @author root
 *
 */
public class ComputerLV1 extends Player {
	
	/**
	 * Initiation of ComputerLV1 object
	 * @param playerSymbol
	 */
	public ComputerLV1(char playerSymbol){
		super(playerSymbol);
	}
	
	/**
	 * Make a move randomly among all the columns that are not full
	 * 
	 * @param gameBoard An ArrayList of Stack of Characters that represents the gameBoard
	 * @return the index of column that the player makes a move at
	 */
	public int nextColumn(List<Stack<Character>> gameBoard) {
		return this.randomColumn(validColumns(gameBoard));
	}
	
	/**
	 * Get the set of indices of columns that are not full
	 * @param gameBoard
	 * @return a HashSet of Integers that only contains all indices of columns that are not full
	 */
	public Set<Integer> validColumns(List<Stack<Character>> gameBoard){
		Set<Integer> validColumnsSet = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.size(); i++){
			if (gameBoard.get(i).size() < 6){
				validColumnsSet.add(i);
			}
		}
		return validColumnsSet;
	}
	
	/**
	 * Make a random move 
	 * @param validColumnsSet a HashSet of Integers that only contains all indices of columns that are not full
	 * @return a random number among validColumnsSet
	 */
	protected int randomColumn(Set<Integer> validColumnsSet){
		Integer[] validColumnsArray = validColumnsSet.toArray(new Integer[validColumnsSet.size()]);
		Random randomizer = new Random();
		return validColumnsArray[randomizer.nextInt(validColumnsSet.size())];
	}
}
