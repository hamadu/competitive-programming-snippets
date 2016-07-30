package data_structure.segment;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SegmentTreeRURSQTest {
    private static final int INF = (int)1e9;

    @Test
    public void get() {
        long[] a = new long[]{8,4,2,1,3,5,7,9,6};
        SegmentTreeRURSQ range = new SegmentTreeRURSQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            assertThat(range.sum(i, i+1), is(a[i]));
        }
    }

    @Test
    public void rangeSum() {
        long[] a = new long[]{8,4,11,2,5,1,3,12,1,5,7,5,8,13,14,3,9,6,2,10,15};
        SegmentTreeRURSQ range = new SegmentTreeRURSQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            for (int j = i+1 ; j < a.length ; j++) {
                long expected = 0;
                for (int k = i ; k < j ; k++) {
                    expected += a[k];
                }
                assertThat(range.sum(i, j), is(expected));
            }
        }
    }

    @Test
    public void rangeUpdate() {
        long[] a = new long[]{0,0,0,0,0,0};
        SegmentTreeRURSQ range = new SegmentTreeRURSQ(a);

        // 1,1,0,0,0,0
        range.update(0, 2, 1);
        assertThat(range.sum(0, 6), is(2L));

        // 2,2,2,2,2,2
        range.update(0, 6, 2);
        assertThat(range.sum(0, 2), is(4L));
        assertThat(range.sum(0, 6), is(12L));

        // 2,4,4,4,4,2
        range.update(1, 5, 4);
        assertThat(range.sum(1, 2), is(4L));
        assertThat(range.sum(3, 6), is(10L));
        assertThat(range.sum(0, 2), is(6L));
        assertThat(range.sum(0, 6), is(20L));
    }
}
