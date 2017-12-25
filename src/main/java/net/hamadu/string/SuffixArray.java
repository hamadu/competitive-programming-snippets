package net.hamadu.string;

import java.util.Arrays;
import java.util.Comparator;

public class SuffixArray {
    int n;
    char[] base;

    Integer[] sa;
    int[] rank;
    int[] tmp;
    int[] lcp;

    int compareNode(int i, int j, int k) {
        if (rank[i] != rank[j]) {
            return rank[i] - rank[j];
        } else {
            int ri = i + k <= n ? rank[i+k] : -1;
            int rj = j + k <= n ? rank[j+k] : -1;
            return ri - rj;
        }
    }

    public SuffixArray(char[] x) {
        base = x;
        n = base.length;
    }

    void buildSA() {
        sa = new Integer[n+1];
        rank = new int[n+1];
        tmp = new int[n+1];
        for (int i = 0 ; i <= n ; i++) {
            sa[i] = i;
            rank[i] = (i < n) ? base[i] : -1;
        }
        for (int _k = 1 ; _k <= n ; _k *= 2) {
            final int k = _k;
            Arrays.sort(sa, new Comparator<Integer>() {
                @Override
                public int compare(Integer i, Integer j) {
                    return compareNode(i, j, k);
                }
            });
            tmp[sa[0]] = 0;
            for (int i = 1 ; i <= n ; i++) {
                tmp[sa[i]] = tmp[sa[i-1]] + ((compareNode(sa[i-1], sa[i], k) < 0) ? 1 : 0);
            }
            for (int i = 0 ; i <= n ; i++) {
                rank[i] = tmp[i];
            }
        }
    }

    void buildLCP() {
        for (int i = 0 ; i <= n ; i++) {
            rank[sa[i]] = i;
        }
        lcp = new int[n];
        int h = 0;
        for (int i = 0 ; i < n ; i++) {
            int j = sa[rank[i]-1];
            if (h > 0) {
                h--;
            }
            for (; j + h < n && i + h < n ; h++) {
                if (base[j+h] != base[i+h]) {
                    break;
                }
            }
            lcp[rank[i]-1] = h;
        }
    }
}