package greedy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by John on 10.01.2017.
 */
public class NeedleProblem {
    public static void main(String[] args) {
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(Point.leftPoint(0), Point.rightPoint(2)));
        segments.add(new Segment(Point.leftPoint(1), Point.rightPoint(7)));
        segments.add(new Segment(Point.leftPoint(2), Point.rightPoint(6)));
        segments.add(new Segment(Point.leftPoint(5), Point.rightPoint(14)));
        segments.add(new Segment(Point.leftPoint(8), Point.rightPoint(16)));

        System.out.println(getNecessaryPoints(segments));
    }

    static List<Point> pointify(List<Segment> segments) {
        List<Point> points = new ArrayList<>();
        for(Segment s : segments) {
            points.add(s.left);
            points.add(s.right);
        }
        points.sort(new PointComparator());
        return points;
    }

    static List<Integer> getNecessaryPoints(List<Segment> segments) {
        List<Integer> result = new ArrayList<>();
        List<Segment> processing = new LinkedList<>();
        List<Point> points = pointify(segments);

        int remainingSegments = segments.size();
        for(Point point : points) {
            if(point.originSegment.processed)
                continue;

            if(point.left)
                processing.add(point.originSegment);

            if(point.right) {
                result.add(point.value);
                processing.forEach(segment -> segment.processed = true);
                processing.clear();
            }
        }
        return result;
    }
}

class Segment {
    Point left,right;
    boolean processed = false;

    Segment(Point left, Point right) {
        addLeft(left);
        addRight(right);
    }

    void addLeft(Point left) {
        left.originSegment = this;
        this.left = left;
    }

    void addRight(Point right) {
        right.originSegment = this;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.value + ", " + right.value + "]";
    }
}

class Point {
    Integer value;
    Boolean left, right;
    Segment originSegment;

    private Point(int value, boolean left, boolean right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    static Point leftPoint(int value) {
        return new Point(value, true, false);
    }

    static Point rightPoint(int value) {
        return new Point(value, false, true);
    }

    @Override
    public String toString() {
        return value + " " + (left ? "left" : "") + (right ? "right" : "") + " of " + originSegment.toString();
    }
}

class PointComparator implements Comparator<Point> {
    @Override
    public int compare(Point p1, Point p2) {
        int result;
        if((result = p1.value.compareTo(p2.value)) != 0) return result;
        if((result = p1.left.compareTo(p2.left)) != 0) return -result;
        if((result = p2.right.compareTo(p2.right)) != 0) return -result;
        return result;
    }
}
