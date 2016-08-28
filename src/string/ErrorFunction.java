package string;


public class ErrorFunction {
    /**
     * Computes error function of given string a.
     * (err[idx] := length of longest suffix |S| that matches to prefix of length idx.
     * i.e. largest S < idx s.t. [0,S) == [idx-S, idx)
     *
     * @param a
     * @return
     */
    public static int[] errorFunction(char[] a) {
        int len = a.length;
        int[] err = new int[len+1];
        err[0] = -1;
        for (int i = 2 ; i <= len ; i++) {
            int now = err[i-1];
            while (now > 0 && a[i-1] != a[now]) {
                now = err[now];
            }
            if (a[i-1] == a[now]) {
                now++;
            } else {
                now = 0;
            }
            err[i] = now;
        }
        return err;
    }

}
