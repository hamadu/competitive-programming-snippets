package graph;

import data_structure.UndoableUnionFind;

import java.util.ArrayList;
import java.util.List;

public class BatchedDynamicConnectivity {
    UndoableUnionFind uf;
    int[][] edges;

    int Q;
    int en;
    int N;
    int M;
    int[] cnt;
    int[] first;

    int eidx;
    int[][] edgeTimes;
    List<Integer>[] edgeSegments;

    public BatchedDynamicConnectivity(int n, int[][] ed, int numberOfQueries) {
        Q = numberOfQueries+1;
        N = Integer.highestOneBit(Q-1)<<2;
        M = N / 2 - 1;
        uf = new UndoableUnionFind(n, Q);
        edges = ed;
        en = edges.length;
        cnt = new int[en];
        first = new int[en];

        edgeSegments = new List[N];
        for (int i = 0; i < N ; i++) {
            edgeSegments[i] = new ArrayList<>();
        }

        edgeTimes = new int[M][3];
    }

    void addEdge(int queryTime, int edgeId) {
        if (cnt[edgeId] == 0) {
            first[edgeId] = queryTime;
        }
        cnt[edgeId]++;
    }

    void removeEdge(int queryTime, int edgeId) {
        cnt[edgeId]--;
        if (cnt[edgeId] == 0) {
            edgeTimes[eidx][0] = first[edgeId];
            edgeTimes[eidx][1] = queryTime;
            edgeTimes[eidx++][2] = edgeId;
        }
    }

    void pushDown(int a, int b, int edgeId) {
        pushDown(a, b, edgeId, 0, 0, M+1);
    }

    void pushDown(int from, int to, int edgeId, int id, int l, int r) {
        if (r <= from || to <= l) {
            return;
        }
        if (from <= l && r <= to) {
            edgeSegments[id].add(edgeId);
            return;
        }

        int med = (l+r)/2;
        pushDown(from, to, edgeId, id*2+1, l, med);
        pushDown(from, to, edgeId, id*2+2, med, r);
    }

    void dfs(int k) {
        int added = 0;
        for (int eidx : edgeSegments[k]) {
            added++;
            uf.unite(edges[eidx][0], edges[eidx][1]);
        }

        if (k < M) {
            dfs(k*2+1);
            dfs(k*2+2);
        } else {
            int time = k-M;
            if (time < Q) {
                //
                // answer to the time-th query here.
                //
            }
        }

        while (--added >= 0) {
            uf.undo();
        }
    }

    void doit() {
        for (int i = 0; i < en ; i++) {
            if (cnt[i] >= 1) {
                edgeTimes[eidx][0] = first[i];
                edgeTimes[eidx][1] = Q-1;
                edgeTimes[eidx++][2] = i;
            }
        }

        for (int i = 0 ; i < eidx ; i++) {
            pushDown(edgeTimes[i][0], edgeTimes[i][1], edgeTimes[i][2]);
        }

        dfs(0);
    }
}
