package org.example.algo;

import org.example.model.*;
import java.util.*;

public class Kruskal {
    public static class Result {
        public final List<Edge> mst; public final int cost; public final long ops; public final long timeMs;
        public Result(List<Edge> mst, int cost, long ops, long timeMs){ this.mst=mst; this.cost=cost; this.ops=ops; this.timeMs=timeMs; }
    }
    public static Result run(Graph g){
        long ops=0;
        long t0 = System.nanoTime();

        List<Edge> edges = new ArrayList<>(g.edges);
        Collections.sort(edges);
        ops += Math.max(0, edges.size() * (int)(Math.log(Math.max(1, edges.size()))/Math.log(2)));

        DisjointSet dsu = new DisjointSet(g.n);
        List<Edge> mst = new ArrayList<>();
        int cost=0;

        for(Edge e: edges){
            ops++;
            if(dsu.union(e.u, e.v)){
                mst.add(e);
                cost += e.w;
                if(mst.size()==g.n-1) break;
            }
        }
        ops += dsu.makes + dsu.finds + dsu.unions;

        long t1 = System.nanoTime();
        return new Result(mst, cost, ops, (t1-t0)/1_000_000);
    }
}