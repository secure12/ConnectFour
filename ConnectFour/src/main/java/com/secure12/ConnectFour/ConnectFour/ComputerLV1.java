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
		return ((Integer[])set.toArray())[new Random().nextInt(set.size())];
	}

}
