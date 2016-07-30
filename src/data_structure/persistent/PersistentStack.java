package data_structure.persistent;

/**
 * Persistent stack.
 */
public class PersistentStack {
    private int[] val;
    private int[] par;
    private int[] entries;
    private int pi, ei, currentPtr;

    public PersistentStack(int n) {
        val = new int[n];
        par = new int[n];
        entries = new int[n];
        par[pi++] = -1;
        entries[ei++] = 0;
        currentPtr = 0;
    }

    public int add(int x)  {
        val[pi] = x;
        par[pi++] = currentPtr;
        currentPtr = entries[ei++] = pi-1;
        return currentPtr;
    }

    public int undo(int to) {
        entries[ei++] = currentPtr = entries[to];
        return currentPtr;
    }

    public int pop() {
        if(par[currentPtr] == -1) {
            return currentPtr;
        }
        entries[ei++] = currentPtr = par[currentPtr];
        return currentPtr;
    }

    public int get(int pos) {
        return val[pos];
    }
}
