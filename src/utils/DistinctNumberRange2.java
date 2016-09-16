package utils;

import java.util.*;

public class DistinctNumberRange2 {
    /**
     * Computes distinct numbers of give array.
     * Answers multiple query.
     *
     * O((n+q)(logn+logq)) where q = query.length, n = a.length.
     *
     * @refs https://twitter.com/uwitenpen/status/761777092069462016
     * @param a array
     * @param query queries([l, r))
     * @return answers to the queries
     */
    public static int[] answerDistinctQuery(int[] a, int[][] query) {
        int n = a.length;

        // last index that number i appeared.
        // it may need value compression.
        int[] lastAppeared = new int[1000000];
        Arrays.fill(lastAppeared, -1);

        // same number ranges.
        List<int[]> ranges = new ArrayList<>();
        int[] ct = new int[1000000];
        for (int i = 0; i < n ; i++) {
            ct[a[i]]++;
            if (lastAppeared[a[i]] == -1) {
                lastAppeared[a[i]] = i;
                continue;
            }
            ranges.add(new int[]{lastAppeared[a[i]], i+1});
            lastAppeared[a[i]] = i;
        }

        int[][] ranges0 = new int[ranges.size()][];
        for (int i = 0; i < ranges.size() ; i++) {
            ranges0[i] = ranges.get(i);
        }
        int[] coveringRanges = CountRangeCoveringRange.answerCountQuery(ranges0, query);
        int q = query.length;
        int[] ret = new int[q];
        for (int i = 0; i < q ; i++) {
            ret[i] = (query[i][1] - query[i][0]) - coveringRanges[i];
        }
        return ret;
    }
}
