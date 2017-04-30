package numeric;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ExtgcdTest {
    @Test
    public void crtTest() {
        assertThat(Extgcd.crt(2, 3, 3, 5), is(new long[]{8L, 15L}));
        assertThat(Extgcd.crt(8, 15, 2, 7), is(new long[]{23L, 105L}));
    }

    @Test
    public void crtGarnerTest() {
        assertThat(Extgcd.crtGarner(new long[]{2, 3, 2}, new long[]{3, 5, 7}, 1000000), is(23L));


        assertThat(Extgcd.crtGarner(new long[]{3, 8, 12}, new long[]{9, 16, 35}, 1000000), is(1272L));
    }

    @Test
    public void modInvTest() {
        assertThat(Extgcd.modInv(9, 16), is(9L));

        assertThat(Extgcd.modInv(144, 35), is(9L));
    }
}
