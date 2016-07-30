package data_structure.heap;

import utils.rand.XorShift;

/**
 * Created by hama_du on 2016/07/28.
 */
public class TreapInt {
    private static final int INF = 100000001;

    static XorShift rand = new XorShift();

    static class Node {
        Node l, r;
        int value;
        int count;
        int heapValue;

        public Node(int v) {
            value = v;
            heapValue = rand.next();
            count = 1;
        }
    }

    Node root;



    public static Node update(Node a) {
        if (a == null) {
            return null;
        }
        a.count = 1 + count(a.l) + count(a.r);
        return a;
    }

    public static int count(Node a) {
        return (a == null) ? 0 : a.count;
    }


    /**
     * Finds node position that value = v.
     * If there is no such node, returns minus value.
     *
     * @param v
     * @return found node.
     */
    public static int detect(Node x, int v) {
        if (x == null) {
            return -INF;
        }
        if (x.value == v) {
            return count(x.l);
        } else if (v < x.value) {
            return detect(x.l, v);
        } else {
            return count(x.l) + 1 + detect(x.r, v);
        }
    }


    /**
     * Merges two trees.
     *
     * @param a
     * @param b
     * @return merged root node
     */
    public static Node merge(Node a, Node b) {
        if (a == null || b == null) {
            return a == null ? b : a;
        }
        if (a.heapValue < b.heapValue) {
            a.r = merge(a.r, b);
            return update(a);
        } else {
            b.l = merge(a, b.l);
            return update(b);
        }
    }

    /**
     * Splits tree at point k. [0, k), [k, n)
     *
     * @param a
     * @param k
     * @return
     */
    public static Node[] split(Node a, int k) {
        if (a == null) {
            return new Node[]{null, null};
        }
        if (k <= count(a.l)) {
            Node[] s = split(a.l, k);
            a.l = s[1];
            return new Node[]{s[0], update(a)};
        } else {
            Node[] s = split(a.r, k - count(a.l) - 1);
            a.r = s[0];
            return new Node[]{update(a), s[1]};
        }
    }

    /**
     * Inserts node v at point k.
     *
     * @param a
     * @param k
     * @return new tree
     */
    public static Node insert(Node a, int k, Node v) {
        Node[] x = split(a, k);
        return merge(x[0], merge(v, x[1]));
    }

    /**
     * Erases node at point k.
     *
     * @param a
     * @param k
     * @return new tree
     */
    public static Node erase(Node a, int k) {
        Node[] al = split(a, k);
        Node[] ar = split(al[1], 1);
        return merge(al[0], ar[1]);
    }

    public int size() {
        return count(root);
    }

    /**
     * Adds new node to tree.
     *
     * @param v
     */
    public void push(int v) {
        root = insert(root, INF, new Node(v));
    }

    /**
     * Finds node position that value = v.
     * If there is no such node, returns minus value.
     *
     * @param v
     * @return
     */
    public int detect(int v) {
        return detect(root, v);
    }

    /**
     * Removes a node value = v from tree.
     *
     * @param v
     */
    public void remove(int v) {
        int pos = detect(v);
        if (pos >= 0) {
            root = erase(root, pos);
        }
    }
}
