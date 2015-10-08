
#Info Header

**Course:   Algorithms, Part I @ Princeton University on Cousera**
  
By:       Kevin Wayne, Robert Sedgewick

**Coder:    Heng-Yi Lin (Henry Lin) -> site: http://hyl.tech**

**Programming Assignment 1 - Percolation**

#Assessment Summary

    Compilation:  PASSED
    Style:        FAILED (Free to ignore)
    Findbugs:     No potential bugs found.
    API:          PASSED

    Correctness:  26/26 tests passed
    Memory:       8/8 tests passed
    Timing:       9/9 tests passed

    Aggregate score: 100.00% [Correctness: 65%, Memory: 10%, Timing: 25%, Style: 0%]

#Percolation.java

Percolation data type for modeling a percolation system  with the following API:

    public class Percolation {
      public Percolation(int N)               // create N-by-N grid, with all sites blocked
      public void open(int i, int j)          // open site (row i, column j) if it is not open already
      public boolean isOpen(int i, int j)     // is site (row i, column j) open?
      public boolean isFull(int i, int j)     // is site (row i, column j) full?
      public boolean percolates()             // does the system percolate?
      public static void main(String[] args)  // test client (optional)
    }

**Corner cases.**

By convention, the row and column indices i and j are integers between 1 and N, where (1, 1) is the upper-left site: Throw a java.lang.IndexOutOfBoundsException if any argument to open(), isOpen(), or isFull() is outside its prescribed range. The
constructor should throw a java.lang.IllegalArgumentException if N ≤ 0.

**Performance requirements.**

The constructor should take time proportional to N2; all methods should take constant time plus a constant number of calls to the union-find methods union(), find(), connected(), and count().


#PercolationStats.java

Monte Carlo simulation for estimating the percolation threshold

perform a series of computational experiments, create a data type PercolationStats with the following API.

    public class PercolationStats {
      public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
      public double mean()                      // sample mean of percolation threshold
      public double stddev()                    // sample standard deviation of percolation threshold
      public double confidenceLo()              // low  endpoint of 95% confidence interval
      public double confidenceHi()              // high endpoint of 95% confidence interval
      public static void main(String[] args)    // test client (described below)
    }

The constructor should throw a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0.

Also, include a main() method that takes two command-line arguments N and T, performs T independent computational experiments (discussed above) on an N-by-N grid, and prints the mean, standard deviation, and the 95% confidence interval for the percolation threshold. Use StdRandom to generate random numbers; use StdStats to compute the sample mean and standard deviation.

#Reference

Specificaiton:    http://coursera.cs.princeton.edu/algs4/assignments/percolation.html

Checklist:        http://coursera.cs.princeton.edu/algs4/checklists/percolation.html

Assessment Guide: https://class.coursera.org/algs4partI-009/wiki/view?page=Assessments
