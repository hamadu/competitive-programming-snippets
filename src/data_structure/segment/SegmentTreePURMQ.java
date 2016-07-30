package data_structure.segment;

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
     * Uodates value at position idx.
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

    /**
     * Finds minimum position k s.t. k >= l and min(k,k+1) <= v
     * If there is no such k, returns -1.
     *
     * @param l
     * @param v
     * @return
     */
    public int findLessOrEqualIndexRight(int l, int v) {
        int cur = M + l;
        while (true) {
            if (seg[cur] <= v) {
                if (cur < M) {
                    cur = cur*2+1;
                } else {
                    return cur - M;
                }
            } else {
                cur += 1;
                if ((cur & (cur+1)) == 0) {
                    return -1;
                }
                if ((cur&1) == 1) {
                    cur >>>= 1;
                }
            }
        }
    }

    /**
     * Finds maximum position k s.t. k <= l and min(k,k+1) <= v
     * If there is no such k, returns -1.
     *
     * @param l
     * @param v
     * @return
     */
    public int findLessOrEqualIndexLeft(int l, int v) {
        int cur = M + l;
        while (true) {
            if (seg[cur] <= v) {
                if (cur < M) {
                    cur = cur*2+2;
                } else {
                    return cur - M;
                }
            } else {
                if ((cur & (cur+1)) == 0) {
                    return -1;
                }
                cur--;
                if ((cur&1)==0) {
                    cur = (cur-1)>>>1;
                }
            }
        }
    }
}