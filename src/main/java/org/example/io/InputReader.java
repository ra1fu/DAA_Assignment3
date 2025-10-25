package org.example.io;

import com.google.gson.*;
import org.example.model.Edge;
import org.example.model.Graph;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.*;

public class InputReader {

    public static List<LabeledGraph> readAll(Path jsonPath) throws Exception {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(jsonPath.toFile())) {
            JsonObject root = gson.fromJson(reader, JsonObject.class);
            JsonArray graphsJson = root.getAsJsonArray("graphs");
            List<LabeledGraph> result = new ArrayList<>();

            for (JsonElement graphElem : graphsJson) {
                JsonObject gObj = graphElem.getAsJsonObject();

                String id = gObj.has("id") ? gObj.get("id").getAsString() : "unknown";
                String category = gObj.has("category") ? gObj.get("category").getAsString() : "unspecified";

                if (!gObj.has("nodes")) {
                    throw new IllegalArgumentException("Graph " + id + " missing 'nodes' field.");
                }

                JsonArray nodesArray = gObj.getAsJsonArray("nodes");
                List<String> nodeNames = new ArrayList<>();
                for (JsonElement n : nodesArray) {
                    nodeNames.add(n.getAsString());
                }

                Map<String, Integer> indexMap = new HashMap<>();
                for (int i = 0; i < nodeNames.size(); i++) {
                    indexMap.put(nodeNames.get(i), i);
                }

                if (!gObj.has("edges")) {
                    throw new IllegalArgumentException("Graph " + id + " missing 'edges' field.");
                }

                JsonArray edgesArray = gObj.getAsJsonArray("edges");
                List<Edge> edges = new ArrayList<>();

                for (JsonElement eElem : edgesArray) {
                    JsonObject eObj = eElem.getAsJsonObject();

                    if (!eObj.has("from") || !eObj.has("to") || !eObj.has("weight")) {
                        throw new IllegalArgumentException("Edge in graph " + id + " missing required fields.");
                    }

                    String from = eObj.get("from").getAsString();
                    String to = eObj.get("to").getAsString();
                    int weight = eObj.get("weight").getAsInt();

                    if (!indexMap.containsKey(from) || !indexMap.containsKey(to)) {
                        throw new IllegalArgumentException("Edge in graph " + id + " refers to unknown node(s): "
                                + from + " or " + to);
                    }

                    edges.add(new Edge(indexMap.get(from), indexMap.get(to), weight));
                }

                Graph graph = new Graph(nodeNames.size(), new ArrayList<>(indexMap.values()), edges);

                result.add(new LabeledGraph(id, category, graph, nodeNames));
            }

            return result;
        }
    }

    public record LabeledGraph(String id, String category, Graph graph, List<String> nodeNames) {}
}
