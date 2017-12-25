package net.hamadu.graph.flow;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/07/31.
 */
public class Hungarian {
    public static int[] hungarian(int[][] table) {
        int n = table.length;
        int m = table[0].length;
        int[] toright = new int[n];
        int[] toleft = new int[m];
        int[] ofsleft = new int[n];
        int[] ofsright = new int[m];
        Arrays.fill(toright, -1);
        Arrays.fill(toleft, -1);

        for (int r = 0 ; r < n ; r++) {
            boolean[] left = new boolean[n];
            boolean[] right = new boolean[m];
            int[] trace = new int[m];
            int[] ptr = new int[m];
            Arrays.fill(trace, -1);
            Arrays.fill(ptr, r);
            left[r] = true;
            while (true) {
                int d = Integer.MAX_VALUE;
                for (int j = 0 ; j < m ; j++) {
                    if (!right[j]) {
                        d = Math.min(d, table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]);
                    }
                }
                for (int i = 0 ; i < n ; i++) {
                    if (left[i]) {
                        ofsleft[i] -= d;
                    }
                }
                for (int j = 0 ; j < m ; j++) {
                    if (right[j]) {
                        ofsright[j] += d;
                    }
                }
                int b = -1;
                for (int j = 0 ; j < m ; j++) {
                    if (!right[j] && table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j] == 0) {
                        b = j;
                    }
                }
                trace[b] = ptr[b];
                int c = toleft[b];
                if (c < 0) {
                    while (b >= 0) {
                        int a = trace[b];
                        int z = toright[a];
                        toleft[b] = a;
                        toright[a] = b;
                        b = z;
                    }
                    break;
                }
                right[b] = true;
                left[c] = true;
                for (int j = 0 ; j < m ; j++) {
                    if (table[c][j] + ofsleft[c] + ofsright[j] < table[ptr[j]][j] + ofsleft[ptr[j]] + ofsright[j]) {
                        ptr[j] = c;
                    }
                }
            }
        }
        return toright;
    }
}
