package net.hamadu.graph.flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Maximum flow (dinic).
 */
public class MaxFlowDinic {
    public List<int[]>[] graph;
    public int[] deg;

    public int[] level;
    public int[] itr;

    public int[] que;

    @SuppressWarnings("unchecked")
    public void init(int size) {
        graph = new List[size];
        for (int i = 0; i < size ; i++) {
            graph[i] = new ArrayList<int[]>();
        }
        deg = new int[size];
        level = new int[size];
        itr = new int[size];
        que = new int[size+10];
    }

    /**
     * Adds directed edge between from-to with specified capacity.
     *
     * @param from
     * @param to
     * @param cap  the edge capacity
     */
    public void addEdge(int from, int to, int cap) {
        int fdeg = deg[from];
        int tdeg = deg[to];
        graph[from].add(new int[]{to, cap, tdeg});
        graph[to].add(new int[]{from, 0, fdeg});
        deg[from]++;
        deg[to]++;
    }

    private int dfs(int v, int t, int f) {
        if (v == t) {
            return f;
        }
        for (int i = itr[v] ; i < graph[v].size() ; i++) {
            itr[v] = i;
            int[] e = graph[v].get(i);
            if (e[1] > 0 && level[v] < level[e[0]]) {
                int d = dfs(e[0], t, Math.min(f, e[1]));
                if (d > 0) {
                    e[1] -= d;
                    graph[e[0]].get(e[2])[1] += d;
                    return d;
                }
            }
        }
        return 0;
    }

    private void bfs(int s) {
        Arrays.fill(level, -1);
        int qh = 0;
        int qt = 0;
        level[s] = 0;
        que[qh++] = s;
        while (qt < qh) {
            int v = que[qt++];
            for (int i = 0; i < graph[v].size() ; i++) {
                int[] e = graph[v].get(i);
                if (e[1] > 0 && level[e[0]] < 0) {
                    level[e[0]] = level[v] + 1;
                    que[qh++] = e[0];
                }
            }
        }
    }

    /**
     * Computes s-t maximum flow.
     *
     * @param s source
     * @param t sink
     * @return s-t maximum flow
     */
    public int maxFlow(int s, int t) {
        int flow = 0;
        while (true) {
            bfs(s);
            if (level[t] < 0) {
                return flow;
            }
            Arrays.fill(itr, 0);
            while (true) {
                int f = dfs(s, t, Integer.MAX_VALUE);
                if (f <= 0) {
                    break;
                }
                flow += f;
            }
        }
    }

    /**
     * Returns vertex cover of bipartite-matching.
     * Call this after executing maxFlow.
     *
     * @param s number of vertices except source and sink
     * @param left number of vertices placed on left
     * @param right number of vertices placed on right
     * @return
     */
    public int[] getVertexCover(int s, int left, int right) {
        boolean[] canVisit = new boolean[graph.length];
        int[] que = new int[graph.length+10];
        int qh = 0;
        int qt = 0;
        que[qh++] = s;
        canVisit[s] = true;
        while (qt < qh) {
            int now = que[qt++];
            for (int[] e : graph[now]) {
                if (e[1] >= 1 && !canVisit[e[0]]) {
                    canVisit[e[0]] = true;
                    que[qh++] = e[0];
                }
            }
        }

        int[] response = new int[graph.length];
        int ri = 0;
        for (int i = 0 ; i < left ; i++) {
            if (!canVisit[i]) {
                response[ri++] = i;
            }
        }
        for (int i = left ; i < left+right ; i++) {
            if (canVisit[i]) {
                response[ri++] = i;
            }
        }
        return Arrays.copyOf(response, ri);
    }
}
