/******************************************************************************
 *  Author:        Heng-Yi Lin
 *  Written:       9/29/2015
 *  Last updated:  10/2/2015
 *
 *  Compilation:   javac Percolation.java
 *  Execution:     java Percolation
 *  Dependencies:  WeightedQuickUnionUF.java
 *  
 *  Data Type "Percolation" for modeling percolation systems
 *  
 *  Constructor: 	Percolation(int N) init a N*N grid model
 *	Method:		open(int i, int j) open site(row,column)
 *			isOpen(int i, int j) check if site(row,column) is open
 *			isFull(int i, int j) check if site(row,column) is full
 *			percolates() check if the grid percolates
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private int N; // grid length
	private WeightedQuickUnionUF grid;  // WeightedQuickUnionUF object as N*N grid
	private WeightedQuickUnionUF bwUF;  // WeightedQuickUnionUF object as N*N grid for backwash bug
	private boolean[] siteState;  // siteState[i] = block(false)/open(true) state of site i
    private int vTop; // virtual top
    private int vBot; // virtual bot
    
    
    /**
     *   Create N-by-N grid into 1-dimension array with WeightedQuickUnionUF object, and assign all sites blocked in N*N-size array
     *   
     *   @param N the number of sides in grid 
     *   @throws IllegalArgumentException if N ≤ 0
     */
    public Percolation (int N) {
        if(N > 0) { 
        	this.N = N;
        	this.vTop = 0;
        	this.vBot = N * N + 1;
        	this.grid = new WeightedQuickUnionUF(N * N + 2); //Data Structure:  1(virtual top) + N*N grid + 1(virtual bot)
        	this.bwUF = new WeightedQuickUnionUF(N * N + 2);
        	this.siteState = new boolean[N * N]; //init memory for site state in N*N sites
        } else 
            throw new java.lang.IllegalArgumentException("N is less than 1, couldn't init a grid.");
    }
        
     /**
     *   open site (row i, column j) if it is not open already
     *
     *	 @param i row index of the site (1 <= i <= N)
     *	 @param	j column index of the site (1 <= i <= N) 
     *   @throws IndexOutOfBoundsException from validate() if any argument is outside its prescribed range.
     */
    public void open(int i, int j) {
    	validate(i, j);
    	// open site if it's not open, and union with neighboring site
    	if(!isOpen(i, j)){ //open site(i,j) if it isn't open.
        	int siteIndex = twoToOneDimensionConverter(i, j) + 1;
        	this.siteState[siteIndex - 1] = true; //open site
    		if (i == 1)	{ //connect to virtual top if the site is on first row
    			this.grid.union(this.vTop, siteIndex);
    			this.bwUF.union(this.vTop, siteIndex);
    		}
    		if (i == this.N) //connect to virtual bot if the site is on last row
    			this.grid.union(this.vBot, siteIndex);
    		unionNeighborSites(i, j); 
    	}
    }
    
    // union neighboring sites
    // 4 scenario: open top/left/right/bottom site
    private void unionNeighborSites(int row, int column){
    	int index = twoToOneDimensionConverter(row, column) + 1; // +1 for virtual top in the beginning
    	// scenario logic
    	// union top site(row-1,column) if site(row,column) have a top site which is open
    	if(row - 1 >= 1 && isOpen(row - 1, column)) {
    		this.grid.union(index - this.N, index); 
    		this.bwUF.union(index - this.N, index); 
    	}
    	// union bot site(row+1,column) if site(row,column) have a bot site which is open
    	if(row + 1 <= this.N && isOpen(row + 1, column)) {
			this.grid.union(index, index + N); 
			this.bwUF.union(index, index + N); 
    	}
    	// union left site(row,column-1) if site(row,column) have a left site which is open
    	if(column - 1 >= 1 && isOpen(row, column - 1)) {
			this.grid.union(index - 1, index);
			this.bwUF.union(index - 1, index);
    	}
    	// union bot site(row,column+1) if site(row,column) have a right site which is open
    	if(column + 1 <= N && isOpen(row, column + 1)) {
			this.grid.union(index, index + 1);
			this.bwUF.union(index, index + 1);
    	}
    }
    
     /**
     *   check if the site (row i, column j) is open?
     *   
     *	 @param i row index of the site (1 <= i <= N)
     *	 @param	j column index of the site (1 <= i <= N) 
     *	 @return TRUE if the site is open; FALSE if it's not
     *   @throws IndexOutOfBoundsException from validate() if any argument is outside its prescribed range.
     */ 
    public boolean isOpen(int i, int j) {
    	validate(i, j);
    	int index = twoToOneDimensionConverter(i, j); 
    	return this.siteState[index];
    }
    
     /**
     *   check if the site (row i, column j) is full?
     *   
     *	 @param i row index of the site (1 <= i <= N)
     *	 @param	j column index of the site (1 <= i <= N) 
     *	 @return TRUE if the site is full; FALSE if it's not
     *   @throws IndexOutOfBoundsException from validate() if any argument is outside its prescribed range.
     */ 
    public boolean isFull(int i, int j) {
    	validate(i, j);
    	int index = twoToOneDimensionConverter(i, j) + 1; //index in N*N+2 grid (include virtual top & virtual bot)
    	return this.bwUF.connected(this.vTop, index); // check if the site is connected with virtual top (0 = virtual top)
    }
        
    // validate if site(i,j) is a valid site, and throw IndexOutOfBoundsException if it's not
    private void validate(int i, int j){
        if ( (i < 1 || i > this.N) || (j < 1 || j > this.N)) 
            throw new IndexOutOfBoundsException("site(" + i + "," + j + ") is not between (1,1) and (" + this.N + "," + this.N + ")");  
    }

    // convert index of 2d array to 1d array 
    private int twoToOneDimensionConverter(int row, int column){
    	// N*N grid(x,y) -> 1d array(i) in N*N size
    	return (this.N * (row - 1) + (column - 1)); //eg. (1,1)->0, (1,2)->1, ..., (N,N)-> N^2-1
    }
        
    /**
    *   check if the grid percolates?
    *   
    *   @return true if it percolates; false otherwise
    */ 
    public boolean percolates() {
    	return this.grid.connected(vTop, vBot); //it percolates if virtual top(index = 0) is connected to virtual bot
    }
    
    /**
    *   (Optional) test client
    */ 
    public static void main(String[] args){
    }
}
