package com.secure12.ConnectFour.ConnectFour;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.List;

/**
 * 	This is the main class of ConnectFour project.
 * 
 *	@author secure12
 *	@version v1.0
 */
public class ConnectFour {
	static List<Stack<Character>> gameBoard;
	static Boolean end;
	static Set<Integer> winDiscs;
	static Player turn, player1, player2;
	static int step;
	
	/**
	 * 	A function that prints a ConnectFour game board, with inverse text in end game.
	 */
	private static void printGameBoard(){
		System.out.println("-----------------------------");
		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
		System.out.println("-----------------------------");
		for (int i = 5; i >= 0; i--){
			for (int j = 0; j < 7; j++){
				try{
					if (!end || !winDiscs.contains(i+6*j)){
						System.out.printf("| %c ", gameBoard.get(j).get(i));
					}
					else{
						System.out.printf("|%s %c %s", "\u001B[7m", gameBoard.get(j).get(i), "\u001B[0m");
					}
				}
				catch (ArrayIndexOutOfBoundsException e){
					System.out.print ("|   ");
				}
			}
			System.out.println('|');
			System.out.println("-----------------------------");
		}
	}
	
	/**
	 * 	A function for choose players by user prompt and set the opponents for the players
	 */
	private static void choosePlayer(){
		player1 = choosePlayer(1);
		player2 = choosePlayer(2);
		player1.setOppo(player2);
		player2.setOppo(player1);
	}
	
	/**
	 * A function for setting players and the opponents of the players
	 * @param p1 A Player object representing player with player symbol 'O'
	 * @param p2 A Player object representing player with player symbol 'X'
	 */
	private static void choosePlayer(Player p1, Player p2){
		player1 = p1;
		player2 = p2;
		player1.setOppo(p2);
		player2.setOppo(p1);
	}
	
