package utils;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MeldableSegmentTest {
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
    public void testTrivial() {
        MeldableSegment segment = new MeldableSegment();
        assertThat(segment.size(), is(0));

        segment.meld(new MeldableSegment.Segment(-114, 514));
        assertThat(segment.size(), is(1));
    }

    @Test
    public void testMeld() {
        MeldableSegment segment = new MeldableSegment();
        segment.meld(new MeldableSegment.Segment(-10, 10));
        segment.meld(new MeldableSegment.Segment(13, 20));
        segment.meld(new MeldableSegment.Segment(8, 15)); // meld left and right!

        assertThat(segment.size(), is(1));
    }

    @Test
    public void testMeldJust() {
        MeldableSegment segment = new MeldableSegment();
        segment.meld(new MeldableSegment.Segment(-10, 10));
        segment.meld(new MeldableSegment.Segment(13, 20));
        segment.meld(new MeldableSegment.Segment(11, 12)); // meld left and right!

        assertThat(segment.size(), is(1));
    }

    @Test
    public void testMeldInRow() {
        MeldableSegment segment = new MeldableSegment();
        segment.meld(new MeldableSegment.Segment(0, 3));
        segment.meld(new MeldableSegment.Segment(5, 6));
        segment.meld(new MeldableSegment.Segment(8, 10));
        segment.meld(new MeldableSegment.Segment(4, 9)); // meld all

        assertThat(segment.size(), is(1));
    }

    @Test
    public void testMeldInRow2() {
        MeldableSegment segment = new MeldableSegment();
        segment.meld(new MeldableSegment.Segment(0, 10));
        segment.meld(new MeldableSegment.Segment(20, 40));
        segment.meld(new MeldableSegment.Segment(50, 80));
        segment.meld(new MeldableSegment.Segment(120, 150));

        segment.meld(new MeldableSegment.Segment(20, 50));

        assertThat(segment.size(), is(3));
    }

    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
