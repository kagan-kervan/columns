package columns;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) {
		try {
			Game game = new Game();
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find highscore table file");
		}
	}
}