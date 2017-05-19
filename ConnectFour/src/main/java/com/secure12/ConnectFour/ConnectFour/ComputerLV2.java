package com.secure12.ConnectFour.ConnectFour;

import java.util.Set;
import java.util.Stack;
import java.util.List;

/**
 * A slightly more complex version of ComputerLV1
 * Tries to win or block in the next move
 * @author root
 *
 */
public class ComputerLV2 extends ComputerLV1 {
	
	/**
	 * Initiation of ComputerLV2 object
	 * @param playerSymbol
	 */
	public ComputerLV2(char playerSymbol){
		super(playerSymbol);
	}
	
	/**
	 * Get the index of column that player makes move at
	 * @param gameBoard
	 * @return an index of a column, ranges from 0 to 6
	 */
	@Override
	public int nextColumn(List<Stack<Character>> gameBoard){
		int column = critical(gameBoard);
		return (column == -1) ? randomColumn(validColumns(gameBoard)) : column;
	}
	
	/**
	 * Determines whether the current game state is critical
	 * A game state is critical iff a player wins after his next move at best
	 * @param gameBoard
	 * @return -1 if the game state is not critical; an index of one of the columns after the player or his opponent making the move at  
	 */
	public int critical(List<Stack<Character>> gameBoard){
		Set<Integer> validColumnsSet = validColumns(gameBoard);
		if (validColumnsSet.size() == 1){
			int column = validColumnsSet.iterator().next();
			return column;
		}
		else{
			for (int column: validColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this)){
						return column;
					}
				}
			}
			for (int column: validColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this.oppo)){
						return column;
					}
				}
			}
		}
		return -1;
	}
	
	/**
	 * Determines whether player wins after placing a disc at column column in the direction
	 * @param gameBoard
	 * @param column an index of a column, ranges from 0 to 6
	 * @param direction see wins function in the ConnectFour class
	 * @param player
	 * @return true if wins, false otherwise
	 */
	protected Boolean wins(List<Stack<Character>> gameBoard, int column, int direction, Player player){
		char playerSymbol = player.getPlayerSymbol();
		int height = gameBoard.get(column).size();
		int count = 0;
		int offset;
		switch(direction){
			case 0:
				if (height < 3){
					return false;
				}
				offset = -1;
				while (height + offset >= 0
						&& gameBoard.get(column).get(height + offset) == playerSymbol){
					count++;
					offset--;
				}
			case 1:
				offset = -1;
				while (column + offset >= 0
						&& height + offset >= 0
						&& gameBoard.get(column + offset).size() > height + offset
						&& gameBoard.get(column + offset).get(height + offset) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7 
						&& height + offset < 6
						&& gameBoard.get(column + offset).size() > height + offset 
						&& gameBoard.get(column + offset).get(height + offset) == playerSymbol){
					count++;
					offset++;
				}
				break;
			case 2:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard.get(column + offset).size() > height
						&& gameBoard.get(column + offset).get(height) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7
						&& gameBoard.get(column + offset).size() > height
						&& gameBoard.get(column + offset).get(height) == playerSymbol){
					count++;
					offset++;
				}
				break;
			case 3:
				offset = -1;
				while (column + offset >= 0
						&& height - offset < 7
						&& gameBoard.get(column + offset).size() > height - offset
						&& gameBoard.get(column + offset).get(height - offset) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7
						&& height - offset >= 0
						&& gameBoard.get(column + offset).size() > height - offset
						&& gameBoard.get(column + offset).get(height - offset) == playerSymbol){
					count++;
					offset++;
				}
				break;
			default:
				break;
		}
		return (count >= 3);
	}
}
