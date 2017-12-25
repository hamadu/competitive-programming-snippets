package net.hamadu.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Strongly connected component decomposition.
 */
public class SCC1 {
    boolean[] visited;
    int[] node_id;
    List<Integer> rev;

    int n;
    int[][] graph;
    int[][] r_graph;

    public SCC1(int[][] g) {
        n = g.length;
        graph = g;
        r_graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                deg[j]++;
            }
        }
        for (int i = 0 ; i < n ; i++) {
            r_graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                r_graph[j][--deg[j]] = i;
            }
        }
    }

    public int[] scc() {
        visited = new boolean[n];
        rev = new ArrayList<Integer>();
        for (int i = 0; i<n; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        int id = 0;
        node_id = new int[n];
        visited = new boolean[n];
        for (int i = rev.size()-1; i>=0; i--) {
            if (!visited[rev.get(i)]) {
                rdfs(rev.get(i), id);
                id++;
            }
        }
        return node_id;
    }

    private void dfs(int i) {
        visited[i] = true;
        for (int next : graph[i]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
        rev.add(i);
    }

    private void rdfs(int i, int id) {
        visited[i] = true;
        node_id[i] = id;
        for (int next : r_graph[i]) {
            if (!visited[next]) {
                rdfs(next, id);
            }
        }
    }
}

