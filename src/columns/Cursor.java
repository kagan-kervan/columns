package columns;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Cursor {
	private static boolean initialized = false;
	private static boolean selectionMode = false;
	private static int column = 0;
	private static int row = 0;

	public static void initialize() {
		if (initialized) return;
		
		// Start the cursor from the first non-empty column.
		for (int i = 0; i < Game.NUMBER_OF_COLUMNS; i++) {
			if (Game.getColumn(i).size() > 0) {
				Cursor.column = i;
				break;
			}
		}
		
		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }

			@Override
			public void keyReleased(KeyEvent e) { }

			@Override
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();
				
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
					case KeyEvent.VK_Z:
						selectionMode = !selectionMode;
						break;
				}
			}
		};
		
		// Listen for key events.
		Display.window.addKeyListener(keyListener);
		
		// Set the `initialized` flag true so that the cursor does
		// not get initialized more than once in the future.
		initialized = true;
	}
	
	/**
	 * Moves the cursor vertically. If the limit is
	 * exceeded, the cursor starts from the opposite-end.
	 */
	private static void moveCursorVertical(boolean moveUp) {
		// Clear the cursor frame.
		Display.clearCursorFrameAtRowOfColumn(column, row);
		
		// Move the current row
		int columnSize = Game.getColumn(Cursor.column).size();
		Cursor.row += columnSize + (moveUp ? -1 : 1);
		Cursor.row %= columnSize;
		
		// Draw the cursor frame.
		Display.displayCursorFrameAtRowOfColumn(column, row);
	}
	
	/**
	 * Moves the cursor horizontally. If the limit is
	 * exceeded, the cursor starts from the opposite-end.
	 * <p>
	 * This method will skip the empty columns.
	 * <p>
	 * If the `row` is bigger than the next column's size, the `row`
	 * starts from the last number of the next column.
	 */
	private static void moveCursorHorizontal(boolean moveLeft) {
		// Clear the cursor frame.
		Display.clearCursorFrameAtRowOfColumn(column, row);
		
		// Search for a non-empty column.
		for (int i = 0; i < Game.NUMBER_OF_COLUMNS; i++) {
			Cursor.column += Game.NUMBER_OF_COLUMNS + (moveLeft ? -1 : 1);			
			Cursor.column %= Game.NUMBER_OF_COLUMNS;
			int columnSize = Game.getColumn(Cursor.column).size();
			
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