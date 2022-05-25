package columns;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import enigma.console.TextAttributes;

public abstract class Cursor {
	private static boolean initialized = false;
	private static int column = 0;
	private static int row = 0;
	private static boolean selectionMode = false;
	private static int destinationColumn = 0;

	public static void initialize() {
		if (initialized)
			return;

		// Start the cursor from the first non-empty column.
		for (int i = 0; i < Game.NUMBER_OF_COLUMNS; i++) {
			if (Game.getColumn(i).getSize() > 0) {
				Cursor.column = i;
				break;
			}
		}

		KeyListener keyListener = new KeyListener() {
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
						Cursor.selectColumn(false);
						break;
					case KeyEvent.VK_RIGHT:
						Cursor.selectColumn(true);
						break;
					case KeyEvent.VK_X:
						boolean transferred = Game.transferNumbers(column, row, destinationColumn);
						if (transferred) exitSelectionMode();
						Soundpl.playCardTransferringSound();
						break;
					case KeyEvent.VK_Z:
						exitSelectionMode();
						break;
					}

				} else {
					switch (keyCode) {
					case KeyEvent.VK_UP:
						Cursor.moveCursorVertical(true);
						break;
					case KeyEvent.VK_DOWN:
						Cursor.moveCursorVertical(false);
						break;
					case KeyEvent.VK_LEFT:
						Cursor.moveCursorHorizontal(true);
						break;
					case KeyEvent.VK_RIGHT:
						Cursor.moveCursorHorizontal(false);
						break;
					case KeyEvent.VK_B:
						if(Game.getBox().size() != 0)
						{
							Game.drawNumberFromBox();
						}
						break;
					case KeyEvent.VK_Z:
						enterSelectionMode();
						break;
					case KeyEvent.VK_E:
						System.exit(0); // user can go back to menu with pressing E, we will redirect the user.
						break;
					}					
				}
			}
		};

		// Listen for key events.
		Display.window.addKeyListener(keyListener);

		// Set the `initialized` flag true so that the cursor does
		// not get initialized more than once in the future.
		initialized = true;
	}
	
	private static void enterSelectionMode() {
		selectionMode = true;
		destinationColumn = column;
		Display.displayColumnTitle(destinationColumn, new TextAttributes(Color.GREEN));
	}
	
	private static void exitSelectionMode() {
		selectionMode = false;
		Display.displayColumnTitle(destinationColumn, new TextAttributes(Color.WHITE));
	}
		
	public static void selectColumn(boolean selectNext) {
		Display.displayColumnTitle(destinationColumn, new TextAttributes(Color.WHITE));
		destinationColumn += Game.NUMBER_OF_COLUMNS + (selectNext ? 1 : -1);
		destinationColumn %= Game.NUMBER_OF_COLUMNS;
		Display.displayColumnTitle(destinationColumn, new TextAttributes(Color.GREEN));
	}

	/**
	 * Moves the cursor vertically. If the limit is exceeded, the cursor starts from
	 * the opposite-end.
	 */
	private static void moveCursorVertical(boolean moveUp) {
		// Clear the cursor frame.
		Display.clearCursorFrameAtRowOfColumn(column, row);

		// Move the current row
		int columnSize = Game.getColumn(Cursor.column).getSize();
		Cursor.row += columnSize + (moveUp ? -1 : 1);
		Cursor.row %= columnSize;

		// Draw the cursor frame.
		Display.displayCursorFrameAtRowOfColumn(column, row);
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
	private static void moveCursorHorizontal(boolean moveLeft) {
		// Clear the cursor frame.
		Display.clearCursorFrameAtRowOfColumn(column, row);

		// Search for a non-empty column.
		for (int i = 0; i < Game.NUMBER_OF_COLUMNS; i++) {
			Cursor.column += Game.NUMBER_OF_COLUMNS + (moveLeft ? -1 : 1);
			Cursor.column %= Game.NUMBER_OF_COLUMNS;
			int columnSize = Game.getColumn(Cursor.column).getSize();

			// If a non-empty column is found, select it.
			if (columnSize > 0) {
				// If the row is bigger than the next column's,
				// size, start it from the last element of the next
				// column.
				if (Cursor.row >= columnSize)
					Cursor.row = columnSize - 1;

				break;
			}
		}

		// Draw the cursor frame.
		Display.displayCursorFrameAtRowOfColumn(column, row);
	}
}
