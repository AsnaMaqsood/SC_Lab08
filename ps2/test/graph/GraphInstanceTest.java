/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
 // Test2: Add vertex test
    @Test
    public void testAddVertex() {
        Graph<String> chargraph = emptyInstance();

        // Adding a vertex 'A'
        assertTrue("Vertex 'A' should have been added", chargraph.add("A"));
        assertEquals("Graph should have had one vertex", Set.of("A"), chargraph.vertices());

        // Adding a vertex "B"
        assertTrue("Vertex 'B' should have be added", chargraph.add("B"));
        assertEquals("Graph should have had two vertices", Set.of("A", "B"), chargraph.vertices());
    }

    // Test3: Adding duplicate vertex to check that it is not added again
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> chargraph = emptyInstance();

        // Adding a vertex 'A'
        assertTrue("Vertex 'A' should be added", chargraph.add("A"));
        
        // Adding vertex 'A' again
        assertFalse("Vertex 'A' should not be added again", chargraph.add("A"));
        assertEquals("There should only be one vertex", Set.of("A"), chargraph.vertices());
    }

    // Test4: Adding edges with weights
    @Test
    public void testSetEdge() {
        Graph<String> charGraph = emptyInstance();

        // Adding an edge between 'A' and 'B' with weight 10
        int prevWeightOfEdge = charGraph.set("A", "B", 10);
        assertEquals("Previous weight should be 0 bcz no edge exists", 0, prevWeightOfEdge);

        // Checking existence of edges
        Map<String, Integer> targetVertices = charGraph.targets("A");
        assertEquals("A should have an edge with B of weight 10", Map.of("B", 10), targetVertices);

        // Updating weight of edge and checking previous weight
        prevWeightOfEdge = charGraph.set("A", "B", 20);
        assertEquals("Previous weight of edge should be 10", 10, prevWeightOfEdge);

        // Checking if weight was updated
        targetVertices = charGraph.targets("A");
        assertEquals("A should have edge with B of weight 20", Map.of("B", 20), targetVertices);
    }

    // Test5: Removing edges
    @Test
    public void testRemoveVertex() {
        Graph<String> charGraph = emptyInstance();

        // Adding edges
        charGraph.add("A");
        charGraph.add("B");
        charGraph.set("A", "B", 20);

        // Removing a single vertex
        assertTrue("Vertex A should be removed", charGraph.remove("A"));
        assertEquals("Graph should only one vertex B", Set.of("B"), charGraph.vertices());

        // Checking if edge is removed too
        Map<String, Integer> sourceVertices= charGraph.sources("B");
        assertTrue("No source vertex should exist", sourceVertices.isEmpty());

        // Trying to remove non-existent vertex
        assertFalse("Removing A again should return false", charGraph.remove("A"));
    }

    // Test6: Checking sources and targets of edges
    @Test
    public void testSourcesAndTargets() {
        Graph<String> charGraph = emptyInstance();

        // Adding edges
        charGraph.add("A");
        charGraph.add("B");
        charGraph.set("A", "B", 10);

        // Testing sources for vertex B
        Map<String, Integer> sourceVertices = charGraph.sources("B");
        assertEquals("There should be only one source for B: A with weight 10", 
                Map.of("A", 10), sourceVertices);

        // Test targets for vertex A
        Map<String, Integer> targetVertices = charGraph.targets("A");
        assertEquals("There should be one target for A: B with weight 10", 
                Map.of("B", 10), targetVertices);

        // No sources for vertex A
        sourceVertices = charGraph.sources("A");
        assertTrue("There should be no sources for A", sourceVertices.isEmpty());

        // No targets for vertex B
        targetVertices = charGraph.targets("B");
        assertTrue("There should be no targets for B", targetVertices.isEmpty());
    }
}
