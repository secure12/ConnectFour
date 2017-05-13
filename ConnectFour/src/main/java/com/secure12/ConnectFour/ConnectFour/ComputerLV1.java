package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerLV1 extends Player {
	public ComputerLV1(char playerSymbol){
		super(playerSymbol);
	}
	
	public int nextColumn(String[] gameBoard) {
		int move = randomMove(gameBoard);
		addDiscs(gameBoard, move);
		return move;
	}
	
	private int randomMove(String[] gameBoard){
		Set<Integer> possibleMovesSet = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.length; i++){
			if (gameBoard[i].length() < 6){
				possibleMovesSet.add(i);
			}
		}
		Integer[] possibleMovesArray = (Integer[]) possibleMovesSet.toArray();
		Random randomizer = new Random();
		return possibleMovesArray[randomizer.nextInt(possibleMovesSet.size())];
	}
	
	private void addDiscs(String[] gameBoard, int move){
		this.discs.add(move*6 + gameBoard[move].length());
	}
}
