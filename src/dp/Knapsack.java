package dp;

/**
 * Created by hama_du on 2016/07/31.
 */
public class Knapsack {

    /**
     * Item-answerCountQuery-limited knapsack.
     * O(nW)
     *
     * @param dp   dp value
     * @param item array of item({value, weight, limit})
     * @param maxW quota of the knapsack
     */
    static void itemLimitedKnapsack(int[] dp, int[][] item, int maxW) {
        int[] deqIdx = new int[maxW+1];
        int[] deqVal = new int[maxW+1];
        int n = item.length;
        for (int i = 0; i < n ; i++) {
            int v = item[i][0]; // value
            int w = item[i][1]; // weight
            int m = item[i][2]; // limit
            for (int a = 0 ; a < w ; a++) {
                int s = 0;
                int t = 0;
                for (int j = 0; j * w + a <= maxW ; j++) {
                    int val = dp[j * w + a] - j * v;
                    while (s < t && deqVal[t-1] <= val) {
                        t--;
                    }
                    deqIdx[t] = j;
                    deqVal[t] = val;
                    t++;
                    dp[j * w + a] = Math.max(dp[j * w + a], deqVal[s] + j * v);
                    if (deqIdx[s] == j - m) {
                        s++;
                    }
                }
            }
        }
    }
}
