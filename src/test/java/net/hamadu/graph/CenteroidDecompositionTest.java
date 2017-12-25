package net.hamadu.graph;

import net.hamadu.utils.io.GraphBuilder;
import net.hamadu.utils.rand.XorShift;
import org.junit.Test;

import static net.hamadu.graph.tree.HeavyLightDecompositionTest.buildLineGraph;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hama_du on 2016/07/31.
 */
public class CenteroidDecompositionTest {
    @Test
    public void starGraph() {
        int[][] tree = {
            {1},
            {0, 2, 3, 4},
            {1},
            {1},
            {1}
        };
        CenteroidDecomposition decomp = new CenteroidDecomposition(tree);
        assertThat(decomp.par, is(new int[]{1,-1,1,1,1}));
    }

    @Test
    public void lineGraph() {
        int[][] tree = buildLineGraph(11);
        CenteroidDecomposition decomp = new CenteroidDecomposition(tree);
        assertThat(decomp.par, is(new int[]{1,2,5,2,3,-1,7,8,5,8,9}));
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
        CenteroidDecomposition decomp = new CenteroidDecomposition(tree);
        assertThat(decomp.par.length, is(n));

        int[][] tree2 = GraphBuilder.buildRootedTreeFromPar(decomp.par);
        int root = -1;
        for (int i = 0; i < n ; i++) {
            if (decomp.par[i] == -1) {
                root = i;
            }
        }
        assertThat(depth(tree2, root) < 20, is(true));
    }

    private static int depth(int[][] tree, int root) {
        int max = 0;
        int n = tree.length;
        int[] depth = new int[n];
        int[] que = new int[2*n];
        int qh = 0, qt = 0;
        que[qh++] = root;
        que[qh++] = -1;
        while (qt < qh) {
            int now = que[qt++];
            int par = que[qt++];
            if (par != -1) {
                depth[now] = depth[par]+1;
                max = Math.max(max, depth[now]);
            }
            for (int to : tree[now]) {
                if (to == par) {
                    continue;
                }
                que[qh++] = to;
                que[qh++] = now;
            }
        }
        return max+1;
    }
}
