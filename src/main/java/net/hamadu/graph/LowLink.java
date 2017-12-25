package net.hamadu.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class LowLink {
    int n;
    int[] parent;
    int[] cnt;
    int[] ord;
    int[] low;
    int[][] graph;
    int[][] dfsTree;
    boolean[] root;
    int oi = 0;

    public LowLink(int[][] graph) {
        this.n = graph.length;
        this.parent = new int[n];
        this.graph = graph;
        this.ord = new int[n];
        this.low = new int[n];
        this.root = new boolean[n];
        this.cnt = new int[n];
        this.dfsTree = new int[n][];
        for (int i = 0; i < n ; i++) {
            this.dfsTree[i] = new int[graph[i].length];
            Arrays.fill(this.dfsTree[i], -1);
        }
        Arrays.fill(parent, -1);
        Arrays.fill(ord, -1);
        Arrays.fill(low, n);
    }

    public void build() {
        for (int i = 0 ; i < n ; i++) {
            if (ord[i] == -1) {
                root[i] = true;
                // optional
                // dfsWithoutRecursive(i);
                dfs(i, -1);
                dfs0(i, -1);
            }
        }
    }

    private void dfsWithoutRecursive(int rt) {
        Stack<Integer> stk = new Stack<>();
        stk.push(rt);
        stk.push(-1);
        stk.push(-1);

        List<Integer> vi = new ArrayList<>();
        while (stk.size() >= 1) {
            int pid = stk.pop();
            int par = stk.pop();
            int now = stk.pop();
            if (ord[now] != -1) {
                continue;
            }
            vi.add(now);
            if (pid >= 0) {
                dfsTree[par][pid] = now;
            }
            parent[now] = par;
            ord[now] = oi;
            low[now] = oi++;
            for (int i = 0 ; i < graph[now].length ; i++) {
                int to = graph[now][i];
                if (to == par) {
                    continue;
                }
                if (ord[to] == -1) {
                    stk.push(to);
                    stk.push(now);
                    stk.push(i);
                }
            }
        }

        for (int i = vi.size()-1 ; i >= 0 ; i--) {
            int now = vi.get(i);
            cnt[now] = 1;
            for (int j = 0 ; j < graph[now].length ; j++) {
                int to = graph[now][j];
                if (to == parent[now]) {
                    // ignore parent edge
                    continue;
                }
                if (dfsTree[now][j] != -1) {
                    cnt[now] += cnt[dfsTree[now][j]];
                    low[now] = Math.min(low[now], low[dfsTree[now][j]]);
                } else {
                    // that's a back edge!
                    low[now] = Math.min(low[now], ord[to]);
                }
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
            if (ord[to] == -1) {
                dfsTree[now][i] = to;
                dfs(to, now);
                low[now] = Math.min(low[now], low[to]);
            } else {
                // that's a back edge!
                low[now] = Math.min(low[now], ord[to]);
            }
        }
    }

    private void dfs0(int now, int par) {
        cnt[now] = 1;
        for (int to : dfsTree[now]) {
            if (to == -1 || to == par) {
                continue;
            }
            dfs0(to, now);
            cnt[now] += cnt[to];
        }
    }

    private boolean isBridge(int u, int v) {
        return ord[u] < low[v];
    }

    private boolean isArticulationPoint(int u) {
        if (root[u]) {
            int cn = 0;
            for (int to : dfsTree[u]) {
                if (to != -1) {
                    cn++;
                }
            }
            return cn >= 2;
        } else {
            for (int to : dfsTree[u]) {
                if (to != -1 && ord[u] <= low[to]) {
                    return true;
                }
            }
            return false;
        }
    }
}
