package net.hamadu.numeric;

public class GaussianElimination {
    /**
     * Computes mod2 basis on bit vectors. In this implementation, it just returns the rank.
     *
     * @param vectors
     * @return
     */
    public static int byMOD2(long[] vectors) {
        long[] basis = vectors.clone();
        int n = basis.length;
        int rank = 0;
        for (int i = 0; i < 60 ; i++) {
            int pv = -1;
            for (int j = rank ; j < n ; j++) {
                if (((basis[j] >> i) & 1) == 1) {
                    pv = j;
                    break;
                }
            }
            if (pv == -1) {
                continue;
            }
            long tmp = basis[pv];
            basis[pv] = basis[rank];
            basis[rank] = tmp;
            for (int j = 0 ; j < n ; j++) {
                if (j == rank) {
                    continue;
                }
                if (((basis[j] >> i) & 1) == 1) {
                    basis[j] ^= basis[rank];
                }
            }
            rank++;
        }
        return rank;
    }

    public static double[] byRealEquations(double[][] table) {
        int n = table.length;
        int m = table[0].length;
        if (m != n+1) {
            throw new RuntimeException("wrong table size");
        }

        for (int c = 0; c < n ; c++) {
            int pivot = c;
            for (int i = c ; i < n ; i++) {
                if (Math.abs(table[pivot][c]) < Math.abs(table[i][c])) {
                    pivot = i;
                }
            }
            for (int j = 0; j < m ; j++) {
                double tmp = table[c][j];
                table[c][j] = table[pivot][j];
                table[pivot][j] = tmp;
            }

            if (Math.abs(table[c][c]) < 1e-15) {
                return null;
            }

            for (int j = c+1 ; j < m  ; j++) {
                table[c][j] /= table[c][c];
            }
            for (int i = 0; i < n ; i++) {
                if (c != i) {
                    double bai = table[i][c];
                    for (int j = c ; j < m ; j++) {
                        table[i][j] -= bai * table[c][j];
                    }
                }
            }
        }

        double[] answer = new double[n];
        for (int i = 0; i < n ; i++) {
            answer[i] = table[i][n];
        }
        return answer;
    }

}
