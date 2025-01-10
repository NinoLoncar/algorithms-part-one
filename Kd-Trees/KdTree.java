import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;


public class KdTree {
    private Node root;
    private int size;

    private List<Point2D> pointsInRect;
    private Point2D nearestPoint;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        checkArgument(p);
        if (!contains(p)) {
            root = put(root, p, 0);
            size++;
        }

    }

    private Node put(Node x, Point2D p, int depth) {
        if (x == null) return new Node(p);
        int cmp;
        if (depth % 2 == 0)
            cmp = Double.compare(p.x(), x.point.x());
        else
            cmp = Double.compare(p.y(), x.point.y());

        if (cmp < 0)
            x.left = put(x.left, p, depth + 1);
        else if (cmp > 0)
            x.right = put(x.right, p, depth + 1);
        else
            x.right = put(x.right, p, depth + 1);
        return x;
    }

    public boolean contains(Point2D p) {
        checkArgument(p);
        Node x = root;
        int depth = 0;
        while (x != null) {
            int cmp;
            if (depth % 2 == 0)
                cmp = Double.compare(p.x(), x.point.x());
            else
                cmp = Double.compare(p.y(), x.point.y());
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else {
                if (p.equals(x.point))
                    return true;
                x = x.right;
            }
            depth++;
        }
        return false;
    }

    public void draw() {
        drawNode(root, 0, 0, 0, 1, 1);
        StdDraw.show();
    }

    private void drawNode(Node x, int depth, double startX, double startY, double endX, double endY) {
        if (x == null) return;

        if (depth % 2 == 0) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(x.point.x(), startY, x.point.x(), endY);
            drawNode(x.left, depth + 1, startX, startY, x.point.x(), endY);
            drawNode(x.right, depth + 1, x.point.x(), startY, endX, endY);

        } else {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(startX, x.point.y(), endX, x.point.y());
            drawNode(x.left, depth + 1, startX, startY, endX, x.point.y());
            drawNode(x.right, depth + 1, startX, x.point.y(), endX, endY);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.point(x.point.x(), x.point.y());

    }

    private void checkArgument(Object o) {
        if (o == null) throw new IllegalArgumentException();
    }

    public Iterable<Point2D> range(RectHV rect) {
        checkArgument(rect);
        pointsInRect = new ArrayList<>();
        getInRange(root, rect, 0);
        return pointsInRect;
    }

    private void getInRange(Node x, RectHV rect, int depth) {
        if (x == null) return;
        if (rect.contains(x.point))
            pointsInRect.add(x.point);
        if (depth % 2 == 0) {
            if (x.point.x() < rect.xmin())
                getInRange(x.right, rect, depth + 1);
            else if (x.point.x() > rect.xmax())
                getInRange(x.left, rect, depth + 1);
            else {
                getInRange(x.right, rect, depth + 1);
                getInRange(x.left, rect, depth + 1);
            }
        } else {
            if (x.point.y() < rect.ymin())
                getInRange(x.right, rect, depth + 1);
            else if (x.point.y() > rect.ymax())
                getInRange(x.left, rect, depth + 1);
            else {
                getInRange(x.right, rect, depth + 1);
                getInRange(x.left, rect, depth + 1);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        checkArgument(p);
        if (size == 0) return null;
        nearestPoint = root.point;
        lookForNearest(root, p, 0);
        return nearestPoint;
    }

    private void lookForNearest(Node x, Point2D query, int depth) {

        if (x == null) return;
        if (x.point.distanceTo(query) < nearestPoint.distanceTo(query))
            nearestPoint = x.point;

        boolean searchedLeft;

        if (depth % 2 == 0) {
            if (query.x() < x.point.x()) {
                lookForNearest(x.left, query, depth + 1);
                searchedLeft = true;
            } else {
                lookForNearest(x.right, query, depth + 1);
                searchedLeft = false;
            }
            double distanceFromRectangle = Math.abs(query.x() - x.point.x());
            if (distanceFromRectangle < nearestPoint.distanceTo(query)) {
                if (searchedLeft)
                    lookForNearest(x.right, query, depth + 1);
                else
                    lookForNearest(x.left, query, depth + 1);
            }
        } else {
            if (query.y() < x.point.y()) {
                lookForNearest(x.left, query, depth + 1);
                searchedLeft = true;
            } else {
                lookForNearest(x.right, query, depth + 1);
                searchedLeft = false;
            }
            double distanceFromRectangle = Math.abs(query.y() - x.point.y());
            if (distanceFromRectangle < nearestPoint.distanceTo(query)) {
                if (searchedLeft)
                    lookForNearest(x.right, query, depth + 1);
                else
                    lookForNearest(x.left, query, depth + 1);
            }
        }
    }


    private class Node {
        private final Point2D point;
        private Node left;
        private Node right;

        public Node(Point2D point) {
            this.point = point;
            left = null;
            right = null;
        }
    }

    public static void main(String[] args) {
        KdTree p = new KdTree();
        p.insert(new Point2D(0.7, 0.2));
        p.insert(new Point2D(0.5, 0.4));
        p.insert(new Point2D(0.8, 0.1));
        p.insert(new Point2D(0.2, 0.3));
        p.insert(new Point2D(0.4, 0.8));
        p.draw();
    }
}
