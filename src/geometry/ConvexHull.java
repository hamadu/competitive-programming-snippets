package geometry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvexHull {
    static class Point implements Comparable<Point> {
        long x;
        long y;

        public Point(long _x, long _y) {
            x = _x;
            y = _y;
        }

        Point(Point a, Point b) {
            x = b.x - a.x;
            y = b.y - a.y;
        }

        public int compareTo(Point o) {
            if (x != o.x) {
                return Long.signum(x - o.x);
            }
            return Long.signum(y - o.y);
        }

        public long det(Point other) {
            return x * other.y - y * other.x;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static List<Point> convexHull(List<Point> points) {
        int n = points.size();
        Collections.sort(points);
        Point[] candidate = new Point[n*2];
        int k = 0;

        // downer
        for (int i = 0 ; i < n ; i++) {
            while (k > 1) {
                Point a = new Point(candidate[k-2], candidate[k-1]);
                Point b = new Point(candidate[k-1], points.get(i));
                if (a.det(b) <= 0) {
                    k--;
                } else {
                    break;
                }
            }
            candidate[k++] = points.get(i);
        }

        // upper
        int t = k;
        for (int i = n-2 ; i >= 0 ; i--) {
            while (k > t) {
                Point a = new Point(candidate[k-2], candidate[k-1]);
                Point b = new Point(candidate[k-1], points.get(i));
                if (a.det(b) <= 0) {
                    k--;
                } else {
                    break;
                }
            }
            candidate[k++] = points.get(i);
        }

        List<Point> ret = new ArrayList<>();
        for (int i = 0 ; i < k - 1 ; i++) {
            ret.add(candidate[i]);
        }
        return ret;
    }
}
