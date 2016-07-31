package graph;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hama_du on 2016/07/31.
 */
public class CenteroidDecompositionTest {
    @Test
    public void test() {
        int[][] tree = {
            {1},
            {0, 2, 3, 4, 5},
            {1},
            {1},
            {1},
            {1},
        };
        CenteroidDecomposition decomp = new CenteroidDecomposition(tree);
        decomp.buildCenteroidTree();

        // TODO:
        assertThat(decomp.centeroidRoot, is(1));
    }

    public void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }
}
