package numeric;

public class Kitamasa {
    static long MOD = 1000000007;

    long[] base;

    // a_k = b_0 * a_0 + b_1 * a_1 + ... + b_k-1 * a_k-1
    public Kitamasa(long[] b) {
        this.base = b;
    }

    // find value a_n given a0.
    public long pow(long n, long a0) {
        int k = base.length;
        long[] a = new long[k];
        a[0] = a0;
        for (int l = 32 ; l >= 0 ; l--) {
            long ptn = 1L<<l;
            if ((n & ptn) >= 1) {
                a = stepTwo(a);
                a = stepOne(a);
            } else {
                a = stepTwo(a);
            }
        }
        long ret = 0;
        for (int i = 0; i < k ; i++) {
            ret += a[i];
        }
        return ret;
    }

    private long[] stepOne(long[] tbl) {
        int k = tbl.length;
        long[] newTbl = new long[k];
        newTbl[0] = tbl[k-1] * base[0] % MOD;
        for (int i = 1; i < k ; i++) {
            newTbl[i] = (tbl[i-1] + tbl[k-1] * base[i]) % MOD;
        }
        return newTbl;
    }

    private long[] stepTwo(long[] tbl) {
        int k = tbl.length;
        long[][] subK = new long[2][];
        long[] ret = new long[k];
        subK[0] = tbl.clone();
        for (int i = 1 ; i <= k ; i++) {
            int to = i%2;
            int fr = 1-to;
            for (int j = 0; j < k; j++) {
                ret[j] += subK[fr][j] * tbl[i-1] % MOD;
            }
            if (i < k) {
                subK[to] = stepOne(subK[fr]);
            }
        }
        for (int i = 0; i < k ; i++) {
            ret[i] %= MOD;
        }
        return ret;
    }
}
