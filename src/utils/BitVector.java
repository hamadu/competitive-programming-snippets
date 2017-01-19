package utils;

import java.util.*;

public class BitVector {
    public int n;
    public int m;
    public long[] bits;

    public BitVector(int length) {
        n = length;
        bits = new long[(n+63)>>>6];
        m = bits.length;
    }

    public void set(int at) {
        bits[at>>>6] |= 1L<<(at&63);
    }

    public void set(int at, boolean s) {
        if (s) {
            bits[at>>>6] |= 1L<<(at&63);
        } else {
            bits[at>>>6] &= ~(1L<<(at&63));
        }
    }

    public boolean get(int at) {
        int big = at >>> 6 ;
        if (big >= bits.length) {
            return false;
        }
        return ((bits[big] >>> (at&63)) & 1) == 1;
    }

    public BitVector shiftLeft(int l) {
        BitVector ret = new BitVector(n+l);
        int big = l >>> 6;
        int small = l & 63;
        for (int i = 0; i < m ; i++) {
            ret.bits[i+big] |= bits[i] << small;
        }
        if (small >= 1) {
            for (int i = 0; i+big+1 < ret.m; i++) {
                ret.bits[i+big+1] |= (bits[i] >>> (64-small));
            }
        }
        return ret;
    }

    public BitVector or(BitVector o) {
        BitVector ans = new BitVector(Math.max(n, o.n));
        for (int i = 0; i < ans.m ; i++) {
            if (i < m) {
                ans.bits[i] = bits[i];
            }
            if (i < o.m) {
                ans.bits[i] |= o.bits[i];
            }
        }
        return ans;
    }

    public static boolean canMake(int[] x, int n) {
        Map<Integer,Integer> cnt = new HashMap<>();
        for (int i = 0; i < x.length ; i++) {
            cnt.put(x[i], cnt.getOrDefault(x[i],0)+1);
        }
        List<Integer> t = new ArrayList<>();
        for (int k : cnt.keySet()) {
            int v = cnt.get(k);
            int l = 1;
            while (l <= v) {
                v -= l;
                t.add(k * l);
                l *= 2;
            }
            if (v >= 1) {
                t.add(k * v);
            }
        }
        Collections.sort(t);

        BitVector v = new BitVector(1);
        v.set(0);
        for (int ti : t) {
            v = v.or(v.shiftLeft(ti));
        }
        return v.get(n);
    }

}
