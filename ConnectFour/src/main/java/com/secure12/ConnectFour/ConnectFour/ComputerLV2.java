package com.secure12.ConnectFour.ConnectFour;

import java.util.Set;
import java.util.Stack;

public class ComputerLV2 extends ComputerLV1 {
	public ComputerLV2(char playerSymbol){
		super(playerSymbol);
	}
	
	@Override
	public int nextColumn(Stack<Character>[] gameBoard) {;
		int column = critical(gameBoard);
		if (column == -1){
			column = randomColumn(validColumns(gameBoard));
		}
		this.addDisc(gameBoard, column);
		return column;
	}
	
	public int critical(Stack<Character>[] gameBoard){
		Set<Integer> validColumnsSet = validColumns(gameBoard);
		if (validColumnsSet.size() == 1){
			int column = validColumnsSet.iterator().next();
			this.addDisc(gameBoard, column);
			return column;
		}
		else{
			for (int column: validColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this)){
						this.addDisc(gameBoard, column);
						return column;
					}
				}
			}
			for (int column: validColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this.oppo)){
						this.addDisc(gameBoard, column);
						return column;
					}
				}
			}
		}
		return -1;
	}
	
	private Boolean wins(Stack<Character>[] gameBoard, int column, int direction, Player player){
		char playerSymbol = player.getPlayerSymbol();
		int height = gameBoard[column].size();
		int count = 0;
		int offset;
		switch(direction){
			case 0:
				if (height < 3){
					return false;
				}
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column].get(height + offset) == playerSymbol){
					count++;
					offset--;
				}
			case 1:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column + offset].size() >= height + offset
						&& gameBoard[column + offset].get(height + offset) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7 
						&& gameBoard[column + offset].size() >= height + offset 
						&& gameBoard[column + offset].get(height + offset) == playerSymbol){
					count++;
					offset++;
				}
				break;
			case 2:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column + offset].size() >= height
						&& gameBoard[column + offset].get(height) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7
						&& gameBoard[column + offset].size() >= height
						&& gameBoard[column + offset].get(height) == playerSymbol){
					count++;
					offset++;
				}
				break;
			case 3:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column + offset].size() >= height - offset
						&& gameBoard[column + offset].get(height - offset) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7
						&& gameBoard[column + offset].size() >= height - offset
						&& gameBoard[column + offset].get(height - offset) == playerSymbol){
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
