package com.hao.ds.graph;

import java.util.*;
import lombok.*;

public interface Graph<V> {

    void addVertex(V v);
    void addEdge(V from, V to);
    void addEdge(V from, V to, int weight);

    void removeVertex(V v);
    void removeEdge(V from, V to);

    int edgeSize();
    int verticesSize();


    @ToString
    @Getter
    @EqualsAndHashCode(onlyExplicitlyIncluded = true)
    class Vertex<V> {
        @EqualsAndHashCode.Include
        V value;
        Set<Edge<V>> inEdges = new HashSet<>();
        Set<Edge<V>> outEdges = new HashSet<>();

        public static <V> Vertex<V> of(V v) {
            Vertex<V> vNode = new Vertex<>();
            vNode.value = v;
            return vNode;
        }
    }


    @EqualsAndHashCode(exclude = {"weight"})
    class Edge<V> {
        Vertex<V> from;
        Vertex<V> to;
        @Getter
        int weight = 0;

        public static <V> Edge<V> of(Vertex<V> from, Vertex<V> to) {
            return of(from, to, 0);
        }

        public static <V> Edge<V> of(Vertex<V> from, Vertex<V> to, int weight) {
            Edge<V> edge = new Edge<>();
            edge.from = from;
            edge.to = to;
            edge.weight = weight;
            return edge;
        }

        EdgeInfo<V> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public String toString() {
            return String.format("%s -> %s (%s)", from.value, to.value, weight);
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class EdgeInfo<V> {
        V from;
        V to ;
        int weight = 0;

        public EdgeInfo(V from, V to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            int fromHash = from == null ? 0 : from.hashCode();
            int toHash = to == null ? 0 : to.hashCode();
            return fromHash + toHash;
        }

        @Override
        public boolean equals(Object obj) {
            EdgeInfo<V> another = (EdgeInfo) obj;
            return (Objects.equals(from, another.from) && Objects.equals(to, another.to)) ||
                    (Objects.equals(from, another.to) && Objects.equals(to, another.from));
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    class PathInfo<V> {
        List<EdgeInfo<V>> edges = new ArrayList<>();
        int weight;
    }
}
