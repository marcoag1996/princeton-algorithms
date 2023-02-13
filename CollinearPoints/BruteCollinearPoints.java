import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        // check for duplicate points
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        // check for duplicates
        checkDuplicates(sortedPoints);

        int n = points.length;
        List<LineSegment> list = new LinkedList<>();

        for (int a = 0; a < n - 3; a++) { // N-3 bc we are comparing this point with the next three points
            Point ptA = sortedPoints[a];
            for (int b = a + 1; b < n - 2; b++) {
                Point ptB = sortedPoints[b];
                double slopeAB = ptA.slopeTo(ptB);
                for (int c = b + 1; c < n - 1; c++) {
                    Point ptC = sortedPoints[c];
                    double slopeAC = ptA.slopeTo(ptC);
                    if (slopeAB == slopeAC) {
                        for (int d = c + 1; d < n; d++) {
                            Point ptD = sortedPoints[d];
                            double slopeAD = ptA.slopeTo(ptD);
                            if (slopeAB == slopeAD) {
                                // add segment to list
                                list.add(new LineSegment(ptA, ptD));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = list.toArray(new LineSegment[0]); // LineSegment[0] represents the type of the array to hold the collection
    }

    private void checkNull(Point[] points) {
        // check for null points
        if (points == null) {
            throw new IllegalArgumentException("points cannot be null");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("point cannot be null");
            }
        }
    }

    private void checkDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found");
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    // Client sample provided by Princeton University
    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
