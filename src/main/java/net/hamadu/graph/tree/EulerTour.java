package net.hamadu.graph.tree;

import java.util.Arrays;

import static net.hamadu.graph.tree.ParentCountOrder.parentCountOrder;

/**
 * Created by hama_du on 2016/08/26.
 */
public class EulerTour {
    /**
     * Computes euler-tour of given tree.
     * It returns array of size 2*n.
     *
     * O(n)
     *
     * @param graph
     * @param root
     * @return
     */
    public static int[] eulerTour(int[][] graph, int root) {
        int[][] pco = parentCountOrder(graph, root);

        int n = graph.length;
        int[] parent = pco[0];
        int[] cnt = pco[1];
        int[] euler = new int[2*n];

        Arrays.fill(euler, -1);
        int ei = 0;
        int[] stk = new int[n];
        int head = 0;
        stk[head++] = root;
        while (head > 0) {
            int now = stk[--head];
            while (euler[ei] != -1) {
                ei++;
            }
            euler[ei++] = now;
            euler[ei+(cnt[now]-1)*2] = now;
            for (int to : graph[now]) {
                if (to == parent[now]) {
                    continue;
                }
                stk[head++] = to;
            }
        }
        return euler;
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
