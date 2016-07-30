package data_structure.persistent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PersistentArrayTest {
    @Test
    public void neutral() {
        int SIZE = 256;
        int[] f = new int[SIZE];
        for (int i = 0; i < SIZE ; i++) {
            f[i] = (int)(Math.random() * 256);
        }
        PersistentArray array = new PersistentArray(f, 10000);
        for (int x = 0 ; x < f.length  ; x++) {
            assertThat(array.get(0, x), is(f[x]));
        }
    }

    @Test
    public void mutate() {
        PersistentArray array = new PersistentArray(new int[]{0,1,2,3,4,5,6,7}, 10000);

        int a0 = array.set(0, 1, 10);
        assertThat(array.get(a0, 1), is(10));

        int a1 = array.set(0, 7, 70);
        assertThat(array.get(a1, 1), is(1));
        assertThat(array.get(a1, 7), is(70));

        int a2 = array.set(a0, 2, 20);
        int a3 = array.set(a2, 2, 200);
        int[] expected = new int[]{0, 10, 200, 3, 4, 5, 6, 7};
        for (int i = 0 ; i < expected.length ; i++) {
            assertThat(array.get(a3, i), is(expected[i]));
        }
    }
}
