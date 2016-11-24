package data_structure.fenwick;

/**
 * process Range-add, Range-sum query using two Fenwick trees.
 *
 * @WARNING 1-indexed, [inclusive,inclusive].
 */
public class FenwickRange {
    FenwickTree bit0;
    FenwickTree bit1;

    public FenwickRange(int n) {
        bit0 = new FenwickTree(n);
        bit1 = new FenwickTree(n);
    }

    /**
     * Adds value x to [l, r].
     *
     * O(logn)
     *
     * @param l
     * @param r
     * @param x
     */
    void addRange(int l, int r, long x) {
        bit0.add(l, -x * (l-1));
        bit1.add(l, x);
        bit0.add(r+1, x * r);
        bit1.add(r+1, -x);
    }

    /**
     * Computes sum [l, r].
     *
     * O(logn)
     *
     * @param l
     * @param r
     * @return
     */
    long range(int l, int r) {
        long right = bit0.sum(r) + bit1.sum(r) * r;
        long left = bit0.sum(l-1) + bit1.sum(l-1) * (l-1);
        return right - left;
    }

    /**
     * Computes i-th value.
     *
     * O(logn)
     *
     * @param i
     * @return
     */
    long get(int i) {
        return range(i, i);
    }
}
