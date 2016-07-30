package data_structure;

/**
 * Disjoint-Set data structure.
 *
 * find: amortized O(a(n))
 * unite: amortized O(a(n))
 * (a(n) : inverse of Ack(n,n))
 */
public class UnionFind {
    int[] rank;
    int[] parent;
    int[] cnt;

    public UnionFind(int n) {
        rank = new int[n];
        parent = new int[n];
        cnt = new int[n];
        for (int i = 0; i < n ; i++) {
            parent[i] = i;
            cnt[i] = 1;
        }
    }

    public int find(int a) {
        if (parent[a] == a) {
            return a;
        }
        parent[a] = find(parent[a]);
        return parent[a];
    }

    public void unite(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) {
            return;
        }
        if (rank[a] < rank[b]) {
            parent[a] = b;
            cnt[b] += cnt[a];
            cnt[a] = cnt[b];
        } else {
            parent[b] = a;
            cnt[a] += cnt[b];
            cnt[b] = cnt[a];
            if (rank[a] == rank[b]) {
                rank[a]++;
            }
        }
    }

    public int groupCount(int a) {
        return cnt[find(a)];
    }

    private boolean issame(int a, int b) {
        return find(a) == find(b);
    }
}
