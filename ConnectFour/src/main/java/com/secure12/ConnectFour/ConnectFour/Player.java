package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Set;

public abstract class Player {
	protected char playerSymbol;
	protected Set<Integer> discs;
	protected Player oppo;
	
	public Player(char playerSymbol){
		this.playerSymbol = playerSymbol;
		this.discs = new HashSet<Integer>();
	}
	
	public char getPlayerSymbol(){
		return this.playerSymbol;
	}
	
	public Set<Integer> getDiscs(){
		return this.discs;
	}
	
	public void setOppo(Player oppo){
		this.oppo = oppo;
	}
	
	abstract public int nextColumn(String[] gameBoard);
}
