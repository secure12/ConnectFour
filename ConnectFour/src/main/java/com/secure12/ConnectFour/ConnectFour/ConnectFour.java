package com.secure12.ConnectFour.ConnectFour;

import java.util.Arrays;
import java.util.HashSet;

public class ConnectFour {
	static String[] gameBoard;
	static Boolean end;
	static HashSet<Integer> winDiscs;
	private void printGameBoard(){
		System.out.println("| 1 | 2 | 3 | 4 | 5 | 6 | 7 |");
		System.out.println("-----------------------------");
		if (!end){
			for (int i = 5; i >= 0; i--){
				for (int j = 0; j < 7; j++){
					System.out.printf("| %c ", gameBoard[j].charAt(i));
				}
				System.out.println('|');
				System.out.println("-----------------------------");
			}
		}
		else{
		}
	}
	private void choosePlayer(){
		// TODO: choosePlayer();
	}
	private void move(){
		// TODO: move();
	}
	private void flipTurn(){
		// TODO: flipTurn();
	}
	private void checkWin(){
		// TODO: checkWin();
	}
	public static void main(String[] args){
		gameBoard = new String[7];
		Arrays.fill(gameBoard, "");
		end = false;
		winDiscs = new HashSet<Integer>();
		System.out.println("\u001B[31m a ");
		// TODO: startGame();
		player1.setOppo(player2);
		player2.setOppo(player1);
	}
}
