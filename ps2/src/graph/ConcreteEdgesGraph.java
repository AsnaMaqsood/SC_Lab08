/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    
    @Override public boolean add(String vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(String source, String target, int weight) {
    	add(source);
        add(target);

        // If weight is 0 remove the edge
        if (weight == 0) {
            edges.removeIf(e -> e.source.equals(source) && e.target.equals(target));
            return 0;
        }

        // Update weight if edge exists
        for (Edge edge : edges) {
            if (edge.source.equals(source) && edge.target.equals(target)) {
                int prevWeight = edge.weight;
                edge.weight = weight;
                return prevWeight;
            }
        }

        // else add new edge
        edges.add(new Edge(source, target, weight));
        return 0;    }
    
    @Override public boolean remove(String vertex) {
    	 if (!vertices.remove(vertex)) return false;

         // Remove all edges with this vertex
         edges.removeIf(e -> e.source.equals(vertex) || e.target.equals(vertex));
         return true;    }
    
    @Override public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
    	 Map<String, Integer> result = new HashMap<>();
         for (Edge edge : edges) {
             if (edge.target.equals(target)) {
                 result.put(edge.source, edge.weight);
             }
         }
         return Collections.unmodifiableMap(result);    }
    
    @Override public Map<String, Integer> targets(String source) {
    	Map<String, Integer> result = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.source.equals(source)) {
                result.put(edge.target, edge.weight);
            }
        }
        return Collections.unmodifiableMap(result);
   }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\nEdges: ");
        for (Edge edge : edges) {
            sb.append("\n  ").append(edge);
        }
        return sb.toString();
    }
    private static class Edge {
        private final String source;
        private final String target;
        private int weight;

        public Edge(String source, String target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return String.format("%s -> %s (weight: %d)", source, target, weight);
        }
    }
}

