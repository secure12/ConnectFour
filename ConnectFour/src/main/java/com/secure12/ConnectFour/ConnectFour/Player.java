package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;

public abstract class Player {
	protected char playerSymbol;
	protected HashSet<Integer> discs;
	
	public Player(char playerSymbol){
		this.playerSymbol = playerSymbol;
		this.discs = new HashSet<Integer>();
	}
	
	public char getPlayerSymbol(){
		return this.playerSymbol;
	};
	
	public HashSet<Integer> getDiscs(){
		return this.discs;
	};
	
	abstract public int nextColumn(String[] gameBoard);
}
