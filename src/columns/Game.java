package columns;

import java.io.File;
import java.io.IOException;

import util.SingleLinkedList;

public class Game {
	public static final int NUMBER_OF_COLUMNS = 5;
	public static final int INITIAL_NUMBER_COUNT = 6;

	public static boolean emptyBox = true;
	
	private static SingleLinkedList[] columns = new SingleLinkedList[NUMBER_OF_COLUMNS];
	private static SingleLinkedList box = new SingleLinkedList();
	private static SingleLinkedList drawingbox = new SingleLinkedList();
		
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
	public static SingleLinkedList getDrawingbox() {

		return drawingbox;
	}

	public static SingleLinkedList getColumn(int index) {
		return columns[index];
	}

	private static void initialize() {
		// Initialize single linked lists in the `columns` array.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++)
			columns[i] = new SingleLinkedList();

		fillAndShuffleBox();
		distributeNumbersToColumns();

		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			columns[i].display(i);
		}
	}

	private static void fillAndShuffleBox() {
		int count = 0;
		int randomno = 0;
		
		while (count < 30)  // filling the list of game table
		{
			randomno = (int) (Math.random() * 10 + 1);
			if(box.counting(randomno) != 3)
			{
				box.add(randomno);
				count++;
			}
		}
		box.shuffling();
		count = 0;
		while(count < 50)  // filling the list of box
		{
			randomno = (int) (Math.random() * 10 + 1);
			if(drawingbox.counting(randomno) != 5)
			{
				drawingbox.add(randomno);
				count++;
			}
		}
		drawingbox.shuffling();
	}
	
	private static void distributeNumbersToColumns() {
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			for (int j = 0; j < INITIAL_NUMBER_COUNT; j++) {
				columns[i].add(box.returnHead());
				box.removeNodeWithPosition(0);
			}
		}
	}
	
	public static void drawNumberFromBox() 
	{
		if (drawingbox.size() != 0) // box empty boolean will be added here when transferring operations are done 
		{
			Display.displayBox(drawingbox.returnHead());
			drawingbox.removeNodeWithPosition(0);
			
		} 
		else 
		{
			// The box is empty, number 0 prints an
			// empty box.
			Display.displayBox(0);
		}
	}
}
