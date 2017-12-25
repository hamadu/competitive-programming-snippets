package data_structure.segment;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SegmentTreeRARMQTest {
    private static final int INF = (int)1e9;

    @Test
    public void get() {
        long[] a = new long[]{8,4,2,1,3,5,7,9,6};
        SegmentTreeRARMQ range = new SegmentTreeRARMQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            assertThat(range.min(i, i+1), is(a[i]));
        }
    }

    @Test
    public void rangeMin() {
        long[] a = new long[]{8,4,11,2,5,1,3,12,1,5,7,5,8,13,14,3,9,6,2,10,15};
        SegmentTreeRARMQ range = new SegmentTreeRARMQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            for (int j = i+1 ; j < a.length ; j++) {
                long expected = INF;
                for (int k = i ; k < j ; k++) {
                    expected = Math.min(expected, a[k]);
                }
                assertThat(range.min(i, j), is(expected));
            }
        }
    }

    @Test
    public void rangeAdd() {
        long[] a = new long[]{0,0,0,0,0,0};
        SegmentTreeRARMQ range = new SegmentTreeRARMQ(a);

        // 1,1,0,0,0,0
        range.add(0, 2, 1);
        assertThat(range.min(0, 6), is(0L));

        // 3,3,2,2,2,2
        range.add(0, 6, 2);
        assertThat(range.min(0, 2), is(3L));
        assertThat(range.min(3, 6), is(2L));

        // 3,7,6,6,6,2
        range.add(1, 5, 4);
        assertThat(range.min(1, 2), is(7L));
        assertThat(range.min(4, 6), is(2L));
        assertThat(range.min(0, 3), is(3L));

        // 3,-93,-94,6,6,2
        range.add(1, 3, -100);
        assertThat(range.min(0, 6), is(-94L));
        assertThat(range.min(1, 3), is(-94L));
        assertThat(range.min(0, 1), is(3L));

        // 2,-94,-95,5,5,1
        range.add(0, 6, -1);
        assertThat(range.min(0, 6), is(-95L));
        assertThat(range.min(1, 2), is(-94L));
        assertThat(range.min(3, 5), is(5L));
    }
}
