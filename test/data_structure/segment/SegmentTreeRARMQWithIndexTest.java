package data_structure.segment;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SegmentTreeRARMQWithIndexTest {
    private static final int INF = (int)1e9;

    private int[][] addIndex(int[] value) {
        int[][] a = new int[value.length][2];
        for (int i = 0; i < a.length; i++) {
            a[i][0] = i;
            a[i][1] = value[i];
        }
        return a;
    }

    @Test
    public void get() {
        int[][] a = addIndex(new int[]{8,4,2,1,3,5,7,9,6});
        SegmentTreeRARMQWithIndex range = new SegmentTreeRARMQWithIndex(a);

        for (int i = 0 ; i < a.length ; i++) {
            assertThat(range.min(i, i+1), is(a[i]));
        }
    }

    @Test
    public void rangeMin() {
        int[] value = new int[]{8,4,11,2,5,1,3,12,1,5,7,5,8,13,14,3,9,6,2,10,15};
        int[][] a = addIndex(value);

        SegmentTreeRARMQWithIndex range = new SegmentTreeRARMQWithIndex(a);

        for (int i = 0 ; i < a.length ; i++) {
            for (int j = i+1 ; j < a.length ; j++) {
                int expected = INF;
                int idx = -1;
                for (int k = i ; k < j ; k++) {
                    if (expected > a[k][1]) {
                        expected = a[k][1];
                        idx = k;
                    }
                }
                assertThat(range.min(i, j), is(new int[]{idx, expected}));
            }
        }
    }

    @Test
    public void rangeAdd() {
        int[][] a = addIndex(new int[]{0,0,0,0,0,0});
        SegmentTreeRARMQWithIndex range = new SegmentTreeRARMQWithIndex(a);

        // 1,1,0,0,0,0
        range.add(0, 2, 1);
        assertThat(range.min(0, 6), is(new int[]{2,0}));

        // 3,3,2,2,2,2
        range.add(0, 6, 2);
        assertThat(range.min(0, 2), is(new int[]{0,3}));
        assertThat(range.min(3, 6), is(new int[]{3,2}));

        // 3,7,6,6,6,2
        range.add(1, 5, 4);
        assertThat(range.min(1, 2), is(new int[]{1,7}));
        assertThat(range.min(4, 6), is(new int[]{5,2}));
        assertThat(range.min(0, 3), is(new int[]{0,3}));
    }
}
