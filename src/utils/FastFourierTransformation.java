package utils;

import java.util.Arrays;

public class FastFourierTransformation {
    public static void main(String[] args) {
        FastFourierTransformation fft = new FastFourierTransformation(8);
        debug(fft.convolution(new int[]{1, 2, 3}, new int[]{3, 4, 5, 6}));
    }

    public int n;
    public double[] cosi2pi;
    public int[] reverseBitMap;

    public FastFourierTransformation(int maxSize) {
        n = Integer.highestOneBit(maxSize - 1) << 1;
        cosi2pi = cosi2pi(n);
        reverseBitMap = reverseBitMap(n);
    }

    public static double[] cosi2pi(int n) {
        double[] cos = new double[n+1];
        for (int i = 0; i  <= n ; i++) {
            cos[i] = Math.cos(Math.PI * i / (n >>> 1));
        }
        return cos;
    }

    public static int[] reverseBitMap(int n) {
        int[] ord = new int[n];
        for (int i = 1, h = n>>1 ; i < n ; i <<= 1, h >>>= 1) {
            for (int j = 0; j < i ; j++) {
                ord[j+i] = ord[j] + h;
            }
        }
        return ord;
    }


    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

    public int[] convolution(int[] a, int[] b) {
        double[] aRe = new double[n];
        double[] aIm = new double[n];
        double[] bRe = new double[n];
        double[] bIm = new double[n];
        for (int i = 0; i < a.length ; i++) {
            aRe[i] = a[i];
        }
        for (int i = 0; i < b.length ; i++) {
            bRe[i] = b[i];
        }

        int[] revMap = new int[n];
        for (int i = 1, h = n>>1 ; i < n ; i <<= 1, h >>>= 1) {
            for (int j = 0; j < i ; j++) {
                revMap[j+i] = revMap[j] + h;
            }
        }
        double[][] aReIm = fft(aRe, aIm);
        double[][] bReIm = fft(bRe, bIm);

        double[][] abReIm = new double[2][n];
        for (int i = 0; i < n ; i++) {
            abReIm[0][i] = (aReIm[0][i] * bReIm[0][i] - aReIm[1][i] * bReIm[1][i]) / n;
            abReIm[1][i] = (aReIm[0][i] * bReIm[1][i] + aReIm[1][i] * bReIm[0][i]) / (-n);
        }

        double[][] result = fft(abReIm[0], abReIm[1]);
        int[] ret = new int[n];
        for (int i = 0; i < n ; i++) {
            ret[i] = (int)Math.round(result[0][i]);
        }
        return ret;
    }

    private double[][] fft(double[] fromRe, double[] fromIm) {
        int n = fromRe.length;
        double[] toRe = new double[n];
        double[] toIm = new double[n];
        for (int i = 0; i < n ; i++) {
            toRe[i] = fromRe[reverseBitMap[i]];
            toIm[i] = fromIm[reverseBitMap[i]];
        }

        for (int d = 1 ; d <= n ; d <<= 1) {
            int halfD = d >>> 1;
            for (int i = 0; i < halfD ; i++) {
                int nn = i * (n / d);
                double mulRe = cosi2pi[nn];
                double mulIm = cosi2pi[(n>>>2)+nn];
                for (int idx0 = i ; idx0 < n ; idx0 += d) {
                    int idx1 = idx0 + halfD;
                    double re = toRe[idx1] * mulRe - toIm[idx1] * mulIm;
                    double im = toRe[idx1] * mulIm + toIm[idx1] * mulRe;
                    toRe[idx1] = toRe[idx0] - re;
                    toIm[idx1] = toIm[idx0] - im;
                    toRe[idx0] += re;
                    toIm[idx0] += im;
                }
            }
        }
        return new double[][]{toRe, toIm};
    }

}
