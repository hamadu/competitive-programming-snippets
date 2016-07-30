package data_structure.heap;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SkewHeapIntTest {

    public static SkewHeapInt createHeap(int[] a) {
        SkewHeapInt heap = new SkewHeapInt();
        for (int i = 0; i < a.length ; i++) {
            heap.push(a[i]);
        }
        return heap;
    }

    @Test
    public void pushAndPopValue() {
        SkewHeapInt heap = createHeap(new int[]{3,5,2,1,4});
        for (int c = 1 ; c <= 5 ; c++) {
            assertThat(heap.peek(), is(c));
            assertThat(heap.pop(), is(c));
        }
    }

    @Test
    public void meld() {
        SkewHeapInt heap1 = createHeap(new int[]{10,20,30,40,50});
        SkewHeapInt heap2 = createHeap(new int[]{5,15,25,35,45});

        SkewHeapInt newHeap = heap1.meld(heap2);
        for (int x = 5 ; x <= 50 ; x += 5) {
            assertThat(newHeap.peek(), is(x));
            assertThat(newHeap.pop(), is(x));
        }
    }

    @Test
    public void clear() {
        SkewHeapInt heap = createHeap(new int[]{3,2,1,4,5,6});
        assertThat(heap.isEmpty(), is(false));

        heap.clear();
        assertThat(heap.isEmpty(), is(true));
    }

    @Test
    public void pushPopManyValues() {
        final int SIZE = (int)1e6;
        SkewHeapInt heap = createHeap(new int[]{});

        for (int x = 0 ; x < SIZE ; x++) {
            heap.push((int)(Math.random() * 1000000000));
        }
        assertThat(heap.size(), is(SIZE));
        for (int x = 0 ; x < SIZE ; x++) {
            heap.pop();
        }
        assertThat(heap.size(), is(0));
    }
}
