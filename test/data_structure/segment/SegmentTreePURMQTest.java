package data_structure.segment;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SegmentTreePURMQTest {
    private static final int INF = (int)1e9;

    @Test
    public void get() {
        int[] a = new int[]{8,4,2,1,3,5,7,9,6};
        SegmentTreePURMQ range = new SegmentTreePURMQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            assertThat(range.min(i, i+1), is(a[i]));
        }
    }

    @Test
    public void rangeMin() {
        int[] a = new int[]{8,4,11,2,5,1,3,12,1,5,7,5,8,13,14,3,9,6,2,10,15};
        SegmentTreePURMQ range = new SegmentTreePURMQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            for (int j = i+1 ; j < a.length ; j++) {
                int expected = INF;
                for (int k = i ; k < j ; k++) {
                    expected = Math.min(expected, a[k]);
                }
                assertThat(range.min(i, j), is(expected));
            }
        }
    }

    @Test
    public void update() {
        int[] a = new int[]{1,1,1,1};
        SegmentTreePURMQ range = new SegmentTreePURMQ(a);
        assertThat(range.min(0,4), is(1));

        range.update(1,-1);
        assertThat(range.min(0,4), is(-1));

        range.update(3,-3);
        assertThat(range.min(1,3), is(-1));
        assertThat(range.min(0,4), is(-3));

        range.update(3,4);
        assertThat(range.min(1,3), is(-1));
        assertThat(range.min(0,4), is(-1));
    }

    @Test
    public void findIndexLessThanVTest() {
        int[] a = new int[]{8,4,11,2,5,1,3,12,1,5,7,5,8,13,14,3,9,6,2,10,15};
        SegmentTreePURMQ range = new SegmentTreePURMQ(a);

        for (int i = 0 ; i < a.length ; i++) {
            for (int j = i+1 ; j <= a.length ; j++) {
                for (int v = 0; v <= 16; v++) {
                    int naive = -1;
                    for (int k = i; k < j ; k++) {
                        if (a[k] <= v) {
                            naive = k;
                            break;
                        }
                    }
                    assertThat(range.findIndexLessThanV(i, j, v), is(naive));
                }
            }
        }
    }
}
