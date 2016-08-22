package utils;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/08/22.
 */
public class StockSpanTest {

    public static int[] solveNaiveLeft(int[] a) {
        int[] ret = new int[a.length];
        Arrays.fill(ret, -1);
        for (int i = 0; i < a.length; i++) {
            for (int j = i-1 ; j >= 0 ; j--) {
                if (a[j] >= a[i]) {
                    ret[i] = j;
                    break;
                }
            }
        }
        return ret;
    }

    public static int[] solveNaiveRight(int[] a) {
        int[] ret = new int[a.length];
        Arrays.fill(ret, a.length);
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1 ; j < a.length ; j++) {
                if (a[i] <= a[j]) {
                    ret[i] = j;
                    break;
                }
            }
        }
        return ret;
    }

    @Test
    public void testLeft() {
        int[] a = new int[]{3, 2, 1, 1, 4, 5, 5, 3, 2, 4, 1, 6, 6};
        assertThat(StockSpan.stockSpanLeft(a), is(solveNaiveLeft(a)));
    }

    @Test
    public void testRight() {
        int[] a = new int[]{3, 2, 1, 1, 4, 5, 5, 3, 2, 4, 1, 6, 6};
        assertThat(StockSpan.stockSpanRight(a), is(solveNaiveRight(a)));
    }
}
