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
		windowMenu.setCursorPosition(23, 2); 
		windowMenu.output("HINTS",new TextAttributes(Color.green));
		windowMenu.setCursorPosition(22, 3); 
		windowMenu.output("-------",new TextAttributes(Color.green));
		windowMenu.setCursorPosition(2, 4); 
		windowMenu.output("1 - The Card Box has been shuffled",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(6, 5); 
		windowMenu.output("but It does not mean that there is no card to match inside it!",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(2, 7); 
		windowMenu.output("2 - The first opportunity is not the best option at all...",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(2, 9); 
		windowMenu.output("3 - Calculate as many probabilities as you can do!",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(2, 11); 
		windowMenu.output("4 - Pay attention to the Transferring Conditions",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(6, 12); 
		windowMenu.output("You are not able to do every move you wish.",new TextAttributes(Color.red));
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
		windowMenu.setCursorPosition(8, 2); 
		windowMenu.output("You are leaving the game...",new TextAttributes(Color.green));
		windowMenu.setCursorPosition(8, 3); 
		windowMenu.output("------------------------------",new TextAttributes(Color.green));
		windowMenu.setCursorPosition(8, 4); 
		windowMenu.output("                        - Best Wishes",new TextAttributes(Color.green));
		windowMenu.setCursorPosition(13, 7); 
		windowMenu.output("Prepared by",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(13, 8); 
		windowMenu.output("_____________",new TextAttributes(Color.red));
		windowMenu.setCursorPosition(15, 9); 
		windowMenu.output("-Yiðit Önlü");
		windowMenu.setCursorPosition(15, 10); 
		windowMenu.output("-Berkay Dinç");
		windowMenu.setCursorPosition(15, 11); 
		windowMenu.output("-Ahmet Salih Kara");
		windowMenu.setCursorPosition(15, 12); 
		windowMenu.output("-Ahmet Kaðan Kervan");
		windowMenu.setCursorPosition(18, 17); 
		windowMenu.output("Press E to leave",new TextAttributes(Color.green));
		
		
		
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
					break;
					
				case 69: // right
					System.exit(0);
					break;
					
				} 
			}
		};
		
		windowMenu.addKeyListener(keyListener2);
		if (returned == 0) windowMenu.removeKeyListener(keyListener2);
		
		
		
		
	}
}