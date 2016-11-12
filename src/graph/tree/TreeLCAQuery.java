package graph.tree;

public class TreeLCAQuery {
    LCA lca;
    int[] val;
    int[][] dbl;

    public TreeLCAQuery(LCA lca, int[] val) {
        int n = val.length;
        this.lca = lca;
        this.val = val;
        int lg = lca.parent.length;
        dbl = new int[lg][n];
        for (int i = 0 ; i < n ; i++) {
            dbl[0][i] = val[i];
        }
        for (int l = 1 ; l < lg ; l++) {
            for (int i = 0 ; i < n ; i++) {
                int half = lca.parent[l-1][i];
                if (half >= 0) {
                    dbl[l][i] = Math.min(dbl[l-1][i], dbl[l-1][half]);
                } else {
                    dbl[l][i] = dbl[l-1][i];
                }
            }
        }
    }

    public int min(int u, int v) {
        int min = Integer.MAX_VALUE;
        while (u != v) {
            int diff = lca.depth[u] - lca.depth[v];
            int ctr = -1;
            while (diff > 0) {
                ctr++;
                diff /= 2;
            }
            min = Math.min(min, dbl[ctr][u]);
            u = lca.parent[ctr][u];
        }
        min = Math.min(min, dbl[0][u]);
        return min;
    }
}
