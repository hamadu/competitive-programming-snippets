package net.hamadu.combinatorics;

public class SubSet {
    /**
     * Iterate subsets of X.
     *
     * @param X
     * @param dp
     */
    public static void subset(int X, int[] dp) {
        for (int sub = (X - 1) & X ; sub > 0 ; sub = (sub - 1) & X) {
            dp[X] = Math.max(dp[X], dp[sub] + dp[X^sub]);
        }
    }
}
