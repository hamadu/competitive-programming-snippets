package utils;

import data_structure.WaveletTree;

import java.util.Arrays;
import java.util.TreeMap;

public class CountRangeCoveringRangeOnlineWavelet {
    TreeMap<Integer,Integer> yMap;
    TreeMap<Integer,Integer> xMap;
    WaveletTree waveletTree;

    /**
     * Prepare the online query.
     *
     * @cost O(nlogn) where n = ranges.length
     *
     * @warning Destroys the given target ranges array.
     *
     * @param ranges target ranges
     */
    public CountRangeCoveringRangeOnlineWavelet(int[][] ranges) {
        this.yMap = compressY(ranges);
        this.xMap = compressUniqueX(ranges);
        int n = ranges.length;
        int[] ys = new int[n];
        for (int i = 0; i < n ; i++) {
            ys[i] = ranges[i][1];
        }
        this.waveletTree = new WaveletTree(ys);
    }

    private static TreeMap<Integer,Integer> compressY(int[][] ranges) {
        Arrays.sort(ranges, (a, b) -> a[1] - b[1]);

        int n = ranges.length;
        TreeMap<Integer,Integer> yMap = new TreeMap<>();
        int yidx = 0;
        for (int i = 0 ; i < n ; ) {
            int base = ranges[i][1];
            yMap.put(base, yidx);

            int j = i;
            while (j < n && ranges[j][1] == base) {
                ranges[j][1] = yidx;
                j++;
            }
            yidx++;
            i = j;
        }
        yMap.put(Integer.MAX_VALUE, yidx);
        return yMap;
    }

    private static TreeMap<Integer,Integer> compressUniqueX(int[][] ranges) {
        Arrays.sort(ranges, (a, b) -> a[0] - b[0]);

        int n = ranges.length;
        TreeMap<Integer,Integer> xMap = new TreeMap<>();
        for (int i = 0 ; i < n ; ) {
            int base = ranges[i][0];
            xMap.put(base, i);
            int j = i;
            while (j < n && ranges[j][0] == base) {
                j++;
            }
            i = j;
        }
        xMap.put(Integer.MAX_VALUE, n);
        return xMap;
    }

    /**
     * Counts how many ranges in [l,r).
     *
     * @cost O(logn) where n = ranges.length
     *
     * @param l start of the query range(inclusive)
     * @param r end of the query range(exclusive)
     * @return number of target ranges covered by [l,r)
     */
    public int query(int l, int r) {
        int xFrom = xMap.ceilingEntry(l).getValue();
        int xTo = xMap.higherEntry(r).getValue();
        int yFrom = yMap.ceilingEntry(l).getValue();
        int yTo = yMap.higherEntry(r).getValue();

        return waveletTree.rangeCount(xFrom, xTo, yFrom, yTo);
    }
}
