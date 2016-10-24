package graph.flow;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MatchingTest {
    @Test
    public void testTrivial() {
        int[][] graph = new int[][]{
                {1},
                {0}
        };

        Matching matching = new Matching(graph);
        int[] ret = matching.solve();
        assertThat(ret, is(new int[]{1, 0}));
    }

    @Test
    public void testTree() {
        int[][] graph = new int[][]{
                {1,2,3},
                {0, 5},
                {0},
                {0, 4},
                {3},
                {1}
        };

        Matching matching = new Matching(graph);
        int[] ret = matching.solve();
        assertThat(ret, is(new int[]{2, 5, 0, 4, 3, 1}));
    }

}
