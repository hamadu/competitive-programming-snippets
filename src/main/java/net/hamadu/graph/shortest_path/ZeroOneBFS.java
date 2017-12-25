package net.hamadu.graph.shortest_path;

import java.util.Arrays;

public class ZeroOneBFS {
    static int[] zeroOneBFS(int start, int[][] graph) {
        int n = graph.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[start] = 0;
        int[] que = new int[2*n];
        int qh = 0;
        int qt = 0;
        que[qh++] = start;
        que[qh++] = 0;
        while (qt < qh) {
            int now = que[qt++];
            int tim = que[qt++]+1;
            for (int to : graph[now]) {
                if (dp[to] > tim) {
                    dp[to] = tim;
                    que[qh++] = to;
                    que[qh++] = tim;
                }
            }
        }
        return dp;
    }
}
