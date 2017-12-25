package net.hamadu.numeric;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MatrixTest {
    @Test
    public void testE() {
        assertThat(Matrix.e(3), is(new long[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));
    }

    @Test
    public void testMul() {
        long[][] a = {{1, 2}, {3, 4}};
        long[][] b = {{3, 4}, {1, 2}};
        long[][] ab = {{5, 8}, {13, 20}};

        assertThat(Matrix.mul(a, b), is(ab));
    }

    @Test
    public void testPow() {
        long[][] a = {{1, 2}, {3, 4}};
        long[][] a3 = {{37, 54}, {81, 118}};
        long[][] a10 = {{4783807, 6972050}, {10458075, 15241882}};
        assertThat(Matrix.pow(a, 3), is(a3));
        assertThat(Matrix.pow(a, 10), is(a10));
    }

    @Test
    public void testInv() {
        long[][] a = {{1, 2}, {3, 4}};

        // -2   1
        // 3/2  -1/2
        long[][] inva = {{Matrix.MOD-2, 1}, {3 * Matrix.inv(2) % Matrix.MOD, Matrix.MOD - Matrix.inv(2)}};

        assertThat(Matrix.inv(a), is(inva));
    }

    @Test
    public void testSwapRow() {
        long[][] a = {{1, 2}, {3, 4}};
        long[][] a01 = {{3, 4}, {1, 2}};
        Matrix.swapRow(a, 0, 1);
        assertThat(a, is(a01));
    }

    @Test
    public void testMulRow() {
        long[][] a = {{1, 2}, {3, 4}};
        long[][] a10 = {{10, 20}, {3, 4}};
        Matrix.mulRow(a, 0, 10);
        assertThat(a, is(a10));
    }

    @Test
    public void testAddRow() {
        long[][] a = {{1, 2}, {3, 4}};
        long[][] a012 = {{Matrix.MOD-5, Matrix.MOD-6}, {3, 4}};
        Matrix.addRow(a, 0, 1, -2);
        assertThat(a, is(a012));
    }
}
