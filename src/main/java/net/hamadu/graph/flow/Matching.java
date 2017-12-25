package net.hamadu.graph.flow;

import java.util.Arrays;

/**
 * Edmonds' cardinality matching algorithm. O(n^3)
 */
public class Matching {
    int[][] graph;

    int n;
    int[] mu;
    int[] phi;
    int[] rho;
    boolean[] scanned;

    public Matching(int[][] graph) {
        this.n = graph.length;
        this.graph = graph;
        mu = new int[n];
        phi = new int[n];
        rho = new int[n];
        scanned = new boolean[n];
        for (int i = 0; i < n ; i++) {
            mu[i] = phi[i] = rho[i] = i;
        }
    }

    public int[] solve() {
        int x = -1;
        while (true) {
            if (x == -1) {
                for (int i = 0; i < n; i++) {
                    if (scanned[i]) {
                        continue;
                    }
                    if (isEven(i)) {
                        x = i;
                        break;
                    }
                }
            }
            if (x == -1) {
                break;
            }
            int y = -1;
            for (int to : graph[x]) {
                if (isOuter(to) || (isEven(to) && rho[to] != rho[x])) {
                    y = to;
                }
            }
            if (y == -1) {
                scanned[x] = true;
                x = -1;
            } else if (isOuter(y)) {
                phi[y] = x;
            } else {
                int[] dx = new int[n];
                int[] dy = new int[n];
                Arrays.fill(dx, -1);
                Arrays.fill(dy, -1);
                for (int k = 0, w = x ; dx[w] < 0 ; w = (k % 2 == 1 ? mu[w] : phi[w])) {
                    dx[w] = k++;
                }
                for (int k = 0, w = y ; dy[w] < 0 ; w = (k % 2 == 1 ? mu[w] : phi[w])) {
                    dy[w] = k++;
                }
                boolean disjoint = true;
                for (int i = 0; i < n ; i++) {
                    if (dx[i] >= 0 && dy[i] > 0) {
                        disjoint = false;
                        break;
                    }
                }
                if (disjoint) {
                    for (int v = 0; v < n ; v++) {
                        if (dx[v] % 2 == 1) {
                            mu[phi[v]] = v;
                            mu[v] = phi[v];
                        }
                    }
                    for (int v = 0; v < n ; v++) {
                        if (dy[v] % 2 == 1) {
                            mu[phi[v]] = v;
                            mu[v] = phi[v];
                        }
                    }
                    mu[x] = y;
                    mu[y] = x;
                    for (int v = 0; v < n ; v++) {
                        phi[v] = rho[v] = v;
                        scanned[v] = false;
                    }
                    x = -1;
                } else {
                    int r = x;
                    int d = n;
                    for (int v = 0; v < n ; v++) {
                        if (dx[v] >= 0 && dy[v] >= 0 && rho[v] == v && d > dx[v]) {
                            d = dx[v];
                            r = v;
                        }
                    }
                    for (int v = 0; v < n ; v++) {
                        if (dx[v] <= d && dx[v] % 2 == 1 && rho[phi[v]] != r) {
                            phi[phi[v]] = v;
                        }
                    }
                    for (int v = 0; v < n ; v++) {
                        if (dy[v] <= d && dy[v] % 2 == 1 && rho[phi[v]] != r) {
                            phi[phi[v]] = v;
                        }
                    }
                    if (rho[x] != r) {
                        phi[x] = y;
                    }
                    if (rho[y] != r) {
                        phi[y] = x;
                    }
                    for (int v = 0; v < n ; v++) {
                        if (dx[rho[v]] >= 0 || dy[rho[v]] >= 0) {
                            rho[v] = r;
                        }
                    }
                }
            }
        }
        return mu;
    }

    private boolean isEven(int idx) {
        return mu[idx] == idx || (mu[idx] != idx && phi[mu[idx]] != mu[idx]);
    }

    private boolean isOdd(int idx) {
        return mu[idx] != idx && phi[mu[idx]] == mu[idx] && phi[idx] != idx;
    }

    private boolean isOuter(int idx) {
        return mu[idx] != idx && phi[mu[idx]] == mu[idx] && phi[idx] == idx;
    }
}