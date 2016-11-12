package graph.tree;

import org.junit.Test;

import static graph.tree.HeavyLightDecompositionTest.buildLineGraph;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hama_du on 2016/08/27.
 */
public class EulerTourTest {
    @Test
    public void starGraph() {
        int[][] tree = {
                {1},
                {0, 2, 3, 4},
                {1},
                {1},
                {1}
        };
        assertThat(EulerTour.eulerTour(tree, 0), is(new int[]{0, 1, 4, 4, 3, 3, 2, 2, 1, 0}));
    }

    @Test
    public void lineGraph() {
        int[][] tree = buildLineGraph(11);
        assertThat(EulerTour.eulerTour(tree, 0), is(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}));
    }

    @Test
    public void nonObviousGraph() {
        int[][] tree = {
                {1, 2, 3},
                    {0, 4, 5},
                    {0, 9},
                    {0, 11, 12, 13},
                        {1, 6},
                        {1, 7, 8},
                            {4},
                            {5},
                            {5},

                        {2, 10},
                            {9},

                        {3},
                        {3},
                        {3}
        };
        assertThat(EulerTour.eulerTour(tree, 0), is(new int[]{
                0, 3, 13, 13, 12, 12, 11, 11, 3, 2, 9, 10, 10, 9, 2, 1, 5, 8, 8, 7, 7, 5, 4, 6, 6, 4, 1, 0
        }));
    }
}

