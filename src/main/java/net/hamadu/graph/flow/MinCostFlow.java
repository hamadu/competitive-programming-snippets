package net.hamadu.graph.flow;

import java.util.*;

public class MinCostFlow {
    public static class State implements Comparable<State> {
        int dist;
        int now;
        public State(int _n, int _d) {
            now = _n;
            dist = _d;
        }

        @Override
        public int compareTo(State o) {
            return dist - o.dist;
        }
    }

    public static class Edge {
        int to;
        int cap;
        int rev;
        int cost;
        public Edge(int _to, int _cap, int _cost, int _rev) {
            to = _to;
            cap = _cap;
            rev = _rev;
            cost = _cost;
        }
    }

    public static int INF = 1000000000;
    public static int V;
    public static int[] h;
    public static int[] dist;
    public static int[] prevv, preve;
    public static List<Edge>[] graph;

    @SuppressWarnings("unchecked")
    public static void init(int size) {
        graph = new List[size];
        for (int i = 0 ; i < size ; i++) {
            graph[i] = new ArrayList<Edge>();
        }
        dist = new int[size];
        prevv = new int[size];
        preve = new int[size];
        h = new int[size];
        V = size;
    }

    public static void edge(int from, int to, int cap, int cost) {
        graph[from].add(new Edge(to, cap, cost, graph[to].size()));
        graph[to].add(new Edge(from, 0, -cost, graph[from].size() - 1));
    }

    public static long minCostFlowBE(int s, int t, int f) {
        long res = 0;
        Arrays.fill(h, 0);

        // make sure that topo-sorted
        for (int i = 0; i < V ; i++) {
            for (Edge e : graph[i]) {
                if (e.cap >= 1) {
                    h[e.to] = Math.min(h[e.to], h[i] + e.cost);
                }
            }
        }

        Queue<State> q = new PriorityQueue<State>();
        while (f > 0) {
            q.clear();
            Arrays.fill(dist, INF);
            dist[s] = 0;
            q.add(new State(s, 0));
            while (q.size() >= 1) {
                State stat = q.poll();
                int v = stat.now;
                if (dist[v] < stat.dist) {
                    continue;
                }
                for (int i = 0 ; i < graph[v].size(); i++) {
                    Edge e = graph[v].get(i);
                    if (e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to]) {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];
                        prevv[e.to] = v;
                        preve[e.to] = i;
                        q.add(new State(e.to, dist[e.to]));
                    }
                }
            }
            if (dist[t] == INF) {
                return res;
            }
            for (int v = 0 ; v < V ; v++) {
                h[v] += dist[v];
            }
            long d = f;
            for (int v = t ; v != s ; v = prevv[v]) {
                d = Math.min(d, graph[prevv[v]].get(preve[v]).cap);
            }
            f -= d;
            res += d * h[t];
            for (int v = t ; v != s ; v = prevv[v]) {
                Edge e = graph[prevv[v]].get(preve[v]);
                e.cap -= d;
                Edge rev = graph[v].get(e.rev);
                rev.cap += d;
            }
        }
        return res;
    }

    public static long minCostFlow(int s, int t, int f) {
        long res = 0;
        Arrays.fill(h, 0);
        Queue<State> q = new PriorityQueue<State>();
        while (f > 0) {
            q.clear();
            Arrays.fill(dist, INF);
            dist[s] = 0;
            q.add(new State(s, 0));
            while (q.size() >= 1) {
                State stat = q.poll();
                int v = stat.now;
                if (dist[v] < stat.dist) {
                    continue;
                }
                for (int i = 0 ; i < graph[v].size(); i++) {
                    Edge e = graph[v].get(i);
                    if (e.cap > 0 && dist[e.to] > dist[v] + e.cost + h[v] - h[e.to]) {
                        dist[e.to] = dist[v] + e.cost + h[v] - h[e.to];
                        prevv[e.to] = v;
                        preve[e.to] = i;
                        q.add(new State(e.to, dist[e.to]));
                    }
                }
            }
            if (dist[t] == INF) {
                return res;
            }
            for (int v = 0 ; v < V ; v++) {
                h[v] += dist[v];
            }
            long d = f;
            for (int v = t ; v != s ; v = prevv[v]) {
                d = Math.min(d, graph[prevv[v]].get(preve[v]).cap);
            }
            f -= d;
            res += d * h[t];
            for (int v = t ; v != s ; v = prevv[v]) {
                Edge e = graph[prevv[v]].get(preve[v]);
                e.cap -= d;
                Edge rev = graph[v].get(e.rev);
                rev.cap += d;
            }
        }
        return res;
    }
}
