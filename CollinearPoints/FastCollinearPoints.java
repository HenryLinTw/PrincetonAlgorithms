/**************************************************************************************
 *  Author:        Heng-Yi Lin (Henry)
 *  Written:       Oct 19, 2015
 *  Last updated:  Oct 19, 2015
 *
 *  Compilation:   javac FastCollinearPoints.java
 *  Execution:     java FastCollinearPoints
 *  Dependencies:  Point.java, LineSegment.java
 *  
 *  This program uses sorting-based solution to find a set of 4 or more collinear points  
 *  on the same line segment, returning all such line segments. 
 *  
 *  Constructor:   FastCollinearPoints(Point[] points) 
 *                    finds all line segments containing 4 or more points
 *  Method:        numberOfSegments() return the number of line segments
 *                 segments() return the line segments
 **************************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {

    private final Point[] pts;
    private List<LineSegment> segList;
    
    /**
     * finds all line segments containing 4 or more points
     * @param points
     */
    public FastCollinearPoints(Point[] points) {
        /** clone input data in case of mutating data **/
        pts = points.clone();
        /** CORNER CASE VALIDATION **/
        validate(pts);
        /** find all line segments containing 4 or more points **/
        /** 1.Think of p as the origin.
            2.For each other point q, determine the slope it makes with p.
            3.Sort the points according to the slopes they makes with p.
            4.Check if any 3 (or more) adjacent points in the sorted order have equal
              slopes with respect to p. If so, these points, together with p, are collinear.**/
        int boundaryIndex; // for boundary test to reduce times of loop
        double slopeTmp; // for reducing repeatedly call slopeTo() refer to same point
        final Point[] iteratorTmp = pts.clone(); // for outer iterator to loop through exactly N point
        List<Point> pointList; // temporary storing list for a set of collinear points
        segList = new ArrayList<LineSegment>(); 
        
        // 1. loop through points as the origin
        for (Point point : iteratorTmp) {
            // 2. sort the array by the slopes of each points
            Arrays.sort(pts, point.slopeOrder());
            // 3. loop through other points, check if any 3 (or more) adjacent points have equal slopes
            boundaryIndex = 0; // variable to save the index of max boundary within same slope
            for (int i = 0; i < pts.length; i++) {
                // boundary test:  avoid loop through points inside boundary of the same slope & array boundary check
                if (((boundaryIndex == 0) || (i > boundaryIndex)) && (i+2 < pts.length)) {
                   // check if 3 adjacent points have the same slope 
                   if ((point.slopeTo(pts[i+2]) == point.slopeTo(pts[i]))) {
                       slopeTmp = point.slopeTo(pts[i]);
                       // init a list with 4 collinear points
                       pointList = new ArrayList<Point>(Arrays.asList(point, pts[i], pts[i+1], pts[i+2])); 
                       // check whether there are more points on the same segment
                       for (int j = i+3; j < pts.length; j++) {
                           if (point.slopeTo(pts[j]) != slopeTmp) {
                               boundaryIndex = j-1;
                               break;
                           }
                           else {
                               pointList.add(pts[j]);
                               boundaryIndex = j;
                           }
                       }
                       // sort collinear points with its coordinate in ascending order
                       pointList.sort(null); 
                       // add a lineSegment only if the iterator is looping the minimal point
                       if (point == pointList.get(0)) {
                           segList.add(new LineSegment(pointList.get(0), pointList.get(pointList.size() - 1)));
                       }
                    }
                }
            }
        }
    }
    
    // CORNER CASE VALIDATION
    private void validate(Point[] points) {
        // throws NullPointerException if argument contains null point or itself is null
        if (points == null) throw new NullPointerException("Argument is null.");
        
        //loop through points and check whether there is null point
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new NullPointerException("A point in argument is null.");
        }
        
        // loop through points and check whether there is a repeated point
        // throws IllegalArgumentException if the argument to the constructor contains a repeated point
        Arrays.sort(points, null);
        for (int i = 0; i < points.length-1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) throw new IllegalArgumentException("Repeated point exists."); 
        }


    }
    
    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        if (segList != null)
            return segList.size();
        else
            return 0;
    }
    
    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        if (segList != null)
            return this.getSegments();
        else
            return new LineSegment[0];
    }
    
    private LineSegment[] getSegments() {
        if (segList.size() > 0) {
            LineSegment[] lineSegArray = new LineSegment[segList.size()];
            segList.toArray(lineSegArray);
            return lineSegArray;
        }
        else
            return new LineSegment[0];
    }
    
    /**
     * Read the N points from a file. This program will draw collinear lines
     * and print end points of each line and number of collinear lines.
     * 
     * unit test -> java BruteCollinearPoint filename.txt
     */
//    public static void main(String[] args) {
//
//        // read the N points from a file
//        In in = new In(args[0]);
//        int N = in.readInt();
//        Point[] points = new Point[N];
//        for (int i = 0; i < N; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
//
//        // draw the points
//        StdDraw.show(0);
//        StdDraw.setXscale(0, 32768);
//        StdDraw.setYscale(0, 32768);
//        for (Point p : points) {
//            p.draw();
//        }
//        StdDraw.show();
//
//        // print and draw the line segments
//        FastCollinearPoints collinear = new FastCollinearPoints(points);
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//        StdOut.println(collinear.numberOfSegments());
//    }
    
}

