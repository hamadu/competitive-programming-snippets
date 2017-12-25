package net.hamadu.data_structure;

import net.hamadu.utils.BitVector;

public class WaveletTree {
    BitVector[] which;
    public int z;
    public int level;
    public int[] from;
    public int[] to;
    public int[][][] rank;
    public int[] blen;
    public long[] blenMask;
    public long[] mask;

    public WaveletTree(int[] values) {
        int max = 0;
        for (int i = 0; i < values.length ; i++) {
            max = Math.max(max, values[i]);
        }

        int n = values.length;
        z = Integer.highestOneBit(max);
        int zz = z;
        level = 0;
        while (zz >= 1) {
            zz >>= 1;
            level++;
        }
        which = new BitVector[level];
        for (int i = 0; i < level; i++) {
            which[i] = new BitVector(n);
        }
        blen = new int[which[0].bits.length];
        blenMask = new long[blen.length];
        for (int i = 0; i < blen.length; i++) {
            blen[i] = Math.min(64, n-64*i);
            blenMask[i] = (1L<<blen[i])-1;
        }
        mask = new long[64];
        for (int i = 0; i < 64; i++) {
            mask[i] = (1L<<i)-1;
        }


        from = new int[4*z];
        to = new int[4*z];
        to[1] = n;

        int[][] buffer = new int[3][n];
        buffer[0] = values.clone();

        int bi = 0;
        int lv = 0;
        for (int i = 1 ; i <= 2*z-1 ; i++) {
            int mask = (z>>lv);
            int f = lv%2;
            int t = 1-f;
            int tmpbi = 0;

            from[i*2] = bi;
            for (int k = from[i] ; k < to[i] ; k++) {
                if ((buffer[f][k] & mask) == 0) {
                    buffer[t][bi++] = buffer[f][k];
                } else {
                    which[lv].set(k);
                    buffer[2][tmpbi++] = buffer[f][k];
                }
            }
            to[i*2] = bi;
            from[i*2+1] = bi;
            to[i*2+1] = bi+tmpbi;

            System.arraycopy(buffer[2], 0, buffer[t], bi, tmpbi);
            bi += tmpbi;
            if (i+1 == Math.pow(2, lv+1)) {
                lv++;
                bi = 0;
            }
        }

        // prec rank
        int bl = which[0].bits.length;
        rank = new int[level][bl+1][2];
        for (int i = 0 ; i < level ; i++) {
            for (int j = 0; j < bl; j++) {
                int cnt = Long.bitCount(which[i].bits[j]);
                rank[i][j+1][1] += rank[i][j][1] + cnt;
                rank[i][j+1][0] += rank[i][j][0] + (blen[j] - cnt);
            }
        }
    }

    private int _rank(int idx, int fr, int to, int bit) {
        return _rank(idx, to, bit) - _rank(idx, fr, bit);
    }

    private int _rank(int idx, int pos, int bit) {
        int px = pos>>>6;
        int cnt = rank[idx][px][bit];
        int cnt2 = ((pos&63) == 0) ? 0 : Long.bitCount(which[idx].bits[px] & mask[pos&63]);
        return cnt + (bit == 0 ? (pos&63) - cnt2 : cnt2);
    }

    private int _select(int idx, int ith, int bit) {
        int fr = 0;
        int to = rank[idx].length;
        while (to - fr > 1) {
            int med = (to + fr) / 2;
            if (rank[idx][med][bit] < ith) {
                fr = med;
            } else {
                to = med;
            }
        }
        int left = ith - rank[idx][fr][bit];
        int ct = fr * 64;
        long L = which[idx].bits[fr+1];
        for (int x = 0 ; x < 64 ; x++) {
            int f = (int)((L >> x) & 1) ^ bit;
            left -= f;
            if (left == 0) {
                return ct + x;
            }
        }
        throw new RuntimeException("whoaaaa");
    }

    public int rank(int pos, int value) {
        int node = 1;
        for (int lv = 0 ; lv < level ; lv++) {
            int bit = (value >> (level-lv-1)) & 1;
            pos = _rank(lv, from[node], from[node] + pos, bit);
            if (bit == 0) {
                node = node * 2;
            } else {
                node = node * 2 + 1;
            }
        }
        return pos;
    }

    public int rangeKth(int ql, int qr, int k) {
        int node = 1;
        for (int lv = 0 ; lv < level ; lv++) {
            int r0 = _rank(lv, from[node], from[node]+ql, 0);
            int r1 = _rank(lv, from[node]+ql, from[node]+qr, 0);
            if (k < r1) {
                node = node * 2;
                ql = r0;
                qr = ql + r1;
            } else {
                node = node * 2 + 1;
                k -= r1;
                int L =qr-ql;
                ql = ql-r0;
                qr = ql+(L-r1);
            }
        }
        return node - z * 2;
    }

    public int rangeCount(int ql, int qr, int min, int max) {
        return rangeCount(ql, qr, min, max, 0, 1, 0, z*2);
    }

    public int rangeCount(int ql, int qr, int min, int max, int lv, int node, int nodeMin, int nodeMax) {
        if (min <= nodeMin && nodeMax <= max) {
            return qr-ql;
        }
        if (nodeMax <= min || max <= nodeMin) {
            return 0;
        }

        int r0 = _rank(lv, from[node], from[node]+ql, 0);
        int r1 = _rank(lv, from[node]+ql, from[node]+qr, 0);
        int nodeMed = (nodeMin + nodeMax) / 2;

        int L = qr - ql;
        int left = rangeCount(r0, r0+r1, min, max, lv+1, node*2, nodeMin, nodeMed);
        int right = rangeCount(ql-r0, ql-r0+L-r1, min, max, lv+1, node*2+1, nodeMed, nodeMax);
        return left + right;
    }
}
