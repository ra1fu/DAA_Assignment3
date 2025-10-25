package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.algo.Kruskal;
import org.example.algo.Prim;
import org.example.io.InputReader;
import org.example.model.Edge;
import org.example.model.Graph;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class App {
    public static void main(String[] args) {
        try {
            Path input = Path.of("data/assign_3_input.json");
            Path output = Path.of("results/mst_results.json");
            Files.createDirectories(output.getParent());

            var labeledGraphs = InputReader.readAll(input);

            List<Map<String, Object>> allResults = new ArrayList<>();

            int graphCounter = 1;
            for (var lg : labeledGraphs) {
                Graph g = lg.graph();
                System.out.printf("Processing graph %s (%s)...%n", lg.id(), lg.category());

                var prim = Prim.run(g);
                List<Map<String, Object>> primEdges = new ArrayList<>();
                for (Edge e : prim.mst) {
                    primEdges.add(Map.of(
                            "from", lg.nodeNames().get(e.u),
                            "to", lg.nodeNames().get(e.v),
                            "weight", e.w
                    ));
                }

                var kruskal = Kruskal.run(g);
                List<Map<String, Object>> kruskalEdges = new ArrayList<>();
                for (Edge e : kruskal.mst) {
                    kruskalEdges.add(Map.of(
                            "from", lg.nodeNames().get(e.u),
                            "to", lg.nodeNames().get(e.v),
                            "weight", e.w
                    ));
                }

                Map<String, Object> graphResult = new LinkedHashMap<>();
                graphResult.put("graph_id", graphCounter++);
                graphResult.put("input_stats", Map.of(
                        "vertices", g.n,
                        "edges", g.edges.size()
                ));
                graphResult.put("prim", Map.of(
                        "mst_edges", primEdges,
                        "total_cost", prim.cost,
                        "operations_count", prim.ops,
                        "execution_time_ms", prim.timeMs
                ));
                graphResult.put("kruskal", Map.of(
                        "mst_edges", kruskalEdges,
                        "total_cost", kruskal.cost,
                        "operations_count", kruskal.ops,
                        "execution_time_ms", kruskal.timeMs
                ));

                allResults.add(graphResult);

                if (prim.cost != kruskal.cost) {
                    System.err.printf("WARNING: %s MST costs differ (Prim=%d, Kruskal=%d)%n",
                            lg.id(), prim.cost, kruskal.cost);
                } else {
                    System.out.printf("%s OK (MST cost=%d)%n%n", lg.id(), prim.cost);
                }
            }

            Map<String, Object> outputJson = Map.of("results", allResults);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(output.toFile())) {
                gson.toJson(outputJson, writer);
            }

            System.out.println("Done! JSON results saved to: " + output.toAbsolutePath());

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
