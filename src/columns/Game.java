package columns;

import util.SingleLinkedList;

public class Game {
	public static final int NUMBER_OF_COLUMNS = 5;
	public static final int INITIAL_NUMBER_COUNT= 6;

	private static SingleLinkedList[] columns = new SingleLinkedList[NUMBER_OF_COLUMNS];
	private static SingleLinkedList box = new SingleLinkedList();
	
	public static void main(String[] args) {		
		Cursor.initialize();
		Game.initialize();
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

		fillAndShuffleBox();

		box.display();
		System.out.println();

		distributeNumbersToColumns();
		System.out.println();

		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			System.out.println();
			columns[i].display();
		}

		System.out.println();
		System.out.println(box.size());
		System.out.println();
	}
	
	private static void fillAndShuffleBox() {
		int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0, c8 = 0, c9 = 0, c10 = 0;

		for (int i = 0; i < 50; i++) {
			int random = (int) (Math.random() * 10 + 1);

			if (random == 1 && c1 != 5) {
				box.add(1);
				c1++;
			} else if (random == 2 && c2 != 5) {
				box.add(2);
				c2++;
			} else if (random == 3 && c3 != 5) {
				box.add(3);
				c3++;
			} else if (random == 4 && c4 != 5) {
				box.add(4);
				c4++;
			} else if (random == 5 && c5 != 5) {
				box.add(5);
				c5++;
			} else if (random == 6 && c6 != 5) {
				box.add(6);
				c6++;
			} else if (random == 7 && c7 != 5) {
				box.add(7);
				c7++;
			} else if (random == 8 && c8 != 5) {
				box.add(8);
				c8++;
			} else if (random == 9 && c9 != 5) {
				box.add(9);
				c9++;
			} else if (random == 10 && c10 != 5) {
				box.add(10);
				c10++;
			} else {
				i--;
			}

		}
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