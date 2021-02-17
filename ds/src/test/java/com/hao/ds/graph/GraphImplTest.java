package com.hao.ds.graph;

import com.hao.ds.graph.exception.NegativeWeightCycleException;
import java.util.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphImplTest {

/*
           6
    -> V0 ---> V4
   /   ^       ^
9 /    | 2     | 1
 /     |       |
V1 --> V2 ---> V3
   3       5
*/
    private void initGraph(Graph<String> graph) {
        graph.addEdge("V1", "V0", 9);
        graph.addEdge("V1", "V2", 3);
        graph.addEdge("V2", "V0", 2);
        graph.addEdge("V2", "V3", 5);
        graph.addEdge("V3", "V4", 1);
        graph.addEdge("V0", "V4", 6);
    }

    private void initAov(Graph<String> graph) {
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");
        graph.addEdge("B", "E");
        graph.addEdge("C", "E");
        graph.addEdge("D", "E");
        graph.addEdge("E", "F");
    }

    private void initUndirectedGraph(Graph<String> graph) {
        graph.addEdge("V1", "V0", 1);
        graph.addEdge("V0", "V1", 1);

        graph.addEdge("V1", "V2", 5);
        graph.addEdge("V2", "V1", 5);

        graph.addEdge("V2", "V0", 2);
        graph.addEdge("V0", "V2", 2);

        graph.addEdge("V2", "V3", 8);
        graph.addEdge("V3", "V2", 8);

        graph.addEdge("V3", "V4", 20);
        graph.addEdge("V4", "V3", 20);

        graph.addEdge("V0", "V4", 10);
        graph.addEdge("V4", "V0", 10);
    }

    private void initUndirectedGraphForMst(Graph<Integer> graph) {
        addBidirectionEdge(graph, 0, 2, 2);
        addBidirectionEdge(graph, 0, 4, 7);

        addBidirectionEdge(graph, 1, 2, 3);
        addBidirectionEdge(graph, 1, 5, 1);
        addBidirectionEdge(graph, 1, 6, 7);

        addBidirectionEdge(graph, 2, 5, 10);
        addBidirectionEdge(graph, 2, 4, 4);
        addBidirectionEdge(graph, 2, 6, 6);

        addBidirectionEdge(graph, 3, 7, 9);

        addBidirectionEdge(graph, 4, 6, 8);

        addBidirectionEdge(graph, 5, 6, 4);
        addBidirectionEdge(graph, 5, 7, 5);
    }

    private void initUndirectedGraphForDijkstra(Graph<String> graph) {
        addBidirectionEdge(graph, "A", "E", 100);
        addBidirectionEdge(graph, "A", "D", 30);
        addBidirectionEdge(graph, "A", "B", 10);

        addBidirectionEdge(graph, "B", "C", 50);
        addBidirectionEdge(graph, "D", "E", 60);
        addBidirectionEdge(graph, "C", "D", 20);
        addBidirectionEdge(graph, "C", "E", 10);
    }

    private void initUndirectedGraphForBellmanFord(Graph<String> graph) {
        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "E", 8);

        graph.addEdge("B", "E", -5);
        graph.addEdge("B", "C", 8);

        graph.addEdge("E", "D", 7);
        graph.addEdge("D", "C", 2);
        graph.addEdge("D", "F", 6);
        graph.addEdge("E", "F", 3);

    }

    private void initUndirectedGraphForBellmanFordNegativeWeightLoop(Graph<String> graph) {
        graph.addEdge("A", "B", 10); // loop
        graph.addEdge("E", "A", 8); // loop
        graph.addEdge("B", "E", -5); // loop

        graph.addEdge("B", "C", 8);

        graph.addEdge("E", "D", 7);
        graph.addEdge("D", "C", 2);
        graph.addEdge("D", "F", 6);
        graph.addEdge("F", "E", -30);

    }

    private <V> void addBidirectionEdge(Graph<V> graph, V from, V to, int weight) {
        graph.addEdge(from, to, weight);
        graph.addEdge(to, from, weight);
    }

    @Test
    public void aov() {
        GraphImpl<String> graph = new GraphImpl<>();
        initAov(graph);
        List<String> result = graph.aov();
        assertEquals("[A, B, C, D, E, F]", result.toString());
    }

    @Test
    public void dfs() {
        GraphImpl<String> graph = new GraphImpl<>();
        initUndirectedGraph(graph);
        StringBuilder sb = new StringBuilder();
        graph.dfs("V0", v -> sb.append(v + " "));
        // System.err.println("sb.toString(): " + sb.toString());
        assertEquals("V0 V1 V2 V3 V4", sb.toString().trim());

        System.err.println("graph.mst(): " + graph.mstPrim());
    }

    @Test
    public void bfs() {
        GraphImpl<String> graph = new GraphImpl<>();
        initUndirectedGraph(graph);
        StringBuilder sb = new StringBuilder();
        graph.bfs("V0", v -> sb.append(v + " "));
        assertEquals("V0 V1 V2 V4 V3", sb.toString().trim());
    }

    @Test
    public void functionTest() {
        GraphImpl<String> graph = new GraphImpl<>();
        initGraph(graph);

//        graph.addEdge("V1", "V0", 9);
//        Graph.Vertex<String> v0 = graph.vertices.get("V0");
//        System.err.println("v0: " + v0);

        assertEquals(5, graph.verticesSize());
        assertEquals(6, graph.edgeSize());
        assertEquals(1, graph.vertices.get("V0").outEdges.size());
        assertEquals(2, graph.vertices.get("V0").inEdges.size());
        assertEquals(2, graph.vertices.get("V1").outEdges.size());
        assertEquals(0, graph.vertices.get("V1").inEdges.size());
        assertEquals(2, graph.vertices.get("V2").outEdges.size());
        assertEquals(1, graph.vertices.get("V2").inEdges.size());
        assertEquals(1, graph.vertices.get("V3").inEdges.size());
        assertEquals(1, graph.vertices.get("V3").inEdges.size());
        assertEquals(2, graph.vertices.get("V4").inEdges.size());
        assertEquals(0, graph.vertices.get("V4").outEdges.size());

//        Graph.Vertex<String> v0 = graph.vertices.get("V0");
//        System.err.println("v0: " + v0);
//        graph.removeEdge("V0", "V4");
//        v0 = graph.vertices.get("V0");
//        System.err.println("v0: " + v0);

        graph.removeEdge("V0", "V4");
        assertEquals(0, graph.vertices.get("V0").outEdges.size());
        assertEquals(1, graph.vertices.get("V4").inEdges.size());
        assertEquals(5, graph.edgeSize());

        graph.removeVertex("V0");
        assertEquals(4, graph.verticesSize());
        assertEquals(3, graph.edgeSize());
        assertEquals(1, graph.vertices.get("V1").outEdges.size());
        assertEquals(1, graph.vertices.get("V2").outEdges.size());

    }

    @Test
    public void testOrder() {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.naturalOrder());
        minHeap.offer(3);
        minHeap.offer(4);
        minHeap.offer(1);
        assertEquals(1, minHeap.poll().intValue());
    }

    @Test
    public void testEdgeInfo() {
        Set<Graph.EdgeInfo<String>> infos = new HashSet<>();
        infos.add(new Graph.EdgeInfo<>("A", "B", 0));
        assertEquals(1, infos.size());
        infos.add(new Graph.EdgeInfo<>("A", "B", 0));
        assertEquals(1, infos.size());
        infos.add(new Graph.EdgeInfo<>("B", "A", 0));
        assertEquals(1, infos.size());
        infos.add(new Graph.EdgeInfo<>("B", "C", 0));
        assertEquals(2, infos.size());
    }

    @Test
    public void testMstKruskal() {
        GraphImpl<Integer> graph = new GraphImpl<>();
        initUndirectedGraphForMst(graph);
        Set<Graph.EdgeInfo<Integer>> mst = graph.mstPrim();
        mst.forEach(System.err::println);
        assertEquals(7, mst.size());
        assertTrue(mst.contains(new Graph.EdgeInfo<>(0, 2)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(2, 1)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(5, 7)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(2, 4)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(7, 3)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(5, 6)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(1, 5)));
    }

    @Test
    public void testMstPrim() {
        GraphImpl<Integer> graph = new GraphImpl<>();
        initUndirectedGraphForMst(graph);
        Set<Graph.EdgeInfo<Integer>> mst = graph.mstPrim();
        mst.forEach(System.err::println);

        assertEquals(7, mst.size());
        assertTrue(mst.contains(new Graph.EdgeInfo<>(0, 2)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(2, 1)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(5, 7)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(2, 4)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(7, 3)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(5, 6)));
        assertTrue(mst.contains(new Graph.EdgeInfo<>(1, 5)));

    }

    @Test(expected = NegativeWeightCycleException.class)
    public void bellmanFordNegativeWeightLoop() {
        GraphImpl<String> graph = new GraphImpl<>();
        initUndirectedGraphForBellmanFordNegativeWeightLoop(graph);
        Map<String, Graph.PathInfo<String>> pathMap = graph.shortestPathBellmanFord("A");
        pathMap.forEach((k, v) -> {
            System.out.println(String.format("%s: %s", k, v));
        });
    }

    @Test
    public void floyd() {
        GraphImpl<String> graph = new GraphImpl<>();
        initUndirectedGraphForBellmanFord(graph);
        Map<String, Map<String, Graph.PathInfo<String>>> allPathMap = graph.shortestPathFloyd();
        Map<String, Graph.PathInfo<String>> aPathMap = allPathMap.get("A");
        allPathMap.forEach((source, pathMap) -> {
            System.out.println("======= " + source + " ========");
            pathMap.forEach((k, v) -> System.out.println(String.format("%s: %s", k, v)));
        });

        assertEquals(5, aPathMap.size());
        assertEquals(10, aPathMap.get("B").weight);
        assertEquals(14, aPathMap.get("C").weight);
        assertEquals(12, aPathMap.get("D").weight);
        assertEquals(5, aPathMap.get("E").weight);
        assertEquals(8, aPathMap.get("F").weight);

        List<Graph.EdgeInfo<String>> bEdges = aPathMap.get("B").getEdges();
        List<Graph.EdgeInfo<String>> cEdges = aPathMap.get("C").getEdges();
        List<Graph.EdgeInfo<String>> dEdges = aPathMap.get("D").getEdges();
        List<Graph.EdgeInfo<String>> eEdges = aPathMap.get("E").getEdges();
        List<Graph.EdgeInfo<String>> fEdges = aPathMap.get("F").getEdges();

        assertEquals(1, bEdges.size());
        assertEquals("A", bEdges.get(0).from);
        assertEquals("B", bEdges.get(0).to);

        assertEquals(4, cEdges.size());
        assertEquals("A", cEdges.get(0).from);
        assertEquals("B", cEdges.get(0).to);
        assertEquals("B", cEdges.get(1).from);
        assertEquals("E", cEdges.get(1).to);
        assertEquals("E", cEdges.get(2).from);
        assertEquals("D", cEdges.get(2).to);
        assertEquals("D", cEdges.get(3).from);
        assertEquals("C", cEdges.get(3).to);

        assertEquals(3, dEdges.size());
        assertEquals("A", dEdges.get(0).from);
        assertEquals("B", dEdges.get(0).to);
        assertEquals("B", dEdges.get(1).from);
        assertEquals("E", dEdges.get(1).to);
        assertEquals("E", dEdges.get(2).from);
        assertEquals("D", dEdges.get(2).to);

        assertEquals(2, eEdges.size());
        assertEquals("A", eEdges.get(0).from);
        assertEquals("B", eEdges.get(0).to);
        assertEquals("B", eEdges.get(1).from);
        assertEquals("E", eEdges.get(1).to);

        assertEquals(3, fEdges.size());
        assertEquals("A", fEdges.get(0).from);
        assertEquals("B", fEdges.get(0).to);
        assertEquals("B", fEdges.get(1).from);
        assertEquals("E", fEdges.get(1).to);
        assertEquals("E", fEdges.get(2).from);
        assertEquals("F", fEdges.get(2).to);
    }

    @Test
    public void bellmanFord() {
        GraphImpl<String> graph = new GraphImpl<>();
        initUndirectedGraphForBellmanFord(graph);
        Map<String, Graph.PathInfo<String>> pathMap = graph.shortestPathBellmanFord("A");
        pathMap.forEach((k, v) -> {
            System.out.println(String.format("%s: %s", k, v));
        });
        assertEquals(5, pathMap.size());
        assertEquals(10, pathMap.get("B").weight);
        assertEquals(14, pathMap.get("C").weight);
        assertEquals(12, pathMap.get("D").weight);
        assertEquals(5, pathMap.get("E").weight);
        assertEquals(8, pathMap.get("F").weight);

        List<Graph.EdgeInfo<String>> bEdges = pathMap.get("B").getEdges();
        List<Graph.EdgeInfo<String>> cEdges = pathMap.get("C").getEdges();
        List<Graph.EdgeInfo<String>> dEdges = pathMap.get("D").getEdges();
        List<Graph.EdgeInfo<String>> eEdges = pathMap.get("E").getEdges();
        List<Graph.EdgeInfo<String>> fEdges = pathMap.get("F").getEdges();

        assertEquals(1, bEdges.size());
        assertEquals("A", bEdges.get(0).from);
        assertEquals("B", bEdges.get(0).to);

        assertEquals(4, cEdges.size());
        assertEquals("A", cEdges.get(0).from);
        assertEquals("B", cEdges.get(0).to);
        assertEquals("B", cEdges.get(1).from);
        assertEquals("E", cEdges.get(1).to);
        assertEquals("E", cEdges.get(2).from);
        assertEquals("D", cEdges.get(2).to);
        assertEquals("D", cEdges.get(3).from);
        assertEquals("C", cEdges.get(3).to);

        assertEquals(3, dEdges.size());
        assertEquals("A", dEdges.get(0).from);
        assertEquals("B", dEdges.get(0).to);
        assertEquals("B", dEdges.get(1).from);
        assertEquals("E", dEdges.get(1).to);
        assertEquals("E", dEdges.get(2).from);
        assertEquals("D", dEdges.get(2).to);

        assertEquals(2, eEdges.size());
        assertEquals("A", eEdges.get(0).from);
        assertEquals("B", eEdges.get(0).to);
        assertEquals("B", eEdges.get(1).from);
        assertEquals("E", eEdges.get(1).to);

        assertEquals(3, fEdges.size());
        assertEquals("A", fEdges.get(0).from);
        assertEquals("B", fEdges.get(0).to);
        assertEquals("B", fEdges.get(1).from);
        assertEquals("E", fEdges.get(1).to);
        assertEquals("E", fEdges.get(2).from);
        assertEquals("F", fEdges.get(2).to);
    }

    @Test
    public void dijkstra() {
        GraphImpl<String> graph = new GraphImpl<>();
        initUndirectedGraphForDijkstra(graph);
        Map<String, Graph.PathInfo<String>> pathMap = graph.shortestPathDijkstra("A");
        pathMap.forEach((k, v) -> {
            System.out.println(String.format("%s: %s", k, v));
        });
        assertEquals(4, pathMap.size());
        assertEquals(10, pathMap.get("B").weight);
        assertEquals(50, pathMap.get("C").weight);
        assertEquals(30, pathMap.get("D").weight);
        assertEquals(60, pathMap.get("E").weight);

        List<Graph.EdgeInfo<String>> bEdges = pathMap.get("B").getEdges();
        List<Graph.EdgeInfo<String>> cEdges = pathMap.get("C").getEdges();
        List<Graph.EdgeInfo<String>> dEdges = pathMap.get("D").getEdges();
        List<Graph.EdgeInfo<String>> eEdges = pathMap.get("E").getEdges();

        assertEquals(1, bEdges.size());
        assertEquals("A", bEdges.get(0).from);
        assertEquals("B", bEdges.get(0).to);

        assertEquals(2, cEdges.size());
        assertEquals("A", cEdges.get(0).from);
        assertEquals("D", cEdges.get(0).to);
        assertEquals("D", cEdges.get(1).from);
        assertEquals("C", cEdges.get(1).to);

        assertEquals(1, dEdges.size());
        assertEquals("A", dEdges.get(0).from);
        assertEquals("D", dEdges.get(0).to);

        assertEquals(3, eEdges.size());
        assertEquals("A", eEdges.get(0).from);
        assertEquals("D", eEdges.get(0).to);
        assertEquals("D", eEdges.get(1).from);
        assertEquals("C", eEdges.get(1).to);
        assertEquals("C", eEdges.get(2).from);
        assertEquals("E", eEdges.get(2).to);
    }


}