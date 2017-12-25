package net.hamadu.utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlideMinValue {
    /**
     * Computes slide-window min value.
     * Returns array of length (|a|-k+1), i-th element means min(a[i],a[i+1],...,a[i+k-1]).
     *
     * @param a original array
     * @param k window size
     * @return min values
     */
    public static int[] slideMin(int[] a, int k) {
        int n = a.length;

        Deque<Integer> deq = new ArrayDeque<>();
        int[] slideMin = new int[n-k+1];
        for (int i = 0 ; i < n ; i++) {
            while (deq.size() >= 1 && a[deq.peekLast()] >= a[i]) {
                deq.pollLast();
            }
            deq.add(i);

            if (i - k + 1 >= 0) {
                int top = deq.peekFirst();
                slideMin[i-k+1] = a[top];
                if (top == i - k + 1) {
                    deq.pollFirst();
                }
            }
        }
        return slideMin;
    }
}
