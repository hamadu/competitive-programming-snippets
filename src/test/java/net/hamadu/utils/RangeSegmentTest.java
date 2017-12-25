package net.hamadu.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by hama_du on 2016/09/03.
 */
public class RangeSegmentTest {
    @Test
    public void testTrivial() {
        RangeSegment segment = new RangeSegment(100, 2);
        assertThat(segment.size(), is(1));
        assertThat(segment.get(0), is(2));
        assertThat(segment.get(99), is(2));
    }

    @Test
    public void testSplitIntoTwo() {
        RangeSegment segment = new RangeSegment(100, 1);
        segment.replace(30, 80, 0);

        assertThat(segment.size(), is(3));
        assertThat(segment.get(0), is(1));
        assertThat(segment.get(29), is(1));
        assertThat(segment.get(30), is(0));
        assertThat(segment.get(80), is(0));
        assertThat(segment.get(81), is(1));
        assertThat(segment.get(99), is(1));
    }


    @Test
    public void testMergeRange() {
        RangeSegment segment = new RangeSegment(10, -1);
        segment.replace(0, 1, 0);
        segment.replace(2, 3, 2);
        segment.replace(4, 5, 4);
        segment.replace(6, 7, 6);
        segment.replace(8, 9, 8);
        assertThat(segment.size(), is(5));

        segment.replace(0, 9, 10);
        assertThat(segment.size(), is(1));
    }

    @Test
    public void testMergeRange2() {
        RangeSegment segment = new RangeSegment(10, -1);
        segment.replace(0, 1, 0);
        segment.replace(2, 3, 2);
        segment.replace(4, 5, 4);
        segment.replace(6, 7, 6);
        segment.replace(8, 9, 8);
        assertThat(segment.size(), is(5));

        segment.replace(3, 6, 10);
        assertThat(segment.size(), is(5));

        int[] exp = new int[]{0, 0, 2, 10, 10, 10, 10, 6, 8, 8};
        int[] act = new int[exp.length];
        for (int i = 0; i < act.length ; i++) {
            act[i] = segment.get(i);
        }
        assertThat(exp, is(act));
    }

    @Test
    public void testMergeRange3() {
        RangeSegment segment = new RangeSegment(10, -1);
        segment.replace(0, 1, 0);
        segment.replace(2, 3, 2);
        segment.replace(4, 5, 4);
        segment.replace(6, 7, 6);
        segment.replace(8, 9, 8);
        assertThat(segment.size(), is(5));

        segment.replace(2, 7, 10);
        assertThat(segment.size(), is(3));

        int[] exp = new int[]{0, 0, 10, 10, 10, 10, 10, 10, 8, 8};
        int[] act = new int[exp.length];
        for (int i = 0; i < act.length ; i++) {
            act[i] = segment.get(i);
        }
        assertThat(exp, is(act));
    }
}
