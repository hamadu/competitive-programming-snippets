package net.hamadu.data_structure.segment;

import java.util.Arrays;

public class SegmentTreeRARMQWithIndex {
    static final int INF = 100000000;
    static final int[] INFV = new int[]{INF, INF};

    int N;
    int M;
    int[][] segMin;
    int[] lazy;

    public SegmentTreeRARMQWithIndex(int[][] data) {
        N = Math.max(16, Integer.highestOneBit(data.length-1)<<2);
        M = (N >> 1) - 1;

        segMin = new int[N][2];
        lazy = new int[N];
        for (int i = 0; i < N; i++) {
            Arrays.fill(segMin[i], INF);
        }
        for (int i = 0 ; i < data.length ; i++) {
            segMin[M+i] = data[i];
        }
        for (int i = M-1 ; i >= 0 ; i--) {
            int minidx = i*2+1+compare(i*2+1, i*2+2);
            segMin[i][0] = segMin[minidx][0];
            segMin[i][1] = segMin[minidx][1];
        }
    }

    private void propagate(int i) {
        if (lazy[i] == 0) {
            return;
        }
        segMin[i][1] += lazy[i];
        if (i*2+2 < segMin.length) {
            lazy[i*2+1] += lazy[i];
            lazy[i*2+2] += lazy[i];
        }
        lazy[i] = 0;
    }

    private int compare(int[] a, int[] b) {
        if ((a[1] < b[1]) || (a[1] == b[1] && a[0] < b[0])) {
            return 0;
        } else {
            return 1;
        }
    }

    private int compare(int l, int r) {
        return compare(segMin[l], segMin[r]);
    }

    public void add(int l, int r, int k) {
        add(l, r, k, 0, 0, M+1);
    }

    public void add(int l, int r, int x, int idx, int fr, int to) {
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

        int minidx = idx*2+1+compare(idx*2+1, idx*2+2);
        segMin[idx][0] = segMin[minidx][0];
        segMin[idx][1] = segMin[minidx][1];
    }

    public int[] min(int l, int r) {
        return min(l, r, 0, 0, M+1);
    }

    public int[] min(int l, int r, int idx, int fr, int to) {
        propagate(idx);

        if (to <= l || r <= fr) {
            return INFV;
        }
        if (l <= fr && to <= r) {
            return segMin[idx].clone();
        }

        int med = (fr+to) / 2;
        int[] L = min(l, r, idx*2+1, fr, med);
        int[] R = min(l, r, idx*2+2, med, to);
        if (compare(L, R) == 0) {
            return L;
        } else {
            return R;
        }
    }
}
