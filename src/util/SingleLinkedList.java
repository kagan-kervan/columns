package util;

import java.util.Random;
public class SingleLinkedList {
	

	private CardNode head = null;

	public SingleLinkedList() {
	}

	public void add(Object data) {
		if (head == null) {
			CardNode cn = new CardNode(data);
			head = cn;
		} else {
			CardNode temp = head;
			while (temp.getNext() != null) {
				temp = temp.getNext();
			}
			CardNode newCardNode = new CardNode(data);
			temp.setNext(newCardNode);
		}
	}

	public void removeCardNodeWithPosition(int position) {
		if (head == null) {
			return;
		}

		CardNode temp = head;

		if (position == 0) {
			head = temp.next;
			return;
		}
		for (int i = 0; temp != null && i < position - 1; i++) {
			temp = temp.next;
		}
		if (temp == null || temp.next == null) {
			return;
		}
		CardNode next = temp.next.next;

		temp.next = next;
	}
	
	


	public int removeheadandreturn() {
		if (head == null) {
			return 0;
		}
		CardNode temp = head;
		head = head.getNext();
		int tempint = (int)temp.getData();
		return tempint;
	}
	
	public void addSorted(Object dataToAdd) {

		if (head == null) {
			CardNode newnode = new CardNode(dataToAdd);
			head = newnode;
		}

		else if ((int) dataToAdd > (int) head.getData()) {
			CardNode newnode = new CardNode(dataToAdd);
			newnode.setNext(head);
			head = newnode;
		}

		else {
			CardNode temp = head;
			CardNode previous = null;
			while (temp != null && (int) dataToAdd <= (Integer) temp.getData()) {
				previous = temp;
				temp = temp.getNext();

			}
			if (temp == null) {
				CardNode newnode = new CardNode(dataToAdd);
				previous.setNext(newnode);
			} else {
				CardNode newnode = new CardNode(dataToAdd);
				previous.setNext(newnode);
				newnode.setNext(temp);

			}
		}
	}

	public int returnHead() {
		return (int) head.getData();

	}

	public int size() {
		if (head == null) {
			return 0;
		} else {
			int count = 0;
			CardNode temp = head;
			while (temp != null) {
				temp = temp.getNext();
				count++;
			}
			return count;
		}
	}

	public void remove(Object dataToDelete) {
		if (head == null) {
			System.out.println("nexted lis is empty");
		} else if (size() == 1) {
			head = null; ////////////////
		} else {
			while ((Integer) head.getData() == (Integer) dataToDelete)
				head = head.getNext();

			CardNode temp = head;
			CardNode previous = null;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) dataToDelete) {
					previous.setNext(temp.getNext());
					temp = previous;
				}
				previous = temp;
				temp = temp.getNext();
			}
		}

	}
	
	public void addCardNodeWithPosition(int index,Object new_data) 
	{ 
		CardNode new_node=new CardNode(new_data); 
		if(index==0) 
		{ 
			new_node.setNext(head);
			head=new_node; 
			return; 
		} 
		else
		{
			int count=0; 
			CardNode temp=head,prev=null; 
			while(count<index && temp!=null) 
			{ 
				prev=temp; 
				temp=temp.getNext();	 
				count++; 
			} 
			prev.setNext(new_node);
			new_node.setNext(temp); 
		}
	} 

	public CardNode findWithIndex(int position) 
	{
		CardNode temp = head;
		if (temp == null) return null;
		else
		{
			int count = 0;
			while(temp != null)
			{
				if(count == position) break;
				temp = temp.getNext();
				count++;
			}	
		}
		return temp;
	}
	
	public void removeby1(Object dataToDelete) {
		if(size()==1) {
			return;
		}
		while(head != null && (Integer)head.getData()==(Integer)dataToDelete)
			head = head.getNext();
		CardNode temp = head;
		CardNode previous = null;
		while(temp != null) {
			if((Integer)temp.getData()==(Integer)dataToDelete) {
				previous.setNext(temp.getNext());
				temp=previous;
				break;
			}
			previous = temp;
			temp = temp.getNext();
		}
	}
	
	public CardNode removehead() {
		if (head == null) {
			return null;
		}
		CardNode temp = head;
		head = head.getNext();
		return null;
	}
	
	public void shuffling() 
	{
		Random r = new Random();
		CardNode temp, temp2;
		if (head == null) 
		{
			System.out.println("nexted list is empty");
		} 
		else 
		{
			for(int i = 0; i < 30; i++)
			{
				int randno1 = (int) r.nextInt(size());
				int randno2 = (int) r.nextInt(size());
				temp = findWithIndex(randno1);
				temp2 = findWithIndex(randno2); // n1 n2 n3 n4 n5 n6
				removeCardNodeWithPosition(randno1);
				addCardNodeWithPosition(randno1, temp2.getData());
				removeCardNodeWithPosition(randno2);
				addCardNodeWithPosition(randno2, temp.getData());
				
			}
		}
	}
	
	public int findMax() {
		if (head == null) {
			System.err.println("The Nexted List is empty");
			return Integer.MIN_VALUE;
		} else {
			int maxVal = Integer.MIN_VALUE;

			CardNode temp = head;

			while (temp != null) {
				if ((int) temp.getData() > maxVal) {
					maxVal = (int) temp.getData();
				}
				temp = temp.getNext();
			}
			return maxVal;
		}

	}

	public boolean search(Object data) {
		if (head == null) {
			System.out.println("List is empty");
			return false;
		} else {
			CardNode temp = head;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data)
					return true;
				temp = temp.getNext();
			}
			return false;
		}
	}

	public int counting(Object data) {
		int count = 0;
		if (head == null) {
			System.out.println("List is empty");
			return 0;
		} 
		else 
		{
			CardNode temp = head;
			while (temp != null) 
			{
				if ((Integer) temp.getData() == (Integer) data)
					count ++;
				temp = temp.getNext();
			}
			
		}
		return count;
	}
	
	public int getIndex(Object data) {
		if (head == null) {
			System.out.println("List is empty");
			return 0;
		} else {
			CardNode temp = head;
			int counter = 0;
			while (temp != null) {
				if ((Integer) temp.getData() == (Integer) data) {
					return counter;
				}
				counter++;
				temp = temp.getNext();
			}
			return 0;
		}
	}

	public CardNode getCardNode(String data) {
		return new CardNode(data);
	}

	public CardNode addToPosition(int position, String data) {
		CardNode temp = head;
		if (position < 1) {
			System.out.print("Invalid position");
		}
		if (position == 1) {
			CardNode newCardNode = new CardNode(data);
			newCardNode.next = temp;
			head = newCardNode;
		} else {
			while (position-- != 0) {

				if (position == 1) {

					CardNode newCardNode = getCardNode(data);
					newCardNode.next = temp.next;
					temp.next = newCardNode;

					break;
				}
				temp = temp.next;
			}
			if (position != 1) {
				System.out.print("Position out of range");
			}
		}
		return head;
	}
}