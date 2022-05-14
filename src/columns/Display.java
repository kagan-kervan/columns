package columns;

import enigma.core.Enigma;
import enigma.console.Console;
import util.SingleLinkedList;

public class Display {

	private static Console cn = Enigma.getConsole("Columns", 80, 40, 20);

	static SingleLinkedList box = new SingleLinkedList();

	static SingleLinkedList column1 = new SingleLinkedList();
	static SingleLinkedList column2 = new SingleLinkedList();
	static SingleLinkedList column3 = new SingleLinkedList();
	static SingleLinkedList column4 = new SingleLinkedList();
	static SingleLinkedList column5 = new SingleLinkedList();

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
		for (int i = 0; i < 6; i++) {
			column1.add(box.returnHead());
			box.removeNodeWithPosition(0);
			column2.add(box.returnHead());
			box.removeNodeWithPosition(0);
			column3.add(box.returnHead());
			box.removeNodeWithPosition(0);
			column4.add(box.returnHead());
			box.removeNodeWithPosition(0);
			column5.add(box.returnHead());
			box.removeNodeWithPosition(0);
		}
	}

	public static void initialize() {
		fillAndShuffleBox();

		box.display();
		System.out.println();

		distributeNumbersToColumns();
		System.out.println();

		System.out.println();
		column1.display();
		System.out.println();
		column2.display();
		System.out.println();
		column3.display();
		System.out.println();
		column4.display();
		System.out.println();
		column5.display();

		System.out.println();
		System.out.println(box.size());
		System.out.println();
	}

}
