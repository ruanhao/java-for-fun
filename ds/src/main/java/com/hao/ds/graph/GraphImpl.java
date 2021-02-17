package com.hao.ds.graph;

import com.google.common.collect.Lists;
import com.hao.ds.graph.exception.NegativeWeightCycleException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.jgrapht.alg.util.UnionFind;

public class GraphImpl<V> implements Graph<V> {

    Map<V, Vertex<V>> vertices = new HashMap<>();
    Set<Edge<V>> edges = new HashSet<>();

    @Override
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;
        vertices.put(v, Vertex.of(v));
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, 0);
    }

    @Override
    public void addEdge(V from, V to, int weight) {
        Vertex<V> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = Vertex.of(from);
            vertices.put(from, fromVertex);
        }
        Vertex<V> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = Vertex.of(to);
            vertices.put(to, toVertex);
        }

        Edge<V> edge = Edge.of(fromVertex, toVertex, weight);
        edges.remove(edge);
        edges.add(edge);

        fromVertex.outEdges.remove(edge);
        fromVertex.outEdges.add(edge);

        toVertex.inEdges.remove(edge);
        toVertex.inEdges.add(edge);

    }

    @Override
    public void removeVertex(V v) {
        Vertex<V> vertex = vertices.get(v);
        if (vertex == null) return;

        vertex.outEdges.stream()
                .map(e -> e.to)
                .collect(Collectors.toList())
                .forEach(to -> removeEdge(vertex.value, to.value));

        vertex.inEdges.stream()
                .map(e -> e.from)
                .collect(Collectors.toList())
                .forEach(from -> removeEdge(from.value, vertex.value));
        vertices.remove(v);
    }

    @Override
    public void removeEdge(@NonNull V from, @NonNull V to) {
        Vertex<V> fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        Vertex<V> toVertex = vertices.get(to);
        if (toVertex == null) return;

        Edge<V> edge = Edge.of(fromVertex, toVertex);
        fromVertex.outEdges.remove(edge);
        toVertex.inEdges.remove(edge);
        edges.remove(edge);
    }

    @Override
    public int edgeSize() {
        return edges.size();
    }

    @Override
    public int verticesSize() {
        return vertices.size();
    }

    public void dfs(V v, Consumer<V> visitor) {
        Set<Vertex<V>> visited = new HashSet<>();
        Stack<Vertex<V>> stack = new Stack<>();
        Vertex<V> startNode = vertices.get(v);
        if (startNode == null) return;
        stack.push(startNode);
        visitor.accept(startNode.value);
        visited.add(startNode);
        while (!stack.isEmpty()) {
            Vertex<V> node = stack.pop();
            for (Edge<V> edge : node.outEdges) {
                Vertex<V> to = edge.to;
                if (visited.contains(to)) continue;
                stack.push(node);
                stack.push(to);
                visited.add(to);
                visitor.accept(to.value);
                break;
            }
        }
    }

    public void bfs(V v, Consumer<V> visitor) {
        Set<Vertex<V>> visited = new HashSet<>();
        Queue<Vertex<V>> queue = new LinkedList<>();
        Vertex<V> startNode = vertices.get(v);
        if (startNode == null) return;
        queue.offer(startNode);
        visited.add(startNode);
        while (!queue.isEmpty()) {
            Vertex<V> node = queue.poll();
            visitor.accept(node.value);
            node.outEdges.stream()
                    .map(e -> e.to)
                    .filter(to -> !visited.contains(to))
                    .collect(Collectors.toList())
                    .forEach(n -> {
                        queue.offer(n);
                        visited.add(n);
                    });
        }
    }

    public List<V> aov() {
        Map<Vertex<V>, Integer> inDegreeMap = new HashMap<>();
        vertices.values().forEach(v -> inDegreeMap.put(v, v.inEdges.size()));
        List<V> result = new ArrayList<>();
        Queue<Vertex<V>> queue = new LinkedList<>();
        while (result.size() != verticesSize()) {
            for (Vertex<V> v : inDegreeMap.keySet()) {
                if (inDegreeMap.get(v) == 0) {
                    queue.offer(v);
                }
            }
            if (queue.isEmpty()) throw new RuntimeException("There is a LOOP in graph");
            while (!queue.isEmpty()) {
                Vertex<V> vertex = queue.poll();
                inDegreeMap.remove(vertex);
                result.add(vertex.value);
                for (Edge<V> outEdge : vertex.outEdges) {
                    inDegreeMap.put(outEdge.to, inDegreeMap.get(outEdge.to) - 1);
                }
            }
        }
        return result;
    }

    public Set<EdgeInfo<V>> mstKruskal() {
        if (verticesSize() == 0) return null;
        HashSet<Vertex<V>> verticesSet = new HashSet<>(this.vertices.values());
        UnionFind<Vertex<V>> uf = new UnionFind<>(verticesSet);
        PriorityQueue<Edge<V>> heap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        edges.forEach(heap::offer);
        Set<EdgeInfo<V>> result = new HashSet<>();
        while (result.size() < verticesSize()) {
            Edge<V> edge = heap.poll();
            if (uf.inSameSet(edge.from, edge.to)) continue;
            result.add(edge.info());
            uf.union(edge.from, edge.to);
        }
        return result;
    }

    public Set<EdgeInfo<V>> mstPrim() {
        if (verticesSize() == 0) return null;

        Vertex<V> vertex = vertices.values().iterator().next();
        PriorityQueue<Edge<V>> heap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        Set<Vertex<V>> verticesAlongThePath = new HashSet<>();

        vertex.outEdges.forEach(heap::offer);
        verticesAlongThePath.add(vertex);
        Set<EdgeInfo<V>> result = new HashSet<>();

        while (result.size() != verticesSize() - 1) {
            Edge<V> minEdge = heap.poll();
            if (verticesAlongThePath.contains(minEdge.to)) continue;
            result.add(minEdge.info());
            verticesAlongThePath.add(minEdge.to);
            minEdge.to.outEdges.stream()
                    .filter(e -> !verticesAlongThePath.contains(e.to))
                    .forEach(heap::offer);
        }

        return result;
    }

    public Map<V, Map<V, PathInfo<V>>> shortestPathFloyd() {
        Map<V, Map<V, PathInfo<V>>> pathMap = new HashMap<>();

        // init path map
        vertices.values().forEach(v -> pathMap.put(v.value, new HashMap<>()));
        for (Edge<V> edge : edges) {
            pathMap.get(edge.from.value).put(edge.to.value,
                    new PathInfo<>(Lists.newArrayList(edge.info()), edge.weight));
        }

        for (Vertex<V> k : vertices.values()) {
            for (Vertex<V> i : vertices.values()) {
                for (Vertex<V> j : vertices.values()) {
                    if (j == k || j == i || k == i) continue;

                    PathInfo<V> pathInfoIK = pathMap.get(i.value).get(k.value);
                    if (pathInfoIK == null) continue;
                    PathInfo<V> pathInfoKJ = pathMap.get(k.value).get(j.value);
                    if (pathInfoKJ == null) continue;
                    PathInfo<V> pathInfoIJ = pathMap.get(i.value).get(j.value);
                    if (pathInfoIJ == null || pathInfoIK.weight + pathInfoKJ.weight < pathInfoIJ.weight) {
                        PathInfo<V> newPathInfoIJ = new PathInfo<>();
                        newPathInfoIJ.edges = Lists.newArrayList(pathInfoIK.getEdges());
                        newPathInfoIJ.edges.addAll(pathInfoKJ.edges);
                        newPathInfoIJ.weight = pathInfoIK.weight + pathInfoKJ.weight;
                        pathMap.get(i.value).put(j.value, newPathInfoIJ);
                    }
                }
            }
        }
        return pathMap;
    }

    public Map<V, PathInfo<V>> shortestPathBellmanFord(V source) {
        Vertex<V> sourceVertex = vertices.get(source);
        if (sourceVertex == null) return null;
        Map<Vertex<V>, PathInfo<V>> pathMap = new HashMap<>();

        sourceVertex.outEdges.forEach(e -> pathMap.put(e.to, new PathInfo<>(Lists.newArrayList(e.info()), e.weight)));

        for (int i = 0; i < verticesSize() - 1; i++) {
            for (Edge<V> edge : edges) {
                doRelaxation(edge, sourceVertex, pathMap);
            }
        }
        for (Edge<V> edge : edges) {
            if (doRelaxation(edge, sourceVertex, pathMap))
                throw new NegativeWeightCycleException();
        }
        pathMap.remove(sourceVertex);
        return pathMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().value, e -> e.getValue()));
    }

    private boolean doRelaxation(Edge<V> edge, Vertex<V> sourceVertex, Map<Vertex<V>, PathInfo<V>> pathMap) {
//        if (sourceVertex == edge.to) return false;
//        if (sourceVertex == edge.from) {
//            PathInfo<V> oldToPathInfo = pathMap.get(edge.to);
//            if (oldToPathInfo == null) {
//                pathMap.put(edge.to, new PathInfo<>(Lists.newArrayList(edge.info()), edge.weight));
//                return false;
//            }
//
//            else {
//                if (edge.weight < oldToPathInfo.weight) {
//                    oldToPathInfo.weight = edge.weight;
//                } else {
//                    return false;
//                }
//            }
//        }
        PathInfo<V> oldFromPathInfo = pathMap.get(edge.from);
        if (oldFromPathInfo == null) return false;
        PathInfo<V> oldToPathInfo = pathMap.get(edge.to);
        PathInfo<V> newToPathInfo = new PathInfo<>();
        newToPathInfo.getEdges().addAll(oldFromPathInfo.getEdges());
        newToPathInfo.getEdges().add(edge.info());
        newToPathInfo.weight = oldFromPathInfo.weight + edge.weight;

        if (oldToPathInfo == null) {
            pathMap.put(edge.to, newToPathInfo);
            return false;
        } else {
            if (oldFromPathInfo.weight + edge.weight < oldToPathInfo.weight) {
                pathMap.put(edge.to, newToPathInfo);
                return true;
            } else {
                return false;
            }
        }
    }

    public Map<V, PathInfo<V>> shortestPathDijkstra(V source) {
        Vertex<V> sourceVertex = vertices.get(source);
        if (sourceVertex == null) return null;
        Map<Vertex<V>, PathInfo<V>> pathMap = new HashMap<>();
        if (sourceVertex.outEdges.isEmpty()) return null;
        sourceVertex.outEdges.forEach(e -> pathMap.put(e.to, new PathInfo<>(Lists.newArrayList(e.info()), e.weight)));
        Map<V, PathInfo<V>> selectedPath = new HashMap<>();


        while (!pathMap.isEmpty()) {
            Map.Entry<Vertex<V>, PathInfo<V>> entry = selectShortestPath(pathMap);
            Vertex<V> selectedVertex = entry.getKey();
            selectedPath.put(selectedVertex.value, entry.getValue());
            for (Edge<V> outEdge : selectedVertex.outEdges) {
                if (outEdge.to == sourceVertex) continue;
                if (selectedPath.containsKey(outEdge.to.value)) continue;
                doRelaxation(outEdge, pathMap, selectedPath.get(selectedVertex.value));
            }

        }

        return selectedPath;
    }

    private void doRelaxation(Edge<V> outEdge,
                              Map<Vertex<V>, PathInfo<V>> pathMap,
                              PathInfo<V> selectedPath) {
        Vertex<V> to = outEdge.to;
        if (!pathMap.containsKey(to)) {
            pathMap.put(to, new PathInfo<>(Lists.newArrayList(selectedPath.getEdges()), outEdge.weight + selectedPath.weight));
            return;
        }
        PathInfo<V> oldPath = pathMap.get(to);
        if (selectedPath.weight + outEdge.weight < oldPath.weight) {
            ArrayList<EdgeInfo<V>> newEdges = Lists.newArrayList(selectedPath.getEdges());
            newEdges.add(outEdge.info());
            oldPath.setEdges(newEdges);
            oldPath.weight = selectedPath.weight + outEdge.weight;
        }
    }

    private Map.Entry<Vertex<V>, PathInfo<V>> selectShortestPath(Map<Vertex<V>, PathInfo<V>> pathMap) {
        Map.Entry<Vertex<V>, PathInfo<V>> entry = pathMap.entrySet().stream()
                .min(Comparator.comparingInt(e-> e.getValue().getWeight())).get();
        pathMap.remove(entry.getKey());
        return entry;
    }
}
