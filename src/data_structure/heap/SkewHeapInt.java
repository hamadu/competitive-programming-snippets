package data_structure.heap;

/**
 * Meldable heap.
 */
public class SkewHeapInt {
    static class Node {
        Node l, r;
        int value;

        public Node(int v) {
            value = v;
        }
    }

    Node root;
    int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void push(int v) {
        size++;
        root = meld(root, new Node(v));
    }

    public int peek() {
        return root.value;
    }

    public int pop() {
        size--;
        int ret = peek();
        root = meld(root.r, root.l);
        return ret;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public SkewHeapInt meld(SkewHeapInt other) {
        return meld(this, other);
    }

    public static SkewHeapInt meld(SkewHeapInt a, SkewHeapInt b) {
        Node newRoot = meld(a.root, b.root);
        SkewHeapInt newHeap = new SkewHeapInt();
        newHeap.root = newRoot;
        newHeap.size = a.size + b.size;
        return newHeap;
    }

    public static Node meld(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.value > b.value) {
            Node tmp = a;
            a = b;
            b = tmp;
        }
        a.r = meld(a.r, b);
        Node tmp = a.l;
        a.l = a.r;
        a.r = tmp;
        return a;
    }
}
