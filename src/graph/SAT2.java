package graph;

import java.util.Arrays;

/**
 * Solve 2-sat using SCC.
 */
public class SAT2 {
    boolean[] visited;
    int[] nodeId;
    int[] rev;
    int rvn;

    int n;
    int vn;
    DirectedGraph graph;
    DirectedGraph revGraph;

    public SAT2(int maxN) {
        n = maxN;
        vn = n * 2;
        graph = new DirectedGraph(vn, 2000000);
        revGraph = new DirectedGraph(vn, 2000000);
        nodeId = new int[vn];
        visited = new boolean[vn];
        rev = new int[vn];
    }

    public void clear() {
        Arrays.fill(nodeId, 0);
        Arrays.fill(visited, false);
        graph.clear();
        revGraph.clear();
        rvn = 0;
    }

    public int not(int v) {
        return v >= n ? v - n : v + n;
    }

    public void xor(int a, int b) {
        // a xor b => [a or b] and [(not a) or (not b)]
        or(a, b);
        or(not(a), not(b));
    }

    public void or(int a, int b) {
        // a or b => [(not a) then b] and [(not b) then a]
        then(not(a), b);
        then(not(b), a);
    }

    public void then(int a, int b) {
        graph.add(a, b);
        revGraph.add(b, a);
    }

    public void doit() {
        for (int i = 0; i < vn ; i++) {
            if (!visited[i]) {
                dfs(i);
            }
        }
        Arrays.fill(visited, false);
        int id = 0;
        for (int i = rvn-1 ; i >= 0; i--) {
            if (!visited[rev[i]]) {
                rdfs(rev[i], id);
                id++;
            }
        }
    }

    private boolean hasValidAssign() {
        doit();
        for (int i = 0; i < n ; i++) {
            if (nodeId[i] == nodeId[i+n]) {
                return false;
            }
        }
        return true;
    }

    private boolean[] findValidAssign() {
        doit();
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
        for (int next : graph.nexts(i)) {
            if (!visited[next]) {
                dfs(next);
            }
        }
        rev[rvn++] = i;
    }

    private void rdfs(int i, int id) {
        visited[i] = true;
        nodeId[i] = id;
        for (int next : revGraph.nexts(i)) {
            if (!visited[next]) {
                rdfs(next, id);
            }
        }
    }
}

