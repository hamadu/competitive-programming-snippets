package string;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by hama_du on 2016/08/29.
 */
public class ZFunctionTest {
    @Test
    public void abcAbcAbAbcAbc() {
        int[] ret = ZFunction.buildZ("abcababcabcababc".toCharArray());
        assertThat(ret, is(new int[]{16, 0, 0, 2, 0, 5, 0, 0, 8, 0, 0, 2, 0, 3, 0, 0}));
    }
}
