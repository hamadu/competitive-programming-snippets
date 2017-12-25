package net.hamadu.utils;

import java.util.TreeSet;

class MeldableSegment {
    TreeSet<Segment> tree;

    public MeldableSegment() {
        tree = new TreeSet<>();
    }

    /**
     * add and merge segment s.
     * segments are inclusive-inclusive: [fr,to]
     */
    public void meld(Segment s) {
        Segment left = tree.lower(s);
        if (left != null) {
            if (left.to + 1 < s.fr) {
                // ok.
            } else {
                // merge
                s.fr = left.fr;
                s.to = Math.max(s.to, left.to);
                tree.remove(left);
            }
        }
        while (true) {
            Segment right = tree.higher(s);
            if (right != null) {
                if (s.to + 1 < right.fr) {
                    break;
                } else {
                    // merge.
                    tree.remove(right);
                    if (s.to <= right.to) {
                        s.to = right.to;
                        break;
                    }
                }
            } else {
                break;
            }
        }
        tree.add(s);
    }

    public int size() {
        return tree.size();
    }

    static class Segment implements Comparable<Segment> {
        static int __seq = 0;
        int seq;
        int fr;
        int to;
        public Segment(int a, int b) {
            __seq++;
            fr = Math.min(a, b);
            to = Math.max(a, b);
        }
        @Override
        public int compareTo(Segment o) {
            if (fr == o.fr) {
                return seq - o.seq;
            }
            return fr - o.fr;
        }

        @Override
        public String toString() {
            return fr + "/" + to;
        }
    }
}