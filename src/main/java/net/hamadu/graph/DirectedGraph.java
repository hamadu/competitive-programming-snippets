package net.hamadu.graph;

import java.util.Arrays;
import java.util.Iterator;

public class DirectedGraph {
    int[] head;
    int[] next;
    int[] to;
    int eidx;

    public DirectedGraph(int v, int e) {
        head = new int[v];
        next = new int[e];
        to = new int[e];
        clear();
    }

    public void clear() {
        Arrays.fill(head, -1);
        eidx = 0;
    }

    public void add(int a, int b) {
        next[eidx] = head[a];
        head[a] = eidx;
        to[eidx++] = b;
    }

    public Iterable<Integer> nexts(int v) {
        final int firstE = head[v];
        return () -> new Iterator<Integer>() {
            int cursor = firstE;

            @Override
            public boolean hasNext() {
                return cursor != -1;
            }

            @Override
            public Integer next() {
                int ret = to[cursor];
                cursor = next[cursor];
                return ret;
            }
        };
    }


    public static void main(String[] args) {
        DirectedGraph graph = new DirectedGraph(10, 100);


        graph.add(0, 1);
        graph.add(0, 2);
        graph.add(1, 3);
        graph.add(0, 4);
        graph.add(1, 5);
        graph.add(2, 4);

        for (int to : graph.nexts(0)) {
            debug(to);
        }
    }



    public static void debug(Object... o) {
        System.err.println(Arrays.deepToString(o));
    }

}