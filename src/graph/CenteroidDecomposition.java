package graph;

import java.util.Arrays;

/**
 * Centeroid decomposition algorithm.
 */
public class CenteroidDecomposition {
    // Original graph (only reference)
    int[][] tree;

    int n;

    int[] vertex;
    int[] parent;
    int[] children;
    boolean[] finished;

    // Parent of the new tree
    int[] centerParent;

    int centeroidRoot;
    int[][] centeroidTree;

    public CenteroidDecomposition(int[][] tree) {
        this.tree = tree;
        this.n = tree.length;

        this.vertex = new int[n];
        this.parent = new int[n];
        this.children = new int[n];
        this.finished = new boolean[n];

        this.centerParent = new int[n];
        Arrays.fill(this.centerParent, -1);
    }

    public void buildCenteroidTree() {
        int[] deg = new int[n];
        int root = 0;

        int[][] edge = new int[n-1][2];
        int ei = 0;
        for (int i = 0 ; i < n ; i++) {
            if (centerParent[i] != -1) {
                edge[ei][0] = i;
                edge[ei][1] = centerParent[i];
                deg[i]++;
                deg[centerParent[i]]++;
                ei++;
            } else {
                root = i;
            }
        }

        int[][] graph = new int[n][];
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < ei ; i++) {
            int a = edge[i][0];
            int b = edge[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        this.centeroidRoot = root;
        this.centeroidTree = graph;
    }

    public int doit(int root, int d) {
        int qh = 0;
        int qt = 0;
        vertex[qh] = root;
        parent[qh++] = -1;
        while (qt < qh) {
            int v = vertex[qt];
            int p = parent[qt++];
            for (int to : tree[v]) {
                if (to != p && !finished[to]) {
                    vertex[qh] = to;
                    parent[qh++] = v;
                }
            }
        }

        int center = -1;

        sch: for (int i = qt-1 ; i >= 0 ; i--) {
            int vi = vertex[i];
            children[vi] = 1;
            for (int to : tree[vi]) {
                if (to != parent[i] && !finished[to]) {
                    children[vi] += children[to];
                }
            }

            if (qt - children[vi] <= qt / 2) {
                for (int to : tree[vi]) {
                    if (to != parent[i] && !finished[to] && children[to] >= qt/2+1) {
                        continue sch;
                    }
                }
                center = vi;
                break;
            }
        }

        finished[center] = true;

        for (int to : tree[center]) {
            if (!finished[to]) {
                centerParent[doit(to, d+1)] = center;
            }
        }

        return center;
    }
}
