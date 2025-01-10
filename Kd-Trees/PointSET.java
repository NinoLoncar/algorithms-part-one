import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;


public class PointSET {
    private final SET<Point2D> pointSet;

    public PointSET() {
        pointSet = new SET<>();
    }

    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    public int size() {
        return pointSet.size();
    }

    public void insert(Point2D p) {
        checkArgument(p);
        pointSet.add(p);
    }

    public boolean contains(Point2D p) {
        checkArgument(p);
        return pointSet.contains(p);
    }

    public void draw() {
        StdDraw.setPenRadius(0.01);
        pointSet.forEach(point -> {
            StdDraw.point(point.x(), point.y());
        });
        StdDraw.show();
    }

    private void checkArgument(Object o) {
        if (o == null) throw new IllegalArgumentException();
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArgument(rect);
        List<Point2D> pointsInRect = new ArrayList<>();
        pointSet.forEach(point -> {
            if (rect.contains(point)) {
                pointsInRect.add(point);
            }
        });
        return pointsInRect;
    }

    public Point2D nearest(Point2D p) {
        checkArgument(p);
        if (pointSet.isEmpty()) return null;
        Point2D nearestPoint = null;
        for (Point2D point : pointSet) {
            if (nearestPoint == null) {
                nearestPoint = point;
            } else if (nearestPoint.distanceTo(p) > point.distanceTo(p)) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }


    public static void main(String[] args) {
        PointSET p = new PointSET();
        p.insert(new Point2D(0.7, 0.2));
        p.insert(new Point2D(0.5, 0.4));
        p.insert(new Point2D(0.8, 0.1));
        p.insert(new Point2D(0.2, 0.3));
        p.insert(new Point2D(0.4, 0.8));
        p.draw();
    }
}
