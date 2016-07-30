package data_structure.heap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class TreapFindIntTest {

    public static TreapFindInt createTreap(int[] a) {
        TreapFindInt treap = new TreapFindInt();
        for (int i = 0; i < a.length ; i++) {
            treap.push(a[i]);
        }
        return treap;
    }

    @Test
    public void createEmpty() {
        TreapFindInt treap = new TreapFindInt();
        assertThat(treap.root, nullValue());
        assertThat(treap.size(), is(0));
    }

    @Test
    public void pushValues() {
        TreapFindInt treap = createTreap(new int[]{0,1,2,3,4});

        assertThat(treap.root, notNullValue());
        assertThat(treap.size(), is(5));
    }

    @Test
    public void findNode() {
        TreapFindInt treap = createTreap(new int[]{0,10,20,30,40,50,60,70,80,90});

        assertThat(treap.detect(0), is(0));
        assertThat(treap.detect(20), is(2));
        assertThat(treap.detect(70), is(7));
        assertThat(treap.detect(90), is(9));
        assertThat(treap.detect(25) < 0, is(true));
        assertThat(treap.detect(-10) < 0, is(true));
        assertThat(treap.detect(1000) < 0, is(true));
    }

    @Test
    public void eraseValues() {
        TreapFindInt treap = createTreap(new int[]{0,1,2,3,4,5,6,7,8,9});
        assertThat(treap.size(), is(10));

        treap.remove(4);
        assertThat(treap.size(), is(9));
        assertThat(treap.detect(4) < 0, is(true));
    }

    @Test
    public void putFindRemoveManyValues() {
        final int SIZE = (int)1e6;
        TreapFindInt treap = createTreap(new int[]{});
        for (int i = 0; i < SIZE ; i++) {
            treap.push(i);
        }
        assertThat(treap.size(), is(SIZE));
        for (int i = 0; i < SIZE ; i++) {
            treap.remove(i);
        }
        assertThat(treap.size(), is(0));
    }
}
