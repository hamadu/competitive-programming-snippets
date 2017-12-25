package net.hamadu.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/08/06.
 */
public class CountRangeCoveringRangeOnlineWaveletTest {
    @Test
    public void test() {
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

        CountRangeCoveringRangeOnlineWavelet rangeInRange = new CountRangeCoveringRangeOnlineWavelet(ranges);

        assertThat(rangeInRange.query(0, 9), is(9)); // all ranges

        assertThat(rangeInRange.query(0, 8), is(7)); // except [5,9),[7,9)

        assertThat(rangeInRange.query(1, 7), is(3)); // [2,4),[2,5),[3,7)

        assertThat(rangeInRange.query(2, 4), is(1)); // [2,4)

        assertThat(rangeInRange.query(4, 8), is(1)); // [7,8)

        assertThat(rangeInRange.query(7, 9), is(2)); // [7,8),[7,9)

        assertThat(rangeInRange.query(-1, 0), is(0));
        assertThat(rangeInRange.query(11, 13), is(0));
        assertThat(rangeInRange.query(4, 5), is(0));
    }

    @Test
    public void testLargeRange() {
        int[][] ranges = new int[][]{
                {-200000000, -100000000},
                {-2000000, -1000000},
                {-100, 100},
                {100, 200},
                {10000, 20000},
                {100000, 200000},
                {1000000, 2000000},
                {10000000, 20000000},
                {100000000, 200000000},
        };

        CountRangeCoveringRangeOnlineWavelet rangeInRange = new CountRangeCoveringRangeOnlineWavelet(ranges);

        assertThat(rangeInRange.query(-1000000000, 1000000000), is(9)); // all ranges
        assertThat(rangeInRange.query(-200000000, 200000000), is(9)); // all ranges

        assertThat(rangeInRange.query(-100, 200), is(2)); // [-100,100),[100,200)

        assertThat(rangeInRange.query(5000, 20000000), is(4));

        assertThat(rangeInRange.query(-50, 150), is(0));
        assertThat(rangeInRange.query(20000000, 100000000), is(0)); // between the large gap
    }
}
