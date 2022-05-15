package columns;

import java.io.*;
import java.util.*;

import util.DoubleLinkedList;
public class HighScore {

	private DoubleLinkedList dllist;
	private File f;
	
	HighScore(File filename) throws FileNotFoundException
	{
		dllist = new DoubleLinkedList(); //Crates a double linked list.
		f = filename;
		ReadingFile(); //Reads the file and appends the scores to the linked list.
	}
	
	public void ReadingFile() throws FileNotFoundException
	{
		Scanner sc = new Scanner(f); //Reads the file.
		while(sc.hasNextLine()) 
		{
			String input = sc.nextLine();
			String[] parts = input.split("-"); //Splits it.
			DoubleLinkedList.Node nd = new DoubleLinkedList.Node(parts[0],Double.parseDouble(parts[1])); //Creates the new node from the inputs.
			dllist.SortedAdd(nd); //Sorted adds it.
		}
		
		sc.close();
	}
	
	public void display() {
		dllist.displayDescending();
	}
	
	public void AddtoHighScore(String playername, double playerscore) 
	{
		DoubleLinkedList.Node nd = new DoubleLinkedList.Node(playername,playerscore); //Creates the new node for the player.
		dllist.SortedAdd(nd);  //Sorted adds the new node.
		
	}
	public void WritingtoFile() throws IOException 
	{
		FileWriter fw = new FileWriter(f);
		DoubleLinkedList.Node temp = dllist.head;
		while(temp!=null) 
		{
			String output = (String)temp.getData()+"-"+temp.getScore(); //Creates the output string.
			if(temp.getNext()!=null)
				fw.write(output+"\n"); //Writes the output to file.
			else
				fw.write(output);
			temp=temp.getNext();
			
		}
		fw.close();
	}
	
}
