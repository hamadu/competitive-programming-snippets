package data_structure.heap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TreapIntTest {

    public static TreapInt createTreap(int[] a) {
        TreapInt treap = new TreapInt();
        for (int i = 0; i < a.length ; i++) {
            treap.push(a[i]);
        }
        return treap;
    }

    @Test
    public void createEmpty() {
        TreapInt treap = createTreap(new int[]{});
        assertThat(treap.root, nullValue());
        assertThat(treap.size(), is(0));
    }

    @Test
    public void pushValues() {
        TreapInt treap = createTreap(new int[]{1,3,4,0,2});

        assertThat(treap.root, notNullValue());
        assertThat(treap.size(), is(5));
    }

    @Test
    public void getNode() {
        int[] x = new int[]{0,10,20,30,40,50,60,70,80,90};
        TreapInt treap = createTreap(x);
        for (int i = 0; i < x.length; i++) {
            assertThat(treap.get(i), is(x[i]));
        }
        assertThat(treap.get(-1) < 0, is(true));
        assertThat(treap.get(10) < 0, is(true));
    }

    @Test
    public void eraseValues() {
        TreapInt treap = createTreap(new int[]{0,1,2,3,4,5,6,7,8,9});
        treap.remove(3);
        treap.push(10, 0);
        treap.push(20);
        treap.remove(9);

        int[] x = new int[]{10,0,1,2,4,5,6,7,8,20};
        for (int i = 0; i < x.length; i++) {
            assertThat(treap.get(i), is(x[i]));
        }
    }

    @Test
    public void putFindRemoveManyValues() {
        final int SIZE = (int)1e6;
        TreapInt treap = createTreap(new int[]{});
        for (int i = 0; i < SIZE ; i++) {
            treap.push(i);
        }
        assertThat(treap.size(), is(SIZE));
        for (int i = SIZE-1 ; i >= 0 ; i--) {
            treap.remove(i);
        }
        assertThat(treap.size(), is(0));
    }
}
