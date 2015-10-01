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
 *	Method:			open(int i, int j) open site(row,column)
 *					isOpen(int i, int j) check if site(row,column) is open
 *					isFull(int i, int j) check if site(row,column) is full
 *					percolates() check if the grid percolates
 ******************************************************************************/
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF grid;  // WeightedQuickUnionUF object as N*N grid
    private boolean[] siteState;  // siteState[i] = block(false)/open(true) state of site i
    private int gridSize;
    
    /**
     *   Create N-by-N grid into 1-dimension array with WeightedQuickUnionUF object, and assign all sites blocked in N*N-size array
     *   
     *   @param N the number of sides in grid 
     *   @throws IllegalArgumentException if N ≤ 0
     */
    public Percolation (int N) {
        if(N > 0) { 
        	gridSize = N*N;
        	grid = new WeightedQuickUnionUF(gridSize+2); //Data Structure:  1(virtual top) + N*N grid + 1(virtual bot)
        	//union first row with virtual top, and last row with virtual bot
        	for(int j=1; j<=N; j++){
        		grid.union(0, j); //union first row (index = 1~N) with virtual top (index = 0 in an array of size N*N+2)
        		grid.union(N*N+1, N*(N-1)+j); //union last row (index = (N*(N-1)+1)~N*N) with virtual bot (index = N*N+1 in an array of size N*N+2)
        	}
        	siteState = new boolean[gridSize]; //init memory for site state in N*N sites
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
    	validate(i,j);
    	int index = twoToOneDimensionConverter(i,j);
    	// open site if it's not open, and union with neighboring site
    	if(!isOpen(i,j)){ //open site(i,j) if it isn't open.
    		siteState[index] = true; //open site
    		unionNeighborSites(i, j); // +1 for virtual top in the beginning
    	}
    }
    
    // union neighboring sites
    // 4 scenario: open top/left/right/bottom site
    private void unionNeighborSites(int row, int column){
    	int N = (int) Math.sqrt(gridSize); // calculate N from siteState size
    	int index = twoToOneDimensionConverter(row,column) + 1;
    	// scenario logic
    	if(row-1 >= 1 && isOpen(row-1,column)) // union top site(row-1,column) if site(row,column) have a top site which is open
    		grid.union(index-N, index); 
    	if(row+1 <= N && isOpen(row+1,column)) // union bot site(row+1,column) if site(row,column) have a bot site which is open
			grid.union(index, index+N); 
    	if(column-1 >= 1 && isOpen(row,column-1)) // union left site(row,column-1) if site(row,column) have a left site which is open
			grid.union(index-1, index);
    	if(column+1 <= N && isOpen(row,column+1)) // union bot site(row,column+1) if site(row,column) have a right site which is open
			grid.union(index, index+1);
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
    	validate(i,j);
    	int index = twoToOneDimensionConverter(i,j); 
    	return siteState[index];
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
    	validate(i,j);
    	if(!isOpen(i,j)) return false; //return false if the site is not open -> exclude first row error
    	int index = twoToOneDimensionConverter(i,j) + 1; //index in N*N+2 grid (include virtual top & virtual bot)
    	return grid.connected(0, index); // check if the site is connected with virtual top (0 = virtual top)
    }
        
    // validate if site(i,j) is a valid site, and throw IndexOutOfBoundsException if it's not
    private void validate(int i, int j){
    	int N = (int) Math.sqrt(gridSize);
        if ( (i < 1 || i > N) || (j < 1 || j > N)) 
            throw new IndexOutOfBoundsException("site(" + i + "," + j + ") is not between (1,1) and (" + N + "," + N + ")");  
    }

    // convert index of 2d array to 1d array 
    private int twoToOneDimensionConverter(int row, int column){
    	// N*N grid(x,y) -> 1d array(i) in N*N size
    	return ((int) Math.sqrt(gridSize) * (row-1) + (column-1)); //eg. (1,1)->0, (1,2)->1, ..., (N,N)-> N^2-1
    }
        
    /**
    *   check if the grid percolates?
    *   
    *   @return true if it percolates; false otherwise
    */ 
    public boolean percolates() {
    	int virtualBot = gridSize + 1; // grid structure: 1(VirtualTop) + N*N grid + 1(VirtualBot) -> index of VB is grid size + 1
    	return grid.connected(0, virtualBot); //it percolates if virtual top(index = 0) is connected to virtual bot
    }
    
    /**
    *   (Optional) test client
    */ 
    public static void main(String[] args){
    }
}
