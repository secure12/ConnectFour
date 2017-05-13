package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerLV1 extends Player {
	public ComputerLV1(char playerSymbol){
		super(playerSymbol);
	}
	
	public int nextColumn(String[] gameBoard) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.length; i++){
			if (gameBoard[i].length() < 6){
				set.add(i);
			}
		}
		Integer[] possibleMoves = (Integer[])set.toArray();
		Random randomizer = new Random();
		int move = possibleMoves[randomizer.nextInt(set.size())];
		this.discs.add(move*6 + gameBoard[move].length());
		return move;
	}
	
	private int randomMove(Set<Integer> possibleMoves){
		Integer[] possibleMovesArray = (Integer[]) possibleMoves.toArray();
		Random randomizer = new Random();
		return possibleMovesArray[randomizer.nextInt(possibleMoves.size())];
	}
	
	private Set<Integer> possibleMoves(){
		Set<Integer> possibleMovesSet = new HashSet<Integer>();
		for (int i = 0; i < gameBoard.length; i++){
			if (gameBoard[i].length() < 6){
				possibleMovesSet.add(i);
			}
		}
	}
}
