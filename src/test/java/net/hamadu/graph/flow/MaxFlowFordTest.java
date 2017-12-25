package net.hamadu.graph.flow;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MaxFlowFordTest {
    @Test
    public void init() {
        MaxFlowFord maxFlow = new MaxFlowFord();
        maxFlow.init(100);
        assertThat(maxFlow.graph.length, is(100));
        assertThat(maxFlow.maxFlow(0, 99), is(0));
    }

    @Test
    public void addEdge() {
        MaxFlowFord maxFlow = new MaxFlowFord();
        maxFlow.init(10);

        maxFlow.addEdge(0, 3, 100);
        maxFlow.addEdge(3, 6, 50);
        maxFlow.addEdge(6, 9, 70);
        assertThat(maxFlow.maxFlow(0, 9), is(50));
    }
}
