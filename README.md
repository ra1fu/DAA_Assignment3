# Analysis and Comparison of Prim’s and Kruskal’s Algorithms

## Input Data Overview

The experiment was conducted on **six datasets** representing different network sizes:

- **2 Small graphs:** 5 and 6 vertices  
- **2 Medium graphs:** 12 and 15 vertices  
- **2 Large graphs:** 24 and 30 vertices  

Each graph models a possible **city transportation network**,  
where **vertices** represent districts and **edges** represent potential road connections with associated construction costs.

---

## Algorithm Performance

Both **Prim’s** and **Kruskal’s** algorithms successfully produced **identical Minimum Spanning Tree (MST)** total costs for all datasets, confirming the correctness of both implementations.  
However, their **efficiency and performance** vary depending on the graph structure:

### 🔹 Sparse Graphs
When the number of edges (*E*) is relatively small compared to the number of vertices (*V*):
- **Kruskal’s Algorithm** generally performs faster.  
- This is due to:
  - Efficient **sorting** of a small edge set.
  - Optimized **Disjoint Set Union (DSU)** operations for cycle detection.

### 🔹 Dense Graphs
When *E ≈ V²*:
- **Prim’s Algorithm** shows better performance.  
- Especially effective when implemented with:
  - A **binary heap** or **Fibonacci heap** for priority queue operations.  
- Its adjacency list representation minimizes redundant edge checks and improves key updates.

---

## Complexity and Data Representation

| Algorithm | Time Complexity | Dominant Operation | Best Data Representation |
|------------|----------------|--------------------|---------------------------|
| **Kruskal’s** | `O(E log E)` | Edge sorting | Edge list |
| **Prim’s** | `O(E log V)` | Priority queue updates | Adjacency list / matrix |

---

## Conclusion

Both algorithms yield **the same MST total cost**,  
but differ in efficiency based on graph density and representation:

- **Kruskal’s** is preferable for **sparse networks** or when the input is stored as an **edge list**.  
- **Prim’s** is preferable for **dense networks** or when implemented with an **adjacency list** and efficient heap structure.  

In practical applications, the choice between them depends on:
- The **density of the graph**,
- The **data structure used**, and
- The required **balance between simplicity and performance**.

---

### Summary Table Example

| Graph ID | Vertices | Edges | Algorithm | MST Cost | Operations | Time (ms) |
|:----------:|:---------:|:------:|:-----------:|:----------:|:------------:|:-----------:|
| 1 | 5 | 7 | Prim | 16 | 1 | 1 |
| 1 | 5 | 7 | Kruskal | 16 | 42 | 0 |
| 2 | 6 | 8 | Prim | 15 | 0 | 0 |
| 2 | 6 | 8 | Kruskal | 15 | 58 | 0 |
| 3 | 12 | 14 | Prim | 45 | 0 | 0 |
| 3 | 12 | 14 | Kruskal | 45 | 119 | 0 |
| 4 | 13 | 14 | Prim | 38 | 0 | 0 |
| 4 | 13 | 14 | Kruskal | 38 | 117 | 0 |
| 5 | 24 | 34 | Prim | 79 | 0 | 0 |
| 5 | 24 | 34 | Kruskal | 79 | 326 | 0 |
| 6 | 26 | 37 | Prim | 66 | 0 | 0 |
| 6 | 26 | 37 | Kruskal | 66 | 359 | 0 |

---

*Prepared as part of Assignment 3: Optimization of a City Transportation Network (Minimum Spanning Tree)*
