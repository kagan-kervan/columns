package columns;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class Cursor {
	private static boolean initialized = false;
	private static int column = 0;
	private static int row = 0;

	public static void initialize() {
		if (initialized) return;
		
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
				}
			}
		};
		
		Display.window.addKeyListener(keyListener);
		
		initialized = true;
	}
	
	private static void moveCursorVertical(boolean moveUp) {
		Cursor.row += moveUp ? -1 : 1;
		Cursor.row %= Game.getColumn(Cursor.column).size();
	}
	
	private static void moveCursorHorizontal(boolean moveLeft) {
		Cursor.column += moveLeft ? -1 : 1;
		Cursor.column %= Game.NUMBER_OF_COLUMNS;
	}
}