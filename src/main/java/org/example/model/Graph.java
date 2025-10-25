package org.example.model;

import java.util.*;

public class Graph {
    public final int n;
    public final List<Integer> vertices;
    public final List<Edge> edges;
    public Graph(int n, List<Integer> vertices, List<Edge> edges){
        this.n=n; this.vertices=vertices; this.edges=edges;
    }
    public List<List<Edge>> toAdj(){
        List<List<Edge>> g = new ArrayList<>(n);
        for(int i=0;i<n;i++) g.add(new ArrayList<>());
        for(Edge e: edges){
            g.get(e.u).add(e);
            g.get(e.v).add(new Edge(e.v, e.u, e.w));
        }
        return g;
    }
}
