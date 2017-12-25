package net.hamadu.string;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ErrorFunctionTest {
    @Test
    public void abcAbcAbAbcAbc() {
        int[] ret = ErrorFunction.errorFunction("abcababcabcababc".toCharArray());

        // *abcababcabcababc
        // *0001212345345678
        assertThat(ret, is(new int[]{-1, 0, 0, 0, 1, 2, 1, 2, 3, 4, 5, 3, 4, 5, 6, 7, 8}));
    }
}
