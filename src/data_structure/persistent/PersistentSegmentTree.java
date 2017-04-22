package data_structure.persistent;

import java.util.Arrays;

/**
 * Persistent segment tree (point add, range sum query)
 */
public class PersistentSegmentTree {
    int N;
    int M;
    int[] seg;
    int[] left;
    int[] right;
    int[] parent;

    int newNodeId;

    public PersistentSegmentTree(int N, int Q) {
        N = Math.max(16, Integer.highestOneBit(N-1)<<2);
        M = (N >> 1);

        int level = level(N);
        int qdata = Q * level;

        seg = new int[N+qdata];
        left = new int[N+qdata];
        right = new int[N+qdata];
        parent = new int[N+qdata];
        parent[1] = -1;
        for (int x = 1 ; x < M ; x++) {
            left[x] = x*2;
            right[x] = x*2+1;
            parent[left[x]] = x;
            parent[right[x]] = x;

        }
        newNodeId = (M-1)*2+2;
    }

    public static int level(int m) {
        return m == 0 ? 1 : level(m/2) + 1;
    }

    private int make(int oldId) {
        parent[newNodeId] = parent[oldId];
        left[newNodeId] = left[oldId];
        right[newNodeId] = right[oldId];
        seg[newNodeId] = seg[oldId];
        return newNodeId++;
    }

    private int makeBottom(int oldId, int newValue) {
        int newID = make(oldId);
        seg[newID] = newValue;
        return newID;
    }

    private int makeLeftChildParent(int oldParId, int newLeftChildID) {
        int newID = make(oldParId);
        left[newID] = newLeftChildID;
        parent[newLeftChildID] = newID;

        compute(newID);
        return newID;
    }

    private int makeRightChildParent(int oldParId, int newRightChildID) {
        int newID = make(oldParId);
        right[newID] = newRightChildID;
        parent[newRightChildID] = newID;

        compute(newID);
        return newID;
    }

    private void compute(int i) {
        seg[i] = seg[left[i]] + seg[right[i]];
    }

    private boolean isLeftChild(int par, int child) {
        return left[par] == child;
    }


    /**
     * Updates value at position idx, at time root.
     * Returns new root node index.
     *
     * @param idx
     * @param value
     */
    public int add(int idx, int value, int root) {
        int[] seq = find(idx, root);
        int oldNodeID = seq[--seqID];

        int newValue = seg[oldNodeID] + value;
        int i = makeBottom(oldNodeID, newValue);
        int oldI = oldNodeID;

        while (true) {
            int par = seq[--seqID];
            if (isLeftChild(par, oldI)) {
                i = makeLeftChildParent(par, i);
            } else {
                i = makeRightChildParent(par, i);
            }
            oldI = par;

            if (parent[i] == -1) {
                return i;
            }
        }
    }

    public int seqID;
    public int[] sequenceFromRoot = new int[32];

    public int[] find(int pos, int root) {
        int fr = 0;
        int to = M;
        int idx = root;
        seqID = 0;

        while (to - fr > 1) {
            sequenceFromRoot[seqID++] = idx;
            int med = (to + fr) / 2;
            if (pos < med) {
                idx = left[idx];
                to = med;
            } else {
                idx = right[idx];
                fr = med;
            }
        }
        sequenceFromRoot[seqID++] = idx;
        return sequenceFromRoot;
    }

    /**
     * Finds minimum value from range [l,r).
     *
     * @param l
     * @param r
     * @return minimum value
     */
    public int sum(int l, int r, int root) {
        return sum(l, r, root, 0, M);
    }

    private int sum(int l, int r, int idx, int fr, int to) {
        if (to <= l || r <= fr) {
            return 0;
        }
        if (l <= fr && to <= r) {
            return seg[idx];
        }

        int med = (fr+to) / 2;
        return sum(l, r, left[idx], fr, med) + sum(l, r, right[idx], med, to);
    }
}