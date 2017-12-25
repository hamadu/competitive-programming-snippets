package net.hamadu.graph.tree;

import net.hamadu.data_structure.SparseTableRMQ;

import java.util.Arrays;

public class LCASparseTable {
    int[][] graph;
    int ti;
    int[] tour;
    int[] tourDepth;
    int[] depth;
    int[] posTable;
    SparseTableRMQ table;

    public LCASparseTable(int[][] graph) {
        int n = graph.length;
        this.graph = graph;
        init(n);
    }

    void dfs(int now, int from, int dep) {
        depth[now] = dep;
        if (posTable[now] == -1) {
            posTable[now] = ti;
        }
        tour[ti++] = now;
        for (int to : graph[now]) {
            if (to != from) {
                dfs(to, now, dep+1);
                tour[ti++] = now;
            }
        }
    }

    void init(int n) {
        depth = new int[n];
        tour = new int[2*n];
        posTable = new int[n];
        Arrays.fill(posTable, -1);
        dfs(0, -1, 0);

        tourDepth = new int[2*n];
        Arrays.fill(tourDepth, n+100);
        for (int i = 0; i < ti ; i++) {
            tourDepth[i] = depth[tour[i]];
        }
        table = new SparseTableRMQ(tourDepth);
    }

    int lca(int u, int v) {
        int l = posTable[u];
        int r = posTable[v];
        if (l > r) {
            int tmp = l;
            l = r;
            r = tmp;
        }
        int res = table.queryIndex(l, r+1);
        return tour[res];
    }

    int dist(int x, int y) {
        int l = lca(x, y);
        return depth[x] + depth[y] - depth[l] * 2;
    }
}
