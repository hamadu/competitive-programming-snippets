package net.hamadu.data_structure.heap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TreapIntRevTest {

    public static TreapIntRev createTreap(int[] a) {
        TreapIntRev treap = new TreapIntRev();
        for (int i = 0; i < a.length ; i++) {
            treap.push(a[i]);
        }
        return treap;
    }

    @Test
    public void pushValues() {
        TreapIntRev treap = createTreap(new int[]{0,1,2,3,4});

        assertThat(treap.root, notNullValue());
        assertThat(treap.size(), is(5));
    }

    @Test
    public void reverseRange() {
        TreapIntRev treap = createTreap(new int[]{0,10,20,30,40,50,60,70,80,90});

        // 0,10,50,40,30,20,60,70,80,90
        treap.reverse(2,6);
        assertThat(treap.size(), is(10));
        int[] expected = {0,10,50,40,30,20,60,70,80,90};
        for (int i = 0; i < expected.length ; i++) {
            assertThat(treap.get(i), is(expected[i]));
        }
    }

    @Test
    public void reverseRange2() {
        TreapIntRev treap = createTreap(new int[]{0,10,20,30,40,50,60,70,80,90});

        // 90,80,70,60,20,30,40,50,10,0
        treap.reverse(2,6);
        treap.reverse(0,10);

        assertThat(treap.size(), is(10));
        int[] expected = {90,80,70,60,20,30,40,50,10,0};
        for (int i = 0; i < expected.length ; i++) {
            assertThat(treap.get(i), is(expected[i]));
        }
    }

    @Test
    public void reverseRange3() {
        TreapIntRev treap = createTreap(new int[]{0,10,20,30,40,50,60,70,80,90});

        // 90,80,70,60,30,100,0,10,50,40
        treap.reverse(2,6);
        treap.reverse(0,10);
        treap.remove(4);
        treap.push(100);
        treap.reverse(5,10);

        assertThat(treap.size(), is(10));
        int[] expected = {90,80,70,60,30,100,0,10,50,40};
        for (int i = 0; i < expected.length ; i++) {
            assertThat(treap.get(i), is(expected[i]));
        }
    }

    @Test
    public void reverseRange4() {
        TreapIntRev treap = createTreap(new int[]{20,40,60,80,100,10,30,50,70,90});

        // 20,100,90,30,50,70,10,100,60,80,40,110
        treap.reverse(6,9);
        treap.reverse(2,4);
        treap.push(100);
        treap.push(110);
        treap.reverse(1,11);

        assertThat(treap.size(), is(12));
        int[] expected = {20,100,90,30,50,70,10,100,60,80,40,110};
        for (int i = 0; i < expected.length ; i++) {
            assertThat(treap.get(i), is(expected[i]));
        }
    }
}
