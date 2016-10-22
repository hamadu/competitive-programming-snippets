package combinatorics;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StirlingNumberTest {
    @Test
    public void first() {
        long[][] f = StirlingNumber.first(8, 100000);

        for (int i = 0; i <= 8; i++) {
            assertThat(f[i][i], is(1L));
        }
        assertThat(f[4][2], is(11L));
        assertThat(f[6][4], is(85L));
        assertThat(f[7][3], is(1624L));
    }

    @Test
    public void second() {
        long[][] s = StirlingNumber.second(8, 100000);

        for (int i = 0; i <= 8; i++) {
            assertThat(s[i][i], is(1L));
        }
        assertThat(s[4][2], is(7L));
        assertThat(s[6][4], is(65L));
        assertThat(s[7][3], is(301L));
    }

}
