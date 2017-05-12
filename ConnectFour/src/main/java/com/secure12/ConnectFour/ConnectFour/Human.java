package com.secure12.ConnectFour.ConnectFour;

import java.util.Scanner;

public class Human extends Player{
	public Human(char playerSymbol){
		super(playerSymbol);
	}

	public int nextColumn(String[] gameBoard){
		int move;
		Scanner reader = new Scanner(System.in);
		while (true){
			try{
				System.out.println("Make a move[1-7]:");
				move = reader.nextInt();
			}
			catch(java.util.InputMismatchException e){
				System.out.println("Please enter an integer.");
				continue;
			}
			if (move < 1 || move > 7){
				System.out.println("Please enter an integer within [1-7].");
			}
			else{
				move--;
				if (gameBoard[move].length() >= 6){
					System.out.printf("The column [%d] is full.\n", move+1);
				}
				else{
					reader.close();
					return move;
				}
			}
		}
	}
}