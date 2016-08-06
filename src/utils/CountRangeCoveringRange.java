package utils;

import data_structure.fenwick.FenwickTree;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/08/06.
 */
public class CountRangeCoveringRange {
    /**
     * Counts how many ranges in [l,r).
     * Answers multiple query.
     *
     * O((n+q)logn) where q = query.length, n = ranges.length
     *
     * @param ranges array of [l,r)
     * @param query answerCountQuery query
     * @return answers to the queries
     */
    public static int[] answerCountQuery(int[][] ranges, int[][] query) {
        int n = ranges.length;
        int q = query.length;
        int[][] qinfo = new int[q][3];
        for (int i = 0; i < q ; i++) {
            qinfo[i] = new int[]{ query[i][0], query[i][1], i };
        }
        Arrays.sort(qinfo, (q0, q1) -> q0[0] - q1[0]);
        Arrays.sort(ranges, (r0, r1) -> r0[0] - r1[0]);

        // it may need value compression.
        FenwickTree bit = new FenwickTree(1000010);
        for (int i = 0; i < n ; i++) {
            bit.add(ranges[i][1], 1);
        }

        int[] ans = new int[q];
        int head = 0;
        for (int i = 0; i < q ; i++) {
            while (head < n && ranges[head][0] < qinfo[i][0]) {
                bit.add(ranges[head][1], -1);
                head++;
            }
            ans[qinfo[i][2]] = (int)bit.range(qinfo[i][0]+1, qinfo[i][1]);
        }
        return ans;
    }
}
