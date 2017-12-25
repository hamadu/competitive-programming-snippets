package net.hamadu.utils;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/08/22.
 */
public class SlideMinValueTest {
    public static int[] solveNaive(int[] a, int k) {
        int l = a.length-k+1;
        int[] ret = new int[l];
        Arrays.fill(ret, Integer.MAX_VALUE);
        for (int i = 0; i < l ; i++) {
            for (int j = i ; j < i+k ; j++) {
                ret[i] = Math.min(ret[i], a[j]);
            }
        }
        return ret;
    }

    @Test
    public void testLeft() {
        int[] a = new int[]{10, 7, 5, 3, 2, 4, 6, 1, 3, 6, 7, 7, 9};
        for (int k = 1 ; k <= a.length ; k++) {
            assertThat(SlideMinValue.slideMin(a, k), is(solveNaive(a, k)));
        }
    }
}
