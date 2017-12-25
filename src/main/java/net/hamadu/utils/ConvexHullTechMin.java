package net.hamadu.utils;

/**
 * Convex hull technique.
 * Given many lines(say y = a_{i} * x + b_{i}), computes many query:
 *   what is the maximum y of current lines with given x?
 * Make sure that a_{i} is non-increasing.
 *
 * O(n+qlogn) where q = (number of queries), n = (maximum number of lines).
 */
public class ConvexHullTechMin {
    long[][] stk;
    int tail = 0;

    public ConvexHullTechMin(int maxLines) {
        stk = new long[maxLines][2];
    }

    // add line : ax + b
    public void addLine(long a, long b) {
        stk[tail][0] = a;
        stk[tail][1] = b;
        tail++;
        while (tail >= 3 && compare(stk[tail-3], stk[tail-2], stk[tail-1])) {
            stk[tail-2][0] = stk[tail-1][0];
            stk[tail-2][1] = stk[tail-1][1];
            tail--;
        }
    }

    private boolean compare(long[] l1, long[] l2, long[] l3) {
        long a1 = l1[0];
        long a2 = l2[0];
        long a3 = l3[0];
        long b1 = l1[1];
        long b2 = l2[1];
        long b3 = l3[1];
        return  (a2 - a1) * (b3 - b2) >= (a3 - a2) * (b2 - b1);
    }

    long val(int lidx, long x) {
        return stk[lidx][0] * x + stk[lidx][1];
    }

    public long[] queryMin(long x) {
        int min = -1;
        int max = tail - 1;
        while (max - min > 1) {
            int med = (max + min) / 2;
            if (val(med, x) >= val(med+1, x)) {
                min = med;
            } else {
                max = med;
            }
        }
        return stk[max];
    }

    public long computesMin(long x) {
        long[] line = queryMin(x);
        return line[0] * x + line[1];
    }
}
