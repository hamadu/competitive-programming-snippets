package net.hamadu.graph.shortest_path;

public class WarshallFloyd {
    public int[][] distancePair(int[][] dist) {
        int n = dist.length;
        for (int k = 0 ; k < n ; k++) {
            for (int i = 0 ; i < n ; i++) {
                for (int j = 0 ; j < n ; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
        return dist;
    }

    public boolean[][] reachability(boolean[][] reachable) {
        int n = reachable.length;
        for (int k = 0 ; k < n ; k++) {
            for (int i = 0 ; i < n ; i++) {
                for (int j = 0 ; j < n ; j++) {
                    if (reachable[i][k] && reachable[k][j]) {
                        reachable[i][j] = true;
                    }
                }
            }
        }
        return reachable;
    }
}
