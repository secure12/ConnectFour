package com.secure12.ConnectFour.ConnectFour;

import java.util.Set;

public class ComputerLV2 extends ComputerLV1 {
	public ComputerLV2(char playerSymbol){
		super(playerSymbol);
	}
	
	@Override
	public int nextColumn(String[] gameBoard) {;
		int column = critical(gameBoard);
		if (column == -1){
			column = randomColumn(possibleColumns(gameBoard));
		}
		this.addDisc(gameBoard, column);
		return column;
	}
	
	public int critical(String[] gameBoard){
		Set<Integer> possibleColumnsSet = possibleColumns(gameBoard);
		if (possibleColumnsSet.size() == 1){
			int column = possibleColumnsSet.iterator().next();
			this.addDisc(gameBoard, column);
			return column;
		}
		else{
			for (int column: possibleColumnsSet){
				for (int direction = 0; direction < 4; direction++){
					if (wins(gameBoard, column, direction, this)){
						this.addDisc(gameBoard, column);
						return column;
					}
				}
			}
			for (int column: possibleColumnsSet){
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
	
	private Boolean wins(String[] gameBoard, int column, int direction, Player player){
		char playerSymbol = player.getPlayerSymbol();
		int height = gameBoard[column].length();
		int count = 0;
		int offset;
		switch(direction){
			case 0:
				if (height < 3){
					return false;
				}
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column].charAt(height + offset) == playerSymbol){
					count++;
					offset--;
				}
			case 1:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column + offset].length() >= height + offset
						&& gameBoard[column + offset].charAt(height + offset) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7 
						&& gameBoard[column + offset].length() >= height + offset 
						&& gameBoard[column + offset].charAt(height + offset) == playerSymbol){
					count++;
					offset++;
				}
				break;
			case 2:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column + offset].length() >= height
						&& gameBoard[column + offset].charAt(height) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7
						&& gameBoard[column + offset].length() >= height
						&& gameBoard[column + offset].charAt(height) == playerSymbol){
					count++;
					offset++;
				}
				break;
			case 3:
				offset = -1;
				while (column + offset >= 0
						&& gameBoard[column + offset].length() >= height - offset
						&& gameBoard[column + offset].charAt(height - offset) == playerSymbol){
					count++;
					offset--;
				}
				offset = 1;
				while (column + offset < 7
						&& gameBoard[column + offset].length() >= height - offset
						&& gameBoard[column + offset].charAt(height - offset) == playerSymbol){
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
