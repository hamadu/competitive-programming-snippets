package net.hamadu.combinatorics;

import java.util.Arrays;

public class Permutation {
    /**
     * Computes smallest lexicographically larger permutation of given array.
     * Modifies given array directly.
     * If there is no such permutation, returns false.
     *
     * @param a
     * @return
     *
     */
    public static boolean next_permutation(int[] a) {
        int len = a.length;
        int x = len - 2;
        while (x >= 0 && a[x] >= a[x+1]) {
            x--;
        }
        if (x == -1) {
            return false;
        }

        int y = len - 1;
        while (y > x && a[y] <= a[x]) {
            y--;
        }
        int tmp = a[x];
        a[x] = a[y];
        a[y] = tmp;
        Arrays.sort(a, x+1, len);
        return true;
    }
}
