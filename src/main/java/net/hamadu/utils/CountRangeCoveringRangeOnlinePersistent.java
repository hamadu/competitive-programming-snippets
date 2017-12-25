package net.hamadu.utils;

import net.hamadu.data_structure.persistent.PersistentSegmentTree;

import java.util.Arrays;
import java.util.TreeMap;

public class CountRangeCoveringRangeOnlinePersistent {
    TreeMap<Integer,Integer> endMap;
    PersistentSegmentTree segmentTree;

    /**
     * Prepare the online query.
     *
     * @cost O(nlogn) where n = ranges.length
     *
     * @warning Destroys the given target ranges array.
     *
     * @param ranges target ranges
     */
    public CountRangeCoveringRangeOnlinePersistent(int[][] ranges) {
        Arrays.sort(ranges, (a, b) -> a[1] - b[1]);

        int n = ranges.length;
        int maxRange = ranges[n-1][1];

        segmentTree = new PersistentSegmentTree(maxRange, n);

        int root = 1;
        endMap = new TreeMap<>();
        endMap.put(-1, 0);
        for (int i = 0; i < n ;) {
            int j = i;
            int end = ranges[i][1];
            while (j < n && ranges[j][1] == end) {
                root = segmentTree.add(ranges[j][0], 1, root);
                j++;
            }
            i = j;
            endMap.put(end, root);
        }
    }

    /**
     * Counts how many ranges in [l,r).
     *
     * @COST O(logn) where n = ranges.length
     *
     * @param l start of the query range(inclusive)
     * @param r end of the query range(exclusive)
     * @return number of target ranges covered by [l,r)
     */
    public int query(int l, int r) {
        int targetRoot = endMap.floorEntry(r).getValue();
        return segmentTree.sum(l, r, targetRoot);
    }
}
