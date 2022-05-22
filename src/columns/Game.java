package columns;

import java.io.File;
import java.io.IOException;

import util.SingleLinkedList;

public class Game {
	public static final int NUMBER_OF_COLUMNS = 5;
	public static final int INITIAL_NUMBER_COUNT = 6;
	
	public static boolean emptyBox = true;
	public static int lastboxnumber = 0;
	
	private static SingleLinkedList[] columns = new SingleLinkedList[NUMBER_OF_COLUMNS];
	private static SingleLinkedList box = new SingleLinkedList();
		
	public static void main(String[] args) throws IOException {
		File f = new File("highscore.txt");
		HighScore hs = new HighScore(f);
		hs.display();
		hs.AddtoHighScore("Player 1",378.6);
		hs.display();
		Soundpl.initialize();
		Game.initialize();
		Cursor.initialize();
		Display.initialize();
	}

	public static SingleLinkedList getBox() {

		return box;
	}

	public static SingleLinkedList getColumn(int index) {
		return columns[index];
	}

	private static void initialize() {
		// Initialize single linked lists in the `columns` array.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++)
			columns[i] = new SingleLinkedList();

		fillAndShuffleBox(30);
		distributeNumbersToColumns(30);
		fillAndShuffleBox(50);

		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			columns[i].display(i);
		}
	}

	private static void fillAndShuffleBox(int number) {
		int count = 0;
		int randomno = 0;
		
		while(count < number)  // filling the list of box
		{
			randomno = (int) (Math.random() * 10 + 1);
			if(box.counting(randomno) != 5)
			{
				box.add(randomno);
				count++;
			}
			
			box.shuffling();
		}
		box.shuffling();
	}
	
	private static void distributeNumbersToColumns(int number) {
		int randomno = 0;
		int count = 0;
		while(number > count)
		{
			randomno = (int) (Math.random() * 5);
			columns[randomno].add(box.returnHead());
			box.removeNodeWithPosition(0);
			count++;
		}	
	}
	
	public static void drawNumberFromBox() 
	{
		if (box.size() != 0 && emptyBox) // box empty boolean will be added here when transferring operations are done 
		{
			Display.displayBox(box.returnHead());
			lastboxnumber = box.returnHead();
			box.removeNodeWithPosition(0);
			emptyBox = false;
			
		} 
		else if(!emptyBox)
		{
			Display.displayBox(lastboxnumber);
		}
		else
		{
			// The box is empty, number 0 prints an
			// empty box.
			Display.displayBox(0);
		}
	}
	/*
	public static void drawNumberFromBox(int column) 
	{
		if (box.size() != 0 && emptyBox) // box empty boolean will be added here when transferring operations are done 
		{
			Display.displayBox(box.returnHead());
			lastboxnumber = box.returnHead();
			box.removeNodeWithPosition(0);
			emptyBox = false;
			
		} 
		else if(!emptyBox)
		{
			Game.getColumn(column).add(lastboxnumber);
			Display.displayBox(0);
			emptyBox = true;
		}
		else
		{
			// The box is empty, number 0 prints an
			// empty box.
			Display.displayBox(0);
		}
	}
	*/
}
