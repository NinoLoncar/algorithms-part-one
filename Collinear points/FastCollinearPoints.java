import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private final List<LineSegment> segments = new ArrayList<>();

    private final List<Point[]> segmentPoints = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        checkPoints(points);
        Arrays.sort(points);
        for (Point origin : points) {
            Point[] pointsCopy = Arrays.copyOf(points, points.length);
            Arrays.sort(pointsCopy, origin.slopeOrder());
            ArrayList<Point> collinearPoints = new ArrayList<>();
            collinearPoints.add(origin);
            for (int i = 1; i < pointsCopy.length; i++) {
                if (origin.slopeTo(pointsCopy[i]) == origin.slopeTo(pointsCopy[i - 1])) {
                    collinearPoints.add(pointsCopy[i]);
                } else {
                    if (collinearPoints.size() >= 4) {

                        addSegment(collinearPoints);
                    }
                    collinearPoints = new ArrayList<>();
                    collinearPoints.add(origin);
                    collinearPoints.add(pointsCopy[i]);
                }
            }
            if (collinearPoints.size() >= 4) {
                addSegment(collinearPoints);
            }
        }
    }

    public static void main(String[] args) {


        Point[] points = new Point[10];
        points[0] = new Point(19000, 10000);
        points[1] = new Point(18000, 10000);
        points[2] = new Point(32000, 10000);
        points[3] = new Point(21000, 10000);
        points[4] = new Point(1234, 5678);
        points[5] = new Point(14000, 10000);
        points[6] = new Point(1234, 12000);
        points[7] = new Point(1234, 16000);
        points[8] = new Point(1234, 5672);
        points[9] = new Point(1234, 5671);
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private void addSegment(ArrayList<Point> collinearPoints) {
        collinearPoints.sort(null);
        Point first = collinearPoints.get(0);
        Point last = collinearPoints.get(collinearPoints.size() - 1);
        if (!segmentPresent(first, last)) {
            segments.add(new LineSegment(first, last));
            Point[] segmentPoint = {first, last};
            segmentPoints.add(segmentPoint);
        }

    }

    private boolean segmentPresent(Point p1, Point p2) {
        for (Point[] segmentPoints : segmentPoints) {
            if ((segmentPoints[0].equals(p1) && segmentPoints[1].equals(p2)) ||
                    (segmentPoints[0].equals(p2) && segmentPoints[1].equals(p1))) {
                return true;
            }
        }
        return false;
    }

    private void checkPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        checkNull(points[0]);
        for (int i = 1; i < points.length; i++) {
            checkNull(points[i]);
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkNull(Point point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
    }

}
