package dp;

import java.util.Arrays;

public class LongestIncreasingSequence {
    /**
     * Computes longest increasing sequence.
     * If you want to get actual sequence, see dp[1..ans].
     *
     * O(nlogn)
     *
     * @param values
     * @return
     */
    public static int lis(int[] values) {
        int n = values.length;
        long[] lval = new long[n];
        for (int i = 0; i < values.length; i++) {
            lval[i] = values[i]*1000000000L+i;
        }
        int ans = 0;
        long[] dp = new long[n+1];
        Arrays.fill(dp, Long.MIN_VALUE);
        for (int i = 0; i < n; i++) {
            int idx = Arrays.binarySearch(dp, 0, ans+1, lval[i]);
            if (idx < 0) {
                idx = -idx-2;
                dp[idx+1] = lval[i];
                if (idx >= ans) {
                    ans++;
                }
            }
        }
        return ans;
    }
}
