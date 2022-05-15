package util;



public class DoubleLinkedList {
	
	public Node head;
	public Node tail;
	public DoubleLinkedList()
	{
		head=null;
		tail=null;
	}
	
	public void SortedAdd(Node NewNode) 
	{
		if(head==null) //If list is empty, the node adds as head.
		{
			head=NewNode;
		}
		else if(tail==null) //If list's tail is empty, the node adds as tail.
		{
			tail=NewNode;
			NewNode.setPrev(head);
			head.setNext(tail);
		}
		else if(NewNode.getScore()>head.getScore()) 
		{
			NewNode.setNext(head);
			head=NewNode;
			NewNode.getNext().setPrev(NewNode);
			
		}
		else 
		{
			Node current = head; //Current node
			Node save = null; //Node that saves one step before.
			while(current.getNext()!=null && current.getScore()>NewNode.getScore()) //Takes till the score is smaller.
			{
				save = current;
				current=current.getNext();
			}
			if(current.getNext()==null && current.getScore()>NewNode.getScore()) // if the node is the smallest.
			{	
				tail.setNext(NewNode);	//Makes the new node, tail.
				NewNode.setPrev(tail);
				tail=NewNode;
			}
			else 
			{
				save.setNext(NewNode);
				NewNode.setNext(current); //Adds the new node between the save and current nodes.
				current.setPrev(NewNode);
				NewNode.setPrev(save);
				
				
			}
		}
		
	}
	
	
	public void displayDescending() //Descending display.
	{
		Node temp = head;
		while(temp!=null) 
		{
			System.out.println(temp.getData()+"-"+temp.getScore());
			temp=temp.getNext();
		}
		System.out.println();
	}
	
	public void displayAscending() //Ascending display.
	{
		Node temp = tail;
		while(temp!=null) 
		{
			System.out.println(temp.getData()+"-"+temp.getScore());
			temp=temp.getPrev();
		}
		System.out.println();
	}

}
