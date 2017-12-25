package net.hamadu.utils.io;

import java.util.List;

/**
 * TODO: WIP
 */
public class GraphBuilder {
    /**
     * Builds undirected graph from input stream.
     *
     * @param in input stream
     * @param n number of vertices
     * @param m number of edges
     * @return
     */
    public static int[][] buildGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    /**
     * Builds undirected graph from given edges.
     *
     * @param n number of vertices
     * @param edges array of edge({from,to})
     * @return
     */
    public static int[][] buildGraph(int n, int[][] edges) {
        int m = edges.length;
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            deg[a]++;
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    /**
     * Builds undirected graph from given edges.
     *
     * @param n number of vertices
     * @param edges list of edge({from,to})
     * @return
     */
    public static int[][] buildGraph(int n, List<int[]> edges) {
        int m = edges.size();
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            deg[a]++;
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges.get(i)[0];
            int b = edges.get(i)[1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    /**
     * Builds undirected graph from given edges.
     *
     * @param l edge(from)
     * @param r edge(to)
     * @return
     */
    public static int[][] buildGraph(int[] l, int[] r) {
        int n = 0;
        for (int i = 0 ; i < l.length ; i++) {
            n = Math.max(n, l[i]);
            n = Math.max(n, r[i]);
        }
        int m = l.length;
        int[][] edges = new int[m][];
        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            deg[l[i]]++;
            deg[r[i]]++;
            edges[i] = new int[]{l[i], r[i]};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[a][--deg[a]] = b;
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    /**
     * Builds rooted tree(or forest) from vertices' parent array.
     *
     * @param par parent of i-th vertex. if -1 given, it means he is the root of a tree(or forest).
     * @return
     */
    public static int[][] buildRootedTreeFromPar(int[] par) {
        int n = par.length;
        int[][] edges = new int[n-1][2];
        int ei = 0;
        for (int i = 0; i < n ; i++) {
            if (par[i] != -1) {
                edges[ei][0] = i;
                edges[ei][1] = par[i];
                ei++;
            }
        }

        int[][] graph = new int[n][];
        int[] deg = new int[n];
        for (int i = 0 ; i < ei ; i++) {
            int b = edges[i][1];
            deg[b]++;
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]];
        }
        for (int i = 0 ; i < ei ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            graph[b][--deg[b]] = a;
        }
        return graph;
    }

    /**
     * Builds undirected weighted graph from input stream.
     *
     * @param in input stream
     * @param n number of vertices
     * @param m number of edges
     * @return
     */
    public static int[][][] buildWeightedGraph(InputReader in, int n, int m) {
        int[][] edges = new int[m][];
        int[][][] graph = new int[n][][];
        int[] deg = new int[n];
        for (int i = 0 ; i < m ; i++) {
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            int w = in.nextInt();
            deg[a]++;
            deg[b]++;
            edges[i] = new int[]{a, b, w};
        }
        for (int i = 0 ; i < n ; i++) {
            graph[i] = new int[deg[i]][2];
        }
        for (int i = 0 ; i < m ; i++) {
            int a = edges[i][0];
            int b = edges[i][1];
            int w = edges[i][2];
            graph[a][--deg[a]][0] = b;
            graph[b][--deg[b]][0] = a;
            graph[a][deg[a]][1] = w;
            graph[b][deg[b]][1] = w;
        }
        return graph;
    }
}
