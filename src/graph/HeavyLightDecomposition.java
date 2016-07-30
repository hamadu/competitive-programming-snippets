package graph;

import java.util.Arrays;

/**
 * Created by hama_du on 2016/07/31.
 */
public class HeavyLightDecomposition {
    int head;
    int n;
    int parentGid;
    int[] idx;
    int depth;

    // SegmentTree seg;

    public HeavyLightDecomposition(int h, int pgid, int[] ids, int de) {
        n = ids.length;
        head = h;
        idx = ids;
        parentGid = pgid;
        depth = de;

        int[] v = new int[n];
        for (int i = 0; i < n ; i++) {
            // v[i] = W[ids[i]];
        }
        // seg = new SegmentTree(v);
    }

    static class HeavyLightDecomposer {
        int n;
        int[][] graph;
        int[] parent;
        int[] children;

        int[] gid;
        int[] orderInGroup;
        int[] parentGid;

        HeavyLightDecomposition[] components;

        static int[] _temp_ids = new int[300000];
        static int[] _stk = new int[1000000];

        public HeavyLightDecomposer(int[][] g, int root) {
            graph = g;
            n = graph.length;
            parent = new int[n];
            children = new int[n];
            gid = new int[n];
            orderInGroup = new int[n];
            parentGid = new int[n];

            dfs0(root);
        }

        public void dfs0(int root) {
            int head = 0;
            _stk[head++] = root;
            _stk[head++] = -1;
            int tid = 0;
            while (head > 0) {
                int par = _stk[--head];
                int now = _stk[--head];
                _temp_ids[tid++] = now;
                parent[now] = par;
                for (int to : graph[now]) {
                    if (to != par) {
                        _stk[head++] = to;
                        _stk[head++] = now;
                    }
                }
            }
            for (int t = tid -1 ; t >= 0 ; t--) {
                int now = _temp_ids[t];
                children[now] += 1;
                if (parent[now] != -1) {
                    children[parent[now]] += children[now];
                }
            }
        }

        public void doit() {
            ngi = 0;
            decompose(0);
            buildHLComponent();
        }

        static int ngi = 0;

        public void decompose(int root) {
            int sh = 0;
            _stk[sh++] = root;
            _stk[sh++] = -1;
            _stk[sh++] = -1;
            _stk[sh++] = 0;
            while (sh > 0) {
                int depth = _stk[--sh];
                int pgi = _stk[--sh];
                int gi = _stk[--sh];
                int head = _stk[--sh];
                if (gi == -1) {
                    gi = ngi++;
                }

                gid[head] = gi;
                orderInGroup[head] = depth;
                parentGid[head] = pgi;

                int maxTo = -1;
                int max = -1;
                for (int to : graph[head]) {
                    if (parent[head] == to) {
                        continue;
                    }
                    if (max < children[to]) {
                        max = children[to];
                        maxTo = to;
                    }
                }
                if (maxTo == -1) {
                    continue;
                }

                // heavy
                _stk[sh++] = maxTo;
                _stk[sh++] = gi;
                _stk[sh++] = gi;
                _stk[sh++] = depth+1;

                // light
                for (int to : graph[head]) {
                    if (parent[head] == to) {
                        continue;
                    }
                    if (to != maxTo) {
                        _stk[sh++] = to;
                        _stk[sh++] = -1;
                        _stk[sh++] = gi;
                        _stk[sh++] = 0;
                    }
                }
            }
        }

        public void decompose(int head, int gi, int pgi, int depth) {
            if (gi == -1) {
                gi = ngi++;
            }
            gid[head] = gi;
            orderInGroup[head] = depth;
            parentGid[head] = pgi;

            int maxTo = -1;
            int max = -1;
            for (int to : graph[head]) {
                if (parent[head] == to) {
                    continue;
                }
                if (max < children[to]) {
                    max = children[to];
                    maxTo = to;
                }
            }
            if (maxTo == -1) {
                return;
            }

            // heavy
            decompose(maxTo, gi, gi, depth+1);

            // light
            for (int to : graph[head]) {
                if (parent[head] == to) {
                    continue;
                }
                if (to != maxTo) {
                    decompose(to, -1, gi, 0);
                }
            }
        }

        public void buildHLComponent() {
            int maxG = 0;
            for (int gi : gid) {
                maxG = Math.max(maxG, gi+1);
            }

            components = new HeavyLightDecomposition[maxG];
            int[] que = new int[n*2];
            int qh = 0;
            int qt = 0;
            que[qh++] = 0;
            que[qh++] = -1;
            while (qt < qh) {
                int head = que[qt++];
                int parentGid = que[qt++];
                int now = head;

                int tid = 0;
                while (true) {
                    _temp_ids[tid++] = now;
                    int next = -1;
                    for (int to : graph[now]) {
                        if (to == parent[now]) {
                            continue;
                        }
                        if (gid[head] != gid[to]) {
                            que[qh++] = to;
                            que[qh++] = gid[head];
                        } else {
                            next = to;
                        }
                    }
                    if (next == -1) {
                        break;
                    }
                    now = next;
                }

                int[] aids = Arrays.copyOf(_temp_ids, tid);
                int depth = 0;
                if (parentGid != -1) {
                    depth = components[parentGid].depth + 1;
                }
                components[gid[head]] = new HeavyLightDecomposition(head, parentGid, aids, depth);
            }
        }
    }
}

