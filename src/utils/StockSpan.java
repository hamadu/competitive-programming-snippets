package utils;

public class StockSpan {
    /**
     * For each i, computes the maximum position x s.t. a[x] >= a[i] and x < i.
     * If there is no such position, x will be -1.
     *
     * O(n)
     *
     * @param a
     * @return maximum position x for each i
     */
    static int[] stockSpanLeft(int[] a) {
        int n = a.length;
        int head = 0;
        int[] stk = new int[n+1];
        int[] L = new int[n];
        for (int i = 0 ; i < n ; i++) {
            while (head >= 1 && a[i] > a[stk[head-1]]) {
                head--;
            }
            L[i] = (head >= 1) ? stk[head-1] : -1;
            stk[head++] = i;
        }
        return L;
    }

    /**
     * For each i, computes the minimum position x s.t. a[i] <= a[x] and i < x.
     * If there is no such position, x will be n. (n = a.length)
     *
     * O(n)
     *
     * @param a
     * @return minimum position x for each i
     */
    static int[] stockSpanRight(int[] a) {
        int n = a.length;
        int head = 0;
        int[] stk = new int[n+1];
        int[] R = new int[n];
        for (int i = n-1 ; i >= 0 ; i--) {
            while (head >= 1 && a[i] > a[stk[head-1]]) {
                head--;
            }
            R[i] = (head >= 1) ? stk[head-1] : n;
            stk[head++] = i;
        }
        return R;
    }
}
