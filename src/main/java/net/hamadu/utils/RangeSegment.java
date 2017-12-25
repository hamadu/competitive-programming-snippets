package net.hamadu.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by hama_du on 2016/09/03.
 */
public class RangeSegment {
    // map[first_point_of_the_segment, some_value]
    TreeMap<Integer,Integer> tree;

    public RangeSegment(int max, int filledValue) {
        tree = new TreeMap<>();
        tree.put(-1, -1);
        tree.put(0, filledValue);
        tree.put(max, -1);
    }

    public int get(int pos) {
        return tree.floorEntry(pos).getValue();
    }

    public void replace(int l, int r, int value) {
        int lowKey = tree.floorKey(l);
        int highKey = tree.higherKey(r);
        List<Integer> removingKey = new ArrayList<>();
        for (int k : tree.subMap(lowKey, true, highKey, false).keySet()) {
            if (l <= k) {
                removingKey.add(k);
            }
        }

        int appendPos = -1;
        int appendValue = -1;
        if (r+1 < highKey) {
            int lastKey = tree.floorKey(r);
            int lastValue = tree.get(lastKey);
            appendPos = r+1;
            appendValue = lastValue;
        }
        for (int idx : removingKey) {
            tree.remove(idx);
        }
        tree.put(l, value);
        if (appendPos >= 0) {
            tree.put(appendPos, appendValue);
        }
    }

    public int size() {
        return tree.size() - 2;
    }
}
