package columns;

import enigma.core.Enigma;
import util.CardNode;
import util.ColumnNode;

import java.awt.Color;

import org.w3c.dom.Text;

import enigma.console.TextAttributes;
import enigma.console.TextWindow;

public class Display {
	// Global margins, any screen element will obey these margins.
	private static final int MARGIN_TOP = 1;
	private static final int MARGIN_LEFT = 1;

	// The margin between two columns, and two rows.
	private static final int COLUMN_MARGIN = 3;
	private static final int ROW_MARGIN = 1;

	// The total size of the columns' display area.
	private static final int COLUMN_AREA_WIDTH = 2 + (2 + COLUMN_MARGIN) * (Game.NUMBER_OF_COLUMNS - 1);

	// Margins of the status titles.
	private static final int STATUS_MARGIN_LEFT = 5;
	private static final int STATUS_MARGIN_TOP = 0;

	// Margins of the box.
	private static final int BOX_MARGIN_LEFT = 5;
	private static final int BOX_MARGIN_TOP = 2;

	// Console properties.
	private static final String CONSOLE_TITLE = "Columns";
	private static final int CONSOLE_COLUMNS = 80;
	private static final int CONSOLE_ROWS = 40;
	private static final int FONT_SIZE = 20;

	public static TextWindow window = Enigma.getConsole(CONSOLE_TITLE, CONSOLE_COLUMNS, CONSOLE_ROWS, FONT_SIZE)
			.getTextWindow();

	/**
	 * Initializes the elements on the screen, especially the static ones, for
	 * example box's frame, column titles, and status titles.
	 */
	public static void initialize() {
		displayStatusTitles();
		displayBox(0);
		displayCursorFrameAtRowOfColumn(0, 0);
	}

	public static void displayColumnTitle(int column, TextAttributes attributes) {
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * column;
		displayString("C" + (column + 1), horizontalOffset, MARGIN_TOP, attributes);
		displayString("--", horizontalOffset, MARGIN_TOP + 1, attributes);	
	}
	
	public static void displayColumn(int index) {
		displayColumnTitle(index, new TextAttributes(Color.WHITE));
		
		// Display numbers
		ColumnNode column = Game.getColumn(index);
		CardNode card = column.getRight();
		
		int row = 0;
		while (card != null) {
			int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * index;
			int verticalOffset = MARGIN_TOP + 3 + (ROW_MARGIN + 1) * row;
			int number = (int) card.getData();
			displayString(rightAlignNumber(number), horizontalOffset, verticalOffset);
			
			row++;
			card = card.getNext();
		}	
	}
	
	/**
	 * Displays the selection frame of the cursor. The frame starts at the given row
	 * of the column and ends at the end of the column.
	 */
	public static void displayCursorFrameAtRowOfColumn(int column, int row) {
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * column - 1;
		int verticalStartOffset = MARGIN_TOP + 3 + (ROW_MARGIN + 1) * row - 1;
		int verticalLinesToDraw = (ROW_MARGIN + 1) * (Game.getColumn(column).getSize() - row);

		displayString("+--+", horizontalOffset, verticalStartOffset);

		for (int i = 0; i < verticalLinesToDraw; i++) {
			window.output(horizontalOffset, verticalStartOffset + i + 1, '|');
			window.output(horizontalOffset + 3, verticalStartOffset + i + 1, '|');
		}

		displayString("+--+", horizontalOffset, verticalStartOffset + verticalLinesToDraw);
	}

	/**
	 * Clears the selection frame of the cursor.
	 */
	public static void clearCursorFrameAtRowOfColumn(int column, int row) {
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * column - 1;
		int verticalStartOffset = MARGIN_TOP + 3 + (ROW_MARGIN + 1) * row - 1;
		int verticalLinesToDraw = (ROW_MARGIN + 1) * (Game.getColumn(column).getSize() - row);

		displayString("    ", horizontalOffset, verticalStartOffset);

		for (int i = 0; i < verticalLinesToDraw; i++) {
			window.output(horizontalOffset, verticalStartOffset + i + 1, ' ');
			window.output(horizontalOffset + 3, verticalStartOffset + i + 1, ' ');
		}

		displayString("    ", horizontalOffset, verticalStartOffset + verticalLinesToDraw);
	}

	/**
	 * Displays the status titles, namely "Transfer" and "Score".
	 */
	private static void displayStatusTitles() {
		int horizontalOffset = MARGIN_LEFT + COLUMN_AREA_WIDTH + STATUS_MARGIN_LEFT;
		int verticalOffset = MARGIN_TOP + STATUS_MARGIN_TOP;

		displayString("Transfer: ", horizontalOffset, verticalOffset);
		displayString("Score:    ", horizontalOffset, verticalOffset + 1);
	}

	/**
	 * Displays the frame of the box, and the number that was drawn. If the `number`
	 * parameter is zero, inside of the box will be displayed blank.
	 */
	public static void displayBox(int number) {
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
	private static void displayString(String str, int left, int top) {
		window.setCursorPosition(left, top);
		window.output(str);
	}
	
	/**
	 * Displays a string at the specified coordinates.
	 */
	private static void displayString(String str, int left, int top, TextAttributes attributes) {
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
