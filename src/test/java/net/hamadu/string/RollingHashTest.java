package net.hamadu.string;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RollingHashTest {
    @Test
    public void generate() {
        RollingHash rollingHash = new RollingHash(1);

        assertTrue(isPrime(rollingHash.mul[0]));
        assertTrue(isPrime(rollingHash.mul[1]));
        assertTrue(isPrime(rollingHash.mod[0]));
        assertTrue(isPrime(rollingHash.mod[1]));
    }

    @Test
    public void decompose() {
        RollingHash rollingHash = new RollingHash(1);

        long[] ex = {0, 1, -1, Integer.MAX_VALUE, Integer.MIN_VALUE, Long.MAX_VALUE, Long.MIN_VALUE};
        for (long num : ex) {
            assertEquals(rollingHash.makeValue(rollingHash.decompose(num )), num);
        }
    }

    public boolean isPrime(long l) {
        for (long x = 2 ; x * x <= l ; x++) {
            if (l % x == 0) {
                return false;
            }
        }
        return true;
    }
}
