package util;

public class MultiLinkedList {

	private ColumnNode head;
	public MultiLinkedList() {
		this.head=null;
	}
	public ColumnNode getHead() {
		return head;
	}
	public void setHead(ColumnNode head) {
		this.head = head;
	}
	
	public void AddColumn(int columnnum) 
	{
		ColumnNode NewNode = new ColumnNode(columnnum);
		if(head==null)
			head=NewNode;
		else {
			ColumnNode temp = head;
			while(temp.getDown()!=null)
				temp=temp.getDown();
			temp.setDown(NewNode);
		}
	}
	
	public void AddCard(int columnnum, int cardnum) 
	{
		ColumnNode temp = head;
		if(head==null)
			System.out.println("There is no column.");
		else 
		{
			while(temp.getDown()!=null && (int)temp.getData()!=columnnum) 
			{
				temp=temp.getDown();
			}
			if(temp!=null) {
			if(temp.getRight()==null) {
				CardNode cn = new CardNode(cardnum);
				temp.setRight(cn);
			}
			else {
				CardNode NewCardNode = new CardNode(cardnum);
				CardNode tempC = temp.getRight();
				while(tempC.getNext()!=null) {
					tempC=tempC.getNext();
				}
				tempC.setNext(NewCardNode);
			}
			
			}
			
		}
	}
	
	public void DeleteCard(int columnnum,int cardnum) 
	{
		ColumnNode temp = head;
		if(head==null)
			System.out.println("There is no column in the list");
		else {
			while(temp!=null && (int)temp.getData()!=columnnum) {
				temp=temp.getDown();
			}
			if(temp.getRight()==null)
				System.out.println("There is no card in the column like "+cardnum);
			else if ((int)temp.getRight().getData()==cardnum) {
				temp.setRight(temp.getRight().getNext());
			}
			else {
				CardNode currentCard = temp.getRight();
				CardNode prevCard = null;
				while(currentCard!=null && (int)currentCard.getData()!=cardnum) 
				{
					prevCard=currentCard;
					currentCard=currentCard.getNext();
				}
				if(currentCard !=null) {
					prevCard.setNext(currentCard.getNext());
				}
				else
					System.out.println("There is no card in the column like "+cardnum);
			}
				
		}
	}
	
	public void DeleteFromtheFinishedDeck(int columnIndex, int numberIndex, int Startingpoint) 
	{
		ColumnNode column = head;
		if(head==null)
			System.out.println("There is no column in the list");
		else {
			while(column!=null && (int)column.getData()!=columnIndex)
				column=column.getDown();  // Finds the column.
			CardNode currentCard = column.getRight();
			CardNode prevCard = null;
			for (int i = 0; i < Startingpoint; i++)  // Goes till the deck's starting point.
			{
				prevCard=currentCard;
				currentCard=currentCard.getNext();
			}
			while(currentCard!=null && (int)currentCard.getData()!=numberIndex)  // Finds the card that will delete.
			{
				prevCard=currentCard;
				currentCard=currentCard.getNext();
			}
			if(prevCard==null)
				column.setRight(column.getRight().getNext());      // Deletes the card.
			else if(currentCard !=null) {
				prevCard.setNext(currentCard.getNext());
			}
			else
				System.out.println("There is no card in the column like "+numberIndex);
		}
	}
	
	public void display() 
	{
		ColumnNode temp = head;
		while(temp!=null) {
			System.out.println(temp.getData()+": ");
			CardNode tempC = temp.getRight();
			while(tempC!=null) {
				System.out.print(tempC.getData()+" ");
				tempC=tempC.getNext();
			}
			System.out.println();
			temp=temp.getDown();
		}
	}
}
