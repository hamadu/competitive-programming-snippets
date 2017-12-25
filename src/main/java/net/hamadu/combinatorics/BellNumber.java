package net.hamadu.combinatorics;

public class BellNumber {
    /**
     * Computes Bell triangle.
     * ret[n][n] := Bell number of x.
     * ret[n][k] := The number of partitions of the set {1,2,...,n+1} s.t. k+1 is the largest singleton of the partition.
     *
     * O(n^2)
     *
     * @refs https://en.wikipedia.org/wiki/Bell_triangle#Combinatorial_interpretation
     * @param n
     * @param mod
     * @return
     */
    public static long[][] bellTriangle(int n, int mod) {
        long[][] ret = new long[n+1][n+1];
        ret[0][0] = 1;
        for (int i = 1; i <= n ; i++) {
            ret[i][1] = ret[i-1][i-1];
            for (int j = 2 ; j <= i ; j++) {
                ret[i][j] = (ret[i-1][j-1] + ret[i][j-1]) % mod;
            }
        }
        for (int i = 1 ; i <= n ; i++) {
            ret[i][0] = (ret[i][1] - ret[i-1][0] + mod) % mod;
        }
        return ret;
    }
}
