package com.secure12.ConnectFour.ConnectFour;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.List;

/**
 * An abstract class for all the players.
 * @author root
 *
 */
public abstract class Player {
	
	// The symbol that the player object represents
	protected char playerSymbol;
	
	// The set of Integers that contains all the indices of discs of the player object, Integer ranges from 0 to 41
	protected Set<Integer> discs;
	
	// The player object's opponent
	protected Player oppo;
	
	/**
	 * Initiation of Player object
	 * @param playerSymbol The symbol that the Player object represents
	 */
	public Player(char playerSymbol){
		this.playerSymbol = playerSymbol;
		this.discs = new HashSet<Integer>();
	}
	
	/**
	 * Get function for playerSymbol object variable
	 * @return playerSymbol of the Player object
	 */
	public char getPlayerSymbol(){
		return this.playerSymbol;
	}
	
	/**
	 * Get function for discs object variable
	 * @return discs of the Player object
	 */
	public Set<Integer> getDiscs(){
		return this.discs;
	}
	
	/**
	 * Set function for oppo object variable
	 * @param oppo The Player object's opponent to be set
	 */
	public void setOppo(Player oppo){
		this.oppo = oppo;
	}
	
	/**
	 * Get the index of the column that the player makes the move at
	 * @param gameBoard An ArrayList of Stack of Character that represents the gameBoard
	 * @return an index of a column, ranges from 0 to 6
	 */
	abstract public int nextColumn(List<Stack<Character>> gameBoard);
}
