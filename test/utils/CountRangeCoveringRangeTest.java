package utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/08/06.
 */
public class CountRangeCoveringRangeTest {

    public static int[] solveNaive(int[][] ranges, int[][] queries) {
        int[] ret = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int l = queries[i][0];
            int r = queries[i][1];
            int count = 0;
            for (int j = 0; j < ranges.length; j++) {
                if (l <= ranges[j][0] && ranges[j][1] <= r) {
                    count++;
                }
            }
            ret[i] = count;
        }
        return ret;
    }

    @Test
    public void test0() {
        int[][] ranges = new int[][]{
                {0, 1},
                {0, 3},
                {0, 5},
                {2, 4},
                {2, 5},
                {3, 7},
                {5, 9},
                {7, 8},
                {7, 9}
        };
        List<int[]> q = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            for (int j = i+1 ; j <= 10 ; j++) {
                q.add(new int[]{i, j});
            }
        }
        int[][] queries = new int[q.size()][2];
        for (int i = 0; i < q.size() ; i++) {
            queries[i] = q.get(i);
        }
        int[] ret = CountRangeCoveringRange.answerCountQuery(ranges, queries);
        assertThat(ret, is(solveNaive(ranges, queries)));
    }
}
