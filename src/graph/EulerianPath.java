package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EulerianPath {
    /**
     * Given directed graph, returns an eulerian path(array of vertices).
     * It works even it contains self-loops.
     *
     * @param graph
     * @return
     */
    static List<Integer> directedEulerPath(int[][] graph) {
        int n = graph.length;
        int[] indeg = new int[n];
        int[] oudeg = new int[n];
        for (int i = 0; i < n ; i++) {
            for (int to : graph[i]) {
                indeg[to]++;
                oudeg[i]++;
            }
        }
        int from = -1;
        int to = -1;
        for (int i = 0; i < n ; i++) {
            int diff = oudeg[i] - indeg[i];
            if (diff == 1) {
                if (from == -1) {
                    from = i;
                } else {
                    return null;
                }
            } else if (diff == -1) {
                if (to == -1) {
                    to = i;
                } else {
                    return null;
                }
            } else if (diff != 0) {
                return null;
            }
        }
        if (from == -1 ^ to == -1) {
            return null;
        }

        List<Integer> vs = new ArrayList<>();
        int[] pos = new int[n];
        dfs(graph, pos, from == -1 ? 0 : from, vs);
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].length != pos[i]) {
                return null;
            }
        }
        Collections.reverse(vs);
        return vs;
    }

    static void dfs(int[][] graph, int[] pos, int now, List<Integer> vs) {
        while (pos[now] < graph[now].length) {
            int to = graph[now][pos[now]];
            pos[now]++;
            dfs(graph, pos, to, vs);
        }
        vs.add(now);
    }

}
