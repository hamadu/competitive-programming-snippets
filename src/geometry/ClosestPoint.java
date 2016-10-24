package geometry;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class ClosestPoint {
    static long closestDistance(long[][] points) {
        int n = points.length;
        if (n <= 1) {
            return -1;
        }
        Arrays.sort(points, (a, b) -> Long.signum(a[0] - b[0]));

        TreeSet<long[]> set = new TreeSet<>((a, b) -> {
            if (a[0] == b[0]) {
                return Long.signum(a[1] - b[1]);
            }
            return Long.signum(a[0] - b[0]);
        });

        long best = dist2(points[0], points[1]);
        int idx = 0;
        for (int i = 0 ; i < n ; i++) {
            long sq = (long)Math.sqrt(best)+1;

            while (points[idx][0] + sq < points[i][0]) {
                set.remove(new long[]{ points[i][1], idx});
                idx++;
            }

            long[] fr = set.higher(new long[]{ points[i][1]-sq, -1 });
            long[] to = set.higher(new long[]{ points[i][1]+sq, -1 });
            Set<long[]> pset = null;
            if (fr != null && to != null) {
                pset = set.subSet(fr, to);
            } else if (fr != null) {
                pset = set.tailSet(fr);
            } else if (to != null) {
                pset = set.headSet(to);
            }
            if (pset != null) {
                for (long[] p : pset) {
                    int j = (int)p[1];
                    long d = dist2(points[i], points[j]);
                    if (best > d) {
                        best = d;
                    }
                }
            }
            set.add(new long[]{ points[i][1], i });
        }
        return best;
    }

    private static long dist2(long[] a, long[] b) {
        long dx = a[0]-b[0];
        long dy = a[1]-b[1];
        return dx*dx+dy*dy;
    }
}