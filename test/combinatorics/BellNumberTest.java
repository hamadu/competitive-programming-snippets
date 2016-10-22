package combinatorics;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BellNumberTest {
    @Test
    public void bell() {
        long[][] f = BellNumber.bellTriangle(8, 100000);

        long[] bell = new long[]{1, 1, 2, 5, 15, 52, 203, 877};
        for (int i = 0; i < bell.length ; i++) {
            assertThat(f[i][i], is(bell[i]));
        }

        // {1} is the largest singleton.
        // - {{1}, {2,3}}
        assertThat(f[2][0], is(1L));

        // {1} is the largest singleton.
        // - {{1}, {2,3,4,5}}
        // - {{1}, {2,3}, {4,5}}
        // - {{1}, {2,4}, {3,5}}
        // - {{1}, {2,5}, {3,4}}
        assertThat(f[4][0], is(4L));

        // {3} is the largest singleton.
        // - {{1,2,4}, {3}}
        // - {{1}, {2,4}, {3}}
        // - {{1,4}, {2}, {3}}
        assertThat(f[3][2], is(3L));

        // {6} is the largest singleton.
        // - {{1,2,3}, {5,7}, {4}, {6}}
        // - {{1,2,3,4,5,7}, {6}}
        // - {{1,2}, {3,4}, {5,7}, {6}}

        // - ...and so on.
        assertThat(f[6][5], is(151L));
    }
}
