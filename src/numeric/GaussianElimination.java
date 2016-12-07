package numeric;

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
}
