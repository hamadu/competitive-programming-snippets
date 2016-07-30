package graph;

import java.util.Arrays;
import java.util.BitSet;

/**
 * Minimum spanning arborescence. Defined with each root.
 */
public class MinimumSpanningArborescence {
    int INF = 100000000;
    int n;
    int[][] graph;

    MinimumSpanningArborescence(int[][] g) {
        n = g.length;
        graph = g;
    }

    int doit(int root) {
        int cost = 0;
        int[] c = doit(graph, root);
        int err = 0;
        for (int j = 0; j < n ; j++) {
            if (c[j] >= 0) {
                cost += graph[c[j]][j];
            } else {
                err++;
            }
        }
        return err >= 2 ? -1 : cost;
    }

    int[] doit(int[][] graph, int root) {
        int n = graph.length;
        int[] msa = new int[n];
        Arrays.fill(msa, -1);

        for (int i = 0; i < n; i++) {
            if (i == root) {
                continue;
            }
            int min = INF;
            int e = -1;
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                if (min > graph[j][i]) {
                    min = graph[j][i];
                    e = j;
                }
            }
            msa[i] = e;
        }

        int[] res = detectCycle(msa);
        if (res != null) {
            int cv = res[0];

            int[] map = new int[n];
            Arrays.fill(map,-1);
            int quo = n-res[1];
            map[cv] = quo;

            int mincy = graph[msa[cv]][cv];
            for (int k=msa[cv]; k!=cv; k=msa[k]) {
                map[k] = quo;
                mincy = Math.min(mincy, graph[msa[k]][k]);
            }

            int[] imap = new int[n-res[1]];
            int ptr = 0;
            for (int i = 0; i < n; i++) {
                if (map[i] == quo) {
                    continue;
                }
                map[i] = ptr;
                imap[ptr] = i;
                ptr++;
            }

            int[][] quog = new int[quo+1][quo+1];
            for (int i = 0; i < quo+1; i++) {
                Arrays.fill(quog[i],INF);
            }

            int[] to = new int[n];
            int[] from = new int[n];
            for (int i = 0; i < n; i++) {
                if (map[i] != quo) {
                    for (int j = 0; j < n; j++) {
                        if (map[j] == quo) {
                            int nc = graph[i][j] - graph[msa[j]][j] + mincy;
                            if (quog[map[i]][quo] > nc) {
                                quog[map[i]][quo] = nc;
                                to[i] = j;
                            }
                        }else {
                            quog[map[i]][map[j]] = graph[i][j];
                        }
                    }
                }else {
                    for (int j = 0; j < n; j++) {
                        if (map[j] != quo) {
                            int nc = graph[i][j];
                            if (quog[quo][map[j]] > nc) {
                                quog[quo][map[j]] = nc;
                                from[j] = i;
                            }
                        }
                    }
                }
            }

            int[] quomsa = doit(quog, map[root]);
            for (int i = 0; i<quo; i++) {
                if (quomsa[i] == quo) {
                    msa[imap[i]] = from[imap[i]];
                } else if (quomsa[i] != -1) {
                    msa[imap[i]] = imap[quomsa[i]];
                }
            }
            int u = imap[quomsa[quo]];
            msa[to[u]] = u;
        }
        return msa;
    }

    int[] detectCycle(int[] f) {
        int n = f.length;
        BitSet visited = new BitSet(n);
        outer:
        for (int src = 0; src < n; src++) {
            if (visited.get(src) || f[src] < 0) {
                continue;
            }
            int power = 1;
            int lambda = 1;
            int tortoise = src;
            int hare = f[src];
            visited.set(src);
            while (tortoise != hare) {
                if (hare < 0) {
                    continue outer;
                }
                visited.set(hare);
                if (power == lambda) {
                    tortoise = hare;
                    power <<= 1;
                    lambda = 0;
                }
                hare = f[hare];
                lambda++;
            }
            if (lambda > 0) {
                return new int[]{hare, lambda};
            }
        }
        return null;
    }
}