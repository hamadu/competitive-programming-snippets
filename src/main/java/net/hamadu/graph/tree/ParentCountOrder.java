package net.hamadu.graph.tree;

/**
 * Created by hama_du on 2016/08/26.
 */
public class ParentCountOrder {
    /**
     * Computes each node's {parent, number of vertices in its subtrees, BFS order} with given tree.
     * It returns array of size 3*n.
     *
     * O(n)
     *
     * @param graph
     * @param root
     * @return
     */
    public static int[][] parentCountOrder(int[][] graph, int root) {
        int n = graph.length;
        int[] que = new int[2*n];
        int[] parent = new int[n];
        int[] bfsOrd = new int[n];
        int[] cnt = new int[n];
        int qh = 0, qt = 0;
        que[qh++] = root;
        que[qh++] = -1;
        int vi = 0;
        while (qt < qh) {
            int now = que[qt++];
            int par = que[qt++];
            parent[now] = par;
            bfsOrd[vi++] = now;
            for (int to : graph[now]) {
                if (to == par) {
                    continue;
                }
                que[qh++] = to;
                que[qh++] = now;
            }
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            int now = bfsOrd[i];
            cnt[now]++;
            if (parent[now] != -1) {
                cnt[parent[now]] += cnt[now];
            }
        }
        return new int[][]{ parent, cnt, bfsOrd };
    }
}
