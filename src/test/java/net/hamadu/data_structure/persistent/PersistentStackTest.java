package net.hamadu.data_structure.persistent;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PersistentStackTest {
    @Test
    public void push() {
        PersistentStack stack = new PersistentStack(20);
        int pos1 = stack.add(1);
        int pos2 = stack.add(4);
        int pos3 = stack.add(5);
        assertThat(stack.get(pos1), is(1));
        assertThat(stack.get(pos2), is(4));
        assertThat(stack.get(pos3), is(5));
    }

    @Test
    public void undo() {
        PersistentStack stack = new PersistentStack(20);
        int pos1 = stack.add(1);
        int pos2 = stack.add(4);
        int pos3 = stack.add(5);
        int pos4 = stack.pop();
        int pos5 = stack.pop();
        int pos6 = stack.undo(pos2);
        int pos7 = stack.add(7);
        int pos8 = stack.undo(pos5);
        int pos9 = stack.add(10);
        int pos10 = stack.add(12);
        int pos11 = stack.pop();
        int pos12 = stack.pop();

        assertThat(stack.get(pos1), is(1));
        assertThat(stack.get(pos2), is(4));
        assertThat(stack.get(pos3), is(5));
        assertThat(stack.get(pos4), is(4));
        assertThat(stack.get(pos5), is(1));
        assertThat(stack.get(pos6), is(4));
        assertThat(stack.get(pos7), is(7));
        assertThat(stack.get(pos8), is(1));
        assertThat(stack.get(pos9), is(10));
        assertThat(stack.get(pos10), is(12));
        assertThat(stack.get(pos11), is(10));
        assertThat(stack.get(pos12), is(1));
    }
}
