import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by slazakovich on 9/15/2016.
 */
public class FastCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    private Point[] pointsCopy;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new NullPointerException();
        }
        for (Point p : points) {
            if (p == null) throw new NullPointerException();
        }

        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(
                            String.format("Points with indexes %d and %d with values %s and %s are the same",
                                    i, j, points[i], points[j]));
                }
            }
        }

        pointsCopy = Arrays.copyOf(points, points.length);

        for (int i = 0; i < points.length; i++) {
            Point originPoint = points[i];

            Comparator<Point> pointComparator = originPoint.slopeOrder();
            Arrays.sort(pointsCopy, 0, points.length, pointComparator);

            ArrayList<Point> collPointsList = new ArrayList<Point>();

            int collPointsCount = 0;

            double currentSlope = originPoint.slopeTo(pointsCopy[0]);
            double slope = currentSlope;
            for (int j = 1; j < pointsCopy.length; j++) {
                slope = originPoint.slopeTo(pointsCopy[j]);

                if (slope == currentSlope) {
                    collPointsCount++;
                    collPointsList.add(pointsCopy[j - 1]);
                    if (j == pointsCopy.length - 1){
                        collPointsCount++;
                        collPointsList.add(pointsCopy[j]);
                        if (collPointsCount >= 3) {
                            Point[] collPointsArray = new Point[collPointsCount];
                            collPointsList.toArray(collPointsArray);
                            Arrays.sort(collPointsArray);
                            if (originPoint.compareTo(collPointsArray[0]) < 0) {
                                segments.add(new LineSegment(originPoint, collPointsArray[collPointsCount - 1]));
                            }
                        }
                    }
                } else if (slope != currentSlope && collPointsCount > 0) {
                    collPointsCount++;
                    collPointsList.add(pointsCopy[j - 1]);

                    if (collPointsCount >= 3) {
                        Point[] collPointsArray = new Point[collPointsCount];
                        collPointsList.toArray(collPointsArray);
                        Arrays.sort(collPointsArray);
                        if (originPoint.compareTo(collPointsArray[0]) < 0) {
                            segments.add(new LineSegment(originPoint, collPointsArray[collPointsCount - 1]));
                        }
                    }
                    collPointsCount = 0;
                    collPointsList.clear();
                    currentSlope = slope;
                } else if (slope != currentSlope && collPointsCount == 0) {
                    currentSlope = slope;
                }
            }
        }
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }
}
