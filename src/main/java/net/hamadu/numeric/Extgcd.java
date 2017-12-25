package net.hamadu.numeric;

public class Extgcd {
    /**
     * Finds minimum number x s.t.
     *   x = a[0] (mod[0])
     *   x = a[1] (mod[1])
     *   ...
     *   x = a[n-1] (mod[n-1])
     *
     * @param a
     * @param mod <b>co-prime</b> numbers
     * @param M
     * @return x (mod M)
     */
    static long crtGarner(long[] a, long[] mod, long M) {
        int n = a.length;
        long[] gamma = new long[n];
        for (int k = 1 ; k < n ; k++) {
            long prod = 1;
            for (int i = 0; i < k ; i++) {
                prod = prod * mod[i] % mod[k];
            }
            gamma[k] = modInv(prod, mod[k]);
        }

        long[] v = new long[n];
        v[0] = a[0];
        for (int k = 1 ; k < n ; k++) {
            long temp = v[k-1];
            for (int j = k-2 ; j >= 0 ; j--) {
                temp = (temp * mod[j] + v[j]) % mod[k];
            }
            v[k] = (a[k] - temp) * gamma[k] % mod[k];
            if (v[k] < 0) {
                v[k] += mod[k];
            }
        }

        long value = 0;
        for (int i = n-1 ; i >= 0 ; i--) {
            value = value * mod[i] + v[i];
            value %= M;
        }

        return value;
    }

    /**
     * Finds number x s.t.
     *   x = a1 (mod1)
     *   x = a2 (mod2)
     *
     * It's known as Chinese Remainder Theorem.
     *
     * @param a1
     * @param mod1
     * @param a2
     * @param mod2
     * @return (p, L) s.t. x = p (mod L)
     */
    static long[] crt(long a1, long mod1, long a2, long mod2) {
        long g = gcd(mod1, mod2);
        long x = a1 + (mod1 / g) * (a2 - a1) * modInv(mod1 / g, mod2 / g);
        long lcm = (mod1 / g) * mod2;
        return new long[]{ (lcm + (x % lcm)) % lcm, lcm};
    }

    public static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a%b);
    }


    /**
     * computes inverse of x on mod M.
     *
     * @param x
     * @param M
     * @return
     */
    public static long modInv(long x, long M) {
        long y = M, a = 1, b = 0, t;
        while (y != 0) {
            long d = x / y;
            t = x - d * y; x = y; y = t;
            t = a - d * b; a = b; b = t;
        }
        return a >= 0 ? a % M : a % M + M;
    }

    /**
     * Finds (x, y) s.t. ax + by = gcd(a, b)
     *
     * @param a
     * @param b
     * @return (gcd(a, b), x, y)
     */
    static long[] extGCD(long a, long b) {
        if (b == 0) {
            return new long[]{a, 1, 0};
        }
        long[] gyx = extGCD(b, a%b);
        long g = gyx[0];
        long y = gyx[1];
        long x = gyx[2];
        y -= (a / b) * x;
        return  new long[]{g, x, y};
    }
}
