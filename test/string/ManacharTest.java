package string;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ManacharTest {
    @Test
    public void doManachar() {
        final String[] ex = new String[]{"abcdef", "ghijk", "eeeeeee"};
        for (String e : ex) {
            int[][] result = Manachar.manachar(e.toCharArray());
            assertEquals(result.length, 2);
            assertEquals(result[0].length, e.length());
            assertEquals(result[1].length, e.length()-1);
        }
    }

    @Test
    public void doManacharSub() {
        final int[] result = Manachar.manacharSub("abcdcbabc".toCharArray());
        //                               a  b  c  d  c  b  a  b  c
        final int[] expected = new int[]{1, 1, 1, 4, 1, 1, 3, 1, 1};

        assertArrayEquals(result, expected);
    }
}
