package org.example.model;

public class DisjointSet {
    private final int[] p, r;
    public long makes, finds, unions;
    public DisjointSet(int n){
        p=new int[n]; r=new int[n];
        for(int i=0;i<n;i++){ p[i]=i; r[i]=0; makes++; }
    }
    public int find(int x){
        finds++;
        if(p[x]==x) return x;
        p[x]=find(p[x]);
        return p[x];
    }
    public boolean union(int a,int b){
        unions++;
        a=find(a); b=find(b);
        if(a==b) return false;
        if(r[a]<r[b]) p[a]=b;
        else if(r[a]>r[b]) p[b]=a;
        else { p[b]=a; r[a]++; }
        return true;
    }
}
