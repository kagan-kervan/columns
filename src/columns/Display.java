package columns;

import enigma.core.Enigma;
import enigma.console.TextWindow;

public class Display {
	// Global margins, any screen element will obey these margins.
	private static final int MARGIN_TOP = 1;
	private static final int MARGIN_LEFT = 1;

	// The margin between two columns, and two rows.
	private static final int COLUMN_MARGIN = 3;
	private static final int ROW_MARGIN = 1;
	
	// The total size of the columns' display area.
	private static final int COLUMN_AREA_WIDTH = 2+ (2 + COLUMN_MARGIN) * (Game.NUMBER_OF_COLUMNS - 1);

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

	public static TextWindow window = Enigma.getConsole(CONSOLE_TITLE, CONSOLE_COLUMNS, CONSOLE_ROWS, FONT_SIZE).getTextWindow();

	/**
	 * Initializes the elements on the screen, especially the static ones,
	 * for example box's frame, column titles, and status titles.
	 */
	public static void initialize() {
		displayColumnTitles();
		displayStatusTitles();
		displayBox(0);
	}

	/**
	 * Displays the number at a specific row of a column.
	 */
	public static void displayRowOfColumn(int column, int row, int number) {
		int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * column;
		int verticalOffset = MARGIN_TOP + 2 + (ROW_MARGIN + 1) * row;
		displayString(rightAlignNumber(number), horizontalOffset, verticalOffset);
	}

	/**
	 * Displays column titles "C1", "C2", ..., "CN" etc.
	 */
	private static void displayColumnTitles() {
		for (int i = 0; i < Game.NUMBER_OF_COLUMNS; i++) {
			int horizontalOffset = MARGIN_LEFT + (2 + COLUMN_MARGIN) * i;

			displayString("C" + (i + 1), horizontalOffset, MARGIN_TOP);
			displayString("--", horizontalOffset, MARGIN_TOP + 1);
		}
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
	 * Displays the frame of the box, and the number that was drawn. If the
	 * `number` parameter is zero, inside of the box will be displayed blank.
	 */
	private static void displayBox(int number) {
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
	 * Aligns <i>digits</i> to right. If the number has two digits, say 10,
	 * the number is not aligned.
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