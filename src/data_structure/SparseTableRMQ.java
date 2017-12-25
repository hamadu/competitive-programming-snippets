package data_structure;

public class SparseTableRMQ {
    int n;
    int[] data;
    int[] log;
    int[][] lkup;

    public SparseTableRMQ(int[] a) {
        n = a.length;
        log = new int[n+1];
        log[1] = 0;
        for (int i = 2 ; i <= n ; i++) {
            log[i] = log[i-1] + ((Integer.highestOneBit(i) == i) ? 1 : 0);
        }
        data = a;
        lkup = new int[log[n]+1][n];
        for (int i = 0; i < n ; i++) {
            lkup[0][i] = i;
        }
        for (int j = 1 ; j <= log[n] ; j++) {
            for (int i = 0; i < n ; i++) {
                lkup[j][i] = lkup[j-1][i];
                int t = Math.min(n-1, i + (1<<(j-1)));
                if (data[lkup[j-1][i]] < data[lkup[j-1][t]]) {
                    lkup[j][i] = lkup[j-1][i];
                } else {
                    lkup[j][i] = lkup[j-1][t];
                }
            }
        }
    }

    public int queryIndex(int l, int r) {
        int length = r - l;
        int lg = log[length];
        int tl = r - (1<<lg);

        if (data[lkup[lg][l]] < data[lkup[lg][tl]]) {
            return lkup[lg][l];
        } else {
            return lkup[lg][tl];
        }
    }
}