package combinatorics;

import java.util.Arrays;

public class Stirling {
    /**
     * Computes Stirling number of 1st kind up to n.
     * first[n][k] means number of ways to split n labeled items into k alternating-sequences.
     *
     * @param n
     * @param mod
     * @return
     *
     */
    public static long[][] first(int n, long mod) {
        long[][] ret = new long[n+1][n+1];
        ret[0][0] = 1;
        for (int i = 1 ; i <= n ; i++) {
            for (int j = 1 ; j <= i ; j++) {
                ret[i][j] += ret[i-1][j-1];
                ret[i][j] += ret[i-1][j] * (i-1);
                ret[i][j] %= mod;
            }
        }
        return ret;
    }

    /**
     * Computes Stirling number of 2nd kind up to n.
     * second[n][k] means number of ways to split n labeled items into k non-empty groups.
     *
     * @param n
     * @param mod
     * @return
     *
     */
    public static long[][] second(int n, long mod) {
        long[][] ret = new long[n+1][n+1];
        ret[0][0] = 1;
        for (int i = 1 ; i <= n ; i++) {
            for (int j = 1 ; j <= i ; j++) {
                ret[i][j] += ret[i-1][j-1];
                ret[i][j] += ret[i-1][j] * j;
                ret[i][j] %= mod;
            }
        }
        return ret;
    }
}
