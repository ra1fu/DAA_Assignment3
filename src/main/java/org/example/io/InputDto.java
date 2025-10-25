package org.example.io;

import java.util.List;

public class InputDto {
    public List<OneGraph> graphs;
    public static class OneGraph{
        public String id;
        public String category;
        public int n;
        public List<Integer> vertices;
        public List<EdgeDto> edges;
    }
    public static class EdgeDto{ public int u,v,w; }
}
