package net.hamadu.data_structure.segment;

import java.util.Arrays;

/**
 * Segment tree (point update, range minimum query)
 */
public class SegmentTreePURMQ {
    int N;
    int M;
    int[] seg;

    public SegmentTreePURMQ(int[] data) {
        N = Integer.highestOneBit(data.length-1)<<2;
        M = (N >> 1) - 1;

        seg = new int[N];
        Arrays.fill(seg, Integer.MAX_VALUE);
        for (int i = 0 ; i < data.length ; i++) {
            seg[M+i] = data[i];
        }
        for (int i = M-1 ; i >= 0 ; i--) {
            seg[i] = compute(i);
        }
    }

    /**
     * Uodates value at position minIndexSum.
     *
     * @param idx
     * @param value
     */
    public void update(int idx, int value) {
        seg[M+idx] = value;
        int i = M+idx;
        while (true) {
            i = (i-1) >> 1;
            seg[i] = compute(i);
            if (i == 0) {
                break;
            }
        }
    }

    private int compute(int i) {
        return Math.min(seg[i*2+1], seg[i*2+2]);
    }

    /**
     * Finds minimum value from range [l,r).
     *
     * @param l
     * @param r
     * @return minimum value
     */
    public int min(int l, int r) {
        return min(l, r, 0, 0, M+1);
    }

    private int min(int l, int r, int idx, int fr, int to) {
        if (to <= l || r <= fr) {
            return Integer.MAX_VALUE;
        }
        if (l <= fr && to <= r) {
            return seg[idx];
        }

        int med = (fr+to) / 2;
        int ret = Integer.MAX_VALUE;
        ret = Math.min(ret, min(l, r, idx*2+1, fr, med));
        ret = Math.min(ret, min(l, r, idx*2+2, med, to));
        return ret;
    }

    public int findIndexLessThanV(int l, int r, int v) {
        int ret = findIndexLessThanV(l, r, 0, 0, M+1, v);
        if (ret == Integer.MAX_VALUE) {
            return -1;
        }
        return ret;
    }

    private int findIndexLessThanV(int l, int r, int idx, int fr, int to, int v) {
        if (to <= l || r <= fr) {
            return Integer.MAX_VALUE;
        }

        if (seg[idx] > v) {
            return Integer.MAX_VALUE;
        }

        int med = (fr+to) / 2;
        if (l <= fr && to <= r) {
            int len = to-fr;
            if (len == 1) {
                return idx-M;
            }
        }

        int left = findIndexLessThanV(l, r, idx*2+1, fr, med, v);
        if (left < Integer.MAX_VALUE) {
            return left;
        } else {
            return findIndexLessThanV(l, r, idx*2+2, med, to, v);
        }
    }
}