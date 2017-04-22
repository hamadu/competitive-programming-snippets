package utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/08/06.
 */
public class CountRangeCoveringRangeOnlinePersistentTest {
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

        CountRangeCoveringRangeOnlinePersistent rangeInRange = new CountRangeCoveringRangeOnlinePersistent(ranges);

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
}
