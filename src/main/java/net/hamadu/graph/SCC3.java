package net.hamadu.graph;

import java.util.*;

/**
 * Decompose graph edges into components such that each component doesn't contain an articulation point.
 */
public class SCC3 {
    int n;
    int[] ord;
    int[] low;
    int[][] graph;
    boolean[] root;
    int oi = 0;
    Stack<int[]> tmpEdges;

    List<List<int[]>> edgeComponents;

    public SCC3(int[][] graph) {
        this.n = graph.length;
        this.graph = graph;
        this.ord = new int[n];
        this.low = new int[n];
        this.root = new boolean[n];
        tmpEdges = new Stack<>();
        edgeComponents = new ArrayList<>();
        Arrays.fill(ord, -1);
        Arrays.fill(low, n);
    }

    public void build() {
        for (int i = 0 ; i < n ; i++) {
            if (ord[i] == -1) {
                root[i] = true;
                dfs(i, -1);
            }
        }
    }

    private void dfs(int now, int par) {
        if (ord[now] != -1) {
            return;
        }
        ord[now] = oi;
        low[now] = oi++;
        for (int i = 0 ; i < graph[now].length ; i++) {
            int to = graph[now][i];
            if (to == par) {
                continue;
            }
            if (ord[to] < ord[now]) {
                tmpEdges.add(new int[]{now, to});
            }

            if (ord[to] == -1) {
                dfs(to, now);
                low[now] = Math.min(low[now], low[to]);
                if (low[to] >= ord[now]) {
                    List<int[]> edges = new ArrayList<>();
                    while (tmpEdges.size() >= 1) {
                        int[] head = tmpEdges.pop();
                        edges.add(head);
                        if (Math.min(head[0], head[1]) == Math.min(now, to)) {
                            if (Math.max(head[0], head[1]) == Math.max(now, to)) {
                                break;
                            }
                        }
                    }
                    edgeComponents.add(edges);
                }
            } else {
                // that's a back edge!
                low[now] = Math.min(low[now], ord[to]);
            }
        }
    }
}
