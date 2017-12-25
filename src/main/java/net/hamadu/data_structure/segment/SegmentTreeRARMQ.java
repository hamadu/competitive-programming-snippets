package net.hamadu.data_structure.segment;

import java.util.Arrays;

/**
 * Segment tree (range add, range minimum query).
 */
public class SegmentTreeRARMQ {
    int N;
    int M;
    long[] segMin;
    long[] lazy;

    public SegmentTreeRARMQ(long[] data) {
        N = Integer.highestOneBit(data.length-1)<<2;
        M = (N >> 1) - 1;

        segMin = new long[N];
        lazy = new long[N];
        Arrays.fill(segMin, Long.MAX_VALUE);
        for (int i = 0 ; i < data.length ; i++) {
            segMin[M+i] = data[i];
        }
        for (int i = M-1 ; i >= 0 ; i--) {
            segMin[i] = compute(i);
        }
    }

    private void propagate(int i) {
        if (lazy[i] == 0) {
            return;
        }
        segMin[i] += lazy[i];
        if (i*2+2 < segMin.length) {
            lazy[i*2+1] += lazy[i];
            lazy[i*2+2] += lazy[i];
        }
        lazy[i] = 0;
    }

    private long compute(int i) {
        return Math.min(segMin[i*2+1], segMin[i*2+2]);
    }

    public void add(int l, int r, long k) {
        add(l, r, k, 0, 0, M+1);
    }

    public void add(int l, int r, long x, int idx, int fr, int to) {
        propagate(idx);

        if (to <= l || r <= fr) {
            return;
        }
        if (l <= fr && to <= r) {
            lazy[idx] += x;
            propagate(idx);
            return;
        }

        int med = (fr + to) / 2;
        add(l, r, x, idx*2+1, fr, med);
        add(l, r, x, idx*2+2, med, to);

        segMin[idx] = compute(idx);
    }

    public long min(int l, int r) {
        return min(l, r, 0, 0, M+1);
    }

    public long min(int l, int r, int idx, int fr, int to) {
        propagate(idx);

        if (to <= l || r <= fr) {
            return Long.MAX_VALUE;
        }
        if (l <= fr && to <= r) {
            return segMin[idx];
        }

        int med = (fr+to) / 2;
        long ret = Long.MAX_VALUE;
        ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
        ret = Math.min(ret, min(l, r, idx*2+2, med, to));
        return ret;
    }
}