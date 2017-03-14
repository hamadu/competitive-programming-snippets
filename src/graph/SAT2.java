package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Solve 2-sat using SCC.
 */
public class SAT2 {
    boolean[] visited;
    int[] nodeId;
    List<Integer> rev;

    int n;
    int vn;
    List<Integer>[] graph;
    List<Integer>[] revGraph;

    public SAT2(int maxN) {
        n = maxN;
        vn = n * 2;
        graph = new List[vn];
        revGraph = new List[vn];
        for (int i = 0; i < vn; i++) {
            graph[i] = new ArrayList<>();
            revGraph[i] = new ArrayList<>();
        }
    }

    public int not(int v) {
        return v >= n ? v - n : v + n;
    }

    public void add(int a, int b) {
        // a or b => [(not a) then b] and [(not b) then a]
        graph[not(a)].add(b);
        graph[not(b)].add(a);
        revGraph[b].add(not(a));
        revGraph[a].add(not(b));
    }

    public boolean[] doit() {
        visited = new boolean[vn];
        rev = new ArrayList<>();
        for (int i = 0; i< vn ; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        int id = 0;
        nodeId = new int[vn];
        visited = new boolean[vn];
        for (int i = rev.size()-1; i>=0; i--) {
            if (!visited[rev.get(i)]) {
                rdfs(rev.get(i), id);
                id++;
            }
        }

        boolean[] ret = new boolean[n];
        for (int i = 0; i < n ; i++) {
            if (nodeId[i] == nodeId[i+n]) {
                return null;
            }
            ret[i] = nodeId[i] > nodeId[i+n];
        }
        return ret;
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
        nodeId[i] = id;
        for (int next : revGraph[i]) {
            if (!visited[next]) {
                rdfs(next, id);
            }
        }
    }
}
