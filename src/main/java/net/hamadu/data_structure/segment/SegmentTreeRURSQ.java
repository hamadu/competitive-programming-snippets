package net.hamadu.data_structure.segment;

import java.util.Arrays;

/**
 * Segment tree (range update, range sum query).
 */
public class SegmentTreeRURSQ {
    int N;
    int M;
    long[] segSum;
    long[] segUpd;

    public SegmentTreeRURSQ(long[] data) {
        N = Integer.highestOneBit(data.length-1)<<2;
        M = (N >> 1) - 1;

        segUpd = new long[N];
        Arrays.fill(segUpd, -1);

        segSum = new long[N];
        for (int i = 0 ; i < data.length ; i++) {
            segSum[M+i] = data[i];
        }
        for (int i = M-1 ; i >= 0 ; i--) {
            segSum[i] = compute(i);
        }
    }

    public long compute(int i) {
        return segSum[i*2+1] +  segSum[i*2+2];
    }

    public void update(int l, int r, long k) {
        update(l, r, k, 0, 0, M+1);
    }

    public void propagate(int idx, int fr, int to) {
        if (segUpd[idx] != -1) {
            int l = idx*2+1;
            int r = idx*2+2;
            if (r < segUpd.length) {
                segUpd[l] = segUpd[idx];
                segUpd[r] = segUpd[idx];
            }
            segSum[idx] = (to-fr)*segUpd[idx];
            segUpd[idx] = -1;
        }
    }

    public void update(int l, int r, long x, int idx, int fr, int to) {
        propagate(idx, fr, to);

        if (to <= l || r <= fr) {
            return;
        }
        if (l <= fr && to <= r) {
            segUpd[idx] = x;
            propagate(idx, fr, to);
            return;
        }
        int med = (fr + to) / 2;
        update(l, r, x, idx*2+1, fr, med);
        update(l, r, x, idx*2+2, med, to);

        // make sure that child nodes have been propagated.
        segSum[idx] = segSum[idx*2+1] + segSum[idx*2+2];
    }

    public long sum(int l, int r) {
        return sum(l, r, 0, 0, M+1);
    }

    public long sum(int l, int r, int idx, int fr, int to) {
        propagate(idx, fr, to);

        if (to <= l || r <= fr) {
            return 0;
        }

        if (l <= fr && to <= r) {
            return segSum[idx];
        }
        int med = (fr+to) / 2;
        long ret = sum(l, r, idx*2+1, fr, med) + sum(l, r, idx*2+2, med, to);

        // make sure that child nodes have been propagated.
        segSum[idx] = segSum[idx*2+1] + segSum[idx*2+2];
        return ret;
    }
}
