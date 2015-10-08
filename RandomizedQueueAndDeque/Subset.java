/**************************************************************************************
 *  Author:        Heng-Yi Lin (Henry)
 *  Written:       Oct 8, 2015
 *  Last updated:  Oct 8, 2015
 *
 *  Compilation:   javac Subset.java
 *  Execution:     java echo "A B C D E F G H I" | java Subset 3
 *  
 *  This program takes a command-line integer k; reads in a sequence of N strings from 
 *  standard input using StdIn.readString(); and prints out exactly k of them, uniformly 
 *  at random.
 **************************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Subset {

	public static void main(String[] args) {

		int outputNum = 0; // init variable for number of output
		outputNum = Integer.parseInt(args[0]); // assign from user input
		String[] s = StdIn.readAllStrings(); // read in a sequence of N strings
		
		// run program only if 0 <= number of output <= number of N strings
		if(!(outputNum >= 0 && outputNum <= s.length)) return; 

		// for uniformly at random, create an array to indicate whether the string 
		// has been chosen or not.
		boolean[] chosenIndicator = new boolean[s.length];
		int randomIndex; // init a random index
		int outputTime = 0; // counter for the loop
		// init a Deque Object for saving k strings
		Deque<String> stringDeque = new Deque<String>(); 

		// loop k(outputNum) times, add strings into Deque, uniformly at random.
		// Also, guarantee that every string only output at most once.
		while (outputTime < outputNum) { 
			randomIndex = StdRandom.uniform(0, s.length);
			// check if the string has been chosen, 
			// and add it into Deque if it hasn't been chosen.
			if (chosenIndicator[randomIndex] != true) { 
				stringDeque.addFirst(s[randomIndex]);
				chosenIndicator[randomIndex] = true;
				outputTime++;
			}
		}
		
		for (int i = 0; i < outputNum; i++)
			StdOut.println(stringDeque.removeLast());
	}
}

