package net.hamadu.data_structure.heap;

import java.util.Arrays;

public class MinHeap {
    private static final long INF = Long.MAX_VALUE;

    int pos;
    long[] data;

    public MinHeap(int capacity) {
        data = new long[capacity];
        pos = 1;
        Arrays.fill(data, INF);

    }

    public void push(long x) {
        int p = pos;
        data[pos++] = x;
        while (p != 1) {
            int pp = p>>>1;
            if (data[pp] <= data[p]) {
                break;
            }
            long tmp = data[pp];
            data[pp] = data[p];
            data[p] = tmp;
            p = pp;
        }
    }

    public long peek() {
        return data[1];
    }

    public long poll() {
        if (size() == 0) {
            throw new RuntimeException("queue is empty");
        }
        pos--;
        long ret = data[1];
        data[1] = data[pos];
        data[pos] = INF;

        for (int p = 1 ; p * 2 < pos ; ){
            int l = p<<1;
            int r = l+1;
            int to = data[l] < data[r] ? l : r;
            if (data[to] < data[p]) {
                long tmp = data[to];
                data[to] = data[p];
                data[p] = tmp;
                p = to;
            } else {
                break;
            }
        }
        return ret;
    }

    public int size() {
        return pos-1;
    }
}