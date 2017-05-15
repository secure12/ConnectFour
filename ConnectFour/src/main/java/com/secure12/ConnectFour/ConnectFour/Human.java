package com.secure12.ConnectFour.ConnectFour;

import java.util.Scanner;
import java.util.Stack;
import java.util.List;

public class Human extends Player{
	public Human(char playerSymbol){
		super(playerSymbol);
	}

	public int nextColumn(List<Stack<Character>> gameBoard){
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
					System.out.printf("The column [%d] is full.\n", column+1);
				}
				else{
					reader.close();
					return column;
				}
			}
		}
	}
}