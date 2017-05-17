package com.secure12.ConnectFour.ConnectFour;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.List;

public class ConnectFour {
	static List<Stack<Character>> gameBoard;
	static Boolean end;
	static Set<Integer> winDiscs;
	static Player turn, player1, player2;
	
	private static void printGameBoard(List<Stack<Character>> gameBoard){
		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
		System.out.println("-----------------------------");
		if (!end){
			for (int i = 5; i >= 0; i--){
				for (int j = 0; j < 7; j++){
					try{
						System.out.printf("| %c ", gameBoard.get(j).get(i));
					}
					catch (ArrayIndexOutOfBoundsException e){
						System.out.print ("|   ");
					}
				}
				System.out.println('|');
				System.out.println("-----------------------------");
			}
		}
		else{
		}
	}
	private static Player choosePlayer(int n){
		int index = n - 1;
		int choice = 0;
		PrintStream out = System.out;
		do {
			out.println();
			out.printf ("Please choose the %s player:\n", (index == 0) ? "first" : "second");
			out.println("1. Computer LV 1");
			out.println("2. Computer LV 2");
			out.println("3. Computer LV 3");
			out.println("4. Human");
			Scanner scan = new Scanner(System.in);
			out.print  ("Your choice is[1-4]: ");
			try{
				choice = scan.nextInt() - 1;
				if (choice < 0 || choice > 3){
					out.println("Please choose between [1-4]!");
					continue;
				}
			}
			catch (java.util.InputMismatchException e){
				out.println("Please input an integer!");
				continue;
			}
		} while(false);
		char playerSymbol = (index == 0) ? 'O' : 'X';
		out.printf("Player %c is ", playerSymbol);
		switch (choice){
			case 0:
				out.println("Computer LV 1!");
				return new ComputerLV1(playerSymbol);
			case 1:
				out.println("Computer LV 2!");
				return new ComputerLV2(playerSymbol);
			case 2:
				out.println("Computer LV 3!");
				return new ComputerLV3(playerSymbol);
			default:
				out.println("Human!");
				return new Human(playerSymbol);
		}
	}
	
	private static int move(Player player){
		char playerSymbol = player.getPlayerSymbol();
		System.out.printf("Player %c's turn.\n", playerSymbol);
		int column = player.nextColumn(gameBoard);
		gameBoard.get(column).push(playerSymbol);
		System.out.printf("\nPlayer %c has made the move[%d]!\n", playerSymbol, column);
		int temp = column*6 + gameBoard.get(column).size();
		player.discs.add(temp);
		return temp;
	}
	
	private static void flipTurn(){
		turn = (turn == player1) ? player2: player1;
	}
	
	private static Boolean checkWin(Player player, int move){
		for (int i = 0; i < 4; i++){
			if (wins(player, move, i)){
				return true;
			}
		}
		return false;
	}
	
	private static Boolean wins(Player player, final int move, final int direction){
		Set<Integer> discs = player.getDiscs();
		int count = 0;
		int offset;
		switch(direction){
			case 0:
				if (move%6 < 3){
					return false;
				}
				offset = -1;
				while ((move + offset)%6 != 5
						&& discs.contains(move+offset)){
					count++;
					offset--;
					if ((move + offset)%6 == 0) break;
				}
			case 1:
				if (move%6 - move/6 >= 3
					|| move%6 - move/6 < -3){
					return false;
				}
				offset = -7;
				while ((move + offset)%6 != 5
						&& (move + offset) >= 0
						&& discs.contains(move + offset)){
					count++;
					offset -= 7;
				}
				offset = 7;
				while ((move + offset)%6 != 0
						&& (move + offset) < 42 
						&& discs.contains(move + offset)){
					count++;
					offset += 7;
				}
				break;
			case 2:
				offset = -6;
				while ((move + offset) >= 0
						&& discs.contains(move + offset)){
					count++;
					offset -= 6;
				}
				offset = 6;
				while ((move + offset) < 42
						&& discs.contains(move + offset)){
					count++;
					offset += 6;
				}
				break;
			case 3:
				offset = -5;
				if (move%6 + move/6 < 3
					|| move%6 + move/6 >= 9){
					return false;
				}
				while ((move + offset)%6 != 0
						&& (move + offset) >= 0
						&& discs.contains(move + offset)){
					count++;
					offset -= 5;
				}
				offset = 5;
				while ((move + offset)%6 != 5
						&& (move + offset) < 42
						&& discs.contains(move + offset)){
					count++;
					offset += 5;
				}
				break;
			default:
				break;
		}
		return (count >= 3);
	}
	
	public static void main(String[] args){
		gameBoard = new ArrayList<Stack<Character>>(Collections.nCopies(7, new Stack<Character>()));
		end = false;
		winDiscs = new HashSet<Integer>();
		player1 = choosePlayer(1);
		player2 = choosePlayer(2);
		player1.setOppo(player2);
		player2.setOppo(player1);
		turn = player1;
		while (!end){
			printGameBoard(gameBoard);
			if (checkWin(turn, move(turn))){
				end = true;
			}
			else{
				flipTurn();
			}
		}
		printGameBoard(gameBoard);
		System.out.printf("Player %c wins the game!\n", turn.getPlayerSymbol());
	}
}
