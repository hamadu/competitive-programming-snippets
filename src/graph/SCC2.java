package graph;

import java.util.*;

/**
 * Decompose graphs into component-bridge based tree.
 */
public class SCC2 {
    public static int[] decompose(int[][] graph) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        int[] ord = new int[n];
        int[] low = new int[n];

        int[] ids = new int[n];
        int[] inds = new int[n];
        int[] parct = new int[n];
        int pos = 0;
        for (int i = 0 ; i < n ; i++) {
            if (visited[i]) {
                continue;
            }
            ids[i] = i;
            inds[0] = 0;
            int sp = 1;
            while (sp > 0) {
                int cur = ids[sp-1];
                if (inds[sp-1] == 0) {
                    visited[cur] = true;
                    ord[cur] = low[cur] = pos++;
                    parct[sp-1] = 0;
                }
                if (inds[sp-1] == graph[cur].length) {
                    if(sp-2 >= 0) {
                        low[ids[sp-2]] = Math.min(low[ids[sp-2]], low[cur]);
                    }
                    sp--;
                    continue;
                }
                int next = graph[cur][inds[sp-1]];
                if (!visited[next]){
                    ids[sp] = next;
                    inds[sp] = 0;
                    inds[sp-1]++;
                    sp++;
                    continue;
                } else if (sp-2 >= 0 && (next != ids[sp-2] || ++parct[sp-1] >= 2)){
                    low[cur] = Math.min(low[cur], ord[next]);
                }
                inds[sp-1]++;
            }
        }

        int[] clus = new int[n];
        Arrays.fill(clus, -1);
        int[] q = new int[n];
        int cnum = 0;
        for (int i = 0 ; i < n ; i++){
            if (clus[i] == -1){
                int p = 0;
                q[p++] = i;
                clus[i] = cnum++;
                for(int r = 0;r < p;r++){
                    int cur = q[r];
                    for(int next : graph[cur]){
                        if(clus[next] == -1){
                            clus[next] = ord[cur] < low[next] ? cnum++ : clus[cur];
                            q[p++] = next;
                        }
                    }
                }
            }
        }
        return clus;
    }

    public static int[][] grouping(int[][] graph, int[] groups) {
        int n = graph.length;
        int gn = 0;

        for (int g : groups) {
            gn = Math.max(gn, g);
        }

        Set<Long> done = new HashSet<>();
        List<Integer> edges = new ArrayList<>();
        int[] deg = new int[gn+1];
        for (int i = 0 ; i < n ; i++) {
            for (int j : graph[i]) {
                if (groups[i] != groups[j] && i < j) {
                    int gfr = Math.min(groups[i], groups[j]);
                    int gto = Math.max(groups[i], groups[j]);
                    long eid = (((long) gfr)<<30)+gto;
                    if (done.contains(eid)) {
                        continue;
                    }
                    done.add(eid);

                    edges.add(gfr);
                    edges.add(gto);
                    deg[gfr]++;
                    deg[gto]++;
                }
            }
        }

        int[][] groupedGraph = new int[gn+1][];
        for (int i = 0 ; i <= gn ; i++) {
            groupedGraph[i] = new int[deg[i]];
        }
        int en = edges.size();
        for (int i = 0 ; i < en ; i += 2) {
            int u = edges.get(i);
            int v = edges.get(i+1);
            groupedGraph[u][--deg[u]] = v;
            groupedGraph[v][--deg[v]] = u;
        }
        return groupedGraph;
    }
}
