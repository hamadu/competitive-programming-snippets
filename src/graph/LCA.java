package graph;

import java.util.Arrays;

/**
 * Lowest common anscestor.
 */
public class LCA {
    int[][] graph;
    int[][] parent;
    int[] depth;

    public LCA(int[][] graph) {
        int n = graph.length;
        this.graph = graph;
        init(n);
    }

    void dfs(int now, int from, int dep) {
        parent[0][now] = from;
        depth[now] = dep;
        for (int to : graph[now]) {
            if (to != from) {
                dfs(to, now, dep+1);
            }
        }
    }

    void init(int n) {
        int log = 1;
        int nn = n;
        while (nn >= 1) {
            nn /= 2;
            log++;
        }
        parent = new int[log+1][n];
        for (int i = 0 ; i <= log ; i++) {
            Arrays.fill(parent[i], -1);
        }
        depth = new int[n];

        dfs(0, -1, 0);

        for (int k = 0 ; k < log ; k++) {
            for (int v = 0 ; v < n ; v++) {
                if (parent[k][v] < 0) {
                    parent[k+1][v] = -1;
                } else {
                    parent[k+1][v] = parent[k][parent[k][v]];
                }
            }
        }
    }

    int lca(int u, int v) {
        int loglen = parent.length;
        if (depth[u] > depth[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }
        for (int k = 0 ; k < loglen ; k++) {
            if (((depth[v] - depth[u]) >> k) % 2 == 1) {
                v = parent[k][v];
            }
        }
        if (u == v) {
            return u;
        }

        for (int k = loglen-1 ; k >= 0 ; k--) {
            if (parent[k][u] != parent[k][v]) {
                u = parent[k][u];
                v = parent[k][v];
            }
        }
        return parent[0][u];
    }

    int dist(int x, int y) {
        int l = lca(x, y);
        return depth[x] + depth[y] - depth[l] * 2;
    }
}