package combinatorics;

/**
 * Computes Combination number in various ways.
 */
public class Combination {
    static final int MOD = 1000000007;

    static long[] _fact;

    static long[] _invfact;

    static long pow(long a, long x) {
        long res = 1;
        while (x > 0) {
            if (x % 2 != 0) {
                res = (res * a) % MOD;
            }
            a = (a * a) % MOD;
            x /= 2;
        }
        return res;
    }

    static long inv(long a) {
        return pow(a, MOD - 2) % MOD;
    }


    /**
     * Calculates Combination(n,r) mod MOD. You must call prec(n) to precalculate before call this.
     * O(1)
     *
     * @param n
     * @param r
     * @return Combination(ln,lr) mod MOD
     */
    static long comb(long n, long r) {
        if (n < 0 || r < 0 || r > n) {
            return 0;
        }
        if (r > n / 2) {
            r = n - r;
        }
        int in = (int)n;
        int ir = (int)r;
        return (((_fact[in] * _invfact[in - ir]) % MOD) * _invfact[ir]) % MOD;
    }


    /**
     * Calculates Combination(n,r) mod MOD.
     * O(r)
     *
     * @param n
     * @param r
     * @return
     */
    static long comb2(long n, long r) {
        long upper = 1;
        for (long nl = 0; nl < r; nl++) {
            upper = (upper * ((n - nl) % MOD)) % MOD;
        }

        long downer = 1;
        for (int nl = 1; nl <= r; nl++) {
            downer = (downer * nl) % MOD;
        }

        return (upper * inv(downer)) % MOD;
    }


    /**
     * Calculates n! and (n!)^-1 (mod MOD). Results are given with _fact and _invfact.
     * O(n)
     *
     * @param n
     */
    static void prec(int n) {
        _fact = new long[n + 1];
        _invfact = new long[n + 1];
        _fact[0] = 1;
        _invfact[0] = 1;
        for (int i = 1; i <= n; i++) {
            _fact[i] = _fact[i - 1] * i % MOD;
        }
        _invfact[n] = inv(_fact[n]);
        for (int i = n ; i >= 2 ; i--) {
            _invfact[i-1] = (_invfact[i] * i) % MOD;
        }
    }

}


