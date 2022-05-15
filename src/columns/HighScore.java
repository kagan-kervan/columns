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
		dllist = new DoubleLinkedList();
		f = filename;
	}
	
	public void ReadingFile() throws FileNotFoundException
	{
		Scanner sc = new Scanner(f);
		while(sc.hasNextLine()) 
		{
			String input = sc.nextLine();
			String[] parts = input.split("-");
			Node nd = new Node(parts[0],Double.parseDouble(parts[1]));
			dllist.SortedAdd(nd);
		}
	}
	
	public void display() {
		dllist.displayDescending();
	}
	
	public void AddtoHighScore(String playername, double playerscore) 
	{
		Node nd = new Node(playername,playerscore);
		dllist.SortedAdd(nd);
		
	}
	public void WritingtoFile() throws IOException 
	{
		FileWriter fw = new FileWriter(f);
		Node temp = dllist.head;
		while(temp!=null) 
		{
			String output = (String)temp.getData()+"-"+temp.getScore();
			if(temp.getNext()!=null)
				fw.write(output+"\n");
			else
				fw.write(output);
			temp=temp.getNext();
			
		}
		fw.close();
	}
	
}
