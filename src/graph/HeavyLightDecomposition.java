package graph;

import java.util.Arrays;

public class HeavyLightDecomposition {
    int n;
    int[][] graph;
    int root;

    int[] parent;
    int[] children;
    int[] level;

    int[] groupSize;
    int[] groupID;
    int[] groupLevel;

    public HeavyLightDecomposition(int[][] g, int rt) {
        n = g.length;
        graph = g;
        root = rt;
        parent = new int[n];
        children = new int[n];
        level = new int[n];
        prec();
        doit();
    }

    public void prec() {
        int qh = 0, qt = 0;
        int[] que = new int[2*n];
        que[qh++] = root;
        que[qh++] = -1;

        int[] ord = new int[n];
        int oi = 0;
        while (qt < qh) {
            int now = que[qt++];
            int par = que[qt++];
            parent[now] = par;
            children[now]++;
            if (par != -1) {
                level[now] = level[par] + 1;
            }
            ord[oi++] = now;
            for (int to : graph[now]) {
                if (to != par) {
                    que[qh++] = to;
                    que[qh++] = now;
                }
            }
        }
        for (int i = n-1 ; i >= 0 ; i--) {
            int v = ord[i];
            if (parent[v] != -1) {
                children[parent[v]] += children[v];
            }
        }
    }

    public void doit() {
        int[] next = new int[n];
        int[] groupHeads = new int[n];
        groupHeads[0] = root;

        int gi = 1;
        for (int i = 0 ; i < n ; i++) {
            int max = -1;
            int maxJ = -1;
            for (int j = 0 ; j < graph[i].length ; j++) {
                int to = graph[i][j];
                if (parent[i] != to) {
                    if (max < children[to]) {
                        max = children[to];
                        maxJ = j;
                    }
                }
            }
            if (maxJ != -1) {
                next[i] = graph[i][maxJ];
                for (int j = 0; j < graph[i].length; j++) {
                    int to = graph[i][j];
                    if (parent[i] != to && j != maxJ) {
                        groupHeads[gi++] = to;
                    }
                }
            } else {
                next[i] = -1;
            }
        }

        groupID = new int[n];
        groupLevel = new int[n];
        groupSize = new int[gi];
        for (int i = 0 ; i < gi ; i++) {
            int head = groupHeads[i];
            groupID[head] = i;
            while (next[head] != -1) {
                int ne = next[head];
                groupID[ne] = i;
                groupLevel[ne] = groupLevel[head] + 1;
                head = ne;
            }
        }
        for (int i = 0; i < n ; i++) {
            groupSize[groupID[i]]++;
        }
    }
}
