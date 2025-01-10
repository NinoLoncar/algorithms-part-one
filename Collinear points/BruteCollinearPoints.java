import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BruteCollinearPoints {

    private final List<LineSegment> segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        checkPoints(points);
        Arrays.sort(points);
        for (int p = 0; p < points.length; p++) {
            Comparator<Point> pComparator = points[p].slopeOrder();
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {
                        if (pComparator.compare(points[q], points[r]) == 0
                                && pComparator.compare(points[q], points[s]) == 0) {
                            LineSegment segment = new LineSegment(points[p], points[s]);
                            segments.add(segment);
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private void checkPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();
        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
