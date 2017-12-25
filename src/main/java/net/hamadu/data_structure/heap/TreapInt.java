package net.hamadu.data_structure.heap;

import net.hamadu.utils.rand.XorShift;

/**
 * Treap(tree+heap)
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
     * Grabs k-th node from given node.
     *
     * @param a
     * @param k
     * @return
     */
    public static Node grab(Node a, int k) {
        if (a == null) {
            return null;
        }
        update(a);
        int l = count(a.l);
        if (k == l) {
            return a;
        } else if (k < l) {
            return grab(a.l, k);
        } else {
            return grab(a.r, k-l-1);
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

    /**
     * Returns size of the tree.
     *
     * @return size of the tree
     */
    public int size() {
        return count(root);
    }

    /**
     * Adds new node value=v to the end of tree.
     *
     * @param v
     */
    public void push(int v) {
        root = insert(root, INF, new Node(v));
    }

    /**
     * Adds new node value=v to tree at k-th position.
     *
     * @param v
     */
    public void push(int v, int k) {
        root = insert(root, k, new Node(v));
    }

    /**
     * Removes k-th node from tree.
     *
     * @param k
     */
    public void remove(int k) {
        root = erase(root, k);
    }

    /**
     * Get k-th node value
     *
     * @param k
     * @return
     */
    public int get(int k) {
        Node n = grab(root, k);
        if (n == null) {
            return -INF;
        }
        return n.value;
    }
}
