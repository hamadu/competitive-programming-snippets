package utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConvexHullTechTest {
    @Test
    public void testMax() {
        ConvexHullTechMax tech = new ConvexHullTechMax(100);
        tech.addLine(2, -16);   // y = 2x - 16
        tech.addLine(1, -12);   // y = x - 12
        tech.addLine(-2, 3);    // y = -2x + 3
        tech.addLine(0, -3);    // y = -3
        tech.addLine(0, -6);    // y = -6
        tech.addLine(-1, 3);    // y = -x + 3
        tech.addLine(-5, 5);    // y = -5x + 5

        long[] ans = new long[]{
                 5,  2,  1,  0, -1,
                -2, -3, -2,  0,  2,
                 4,  6,  8, 10, 12,
                14, 16, 18, 20, 22
        };
        for (int x = 0 ; x < 20 ; x++) {
            long[] line = tech.queryMax(x);
            long max = tech.computesMax(x);
            assertThat(line[0] * x + line[1], is(ans[x]));
            assertThat(max, is(ans[x]));
        }
    }

    @Test
    public void testMin() {
        ConvexHullTechMin tech = new ConvexHullTechMin(100);
        tech.addLine(4, -6);    // y = 4x - 6
        tech.addLine(4, -10);   // y = 4x - 10
        tech.addLine(3, -15);   // y = 3x - 15
        tech.addLine(2, -12);   // y = 2x - 12
        tech.addLine(1, -8);    // y =  x -  8
        tech.addLine(0, 3);     // y =       3
        tech.addLine(0, 4);     // y =       4
        tech.addLine(-1, 68);   // y =-3x + 57
        tech.addLine(-2, 30);   // y =-2x + 30
        tech.addLine(-3, 57);   // y =-3x + 57


        long[] ans = new long[]{
                -15, -12, -9, -6, -4,
                -3,  -2, -1,  0,  1,
                2,   3,  3,  3,  2,
                0,  -2, -4, -6, -8
        };
        for (int x = 0 ; x < ans.length ; x++) {
            long[] line = tech.queryMin(x);
            long min = tech.computesMin(x);
            assertThat(line[0] * x + line[1], is(ans[x]));
            assertThat(min, is(ans[x]));
        }
    }
}
