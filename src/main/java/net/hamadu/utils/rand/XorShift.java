package net.hamadu.utils.rand;

public class XorShift {
    private int x = 123456789;
    private int y = 362436069;
    private int z = 521288629;
    private int w = 88675123;

    public int nextInt(final int n) {
        final int t = x^(x<<11);
        x = y;
        y = z;
        z = w;
        w = (w^(w>>>19))^(t^(t>>>8));
        final int r = w%n;
        return r < 0 ? r+n : r;
    }

    public int next() {
        final int t = x^(x<<11);
        x = y;
        y = z;
        z = w;
        w = w^(w>>>19)^(t^(t>>>8));
        return w;
    }

    public double nextDouble() {
        return (double) (next()+(1L<<31))/(1L<<32);
    }

    public long nextLong() {
        return ((long) next()<<32)^(((long) next()<<32)>>>32);
    }
}
