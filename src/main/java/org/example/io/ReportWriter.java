package org.example.io;

import org.example.model.Edge;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class ReportWriter {
    private final Path dir;
    public ReportWriter(Path dir){ this.dir=dir; }

    public void writeCsvHeader(Path file) throws IOException {
        Files.createDirectories(dir);
        try(BufferedWriter w = Files.newBufferedWriter(file)){
            w.write("graph_id,category,algo,n_vertices,n_edges,mst_cost,edges,op_count,time_ms\n");
        }
    }
    public void append(Path file, String graphId, String category, String algo,
                       int nV, int nE, int cost, List<Edge> mst,
                       long ops, long ms) throws IOException {
        String edgesStr = mst.stream().map(Edge::toString)
                .collect(Collectors.joining(" "));
        try(BufferedWriter w = Files.newBufferedWriter(file,
                StandardOpenOption.APPEND)){
            w.write(String.format("%s,%s,%s,%d,%d,%d,\"%s\",%d,%d%n",
                    graphId, category, algo, nV, nE, cost, edgesStr, ops, ms));
        }
    }
}
