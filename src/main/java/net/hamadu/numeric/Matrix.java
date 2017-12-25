package net.hamadu.numeric;

import java.util.Arrays;

public class Matrix {
    static final int MOD = 1000000007;
    static final long MOD2 = (long)MOD * MOD * 8;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long[][] e(int n) {
        long[][] mat = new long[n][n];
        for (int i = 0; i < n ; i++) {
            mat[i][i] = 1;
        }
        return mat;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }

    static void swapRow(long[][] x, int p, int q) {
        int n = x[0].length;
        for (int i = 0; i < n ; i++) {
            long tmp = x[p][i];
            x[p][i] = x[q][i];
            x[q][i] = tmp;
        }
    }

    static void addRow(long[][] x, int p, int q, long mul) {
        int n = x[0].length;
        mul = mul < 0 ? MOD+mul : mul;
        for (int i = 0; i < n ; i++) {
            long add = x[q][i] * mul % MOD;
            x[p][i] += add;
            if (x[p][i] >= MOD) {
                x[p][i] -= MOD;
            }
        }
    }

    static void mulRow(long[][] x, int p, long mul) {
        int n = x[0].length;
        for (int i = 0; i < n ; i++) {
            long to = (x[p][i] * mul % MOD);
            x[p][i] = (int)to;
        }
    }

    static long[][] inv(long[][] x) {
        int n = x.length;

        long[][] fr = new long[n][n];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < n ; j++) {
                fr[i][j] = x[i][j];
            }
        }
        long[][] to = new long[n][n];
        for (int i = 0; i < n ; i++) {
            to[i][i] = 1;
        }
        for (int i = 0; i < n ; i++) {
            int pos = i;
            while (pos < n && fr[pos][i] == 0) {
                pos++;
            }
            if (pos != i) {
                swapRow(fr, pos, i);
                swapRow(to, pos, i);
            }

            long kake = inv(fr[i][i]);
            mulRow(fr, i, kake);
            mulRow(to, i, kake);

            for (int j = 0; j < n ; j++) {
                if (i != j) {
                    long bai = -fr[j][i];
                    addRow(to, j, i, bai);
                    addRow(fr, j, i, bai);
                }
            }
        }
        return to;
    }

    static long[][] pow(long[][] x, long p) {
        int n = x.length;
        long[][] ret = e(n);
        while (p >= 1) {
            if ((p & 1) == 1) {
                ret = mul(ret, x);
            }
            x = mul(x, x);
            p >>>= 1;
        }
        return ret;
    }

    static long[][] mul(long[][] a, long[][] b) {
        int n = a.length;
        int k = a[0].length;
        int m = b[0].length;

        long[][] ret = new long[n][m];
        long[] row = new long[m];
        for (int i = 0; i < n ; i++) {
            Arrays.fill(row, 0);
            for (int l = 0; l < k ; l++) {
                for (int j = 0; j < m ; j++) {
                    row[j] += a[i][l] * b[l][j];
                    if (row[j] >= MOD2) {
                        row[j] -= MOD2;
                    }
                }
            }
            for (int j = 0; j < m ; j++) {
                ret[i][j] = row[j] % MOD;
            }
        }
        return ret;
    }
}
