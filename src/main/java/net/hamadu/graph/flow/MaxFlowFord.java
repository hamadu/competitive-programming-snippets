package net.hamadu.graph.flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Maximum flow (ford-fulkerson).
 */
public class MaxFlowFord {
    public class Edge {
        int to;
        int cap;
        int rev;
        public Edge(int _to, int _cap, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
        }
    }

    public List<Edge>[] graph;
    public boolean[] used;

    @SuppressWarnings("unchecked")
    public void init(int size) {
        graph = new List[size];
        for (int i = 0; i < size ; i++) {
            graph[i] = new ArrayList<>();
        }
        used = new boolean[size];
    }
    public void addEdge(int from, int to, int cap) {
        graph[from].add(new Edge(to, cap, graph[to].size()));
        graph[to].add(new Edge(from, 0, graph[from].size() - 1));
    }

    private int dfs(int v, int t, int f) {
        if (v == t) {
            return f;
        }
        used[v] = true;
        for (Edge e : graph[v]) {
            if (!used[e.to] && e.cap > 0) {
                int d = dfs(e.to, t, Math.min(f, e.cap));
                if (d > 0) {
                    e.cap -= d;
                    graph[e.to].get(e.rev).cap += d;
                    return d;
                }
            }
        }
        return 0;
    }

    public int maxFlow(int s, int t) {
        int flow = 0;
        while (true) {
            used = new boolean[graph.length];
            int f = dfs(s, t, Integer.MAX_VALUE);
            if (f == 0) {
                break;
            }
            flow += f;
        }
        return flow;
    }
}