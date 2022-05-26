package columns;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import enigma.console.TextAttributes;
import enigma.console.TextWindow;
import enigma.core.Enigma;
import util.SingleLinkedList;
import util.CardNode;
import util.ColumnNode;
import util.MultiLinkedList;

public class Game {
	static final int NUMBER_OF_COLUMNS = 5;
	static final int INITIAL_NUMBER_COUNT = 6;
	
	KeyListener keyListener;
	public static int console_x = 80;
	public static int console_y = 40;
	boolean emptyBox = true;
	int lastboxnumber = 0;
	int playerscore = 0;
	int FinishedDeckStartingPoint; // Finished deck's starting point in the column.
	int transfers = 0;
	int finishedSets = 0;
	
	MultiLinkedList columns = new MultiLinkedList();
	SingleLinkedList box = new SingleLinkedList();
		
	public Game() throws FileNotFoundException {
		
		// Load sound files
		try {
			File cardSoundFile = new File("cardsound.wav");
			cardSoundClip = AudioSystem.getClip();
			cardSoundClip.open(AudioSystem.getAudioInputStream(cardSoundFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File cardSoundFile2 = new File("cardtransferringsound.wav");
			cardSoundClipTransfer = AudioSystem.getClip();
			cardSoundClipTransfer.open(AudioSystem.getAudioInputStream(cardSoundFile2));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Initialize single linked lists in the `columns` array.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			 columns.AddColumn(i); 
		}

		fillAndShuffleBox(50);
		Main.Cleaning();
		distributeNumbersToColumns();		
		
		window = Enigma.getConsole("Columns", console_x, console_y, 20).getTextWindow();

		for (int i = 0; i < NUMBER_OF_COLUMNS; i++)
			displayColumn(i);
		
		// Start the cursor from the first non-empty column.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			if (getColumn(i).getSize() > 0) {
				column = i;
				break;
			}
		}

		keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();

				if (selectionMode) {
					switch (keyCode) {
					case KeyEvent.VK_LEFT:
						selectColumn(false);
						break;
					case KeyEvent.VK_RIGHT:
						selectColumn(true);
						break;
					case KeyEvent.VK_X:
						boolean transferred;
						if (transferFromBox) {
 							transferred = transferLastNumberFromBox(destinationColumn);
						} else {
							transferred = transferNumbers(column, row, destinationColumn);	
 						}
						
						
						if (finishedSets == 5)
							exitGame();
						else if (transferred)
							exitSelectionMode();

						playSoundClip(cardSoundClipTransfer);
						break;
					case KeyEvent.VK_Z, KeyEvent.VK_B:
						exitSelectionMode();
						break;
					case KeyEvent.VK_E:
						exitGame();
						break;
					}

				} else {
					switch (keyCode) {
					case KeyEvent.VK_UP:
						moveCursorVertical(true);
						break;
					case KeyEvent.VK_DOWN:
						moveCursorVertical(false);
						break;
					case KeyEvent.VK_LEFT:
						moveCursorHorizontal(true);
						break;
					case KeyEvent.VK_RIGHT:
						moveCursorHorizontal(false);
						break;
					case KeyEvent.VK_B: 
						if (lastboxnumber == 0) {
							drawNumberFromBox();
						} else {
							transferFromBox = true;
							enterSelectionMode();
						}
						break;
					case KeyEvent.VK_Z:
						transferFromBox = false;
						enterSelectionMode();
						break;
					case KeyEvent.VK_E:
						exitGame();
						break;
					}					
				}
			}
		};

		window.addKeyListener(keyListener);
		
		displayTransfersAndScore();
		displayBox(0);
		displayColumnNumbers(0, 0, new TextAttributes(Color.RED));
	}

	private ColumnNode getColumn(int index) {
		ColumnNode column = columns.getHead();
		if (column == null) return null;

		for (int i = 0; i < index; i++)
			column = column.getDown();
		
		return column;
	}
	
	private boolean transferLastNumberFromBox(int destinationColumnIndex) {
		//if (box.size() == 0) return false;
		
		ColumnNode destination = getColumn(destinationColumnIndex);
		
		if (destination.getRight() == null) {
			if (!(lastboxnumber == 1 || lastboxnumber == 10)) return false;
			destination.setRight(new CardNode(lastboxnumber));
		} else {
			CardNode lastNode = destination.getRight();
			
			while (lastNode.getNext() != null)
				lastNode = lastNode.getNext();
			
			int lastNumber = (int) lastNode.getData();
			
			if (Math.abs(lastNumber - lastboxnumber) > 1) return false;
			
			lastNode.setNext(new CardNode(lastboxnumber));
		}

		lastboxnumber = 0;
		emptyBox = true;
		transfers++;
		
		displayColumn(destinationColumnIndex);
		displayBox(0);
		displayTransfersAndScore();
		
		return true;
	}
	
	private boolean transferNumbers(int sourceColumnIndex, int rowIndex, int destinationColumnIndex) {
		if (sourceColumnIndex == destinationColumnIndex) return false;
		
		ColumnNode source = getColumn(sourceColumnIndex);
		ColumnNode destination = getColumn(destinationColumnIndex);
		
		if (source.getSize() == 0) return false;

		CardNode top = source.getRight();
		CardNode nodeBeforeTop = null;
		
		for (int i = 0; i < rowIndex; i++) {
			nodeBeforeTop = top;
			top = top.getNext();
		}
		
		int topNumber = (int) top.getData();
		
		if (destination.getRight() == null) {
			if (!(topNumber == 1 || topNumber == 10)) return false;
			
			destination.setRight(top);
			
			if (nodeBeforeTop == null) {
				source.setRight(null);
			} else {
				nodeBeforeTop.setNext(null);
			}
		} else {
			CardNode lastNode = destination.getRight();
			
			while (lastNode.getNext() != null)
				lastNode = lastNode.getNext();
			
			int lastNumber = (int) lastNode.getData();
			
			if (Math.abs(lastNumber - topNumber) > 1) return false;

			lastNode.setNext(top);
			
			if (nodeBeforeTop == null) {
				source.setRight(null);
			} else {
				nodeBeforeTop.setNext(null);
			}
		}
		
		// Checks if there is any completed decks in the columns.
		if (isDeckCompleted(destinationColumnIndex)) {
			// Gives the score to the player.
			playerscore += 1000;
			finishedSets++;
			
			for (int i = 1; i <= 10; i++) {
				// Deletes the finished deck.
				columns.DeleteFromtheFinishedDeck(destinationColumnIndex, i, FinishedDeckStartingPoint);
			}
		}

		transfers++;

		displayColumn(sourceColumnIndex);
		displayColumn(destinationColumnIndex);
		displayTransfersAndScore();

		return true;
	}
	
	private boolean isDeckCompleted(int ColumnIndex) 
	{
		ColumnNode column = getColumn(ColumnIndex);
		CardNode card = column.getRight();
		FinishedDeckStartingPoint=0; 
		int count = 0;
		while(card !=null) 
		{
			while(card.getNext()!=null && (Integer)card.getData()!=1 && (Integer)card.getData()!=10) { // Finds the starting
				card=card.getNext();																// point of the deck.	
				FinishedDeckStartingPoint ++;
			}
			if(card.getNext() == null)
				return false;
			int startingnumber = (int) card.getData(); // Gets the starting number.
			CardNode temp = card;
			if(startingnumber==10) {
				while(temp!=null) {
					if((int)temp.getData()==startingnumber) {
						count ++;                    // Increase the count.
						startingnumber --;         // Decrease the number that uses in the matching deck.
					}
					else {
						count = 0;   //Reset the count.
						startingnumber = 10;
						break;
					}
					temp=temp.getNext();
				}
			}
			else {
				while(temp!=null) {
					if((int)temp.getData()==startingnumber) {
						count ++;             // Increase the count.
						startingnumber ++;      // Increase the number that uses in the matching deck.
					}
					else {
						count = 0;       // Resets the count.
						startingnumber = 1;
						break;
					}
					temp=temp.getNext();
				}
			}
			if(count == 10)
				return true;  
			card=card.getNext();
			
		}
		return false;
	}
	
	private void fillAndShuffleBox(int number) {
		int count = 0;
		int randomno = 0;
		
		while(count < number)  // filling the list of box
		{
			do {
			randomno = (int) (Math.random() * 10 + 1);
			}while(box.search(randomno)!=false);
			for (int i = 0; i < 5; i++) {
				box.add(randomno);
				count ++;
				box.shuffling();
			}
			box.shuffling();
		}
		box.shuffling();
	}
	
	private void distributeNumbersToColumns() {

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				columns.AddCard(j, box.returnHead());
				box.removeCardNodeWithPosition(0);
			}
		}
	}
	
	private void drawNumberFromBox() {
		// Box empty boolean will be added here when transferring operations are done 
		if (box.size() != 0 && emptyBox) {
			displayBox(box.returnHead());
			lastboxnumber = box.returnHead();
			box.removeCardNodeWithPosition(0);
			emptyBox = false;
			playSoundClip(cardSoundClip);
		} else if(emptyBox) {
			// The box is empty, number 0 prints an empty box.
			displayBox(0);
		}
	}
	
	// ------------------------------------------------------ SOUND RELATED CODE -------------------------------------------------------------
		
	private Clip cardSoundClip;
	private Clip cardSoundClipTransfer;

	private void playSoundClip(Clip sound) {
		sound.setFramePosition(0);
		sound.start();
	}
	
	// ------------------------------------------------------ CURSOR RELATED CODE -------------------------------------------------------------

	private int column = 0;
	private int row = 0;
	private boolean selectionMode = false;
	private boolean transferFromBox = false;
	private int destinationColumn = 0;
	
	private void enterSelectionMode() {
		selectionMode = true;
		destinationColumn = column;
		displayColumnTitle(destinationColumn, new TextAttributes(Color.GREEN));
	}
	
	private void exitSelectionMode() {
		selectionMode = false;
		displayColumnTitle(destinationColumn, new TextAttributes(Color.WHITE));
	}
		
	private void selectColumn(boolean selectNext) {
		displayColumnTitle(destinationColumn, new TextAttributes(Color.WHITE));
		destinationColumn += NUMBER_OF_COLUMNS + (selectNext ? 1 : -1);
		destinationColumn %= NUMBER_OF_COLUMNS;
		displayColumnTitle(destinationColumn, new TextAttributes(Color.GREEN));
	}

	/**
	 * Moves the cursor vertically. If the limit is exceeded, the cursor starts from
	 * the opposite-end.
	 */
	private void moveCursorVertical(boolean moveUp) {
		int columnSize = getColumn(column).getSize();
		
		if (columnSize == 0) return;
		
		displayColumnNumbers(column, row, new TextAttributes(Color.WHITE));

		// Move the current row
		row += columnSize + (moveUp ? -1 : 1);
		row %= columnSize;

		displayColumnNumbers(column, row, new TextAttributes(Color.RED));
	}

	/**
	 * Moves the cursor horizontally. If the limit is exceeded, the cursor starts
	 * from the opposite-end.
	 * <p>
	 * This method will skip the empty columns.
	 * <p>
	 * If the `row` is bigger than the next column's size, the `row` starts from the
	 * last number of the next column.
	 */
	private void moveCursorHorizontal(boolean moveLeft) {
		displayColumnNumbers(column, row, new TextAttributes(Color.WHITE));

		// Search for a non-empty column.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			column += NUMBER_OF_COLUMNS + (moveLeft ? -1 : 1);
			column %= NUMBER_OF_COLUMNS;
			int columnSize = getColumn(column).getSize();

			// If a non-empty column is found, select it.
			if (columnSize > 0) {
				// If the row is bigger than the next column's,
				// size, start it from the last element of the next
				// column.
				if (row >= columnSize)
					row = columnSize - 1;

				break;
			}
		}

		displayColumnNumbers(column, row, new TextAttributes(Color.RED));
	}
	
	// ------------------------------------------------------ DISPLAY RELATED CODE -------------------------------------------------------------

	// Listen for key events.
	private TextWindow window;

	// Global margins, any screen element will obey these margins.
	private static final int MARGIN_TOP = 1;
	private static final int MARGIN_LEFT = 1;

	// The margin between two columns, and two rows.
	private static final int COLUMN_MARGIN = 3;
	
	// The total size of the columns' display area.
	private static final int COLUMN_AREA_WIDTH = 2 + (2 + COLUMN_MARGIN) * (NUMBER_OF_COLUMNS - 1);

	// Margins of the status titles.
	private static final int STATUS_MARGIN_LEFT = 5;
	private static final int STATUS_MARGIN_TOP = 0;

	// Margins of the box.
	private static final int BOX_MARGIN_LEFT = 5;
	private static final int BOX_MARGIN_TOP = 2;
	
	private void exitGame() {
		String blankLine = " ".repeat(window.getColumns());
		
		for (int i = 0; i < window.getRows() - 1; i++)
			displayString(blankLine, 0, i);
		
		float endGameScore = 100.0f * finishedSets + ((float) playerscore / transfers);
		displayString("The game has ended. End-Game Score: " + endGameScore, 0, 0);
		File f = new File("highscore.txt");  //Read the file
		try {
			HighScore hs = new HighScore(f);
			hs.AddtoHighScore("Player", (double)endGameScore);  //Add the player to the high score table.
			try {
				hs.WritingtoFile();   // Writing to the high score file.
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		window.removeKeyListener(keyListener);
	}
	
	private void displayColumnTitle(int column, TextAttributes attributes) {
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * column;
		displayString("C" + (column + 1), horizontalOffset, MARGIN_TOP, attributes);
		displayString("--", horizontalOffset, MARGIN_TOP + 1, attributes);	
	}
	
	private void displayColumnNumbers(int columnIndex, int topNumberIndex, TextAttributes attributes) {
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * columnIndex;

		ColumnNode column = getColumn(columnIndex);
		CardNode card = column.getRight();
		
		for (int i = 0; i < topNumberIndex; i++)
			card = card.getNext();
		
		int row = topNumberIndex;
		while (card != null) {
			horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * columnIndex;
			int verticalOffset = MARGIN_TOP + 3 + row;
			int number = (int) card.getData();
			displayString(rightAlignNumber(number), horizontalOffset, verticalOffset, attributes);
			
			row++;
			card = card.getNext();
		}
	}
	
	private void displayColumn(int index) {
		// Clear the column area entirely
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * index;
		for (int i = 0; i < window.getRows(); i++)
			displayString("    ", horizontalOffset - 1, i);

		displayColumnTitle(index, new TextAttributes(Color.WHITE));
		displayColumnNumbers(index, 0, new TextAttributes(Color.WHITE));
	}

	/**
	 * Displays the status titles, namely "Transfer" and "Score".
	 */
	private void displayTransfersAndScore() {
		int horizontalOffset = MARGIN_LEFT + COLUMN_AREA_WIDTH + STATUS_MARGIN_LEFT;
		int verticalOffset = MARGIN_TOP + STATUS_MARGIN_TOP;

		displayString("Transfer:          " + transfers, horizontalOffset, verticalOffset);
		displayString("   Score:          " + playerscore, horizontalOffset, verticalOffset + 1);
	}

	/**
	 * Displays the frame of the box, and the number that was drawn. If the `number`
	 * parameter is zero, inside of the box will be displayed blank.
	 */
	private void displayBox(int number) {
		int horizontalOffset = MARGIN_LEFT + COLUMN_AREA_WIDTH + BOX_MARGIN_LEFT;
		int verticalOffset = MARGIN_TOP + STATUS_MARGIN_TOP + 2 + BOX_MARGIN_TOP;

		String draw = number == 0 ? "  " : rightAlignNumber(number);

		displayString("+--+", horizontalOffset, verticalOffset);
		displayString("|" + draw + "|", horizontalOffset, verticalOffset + 1);
		displayString("+--+", horizontalOffset, verticalOffset + 2);
	}

	/**
	 * Displays a string at the specified coordinates.
	 */
	private void displayString(String str, int left, int top) {
		window.setCursorPosition(left, top);
		window.output(str);
	}
	
	/**
	 * Displays a string at the specified coordinates with specified attributes.
	 */
	private void displayString(String str, int left, int top, TextAttributes attributes) {
		window.setCursorPosition(left, top);
		window.output(str, attributes);
	}

	/**
	 * Aligns <i>digits</i> to right. If the number has two digits, say 10, the
	 * number is not aligned.
	 * <p>
	 * Example: if the number is 3 the returned string will be " 3".
	 */
	private static String rightAlignNumber(int number) {
		if (number < 10) {
			return " " + number;
		} else {
			return Integer.toString(number);
		}
	}
}
