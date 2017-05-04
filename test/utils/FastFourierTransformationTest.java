package utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FastFourierTransformationTest {
    @Test
    public void testReverseBitMap() {
        int[] ret = FastFourierTransformation.reverseBitMap(16);
        assertThat(ret, is(new int[]{0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15}));
    }

    @Test
    public void testConvolution() {
        FastFourierTransformation fft = new FastFourierTransformation(8);
        assertThat(fft.convolution(new int[]{1, 2, 3}, new int[]{3, 4, 5, 6}), is(new int[]{3, 10, 22, 28, 27, 18, 0, 0}));
    }

    @Test
    public void testConvolutionBig() {
        FastFourierTransformation fft = new FastFourierTransformation(4);
        assertThat(fft.convolution(new int[]{1000, 10000}, new int[]{3200, 120000}), is(new int[]{3200000, 152000000, 1200000000, 0}));
    }
}
