package net.hamadu.graph.shortest_path;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {
    int n;
    int[][][] graph;

    class State implements Comparable<State> {
        int now;
        long time;

        State(int n, long t) {
            now = n;
            time = t;
        }

        @Override
        public int compareTo(State o) {
            return Long.compare(time, o.time);
        }
    }

    public Dijkstra(int[][][] graph) {
        this.n = graph.length;
        this.graph = graph;
    }

    long[] doit(int from) {
        long[] dp = new long[n];
        Arrays.fill(dp, Long.MAX_VALUE / 10);
        Queue<State> q = new PriorityQueue<>();
        q.add(new State(from, 0));
        dp[from] = 0;
        while (q.size() >= 1) {
            State st = q.poll();
            if (dp[st.now] < st.time) {
                continue;
            }
            for (int[] e : graph[st.now]) {
                long time = st.time + e[1];
                if (dp[e[0]] > time) {
                    dp[e[0]] = time;
                    q.add(new State(e[0], time));
                }
            }
        }
        return dp;
    }
}