import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by slazakovich on 9/15/2016.
 */
public class BruteCollinearPoints {

    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;
                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i].slopeTo(points[k]) != points[i].slopeTo(points[l])) continue;
                        Point[] collinearPoints = new Point[4];
                        collinearPoints[0] = points[i];
                        collinearPoints[1] = points[j];
                        collinearPoints[2] = points[k];
                        collinearPoints[3] = points[l];
                        Arrays.sort(collinearPoints);
                        segments.add(new LineSegment(collinearPoints[0], collinearPoints[3]));
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }
}
