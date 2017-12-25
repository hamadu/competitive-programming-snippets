package net.hamadu.string;

import java.math.BigInteger;
import java.util.Random;

public class RollingHash {
    static final long MASK = (1L<<32)-1;

    long[] mul;
    long[] mod;

    public RollingHash(long seed) {
        mul = new long[2];
        mod = new long[2];
        Random rand = new Random(seed);
        for (int i = 0; i < 2 ; i++) {
            mul[i] = BigInteger.probablePrime(30, rand).longValue();
            mod[i] = BigInteger.probablePrime(30, rand).longValue();
        }
    }

    public long next(long have, char c) {
        long[] x = decompose(have);
        for (int i = 0; i < 2 ; i++) {
            x[i] *= mul[i];
            x[i] += c;
            x[i] %= mod[i];
        }
        return makeValue(x);
    }

    public long[] decompose(long val) {
        return new long[]{ val>>>32, val&MASK };
    }

    public long makeValue(long[] a) {
        return a[0]<<32L|a[1];
    }
}
