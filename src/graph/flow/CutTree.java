package graph.flow;

import utils.rand.XorShift;

import java.util.ArrayList;
import java.util.Arrays;

public class CutTree {
    public static int[][] buildCutTree(int[][] graph) {
        int n = graph.length;

        int[][] edges = new int[n-1][2];
        int[] group = new int[n];
        for (int i = 0; i < n ; i++) {
            group[i] = 0;
        }

        int[][] groups = new int[n][n];
        int[] gcnt = new int[n];

        for (int iter = 0 ; iter < n-1 ; iter++) {
            // pick two vertices
            int a = 0;
            int b = 0;
            Arrays.fill(gcnt, 0);
            for (int i = 0; i < n ; i++) {
                groups[group[i]][gcnt[i]++] = i;
            }
            for (int i = 0; i < n ; i++) {
                if (gcnt[i] >= 2) {
                    a = groups[i][0];
                    b = groups[i][1];
                }
            }


        }
    }
}
