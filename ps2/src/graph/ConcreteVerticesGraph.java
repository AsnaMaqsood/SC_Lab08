package graph;

import java.util.*;

/**
 * An implementation of Graph using a vertices-based representation.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Add a new vertex to the graph
    @Override
    public boolean add(String vertex) {
        if (getVertex(vertex) != null) {
            return false; // Vertex already exists
        }
        vertices.add(new Vertex(vertex));
        return true;
    }

    // Set an edge from source to target with weight
    @Override
    public int set(String source, String target, int weight) {
        add(source); // Ensure source vertex exists
        add(target); // Ensure target vertex exists

        Vertex srcVertex = getVertex(source);
        Vertex tgtVertex = getVertex(target);

        // Check if the edge already exists
        for (Edge edge : srcVertex.edges) {
            if (edge.target.equals(target)) {
                int oldWeight = edge.weight;
                edge.weight = weight; // Update the edge weight
                return oldWeight;    // Return the old weight
            }
        }

        // If no edge exists, create a new one
        srcVertex.edges.add(new Edge(target, weight));
        return 0; // No previous edge
    }

    // Remove a vertex and all associated edges
    @Override
    public boolean remove(String vertex) {
        Vertex vertexToRemove = getVertex(vertex);
        if (vertexToRemove == null) return false;

        // Remove the vertex from the vertices list
        vertices.remove(vertexToRemove);

        // Remove any edges pointing to this vertex
        for (Vertex v : vertices) {
            v.edges.removeIf(edge -> edge.target.equals(vertex));
        }

        return true;
    }

    // Return a set of all vertex labels in the graph
    @Override
    public Set<String> vertices() {
        Set<String> vertexLabels = new HashSet<>();
        for (Vertex vertex : vertices) {
            vertexLabels.add(vertex.label);
        }
        return Collections.unmodifiableSet(vertexLabels);
    }

    // Return a map of all vertices with edges pointing to the specified target vertex
    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Vertex vertex : vertices) {
            for (Edge edge : vertex.edges) {
                if (edge.target.equals(target)) {
                    result.put(vertex.label, edge.weight);
                }
            }
        }
        return Collections.unmodifiableMap(result);
    }

    // Return a map of all target vertices that the source vertex has edges to
    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> result = new HashMap<>();
        Vertex srcVertex = getVertex(source);
        if (srcVertex != null) {
            for (Edge edge : srcVertex.edges) {
                result.put(edge.target, edge.weight);
            }
        }
        return Collections.unmodifiableMap(result);
    }

    // Return a string representation of the graph
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices()).append("\nEdges: ");
        for (Vertex vertex : vertices) {
            for (Edge edge : vertex.edges) {
                sb.append("\n  ").append(vertex.label).append(" -> ").append(edge.target)
                        .append(" (weight: ").append(edge.weight).append(")");
            }
        }
        return sb.toString();
    }

    // Helper method to find a vertex by its label
    private Vertex getVertex(String label) {
        for (Vertex vertex : vertices) {
            if (vertex.label.equals(label)) {
                return vertex;
            }
        }
        return null;
    }

    // Inner class representing a vertex in the graph
    private static class Vertex {
        private final String label;
        private final List<Edge> edges;

        public Vertex(String label) {
            this.label = label;
            this.edges = new ArrayList<>();
        }
    }

    // Inner class representing an edge between two vertices
    private static class Edge {
        private final String target;
        private int weight;

        public Edge(String target, int weight) {
            this.target = target;
            this.weight = weight;
        }

        // Getter for target vertex
        public String getTarget() {
            return target;
        }

        // Getter for weight of the edge
        public int getWeight() {
            return weight;
        }

        // Setter for weight of the edge
        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
