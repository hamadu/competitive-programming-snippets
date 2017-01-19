package utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BitVectorTest {
    @Test
    public void testGetAndSet() {
        BitVector v = new BitVector(128);
        v.set(0);
        v.set(63);
        v.set(100);
        v.set(127);

        assertThat(v.get(0), is(true));
        assertThat(v.get(1), is(false));
        assertThat(v.get(63), is(true));
        assertThat(v.get(64), is(false));
        assertThat(v.get(100), is(true));
        assertThat(v.get(127), is(true));

        v.set(127, false);
        assertThat(v.get(127), is(false));
    }

    @Test
    public void testShiftLeft() {
        int[][] ranges = new int[][]{
            {0, 10},
            {30, 110},
            {190, 200}
        };
        BitVector v = new BitVector(250);
        for (int[] r : ranges) {
            for (int i = r[0]; i < r[1] ; i++) {
                v.set(i);
            }
        }

        BitVector nv = v.shiftLeft(72);
        for (int i = 0; i < nv.n; i++) {
            boolean inRange = false;
            for (int[] r : ranges) {
                if (r[0]+72 <= i && i < r[1]+72) {
                    inRange = true;
                }
            }
            assertThat(nv.get(i), is(inRange));
        }
    }

    @Test
    public void testOr() {
        BitVector v1 = new BitVector(5);
        v1.set(0);

        BitVector v2 = new BitVector(5);
        v2.set(1);

        assertThat(v1.or(v2).get(0), is(true));
        assertThat(v1.or(v2).get(1), is(true));
    }

    @Test
    public void testPacking() {
        assertThat(BitVector.canMake(new int[]{5,7,5,7,7}, 31), is(true));
        assertThat(BitVector.canMake(new int[]{5,7,5,7,7}, 24), is(true));
        assertThat(BitVector.canMake(new int[]{5,7,5,7,7}, 15), is(false));

        assertThat(BitVector.canMake(new int[]{3,3,3,3,3,3,3,3,3,3,3,3,3}, 39), is(true));
        assertThat(BitVector.canMake(new int[]{3,3,3,3,3,3,3,3,3,3,3,3,3}, 45), is(false));

        assertThat(BitVector.canMake(new int[]{10,20,30,40,50,60,70,80,90,100000,200000,300000,400000}, 1000270), is(true));
    }
}
