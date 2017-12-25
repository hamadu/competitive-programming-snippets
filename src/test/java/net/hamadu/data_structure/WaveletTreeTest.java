package net.hamadu.data_structure;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WaveletTreeTest {
    private Map<Integer,Integer> countDegree(int[] a) {
        Map<Integer,Integer> deg = new HashMap<>();
        for (int ai : a) {
            deg.put(ai, deg.getOrDefault(ai, 0)+1);
        }
        return deg;
    }

    private void examineRank(int[] a) {
        WaveletTree wt = new WaveletTree(a);
        for (int pos = 0 ; pos <= a.length ; pos++) {
            int[] subArray = Arrays.copyOf(a, pos);
            Map<Integer, Integer> deg = countDegree(subArray);
            for (int x = 0 ; ; x++) {
                if (!deg.containsKey(x)) {
                    deg.put(x, 0);
                    break;
                }
            }

            for (int key : deg.keySet()) {
                int count = deg.get(key);
                assertThat(wt.rank(pos, key), is(count));
            }
        }
    }


    private void examineRangeCount(int[] a) {
        WaveletTree wt = new WaveletTree(a);
        for (int fr = 0 ; fr < a.length ; fr++) {
            for (int to = fr+1 ; to <= a.length ; to++) {
                int[] subArray = Arrays.copyOfRange(a, fr, to);
                Map<Integer, Integer> deg = countDegree(subArray);
                for (int x = 0 ; ; x++) {
                    if (!deg.containsKey(x)) {
                        deg.put(x, 0);
                        break;
                    }
                }


                for (int fromValue : deg.keySet()) {
                    for (int toValue : deg.keySet()) {
                        int expectedCount = 0;
                        for (int k : deg.keySet()) {
                            if (fromValue <= k && k < toValue) {
                                expectedCount += deg.get(k);
                            }
                        }
                        assertThat(wt.rangeCount(fr, to, fromValue, toValue), is(expectedCount));
                    }
                }
            }
        }
    }

    private void examineRangeKth(int[] a) {
        WaveletTree wt = new WaveletTree(a);
        for (int fr = 0 ; fr < a.length ; fr++) {
            for (int to = fr+1 ; to <= a.length ; to++) {
                int[] subArray = Arrays.copyOfRange(a, fr, to);
                Arrays.sort(subArray);
                for (int k = 0 ; k < subArray.length ; k++) {
                    assertThat(wt.rangeKth(fr, to, k), is(subArray[k]));
                }
            }
        }
    }


    @Test
    public void rank() {
        examineRank(new int[]{3,0,4,6,7,1,7,3,4,2,0,0,1});
    }

    @Test
    public void largeRank() {
        final int[] NUMBERS = {10, 1000, 100000};
        int[] a = new int[64*10];
        for (int i = 0; i < a.length ; i++) {
            int co = ((i % 11) == 0 ? 1 : 0) + ((i % 19) == 0 ? 1 : 0);
            a[i] = NUMBERS[co];
        }

        examineRank(a);
    }

    @Test
    public void rangeKth() {
        examineRangeKth(new int[]{3,0,4,6,7,1,7,3,4,2,0,0,1});
    }

    @Test
    public void largeRangeKth() {
        int[] a = new int[250];
        for (int i = 0; i < a.length; i++) {
            a[i] = i % 2 == 0 ? 250 * 250 - i * i : i * i;
        }
        examineRangeKth(a);
    }

    @Test
    public void rangeCount() {
        examineRangeCount(new int[]{3,0,4,6,7,1,7,3,4,2,0,0,1});
    }

    @Test
    public void largeRangeCount() {
        final int[] NUMBERS = {1, 10, 100, 1000};
        int[] a = new int[32*10];
        for (int i = 0; i < a.length ; i++) {
            int co = ((i % 7) == 0 ? 1 : 0) + ((i % 11) == 0 ? 1 : 0) + ((i % 19) == 0 ? 1 : 0);
            a[i] = NUMBERS[co];
        }
        examineRangeCount(a);
    }
}
