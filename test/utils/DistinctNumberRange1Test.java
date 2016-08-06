package utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/08/06.
 */
public class DistinctNumberRange1Test {

    public static int[] solveNaive(int[] a, int[][] queries) {
        int[] ret = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            Set<Integer> dist = new HashSet<>();
            for (int j = l ; j < r ; j++) {
                dist.add(a[j]);
            }
            ret[i] = dist.size();
        }
        return ret;
    }

    @Test
    public void test0() {
        int[] a = new int[]{1, 2, 2, 9, 3, 4, 6, 1, 7, 8, 2, 4, 3, 5, 4, 6};
        List<int[]> q = new ArrayList<>();
        for (int i = 0; i < a.length ; i++) {
            for (int j = i+1 ; j <= a.length ; j++) {
                q.add(new int[]{i, j});
            }
        }
        int[][] queries = new int[q.size()][2];
        for (int i = 0; i < q.size() ; i++) {
            queries[i] = q.get(i);
        }
        int[] ret = DistinctNumberRange1.answerDistinctQuery(a, queries);
        assertThat(ret, is(solveNaive(a, queries)));
    }
}
