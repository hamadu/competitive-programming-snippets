package graph;

import java.util.Arrays;

public class MinimumSteinerTree {
    private static final int INF = 100000000;

    public static int minimumSteinerTree(int[][] graph, int[] terminals) {
        int n = graph.length;
        int[][] dist = new int[n][n];
        for (int i = 0; i < n ; i++) {
            dist[i] = graph[i].clone();
        }
        for (int k = 0; k < n ; k++) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k]+dist[k][j]);
                }
            }
        }

        int tn = terminals.length;
        int[][] opt = new int[1<<tn][n];
        for (int i = 0; i < opt.length ; i++) {
            Arrays.fill(opt[i], INF);
        }

        for (int p = 0; p < tn ; p++) {
            for (int q = 0; q < n ; q++) {
                opt[1<<p][q] = dist[terminals[p]][q];
            }
        }
        for (int s = 1; s < 1<<tn ; s++) {
            if ((s & (s-1)) == 0) {
                continue;
            }
            for (int p = 0 ; p < n ; p++) {
                for (int e = 0 ; e < s ; e++) {
                    if ((e | s) == s) {
                        opt[s][p] = Math.min(opt[s][p], opt[e][p] + opt[s-e][p]);
                    }
                }
            }
            for (int p = 0 ; p < n ; p++) {
                for (int q = 0 ; q < n ; q++) {
                    opt[s][p] = Math.min(opt[s][p], opt[s][q] + dist[p][q]);
                }
            }
        }

        int ans = INF;
        for (int s = 0 ; s < (1<<tn) ; s++) {
            for (int q = 0; q < n ; q++) {
                ans = Math.min(ans, opt[s][q] + opt[(1<<tn)-1-s][q]);
            }
        }
        return ans;
    }
}
