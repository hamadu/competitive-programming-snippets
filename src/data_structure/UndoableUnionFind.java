package data_structure;

import java.util.Arrays;

public class UndoableUnionFind {
    int[] parent;
    int[] size;
    int[][] stk;
    int sp;

    public UndoableUnionFind(int n, int maxDepth) {
        parent = new int[n];
        size = new int[n];
        Arrays.fill(parent, -1);
        for (int i = 0; i < n ; i++) {
            parent[i] = i;
        }
        Arrays.fill(size, 1);
        stk = new int[maxDepth][3];
    }

    public int find(int x) {
        while (x != parent[x]) {
            x = parent[x];
        }
        return x;
    }

    public void unite(int u, int v) {
        u = find(u);
        v = find(v);
        if (size[u] < size[v]) {
            int tmp = u;
            u = v;
            v = tmp;
        }

        // memo order: parent, size
        stk[sp][0] = v;
        stk[sp++][1] = parent[v];
        stk[sp][0] = u;
        stk[sp++][1] = size[u];

        if (u == v) {
            return;
        }

        parent[v] = u;
        size[u] += size[v];
    }

    public void undo() {
        // remember order: size, parent
        int[] prevSize = stk[--sp];
        int[] prevParent = stk[--sp];

        size[prevSize[0]] = prevSize[1];
        parent[prevParent[0]] = prevParent[1];
    }


    public int groupCount(int a) {
        return size[find(a)];
    }

    public boolean isSame(int u, int v) {
        return find(u) == find(v);
    }
}
