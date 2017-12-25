package net.hamadu.string;

public class ZFunction {
    /**
     * Computes z-function of given string a.
     * (z[idx] := longest common prefix of ([idx,n), [0,n))
     *
     * @param a
     * @return
     */
    public static int[] buildZ(char[] a) {
        int n = a.length;
        int[] z = new int[n];
        if (n == 0) return z;
        z[0] = n;
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i > r) {
                l = r = i;
                while (r < n && a[r - l] == a[r]) {
                    r++;
                }
                z[i] = r - l;
                r--;
            } else {
                int k = i - l;
                if (z[k] < r - i + 1) {
                    z[i] = z[k];
                } else {
                    l = i;
                    while (r < n && a[r - l] == a[r]) {
                        r++;
                    }
                    z[i] = r - l;
                    r--;
                }
            }
        }
        return z;
    }
}
