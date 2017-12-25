package net.hamadu.string;

public class Manachar {
    public static int[][] manachar(char[] c) {
        int[] odd = manacharSub(c);
        int n = c.length;
        char[] c2 = new char[2*n-1];
        for (int i = 0; i < n ; i++) {
            c2[2*i] = c[i];
        }
        for (int i = 0; i < n-1 ; i++) {
            c2[2*i+1] = '$';
        }
        int[] e = manacharSub(c2);
        int[] even = new int[n-1];
        for (int i = 0; i < n-1 ; i++) {
            even[i] = e[i*2+1]/2;
        }
        return new int[][]{odd, even};
    }

    public static int[] manacharSub(char[] c) {
        int n = c.length;
        int[] ret = new int[n];
        int i = 0;
        int j = 0;
        while (i < n) {
            while (i-j >= 0 && i+j < n && c[i-j] == c[i+j]) {
                j++;
            }
            ret[i] = j;
            int k = 1;
            while (i - k >= 0 && i+k < n && k+ret[i-k] < j) {
                ret[i+k] = ret[i-k];
                k++;
            }
            i += k;
            j -= k;
        }
        return ret;
    }
}
