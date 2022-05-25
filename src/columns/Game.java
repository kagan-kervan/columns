package columns;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import util.SingleLinkedList;
import util.CardNode;
import util.ColumnNode;
import util.MultiLinkedList;

public class Game {
	public static final int NUMBER_OF_COLUMNS = 5;
	public static final int INITIAL_NUMBER_COUNT = 6;
	
	public static boolean emptyBox = true;
	public static int lastboxnumber = 0;
	public static int playerscore = 0;
	public static int FinishedDeckStartingPoint; // Finished deck's starting point in the column.
	
	public static MultiLinkedList columns = new MultiLinkedList();
	private static SingleLinkedList box = new SingleLinkedList();
		
	public static void main(String[] args) throws IOException {
		File f = new File("highscore.txt");
		HighScore hs = new HighScore(f);
		hs.display();
		
		// Load sound files
		try {
			File cardSoundFile = new File(CARD_SOUND_FILE_PATH);
			cardSoundClip = AudioSystem.getClip();
			cardSoundClip.open(AudioSystem.getAudioInputStream(cardSoundFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			File cardSoundFile2 = new File(CARD_SOUND_TRANSFERRING_FILE_PATH);
			cardSoundClipTransfer = AudioSystem.getClip();
			cardSoundClipTransfer.open(AudioSystem.getAudioInputStream(cardSoundFile2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Game.initialize();
		Cursor.initialize();
		Display.initialize();
	}

	public static SingleLinkedList getBox() {

		return box;
	}

	public static ColumnNode getColumn(int index) {
		ColumnNode column = columns.getHead();
		if (column == null) return null;

		for (int i = 0; i < index; i++)
			column = column.getDown();
		
		return column;
	}

	private static void initialize() {
		// Initialize single linked lists in the `columns` array.
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			 columns.AddColumn(i);
			 
		}

		fillAndShuffleBox(50);
		distributeNumbersToColumns(30);
		

		for (int i = 0; i < NUMBER_OF_COLUMNS; i++)
			Display.displayColumn(i);
	}
	
	public static boolean transferLastNumberFromBox(int destinationColumnIndex) {
		ColumnNode destination = Game.getColumn(destinationColumnIndex);
		
		if (destination.getRight() == null) {
			if (!(lastboxnumber == 1 || lastboxnumber == 10)) return false;
			destination.setRight(new CardNode(lastboxnumber));
		} else {
			CardNode lastNode = destination.getRight();
			
			while (lastNode.getNext() != null)
				lastNode = lastNode.getNext();
			
			int lastNumber = (int) lastNode.getData();
			
			if (Math.abs(lastNumber - lastboxnumber) > 1) return false;
			
			lastNode.setNext(new CardNode(lastboxnumber));
		}

		Game.lastboxnumber = 0;
		Game.emptyBox = true;
		
		Display.displayColumn(destinationColumnIndex);
		Display.displayBox(0);
		
		return true;
	}
	
	public static boolean transferNumbers(int sourceColumnIndex, int rowIndex, int destinationColumnIndex) {
		if (sourceColumnIndex == destinationColumnIndex) return false;
		
		ColumnNode source = Game.getColumn(sourceColumnIndex);
		ColumnNode destination = Game.getColumn(destinationColumnIndex);
		
		CardNode top = source.getRight();
		CardNode nodeBeforeTop = null;
		
		for (int i = 0; i < rowIndex; i++) {
			nodeBeforeTop = top;
			top = top.getNext();
		}
		
		int topNumber = (int) top.getData();
		
		if (destination.getRight() == null) {
			if (!(topNumber == 1 || topNumber == 10)) return false;
			
			destination.setRight(top);
			
			if (nodeBeforeTop == null) {
				source.setRight(null);
			} else {
				nodeBeforeTop.setNext(null);
			}
		} else {
			CardNode lastNode = destination.getRight();
			
			while (lastNode.getNext() != null)
				lastNode = lastNode.getNext();
			
			int lastNumber = (int) lastNode.getData();
			
			if (Math.abs(lastNumber - topNumber) > 1) return false;

			lastNode.setNext(top);
			
			if (nodeBeforeTop == null) {
				source.setRight(null);
			} else {
				nodeBeforeTop.setNext(null);
			}
		}
		
		// Checks if there is any completed decks in the columns.
		if (Game.isDeckCompleted(destinationColumnIndex)) {
			// Gives the score to the player.
			Game.playerscore += 1000;

			for (int i = 1; i <= 10; i++) {
				// Deletes the finished deck.
				Game.columns.DeleteFromtheFinishedDeck(destinationColumnIndex, i, Game.FinishedDeckStartingPoint);
			}
		}

		Display.displayColumn(sourceColumnIndex);
		Display.displayColumn(destinationColumnIndex);

		
		
		return true;
	}
	
	public static boolean isDeckCompleted(int ColumnIndex) 
	{
		ColumnNode column = Game.getColumn(ColumnIndex);
		CardNode card = column.getRight();
		FinishedDeckStartingPoint=0; 
		int count = 0;
		while(card !=null) 
		{
			while(card.getNext()!=null && (Integer)card.getData()!=1 && (Integer)card.getData()!=10) { // Finds the starting
				card=card.getNext();																// point of the deck.	
				FinishedDeckStartingPoint ++;
			}
			if(card.getNext() == null)
				return false;
			int startingnumber = (int) card.getData(); // Gets the starting number.
			CardNode temp = card;
			if(startingnumber==10) {
				while(temp.getNext()!=null) {
					if((int)temp.getData()==startingnumber) {
						count ++;                    // Increase the count.
						startingnumber --;         // Decrease the number that uses in the matching deck.
					}
					else {
						count = 0;   //Reset the count.
						startingnumber = 10;
						break;
					}
					temp=temp.getNext();
				}
			}
			else {
				while(temp!=null) {
					if((int)temp.getData()==startingnumber) {
						count ++;             // Increase the count.
						startingnumber ++;      // Increase the number that uses in the matching deck.
					}
					else {
						count = 0;       // Resets the count.
						startingnumber = 1;
						break;
					}
					temp=temp.getNext();
				}
			}
			if(count == 10)
				return true;  
			card=card.getNext();
			
		}
		return false;
	}
	
	private static void fillAndShuffleBox(int number) {
		int count = 0;
		int randomno = 0;
		
		while(count < number)  // filling the list of box
		{
			do {
			randomno = (int) (Math.random() * 10 + 1);
			}while(box.search(randomno)!=false);
			for (int i = 0; i < 5; i++) {
				box.add(randomno);
				count ++;
				box.shuffling();
			}
			box.shuffling();
		}
		box.shuffling();
	}
	
	private static void distributeNumbersToColumns(int number) {
		int randomno = 0;
		int count = 0;
		while(number > count)
		{
			randomno = (int) (Math.random() * 5);
			columns.AddCard(randomno, box.returnHead());
			box.removeCardNodeWithPosition(0);
			count++;
		}	
	}
	
	public static void drawNumberFromBox() 
	{
		if (box.size() != 0 && emptyBox) // box empty boolean will be added here when transferring operations are done 
		{
			Display.displayBox(box.returnHead());
			lastboxnumber = box.returnHead();
			box.removeCardNodeWithPosition(0);
			emptyBox = false;
			
		} 
		else if(!emptyBox) // number will be added to selected column here.
		{
			Display.displayBox(lastboxnumber);
			playCardSound();
		}
		else
		{
			// The box is empty, number 0 prints an
			// empty box.
			Display.displayBox(0);
		}
	}
	
	private static final String CARD_SOUND_FILE_PATH = "cardsound.wav";
	private static final String CARD_SOUND_TRANSFERRING_FILE_PATH = "cardtransferringsound.wav";
	
	
	private static Clip cardSoundClip;
	private static Clip cardSoundClipTransfer;

	public static void playCardSound() {
		// Reset the frame position to zero so that the
		// audio clip can be played more than once.
		cardSoundClip.setFramePosition(0);
		
		cardSoundClip.start();
	}
	public static void playCardTransferringSound() {

		cardSoundClipTransfer.setFramePosition(0);
		
		cardSoundClipTransfer.start();
	}
}
