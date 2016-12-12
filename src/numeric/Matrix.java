package numeric;

public class Matrix {
    static final long MOD = 1000000007;

    static long[][] e(int n) {
        long[][] mat = new long[n][n];
        for (int i = 0; i < n ; i++) {
            mat[i][i] = 1;
        }
        return mat;
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
        long[][] c = new long[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                long sum = 0;
                for (int l = 0; l < k ; l++) {
                    sum += a[i][l] * b[l][j] % MOD;
                }
                sum %= MOD;
                c[i][j] = sum;
            }
        }
        return c;
    }

    static int[][] mul(int[][] a, int[][] b) {
        int n = a.length;
        int k = a[0].length;
        int m = b[0].length;
        int[][] c = new int[n][m];
        for (int i = 0; i < n ; i++) {
            for (int j = 0; j < m ; j++) {
                long sum = 0;
                for (int l = 0; l < k ; l++) {
                    sum += a[i][l] * b[l][j] % MOD;
                }
                sum %= MOD;
                c[i][j] = (int)sum;

            }
        }
        return c;
    }
}
