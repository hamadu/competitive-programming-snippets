package net.hamadu.graph.tree;

import net.hamadu.utils.io.GraphBuilder;
import net.hamadu.utils.rand.XorShift;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hama_du on 2016/07/31.
 */
public class HeavyLightDecompositionTest {
    public static int[][] buildLineGraph(int n) {
        int[][] tree = new int[n][];
        tree[0] = new int[]{1};
        for (int i = 1 ; i+1 < n ; i++) {
            tree[i] = new int[]{i-1, i+1};
        }
        tree[n-1] = new int[]{n-2};
        return tree;
    }

    @Test
    public void lineGraph1() {
        int[][] tree = buildLineGraph(5);
        HeavyLightDecomposition decomp = new HeavyLightDecomposition(tree, 0);
        assertThat(decomp.groupSize.length, is(1));
    }

    @Test
    public void lineGraph2() {
        int[][] tree = buildLineGraph(5);
        HeavyLightDecomposition decomp = new HeavyLightDecomposition(tree, 1);
        assertThat(decomp.groupSize.length, is(2));
        assertThat(decomp.groupID, is(new int[]{1,0,0,0,0}));
        assertThat(decomp.groupLevel, is(new int[]{0,0,1,2,3}));
    }

    @Test
    public void graph() {
        int[][] tree = new int[][]{
            {1, 2, 13, 14},
            {0},
            {0, 3, 5, 10},
            {2, 4},
            {3},
            {2, 6},
            {5, 7},
            {6, 8, 9},
            {7},
            {7},
            {2, 11},
            {10, 12},
            {11},
            {0},
            {0}
        };
        HeavyLightDecomposition decomp = new HeavyLightDecomposition(tree, 0);

        assertThat(decomp.groupSize.length, is(7));
        for (int x : new int[]{0, 1, 13, 14, 3, 9, 10}) {
            assertThat(decomp.groupLevel[x], is(0));
        }
    }

    @Test
    public void largeGraph() {
        XorShift rand = new XorShift();
        int n = 200000;
        int[] par = new int[n];
        par[0] = -1;
        for (int i = 1 ; i < n ; i++) {
            par[i] = rand.nextInt(i);
        }
        int[][] tree = GraphBuilder.buildRootedTreeFromPar(par);
        HeavyLightDecomposition decomp = new HeavyLightDecomposition(tree, 0);
        assertThat(decomp.groupLevel.length, is(n));
    }
}
