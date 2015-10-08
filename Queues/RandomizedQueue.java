/*****************************************************************************************************************************
 *  Author:        Heng-Yi Lin (Henry)
 *  Written:       Oct 7, 2015
 *  Last updated:  Oct 7, 2015
 *
 *  Compilation:   javac RandomizedQueue.java
 *  Execution:     java RandomizedQueue
 *  
 *  A randomized queue is similar to a stack or queue, except that the item removed is 
 *  chosen uniformly at random from items in the data structure. Create a generic data 
 *  type RandomizedQueue that implements the following API:
 *  
 *  Constructor:    public RandomizedQueue() 		     // construct an empty randomized queue	
 *	Method:	    public boolean isEmpty()                 // is the queue empty?
 *     		    public int size()                        // return the number of items on the queue
 *		    public void enqueue(Item item)           // add the item
 *		    public Item dequeue()                    // remove and return a random item
 *		    public Item sample()                     // return (but do not remove) a random item
 *		    public Iterator<Item> iterator()         // return an independent iterator over items in random order
 *		    public static void main(String[] args)   // unit testing
 *****************************************************************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private Item[] queue;
	private int queueSize;
	private int randomIndex;
	
	
	/**
	 * construct an empty randomized queue
	 */
	public RandomizedQueue() {
		this.queue = (Item[]) new Object[2];
		this.queueSize = 0;
	}
	
	/**
	 * is the queue empty?
	 * @return
	 */
	public boolean isEmpty() {
		return this.queueSize == 0;
	}
	
	/**
	 * return the number of items on the queue
	 * @return
	 */
	public int size() {
		return this.queueSize;
	}
	
	/**
	 * add the item
	 * @param item
	 * @throws NullPointerException if the client attempts to add a null item
	 */
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException();
		if (this.queueSize == this.queue.length) resize(2 * queue.length);
		this.queue[this.queueSize++] = item;
	}
	
	/**
	 * remove and return a random item
	 * @throws NoSuchElementException if the client attempts to dequeue an item from an empty queue
	 * @return
	 */
	public Item dequeue() {
		if (this.queueSize == 0) throw new NoSuchElementException();
		if (this.queueSize == this.queue.length / 4) resize(this.queue.length / 2);
		Item dequeuedItem = sample(); 
		// switch randomly chosen item with last item in the array
		this.queue[randomIndex] = this.queue[queueSize - 1]; 
		this.queue[--queueSize] = null;
		return dequeuedItem;
	}
	
	// resize queue
	private void resize(int capacity) {
		Item[] newQueue = (Item[]) new Object[capacity];
		for(int i = 0; i < this.queueSize; i++)
			newQueue[i] = this.queue[i];
		this.queue = newQueue;
	}
	
	/**
	 * return (but do not remove) a random item
	 * @throws NoSuchElementException if the client attempts to sample an item from an empty queue
	 * @return
	 */
	public Item sample() {
		if (queueSize == 0) throw new NoSuchElementException();
		randomIndex = StdRandom.uniform(queueSize);
		return queue[randomIndex];
	}
	
	/**
	 * return an independent iterator over items in random order
	 */
	public Iterator<Item> iterator()  { return new QueueIterator(); }
	
	private class QueueIterator implements Iterator<Item> {
		private int outputCount;
		private Item[] iteratorArray;
		
		public QueueIterator() { 
			this.outputCount = 0; 
			iteratorArray = queue.clone();
			if(queueSize > 0)
				StdRandom.shuffle(iteratorArray, 0, queueSize - 1);
		}
		public boolean hasNext() { return this.outputCount < queueSize; }
		public void remove() { throw new UnsupportedOperationException(); }
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item randomItem = iteratorArray[outputCount];
			outputCount++;
			return randomItem;
		}
	}
	
	/**
	 * unit testing
	 * @param args
	 */
	public static void main(String[] args) {
	}
}

