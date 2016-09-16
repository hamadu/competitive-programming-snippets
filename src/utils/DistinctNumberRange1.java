package utils;

import data_structure.fenwick.FenwickTree;

import java.util.Arrays;

public class DistinctNumberRange1 {
    /**
     * Computes distinct numbers of give array.
     * Answers multiple query.
     *
     * O(qlogn) where q = query.length, n = a.length.
     *
     * @param a array
     * @param query queries([l, r))
     * @return answers to the queries
     */
    public static int[] answerDistinctQuery(int[] a, int[][] query) {
        int n = a.length;
        int q = query.length;

        int[][] qinfo = new int[q][3];
        for (int i = 0; i < q ; i++) {
            qinfo[i] = new int[]{query[i][0], query[i][1], i};
        }
        Arrays.sort(qinfo, (o1, o2) -> o1[1] - o2[1]);

        // last index that number i appeared.
        // it may need value compression.
        int[] lastAppeared = new int[1000000];
        Arrays.fill(lastAppeared, -1);
        FenwickTree bit = new FenwickTree(n+1);

        int head = 0;
        int[] ret = new int[q];
        for (int i = 0; i < q ; i++) {
            while (head < qinfo[i][1]) {
                int num = a[head];
                if (lastAppeared[num] != -1) {
                    bit.set(lastAppeared[num]+1, 0);
                }
                lastAppeared[num] = head;
                bit.set(lastAppeared[num]+1, 1);
                head++;
            }
            ret[qinfo[i][2]] = (int)bit.range(qinfo[i][0]+1, qinfo[i][1]);
        }
        return ret;
    }
}
