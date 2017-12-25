package net.hamadu.data_structure.persistent;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Persistent segment tree (point add, range sum query)
 */
public class PersistentSegmentTreeTest {
    @Test
    public void addAtZeroOne() {
        PersistentSegmentTree seg = new PersistentSegmentTree(100, 100);

        int add10AtZero = seg.add(0, 10, 1);
        int add100AtOne = seg.add(1, 100, 1);

        assertThat(seg.sum(0, 2, 1), is(0));
        assertThat(seg.sum(0, 1, add10AtZero), is(10));
        assertThat(seg.sum(0, 2, add10AtZero), is(10));
        assertThat(seg.sum(1, 2, add100AtOne), is(100));
        assertThat(seg.sum(0, 2, add100AtOne), is(100));
    }

    @Test
    public void addSequence() {
        PersistentSegmentTree seg = new PersistentSegmentTree(100, 100);

        int add10At0 = seg.add(0, 10, 1);            // 0:10
        assertThat(seg.sum(0, 1, add10At0), is(10));

        int add100At10 = seg.add(10, 100, add10At0);  // 0:10 10:100
        assertThat(seg.sum(0, 1, add100At10), is(10));
        assertThat(seg.sum(10, 11, add100At10), is(100));
        assertThat(seg.sum(0, 11, add100At10), is(110));


        int sub50At5 = seg.add(5, -50, add100At10);  // 0:10 5:-50 10:100
        assertThat(seg.sum(0, 3, sub50At5), is(10));
        assertThat(seg.sum(5, 6, sub50At5), is(-50));
        assertThat(seg.sum(0, 6, sub50At5), is(-40));
        assertThat(seg.sum(0, 11, sub50At5), is(60));


        int add200At5 = seg.add(5, 200, sub50At5);   // 0:10 5:150 10:100
        assertThat(seg.sum(0, 5, add200At5), is(10));
        assertThat(seg.sum(0, 6, add200At5), is(160));
        assertThat(seg.sum(0, 11, add200At5), is(260));

        int add200At8 = seg.add(8, 200, add200At5);  // 0:10 5:150 8:200 10:100
        assertThat(seg.sum(5, 6, add200At8), is(150));
        assertThat(seg.sum(8, 9, add200At8), is(200));
        assertThat(seg.sum(0, 11, add200At8), is(460));

        int sub20At3 = seg.add(3, -20, add200At8);   // 0:10 3:-20 5:150 8:200 10:100
        assertThat(seg.sum(3, 4, sub20At3), is(-20));
        assertThat(seg.sum(0, 11, sub20At3), is(440));


        // 0:10 5:150 8:200 10:100
        assertThat(seg.sum(3, 20, add200At8), is(450));

        // 0:10 5:150 10:100
        assertThat(seg.sum(5, 11, add200At5), is(250));

        // initial state
        assertThat(seg.sum(0, 11, 1), is(0));
    }
}