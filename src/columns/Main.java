package columns;

import java.io.FileNotFoundException;
import java.awt.Color;
import enigma.core.Enigma;
import enigma.console.TextAttributes;
import enigma.console.TextWindow;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main {
	static KeyListener keyListener2;
	static int returned = 6;
	static TextWindow windowMenu = Enigma.getConsole("Columns", 80, 40, 20).getTextWindow();
	
 //.new TextAttributes(Color.RED)
	
	
	public static void Menu()
	{
		Cleaning();
		windowMenu.setCursorPosition(0, 6); 
		windowMenu.output("                                HINTS           \n\n",new TextAttributes(Color.RED));
		windowMenu.output("                                / \\           \n",new TextAttributes(Color.GREEN));
		windowMenu.output("                               // \\\\           \n",new TextAttributes(Color.GREEN));
		windowMenu.output("                               |||||           \n",new TextAttributes(Color.GREEN));
		windowMenu.output("                     _ __ ___   ___ _ __  _   _ \n",new TextAttributes(Color.red));
		windowMenu.output("               ___   ");
		windowMenu.output("| '_ ` _ \\ / _ \\ '_ \\| | | |",new TextAttributes(Color.red));
		windowMenu.output("   ___\n");
		windowMenu.output("  START    ",new TextAttributes(Color.RED));
		windowMenu.output("______    ",new TextAttributes(Color.GREEN));
		windowMenu.output("| | | | | |  __/ | | | |_| |    ",new TextAttributes(Color.red));
		windowMenu.output("______     ",new TextAttributes(Color.GREEN));
		windowMenu.output("EXIT\n",new TextAttributes(Color.RED));
		windowMenu.output("               ___   ");
		windowMenu.output("|_| |_| |_|\\___|_| |_|\\__,_|",new TextAttributes(Color.red));
		windowMenu.output("   ___");

		
	}
	
	public static void HintsOption()
	{
		Cleaning();
		windowMenu.output("Hints\n");
		
	}
	
	public static void GameStartOption()
	{
		try {
			
			Game game = new Game();
			
		} catch (FileNotFoundException e) {
			System.out.println("Couldn't find highscore table file");
		}
		
	}
	
	public static void ExitOption()
	{
		Cleaning();
		windowMenu.output("Exit\n");
		System.exit(0);
		
		
	}
	
	public static void Cleaning()
	{
		windowMenu.setCursorPosition(0, 0);
		for(int i = 1; i < Game.console_x; i++)
		{
			for(int j = 1; j < Game.console_y; j++)
			{
				windowMenu.output(" ");
			}
		}
		windowMenu.setCursorPosition(0, 0);
		
	}
	
	public static void main(String[] args) 
	{
		Menu();
		keyListener2 = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent event) {
				int keyCode = event.getKeyCode();
				
				if (returned == 0) GameStartOption();
				if (returned == 0) windowMenu.removeKeyListener(keyListener2);
				
				
				
				switch(keyCode)
				{
				case 1 | 27:  // menu
					
					Menu();
					break;
				case 38: //  up
					
					HintsOption();
					keyCode = 1;
					break;
				case 37: // left
					returned = 0;
					break;
				case 39: // right
					
					ExitOption();
					keyCode = 1;
					break;
				} 
			}
		};
		
		windowMenu.addKeyListener(keyListener2);
		if (returned == 0) windowMenu.removeKeyListener(keyListener2);
		
		
		
		
	}
}