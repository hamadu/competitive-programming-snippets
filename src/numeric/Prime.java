package numeric;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/07/31.
 */
public class Prime {
    /**
     * Computes Euler's totient function value of num : φ(num)
     *
     * O(sqrt(n))
     *
     * @param num
     * @return
     */
    static long totient(long num) {
        int[] primes = generatePrimes((int)Math.sqrt(num)+10);

        long ans = 1;
        for (long p : primes) {
            if (num < p) {
                break;
            }
            long pn = 1;
            while (num % p == 0) {
                pn *= p;
                num /= p;
            }
            ans *= (pn / p) * (p - 1);
        }
        if (num >= 2) {
            ans *= num - 1;
        }
        return ans;
    }


    /**
     * Generates primes less than upto.
     *
     * O(nlog(logn))
     *
     * @param upto limit
     * @return array of primes
     */
    static int[] generatePrimes(int upto) {
        boolean[] isp = new boolean[upto];
        Arrays.fill(isp, true);
        isp[0] = isp[1] = false;

        int pi = 0;
        for (int i = 2; i < upto ; i++) {
            if (isp[i]) {
                pi++;
                for (int j = i * 2; j < upto; j += i) {
                    isp[j] = false;
                }
            }
        }

        int[] ret = new int[pi];
        int ri = 0;
        for (int i = 2 ; i < upto ; i++) {
            if (isp[i]) {
                ret[ri++] = i;
            }
        }
        return ret;
    }

    /**
     * Moebius function. Returns array of μ(n).
     * O(n)
     *
     * @param n limit
     * @return array of μ(n)
     */
    public static int[] moebius(int n) {
        boolean[] isp = new boolean[n];
        Arrays.fill(isp, true);
        isp[1] = isp[0] = false;
        for (int i = 2 ; i < isp.length ; i++) {
            if (isp[i]) {
                for (int ii = i*2 ; ii < isp.length ; ii += i) {
                    isp[ii] = false;
                }
            }
        }

        int[] mb = new int[n];
        Arrays.fill(mb, 1);
        for (int x = 1 ; x < mb.length ; x++) {
            if (isp[x]) {
                int cnt = 1;
                for (int xx = x ; xx < mb.length ; xx += x) {
                    if (cnt % x == 0) {
                        mb[xx] = 0;
                    } else {
                        mb[xx] *= -1;
                    }
                    cnt++;
                }
            }
        }
        return mb;
    }
}
