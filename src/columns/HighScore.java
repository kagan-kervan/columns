package columns;

import java.io.*;
import java.util.*;

import util.DoubleLinkedList;
import util.Node;
public class HighScore {

	private DoubleLinkedList dllist;
	private File f;
	
	HighScore(File filename)
	{
		dllist = new DoubleLinkedList(); //Crates a double linked list.
		f = filename;  
	}
	
	public void ReadingFile() throws FileNotFoundException
	{
		Scanner sc = new Scanner(f); //Reads the file.
		while(sc.hasNextLine()) 
		{
			String input = sc.nextLine();
			String[] parts = input.split("-"); //Splits it.
			Node nd = new Node(parts[0],Double.parseDouble(parts[1])); //Creates the new node from the inputs.
			dllist.SortedAdd(nd); //Sorted adds it.
		}
	}
	
	public void display() {
		dllist.displayDescending();
	}
	
	public void AddtoHighScore(String playername, double playerscore) 
	{
		Node nd = new Node(playername,playerscore); //Creates the new node for the player.
		dllist.SortedAdd(nd);  //Sorted adds the new node.
		
	}
	public void WritingtoFile() throws IOException 
	{
		FileWriter fw = new FileWriter(f);
		Node temp = dllist.head;
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
