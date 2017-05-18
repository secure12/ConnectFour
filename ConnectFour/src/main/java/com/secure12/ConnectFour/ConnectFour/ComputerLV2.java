package com.secure12.ConnectFour.ConnectFour;

import java.util.Set;
import java.util.Stack;
import java.util.List;

public class ComputerLV2 extends ComputerLV1 {
	public ComputerLV2(char playerSymbol){
		super(playerSymbol);
	}
	
	@Override
	public int nextColumn(List<Stack<Character>> gameBoard){
		int column = critical(gameBoard);
		return (column == -1) ? randomColumn(validColumns(gameBoard)) : column;
	}
	
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
