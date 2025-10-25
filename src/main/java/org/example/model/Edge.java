package org.example.model;

public class Edge implements Comparable<Edge> {
    public final int u, v;
    public final int w;
    public Edge(int u, int v, int w) { this.u=u; this.v=v; this.w=w; }
    @Override public int compareTo(Edge o){ return Integer.compare(this.w, o.w); }
    @Override public String toString(){ return "(" + u + "-" + v + ":" + w + ")"; }
}
