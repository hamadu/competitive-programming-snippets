package data_structure.fenwick;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FenwickTreeTest {
    @Test
    public void init() {
        FenwickTree tree = new FenwickTree(10);
        assertThat(tree.range(1, 10), is(0L));
    }

    @Test
    public void add() {
        FenwickTree tree = new FenwickTree(10);
        tree.add(2, 5);
        assertThat(tree.range(1, 1), is(0L));
        assertThat(tree.range(1, 2), is(5L));
        assertThat(tree.range(2, 2), is(5L));
        assertThat(tree.range(2, 7), is(5L));
        assertThat(tree.range(3, 10), is(0L));

        tree.add(10, 1);
        assertThat(tree.range(3, 10), is(1L));
        assertThat(tree.range(0, 10), is(6L));

        tree.add(1, 2);
        assertThat(tree.range(1, 1), is(2L));
        assertThat(tree.range(0, 10), is(8L));
    }
}
