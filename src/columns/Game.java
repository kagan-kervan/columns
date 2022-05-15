package columns;


import java.io.IOException;

import util.SingleLinkedList;

public class Game {
	public static final int NUMBER_OF_COLUMNS = 5;
	public static final int INITIAL_NUMBER_COUNT= 6;

	private static SingleLinkedList[] columns = new SingleLinkedList[NUMBER_OF_COLUMNS];
	private static SingleLinkedList box = new SingleLinkedList();
	
	public static void main(String[] args) throws IOException {
		Game.initialize();
		Cursor.initialize();
		Display.initialize();
	}
	
	public static SingleLinkedList getBox() 
	{
		
		return box;
	}
	
	public static SingleLinkedList getColumn(int index) 
	{
		return columns[index];
	}

	private static void initialize() 
	{
		// Initialize single linked lists in the `columns` array.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++)
			columns[i] = new SingleLinkedList();
		
		fillAndShuffleBox();
		distributeNumbersToColumns();

		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) 
		{
			columns[i].display(i);
		}
	}

	private static void fillAndShuffleBox() 
	{
		int count = 0;
		int [] usedno = new int[10];
		for(int i = 0; i < usedno.length;i++) usedno[i] = 0;
		int randomno = 0;
		
		for (int a = 0; a < 50; a++)  // if their indexes are 1, it means they are used numbers. if zero, not used yet.
		{
			while(randomno == 0 && count < 10)
			{
				randomno = (int)(Math.random() * 10 + 1);
				for(int i = 0; i < usedno.length;i++)
				{
					if(i == randomno - 1 && usedno[i] == 0)
					{
						for(int b = 0; b < 5;b++) box.add(randomno);;
						usedno[i] = 1;
						count++;
					}
				}
				randomno = 0;
			}
		}
		
		String sS = "";
	}

	private static void distributeNumbersToColumns() {
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			for (int j = 0; j < INITIAL_NUMBER_COUNT; j++) {
				columns[i].add(box.returnHead());
				box.removeNodeWithPosition(0);
			}
		}
	}
}