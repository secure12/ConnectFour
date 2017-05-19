package com.secure12.ConnectFour.ConnectFour;

import java.util.Scanner;
import java.util.Stack;
import java.util.List;

/**
 * The human player class inherited from abstract class Player
 * @author root
 *
 */
public class Human extends Player{
	
	/**
	 * Initiation of Human player object
	 * @param playerSymbol
	 */
	public Human(char playerSymbol){
		super(playerSymbol);
	}
	
	/**
	 * Read an input from the user to get the next column of this Player object
	 * @throws ArrayIndexOutOfBoundsException
	 * 
	 * @param gameBoard An ArrayList of Stack of Characters that represents the game board
	 * @return the index of the column that the human player is going to make the move at
	 */
	public int nextColumn(List<Stack<Character>> gameBoard) throws ArrayIndexOutOfBoundsException{
		int column;
		Scanner reader = new Scanner(System.in);
		while (true){
			try{
				System.out.println("Make a move[1-7]:");
				column = reader.nextInt();
			}
			catch(java.util.InputMismatchException e){
				System.out.println("Please enter an integer.");
				continue;
			}
			if (column < 1 || column > 7){
				System.out.println("Please enter an integer within [1-7].");
			}
			else{
				column--;
				if (gameBoard.get(column).size() >= 6){
					throw new ArrayIndexOutOfBoundsException(String.format("The column [%d] is full.", column+1));
				}
				else{
					return column;
				}
			}
		}
	}
}