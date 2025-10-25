package org.example.algo;

import org.example.model.Edge;
import org.example.model.Graph;

import java.util.*;

public class Prim {
    public static class Result {
        public final List<Edge> mst; public final int cost; public final long ops; public final long timeMs;
        public Result(List<Edge> mst, int cost, long ops, long timeMs){ this.mst=mst; this.cost=cost; this.ops=timeMs; this.timeMs=timeMs; }
    }
    public static Result run(Graph g){
        long ops=0;
        long t0 = System.nanoTime();

        List<List<Edge>> adj = g.toAdj();
        boolean[] used = new boolean[g.n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e->e.w));
        List<Edge> mst = new ArrayList<>();
        int cost=0;

        used[0]=true;
        for(Edge e: adj.get(0)){ pq.add(e); ops++; }

        while(!pq.isEmpty() && mst.size()<g.n-1){
            Edge e = pq.poll(); ops++;
            if(used[e.v]) { ops++; continue; }
            used[e.v]=true;
            mst.add(new Edge(e.u, e.v, e.w)); cost+=e.w;
            for(Edge ne: adj.get(e.v)){
                if(!used[ne.v]) { pq.add(ne); ops++; }
            }
        }

        long t1 = System.nanoTime();
        return new Result(mst, cost, ops, (t1-t0)/1_000_000);
    }
}
