/**************************************************************************************
 *  Author:        Heng-Yi Lin (Henry)
 *  Written:       Oct 2, 2015
 *  Last updated:  Oct 2, 2015
 *
 *  Compilation:   javac PercolationStats.java
 *  Execution:     java PercolationStats N T
 *  			   	N: numbers of the side of grid; T: times of experiments
 *  Dependencies:  Percolation.java StdRandom.java StdStats.java StdOut.java
 *  
 *  Monte Carlo simulation for estimating the percolation threshold
 *  
 *  This program takes two command-line arguments N and T, performs T independent 
 *  computational experiments (discussed above) on an N-by-N grid, and prints the mean, 
 *  standard deviation, and the 95% confidence interval for the percolation threshold.
 **************************************************************************************/

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;


public class PercolationStats {
	
	private Percolation[] testGrid;
	private double[] probability;
	
	/**
	 * perform T independent experiments on an N-by-N grid
	 * 
	 * @param N number of the side of grid -> e.g. N*N grid
	 * @param T time of experiments 
	 * @throws IllegalArgumentException if N or T <= 0
	 */
	public PercolationStats(int N, int T) {
		if(N > 0 && T > 0) { // perform T exps with N*N grid if N(grid size) and T(time of experiments) > 0 
			testGrid = new Percolation[T]; // init array to store model for T experiments
			probability = new double[T]; // init array to store p for T experiments
			int row,column,openSitesNum; // row and column for site, and number of open sites 
			for(int i = 0; i < T; i++) {
				testGrid[i] = new Percolation(N); // init new N*N grid model
				openSitesNum = 0; // init count for number of open sites
				do{ // keep running till it percolates
					row = StdRandom.uniform(1, N+1); // assign random row in [1, N+1)
					column = StdRandom.uniform(1, N+1); // assign random column in [1, N+1)
					if(!testGrid[i].isOpen(row, column)){ //if the site isn't open
						testGrid[i].open(row, column); // open site(row, column)
						openSitesNum++; // number of open sites + 1
					}
				}while(!testGrid[i].percolates());
				probability[i] = ((double)openSitesNum/(N*N)); // save probability to array for each experiment				
			}
		} else // throw IllegalArgumentException if N or T <= 0
			throw new IllegalArgumentException("Both N and T should be greater than 0 (exclusive)");
	}
	   
	/**
	 * calculate sample mean of percolation threshold
	 * 
	 * @return sample mean of percolation threshold
	 */
	public double mean() {
		return StdStats.mean(probability);
	}
	   
	/**
	 * calculate sample standard deviation of percolation threshold
	 * 
	 * @return standard deviation of percolation threshold
	 */
	public double stddev() {
		return StdStats.stddev(probability);
	}
	   
	/**
	 * calculate low endpoint of 95% confidence interval
	 * 
	 * @return  low endpoint of 95% confidence interval
	 */
	public double confidenceLo() {
		return (mean() - (1.96 * stddev()) / Math.sqrt(probability.length));
	}
	   
	/**
	 * calculate high endpoint of 95% confidence interval
	 * 
	 * @return high endpoint of 95% confidence interval
	 */
	public double confidenceHi() {
		return (mean() + (1.96 * stddev()) / Math.sqrt(probability.length));
	}

	/**
	 *	takes two command-line arguments N and T, performs T independent computational experiments
	 *  on an N-by-N grid, and prints the mean, standard deviation, and the 95% confidence interval 
	 *  for the percolation threshold.
	 *   
	 * @param args-> N: numbers of the side of grid
	 * 				 T: times of experiments
	 */
	public static void main(String[] args) {
		int N = 2; // default 2*2 grid
		int T = 30; // default 30 times
		if(args.length == 2){
			N = Integer.parseInt(args[0]);
			T = Integer.parseInt(args[1]);
		}
		PercolationStats MonteCarloSimulation = new PercolationStats(N, T);
		StdOut.println("mean\t\t\t= " + MonteCarloSimulation.mean());
		StdOut.println("stddev\t\t\t= " + MonteCarloSimulation.stddev());
		StdOut.println("95% confidence interval = " + MonteCarloSimulation.confidenceLo() + ", " + MonteCarloSimulation.confidenceHi());
		
	}
}

