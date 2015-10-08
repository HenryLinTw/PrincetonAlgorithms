/**************************************************************************************
 *  Author:        Heng-Yi Lin (Henry)
 *  Written:       Oct 7, 2015
 *  Last updated:  Oct 7, 2015
 *
 *  Compilation:   javac Deque.java
 *  Execution:     java Deque
 *  
 *  A double-ended queue or deque (pronounced "deck") is a generalization of a stack and 
 *  a queue that supports adding and removing items from either the front or the back of 
 *  the data structure. Create a generic data type Deque that implements the following 
 *  API:
 *  
 *  Constructor: public Deque() 						         // construct an empty deque	
 *	Method:	public boolean isEmpty()                 // is the deque empty?
 *	  			public int size()                        // return the number of items on the deque
 * 				  public void addFirst(Item item)          // add the item to the front
 * 				  public void addLast(Item item)           // add the item to the end
 * 				  public Item removeFirst()                // remove and return the item from the front
 * 				  public Item removeLast()                 // remove and return the item from the end
 * 				  public Iterator<Item> iterator()         // return an iterator over items in order from front to end
 * 				  public static void main(String[] args)   // unit testing
 *****************************************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	
	private DoublyNode first;
	private DoublyNode last;
	private int dequeSize;
	
	// Node of Doubly-linked List
	private class DoublyNode {
		 Item item;
		 DoublyNode prev;
		 DoublyNode next;
	}
	
	/**
	 * construct an empty deque
	 */
	public Deque() {
		this.first = null;
		dequeSize = 0;
	}
	
	/**
	 * is the deque empty?
	 * @return true if the deque is empty; vice versa
	 */
	public boolean isEmpty() {
		return dequeSize == 0;
	}
	
	/**
	 * @return the number of items on the deque
	 */
	public int size() {
		return dequeSize;
	}
	
	/**
	 * add the item to the front
	 * @param item
	 * @throws NullPointerException if the param "item" is null
	 */
	public void addFirst(Item item) {
		if (item == null) throw new NullPointerException("Item you added is null.");
		if (dequeSize == 0) {
			first = new DoublyNode();
			first.item = item;
			last = first;
		} else {
			DoublyNode oldFirst = first;
			first = new DoublyNode();
			first.item = item;
			first.next = oldFirst;
			oldFirst.prev = first;
		}
		dequeSize++;
	}
	
	/**
	 * add the item to the end
	 * @param item
	 * @throws NullPointerException if the param "item" is null
	 */
	public void addLast(Item item) {
		if (item == null) throw new NullPointerException("Item you added is null.");
		if (dequeSize == 0) {
			last = new DoublyNode();
			last.item = item;
			first = last;
		} else {
			DoublyNode oldLast = last;
			last = new DoublyNode();
			last.item = item;
			last.prev = oldLast;
			oldLast.next = last;
		}
		dequeSize++;
		
	}
	
	/**
	 * remove and return the item from the front
	 * @throws NoSuchElementException if the Deque is empty
	 * @return the item from the front
	 */
	public Item removeFirst() {
		if (dequeSize == 0) throw new NoSuchElementException("There is no item in Deque.");
		Item temp = first.item;
		if (dequeSize == 1) {
			first = null;
			last = null;
		} else {
			first = first.next;
			first.prev = null;
		}
		dequeSize--;
		return temp;
	}
	
	/**
	 * remove and return the item from the end
	 * @throws NoSuchElementException if the Deque is empty
	 * @return the item from the end
	 */
	public Item removeLast() {
		if (dequeSize == 0) throw new NoSuchElementException("There is no item in Deque.");
		Item temp = last.item;
		if (dequeSize == 1) {
			last = null;
			first = null;
		} else {
			last = last.prev;
			last.next = null;
		}
		dequeSize--;
		return temp;
	}
	
	/**
	 * @throws UnsupportedOperationException if the client calls the remove() method in the 
	 *         iterator
	 * @throws NoSuchElementException if the client calls the next() method in the iterator 
	 *         and there are no more items to return.
	 * @return an iterator over items in order from front to end
	 */
	public Iterator<Item> iterator() { return new ListIterator(); }
	
	// @throws UnsupportedOperationException
	// if the client calls the remove() method in the iterator
	// @throws NoSuchElementException
	// if the client calls the next() method in the iterator and there are no more items to
	// return.
	private class ListIterator implements Iterator<Item> {
		private DoublyNode current = first;
		public boolean hasNext() { return current != null; }
		public void remove() { throw new UnsupportedOperationException("remove() is not supported."); }
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException("There are no more items.");
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	/**
	 * unit testing
	 * @param args
	 */
	public static void main(String[] args) {
	}

}