	/**
	 * A function for choosing the n-th player where n = 1 or 2
	 * @param n The number of player user is going to choose, ranges from 1 to 2
	 * @return A Player object
	 */
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
				return new ComputerLV3(playerSymbol, 2);
			default:
				out.println("Human!");
				return new Human(playerSymbol);
		}
	}
	
	/**
	 * A function that initiates a move for player
	 * @param player The player object that is going to make a move
	 * @return The index of disc that the player just made, ranges from 0 to 41
	 */
	private static int move(Player player){
		char playerSymbol = player.getPlayerSymbol();
		System.out.printf("Player %c's turn.\n", playerSymbol);
		int column;
		while (true){
			try{
				column = player.nextColumn(gameBoard);
				break;
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.println(e);
				continue; 
			}
		}
		int move = column*6 + gameBoard.get(column).size();
		gameBoard.get(column).push(playerSymbol);
		System.out.printf("\nPlayer %c has made the move[%d]!\n", playerSymbol, column+1);
		player.getDiscs().add(move);
		return move;
	}
	
	/**
	 * A function that flip the turn variable of class ConnectFour
	 */
	private static void flipTurn(){
		turn = (turn == player1) ? player2: player1;
	}
	
	/**
	 * A function that checks whether player wins the round AFTER making a move
	 * Also add move and the discs that make the win to the class variable winDiscs for printing
	 * @param player A Player object
	 * @param move The index of disc that the player just made, ranges from 0 to 41
	 * @return True if wins, false otherwise
	 */
	private static Boolean checkWin(Player player, int move){
		for (int i = 0; i < 4; i++){
			Set<Integer> temp = wins(player, move, i);
			if (temp != null){
				winDiscs.addAll(temp);
				winDiscs.add(move);
			}
		}
		return !winDiscs.isEmpty();
	}
	
	/**
	 * A function that checks whether player wins the round in a particular direction AFTER making a move
	 * @param player A Player object
	 * @param move The index of disc that the player just made, ranges from 0 to 41
	 * @param direction 0 for vertical, 1 for bottom-left to upper-right, 2 for horizontal, 3 for upper-left to bottom-right, ranges from 0 to 3
	 * @return A set of discs that make the win in that direction corresponding with parameter move, if wins; null otherwise
	 */
	private static Set<Integer> wins(Player player, final int move, final int direction){
		Set<Integer> discs = player.getDiscs();
		Set<Integer> temp = new HashSet<Integer>();
		int count = 0;
		int offset;
		switch(direction){
			case 0:
				if (move%6 < 3){
					return null;
				}
				offset = -1;
				while ((move + offset)%6 != 5
						&& discs.contains(move+offset)){
					count++;
					temp.add(move + offset);
					offset--;
				}
				break;
			case 1:
				if (move%6 - move/6 >= 3
					|| move%6 - move/6 < -3){
					return null;
				}
				offset = -7;
				while ((move + offset)%6 != 5
						&& (move + offset) >= 0
						&& discs.contains(move + offset)){
					count++;
					temp.add(move + offset);
					offset -= 7;
				}
				offset = 7;
				while ((move + offset)%6 != 0
						&& (move + offset) < 42 
						&& discs.contains(move + offset)){
					count++;
					temp.add(move + offset);
					offset += 7;
				}
				break;
			case 2:
				offset = -6;
				while ((move + offset) >= 0
						&& discs.contains(move + offset)){
					count++;
					temp.add(move + offset);
					offset -= 6;
				}
				offset = 6;
				while ((move + offset) < 42
						&& discs.contains(move + offset)){
					count++;
					temp.add(move + offset);
					offset += 6;
				}
				break;
			case 3:
				offset = -5;
				if (move%6 + move/6 < 3
					|| move%6 + move/6 >= 9){
					return null;
				}
				while ((move + offset)%6 != 0
						&& (move + offset) >= 0
						&& discs.contains(move + offset)){
					count++;
					temp.add(move + offset);
					offset -= 5;
				}
				offset = 5;
				while ((move + offset)%6 != 5
						&& (move + offset) < 42
						&& discs.contains(move + offset)){
					count++;
					temp.add(move + offset);
					offset += 5;
				}
				break;
			default:
				break;
		}
		return (count >= 3) ? temp: null;
	}
	
	/**
	 * A function that starts the game by going into a loop, until either player wins or the board is filled up, i.e. step==42
	 */
	public static void startGame(){
		while (!end && step < 42){
			printGameBoard();
			if (checkWin(turn, move(turn))){
				end = true;
			}
			else{
				flipTurn();
			}
			step++;
		}
	}

	/**
	 * A function that resets all the related variables. player class variables are assumed to be set, so it must be called after creating players.
	 */
	public static void initGame(){
		gameBoard = new ArrayList<Stack<Character>>();
		for (int i = 0; i < 7; i++){
			gameBoard.add(i, new Stack<Character>());
		}
		end = false;
		step = 0;
		winDiscs = new HashSet<Integer>();
		turn = player1;
		player1.getDiscs().clear();
		player2.getDiscs().clear();
	}
	
	/**
	 * A function that print the result of a game. Assumed to be called after startGame() function.
	 */
	public static void printResult(){
		printGameBoard();
		if (end){
			System.out.printf("Player %c wins the game!\n", turn.getPlayerSymbol());
		}
		else{
			if (step == 42){
				System.out.println("Draw!");
			}
		}
	}
	
	/**
	 * The main function that controls the flow of game.
	 * @param args Not used
	 */
	public static void main(String[] args){
		choosePlayer();
		initGame();
		startGame();
		printResult();
		
		/*
		int x=0, y=0;
		final int games = 1000;
		choosePlayer(new ComputerLV2('O'), new ComputerLV3('X', 2));
		for (int i = 0; i < games; i++){
			initGame();
			startGame();
			if (end){
				if (turn == player1){
					x++;
				}
				else{
					y++;
				}
			}
		}
		System.out.printf("Player %c wins: %d\n", player1.getPlayerSymbol(), x);
		System.out.printf("Player %c wins: %d\n", player2.getPlayerSymbol(), y);
		System.out.printf("Draws: %d", games-x-y);*/
	}
}
