package graph;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Topological Sort with lexical smallest order. (Kahn's algorithm)
 */
public class TopologicalSort2 {
    static int[] toposort(List<Integer>[] graph) {
        int n = graph.length;
        int[] in = new int[n];
        for (int i = 0 ; i < n ; i++) {
            for (int t : graph[i]) {
                in[t]++;
            }
        }

        Queue<Integer> q = new PriorityQueue<>();
        int[] res = new int[n];
        for (int i = 0 ; i < n ; i++) {
            if (in[i] == 0) {
                q.add(i);
            }
        }

        int idx = 0;
        while (q.size() >= 1) {
            int now = q.poll();
            res[idx++] = now;
            for (int t : graph[now]) {
                in[t]--;
                if (in[t] == 0) {
                    q.add(t);
                }
            }
        }
        for (int i = 0 ; i < n ; i++) {
            if (in[i] >= 1) {
                return null;
            }
        }
        return res;
    }
}
