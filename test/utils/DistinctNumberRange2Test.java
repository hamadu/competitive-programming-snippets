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
public class DistinctNumberRange2Test {
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
        int[] ret = DistinctNumberRange2.answerDistinctQuery(a, queries);
        assertThat(ret, is(DistinctNumberRangeTest.solveNaive(a, queries)));
    }
}
