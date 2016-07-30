package data_structure.segment;

import java.util.Arrays;

/**
 * Segment tree (range add, range minimum query). Also known as "Starry Sky Tree."
 *
 * @see @link{http://qnighy.github.io/informatics-olympiad/joi2009-day4-starry_sky-problem.html}
 */
public class SegmentTreeRARMQ {
    int N;
    int M;
    long[] segMin;
    long[] segAdd;

    public SegmentTreeRARMQ(long[] data) {
        N = Integer.highestOneBit(data.length-1)<<2;
        M = (N >> 1) - 1;

        segMin = new long[N];
        segAdd = new long[N];
        Arrays.fill(segMin, Long.MAX_VALUE);
        for (int i = 0 ; i < data.length ; i++) {
            segMin[M+i] = data[i];
        }
        for (int i = M-1 ; i >= 0 ; i--) {
            segMin[i] = compute(i);
        }
    }

    public long compute(int i) {
        return Math.min(segMin[i*2+1], segMin[i*2+2]) + segAdd[i];
    }

    public void add(int l, int r, long k) {
        add(l, r, k, 0, 0, M+1);
    }

    public void add(int l, int r, long x, int idx, int fr, int to) {
        if (to <= l || r <= fr) {
            return;
        }
        if (l <= fr && to <= r) {
            segAdd[idx] += x;
            while (idx >= 1) {
                idx = (idx - 1) / 2;
                segMin[idx] = Math.min(segMin[idx*2+1] + segAdd[idx*2+1], segMin[idx*2+2] + segAdd[idx*2+2]);
            }
            return;
        }

        int med = (fr + to) / 2;
        add(l, r, x, idx*2+1, fr, med);
        add(l, r, x, idx*2+2, med, to);
    }

    public long min(int l, int r) {
        return min(l, r, 0, 0, M+1);
    }

    public long min(int l, int r, int idx, int fr, int to) {
        if (to <= l || r <= fr) {
            return Long.MAX_VALUE;
        }
        if (l <= fr && to <= r) {
            return segMin[idx] + segAdd[idx];
        }

        int med = (fr+to) / 2;
        long ret = Long.MAX_VALUE;
        ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
        ret = Math.min(ret, min(l, r, idx*2+2, med, to));
        return ret + segAdd[idx];
    }
}