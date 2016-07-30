package data_structure.persistent;

/**
 * Persistent array.
 */
public class PersistentArray {
    int[] val;
    int[] l;
    int[] r;
    int idx;
    int[] sub;
    int offset;

    public PersistentArray(int[] a, int upto) {
        sub = new int[51];
        val = new int[upto];
        l = new int[upto];
        r = new int[upto];

        offset = Integer.highestOneBit(a.length - 1) << 1;
        for (int i = 0 ; i < a.length ; i++) {
            int oi = offset-1+i;
            val[oi] = a[i];
            l[oi] = r[oi] = -1;
        }
        for (int i = offset-2 ; i >= 0 ; i--) {
            l[i] = i*2+1;
            r[i] = i*2+2;
        }
        idx = offset<<1;
    }

    public int copy(int from) {
        val[idx] = val[from];
        l[idx] = l[from];
        r[idx] = r[from];
        return idx++;
    }

    public int set(int head, int idx, int x) {
        int si = 0;
        int oo = offset >> 1;

        sub[si++] = head;
        while (oo >= 1) {
            if ((idx & oo) >= 1) {
                head = r[head];
            } else {
                head = l[head];
            }
            sub[si++] = head;
            oo >>= 1;
        }

        int newnode = copy(sub[--si]);
        val[newnode] = x;
        while (si >= 1) {
            int base = sub[--si];
            int node = copy(base);
            if ((idx & 1) == 1) {
                r[node] = newnode;
            } else {
                l[node] = newnode;
            }
            newnode = node;
            idx >>= 1;
        }
        return newnode;
    }

    public int get(int head, int idx) {
        int oo = offset >> 1;
        while (oo >= 1) {
            if ((idx & oo) >= 1) {
                head = r[head];
            } else {
                head = l[head];
            }
            oo >>= 1;
        }
        return val[head];
    }
}
