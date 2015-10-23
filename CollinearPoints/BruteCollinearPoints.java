/**************************************************************************************
 *  Author:        Heng-Yi Lin (Henry)
 *  Written:       Oct 18, 2015
 *  Last updated:  Oct 18, 2015
 *
 *  Compilation:   javac BruteCollinearPoints.java
 *  Execution:     java BruteCollinearPoints
 *  Dependencies:  Point.java, LineSegment.java
 *  
 *  This program examines 4 points at a time and checks whether they all lie on the 
 *  same line segment, returning all such line segments. 
 *  
 *  Constructor:   BruteCollinearPoints(Point[] points) finds all 4-point line segments
 *  Method:           numberOfSegments() return the number of line segments
 *                 segments() return the line segments
 **************************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdDraw;
//import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    
    private final Point[] pts;
    private List<LineSegment> segList;
    private int segNum = 0;
    
    /**
     * finds all line segments containing 4 points
     * @param points
     * @throws NullPointerException either the argument to the constructor is null or 
     *         if any point in the array is null
     * @throws IllegalArgumentException if the argument to the constructor contains a 
     *         repeated point
     */
    public BruteCollinearPoints(Point[] points) {
        /** CORNER CASE VALIDATION **/
        validate(points);
        pts = points.clone(); // copy input in case of mutating argument
        /** searching line segments. **/
        int index = 0; // outer loop counter
        segList = new ArrayList<LineSegment>();
        Point[] segPoints;
        for (Point point : pts) { // loop through all combinations
            for (int i = index + 1; i < pts.length; i++) {
                for (int j = i + 1; j < pts.length; j++) {
                    if (point.slopeTo(pts[j]) == point.slopeTo(pts[i])) {
                        for (int k = j + 1; k < pts.length; k++) {
                            if (point.slopeTo(pts[k]) == point.slopeTo(pts[i])) {
                                // collinear found!!
                                // findMinAndMax return an sorted point array (max on first, min on last)
                                segPoints = findMinAndMax(point, pts[i], pts[j], pts[k]);
                                segList.add(new LineSegment(segPoints[0], segPoints[3]));
                                segNum++;
                                
                                //ending loop and break to the iterator
                                break;
                            }
                        }
                    }
                }
            }
            index++;
        }
    }
    
    // CORNER CASE VALIDATION
    private void validate(Point[] points) {
        // throws NullPointerException if argument contains null point or itself is null
        if (points == null) throw new NullPointerException("Argument is null.");
        int count = 0; // counter for index of points 
        for (Point point : points) {
            if (point == null) throw new NullPointerException("A point in argument is null.");
            // loop through points and check whether there is a repeated point
            // throws IllegalArgumentException if the argument to the constructor contains a repeated point
            for (int i = count + 1; i < points.length; i++) {
                if (point.compareTo(points[i]) == 0) throw new IllegalArgumentException("Repeated point exists."); 
            }
            count++; 
        }
    }
    
    // helper method to find max and min in a collinear line segment
    private Point[] findMinAndMax(Point p0, Point p1, Point p2, Point p3) {
        Point[] points = {p0, p1, p2, p3};
        Arrays.sort(points, null); // MAX: points[0]; MIN: points[3]
        return points;
    }
    
    /**
     * the number of line segments
     * @return
     */
    public int numberOfSegments() {
        return segNum;
    }
    
    /**
     * the line segments
     * @return
     */
    public LineSegment[] segments() {
        if (segNum != 0)
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
//        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
//        StdOut.println(collinear.numberOfSegments());
//        for (LineSegment segment : collinear.segments()) {
//            StdOut.println(segment);
//            segment.draw();
//        }
//    }

    
}

