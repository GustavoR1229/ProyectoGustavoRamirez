/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Graph {

    private Map<Object, Vertex> vertices;
    private List<Edge> edges;

    public Graph() {
        vertices = new HashMap<>();
        edges = new ArrayList<>();
    }

    public void addVertex(Object vertex) {
        vertices.put(vertex, new Vertex(vertex));
    }

    public void addEdge(Object source, Object target) {
        Vertex sourceVertex = vertices.get(source);
        Vertex targetVertex = vertices.get(target);
        Edge newEdge =new Edge(sourceVertex, targetVertex);
        edges.add(newEdge);
        sourceVertex.addEdge(newEdge);
        targetVertex.addEdge(newEdge);


    }

    public int getCountVertex() {
        return vertices.size();
    }

    public Collection<Vertex> getVertexs() {
        return vertices.values();
    }

    public Vertex getVertex(Object vertex) {
        return vertices.get(vertex);
    }

    public  void assignCoordinates2(Graph graph, int maxX, int maxY) {
        Map<Object, Point> coordinates = new HashMap<>();
        Random random = new Random();

        int numVertices = graph.getCountVertex();
        double angleIncrement = 2 * Math.PI / numVertices;
        double angle = 0;

        for (Vertex vertex : graph.getVertexs()) {
            int x = (int) (maxX / 2 + (maxX / 2) * Math.cos(angle));
            int y = (int) (maxY / 2 + (maxY / 2) * Math.sin(angle));

            coordinates.put(vertex, new Point(x, y));

            angle += angleIncrement;
        }

        // Guardar las coordenadas asignadas en los nodos del grafo
        for (Vertex vertex : graph.getVertexs()) {
            Vertex v = graph.getVertex(vertex.getId());
            Point point = coordinates.get(vertex);
            v.setCoordinates(point.x, point.y);
        }
    }

    public void assignCoordinates(Graph graph, int maxX,int maxY) {
        // Obtener todas las islas del grafo
        List<List<Vertex>> islands = findIslands(graph);
        Collections.shuffle(islands);
        // Calcular el ancho y alto de cada celda de la matriz
        int cellWidth = 150;
        int cellHeight = 100;

        // Inicializar las coordenadas
        Map<Object, Point> coordinates = new HashMap<>();

        // Asignar las coordenadas a cada nodo
        int currentY = 50; // Posición Y actual

        for (List<Vertex> island : islands) {
            int numVertices = island.size();
            int numRows = (int) Math.sqrt(numVertices);  // Número de filas (cercano a la raíz cuadrada)
            int numCols = (int) Math.ceil((double) numVertices / numRows);  // Número de columnas

            int currentX = (maxX - numCols * cellWidth) / 2;  // Posición X actual
            Collections.shuffle(island);
            for (int i = 0; i < numVertices; i++) {
                Vertex vertex = island.get(i);
                int row = i / numCols;  // Fila actual
                int col = i % numCols;  // Columna actual

                int x = currentX + col * cellWidth;
                int y = currentY + row * cellHeight;

                coordinates.put(vertex, new Point(x, y));
            }

            currentY += numRows * cellHeight + 100; // Avanzar a la siguiente matriz con un margen de 100px
        }

        // Guardar las coordenadas asignadas en los nodos del grafo

        for (Vertex vertex : graph.getVertexs()) {
            Vertex v = graph.getVertex(vertex.getId());
            Point point = coordinates.get(vertex);
            v.setCoordinates(point.x, point.y);
        }
    }

    public List<List<Vertex>> findIslands(Graph graph) {
        List<List<Vertex>> islands = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();

        for (Vertex vertex : graph.getVertexs()) {
            if (!visited.contains(vertex)) {
                List<Vertex> island = new ArrayList<>();
                exploreIsland(vertex, island, visited);
                islands.add(island);
            }
        }

        return islands;
    }

    private void exploreIsland(Vertex vertex, List<Vertex> island, Set<Vertex> visited) {
        visited.add(vertex);
        island.add(vertex);

        for (Vertex neighbor : vertex.getNeighbors()) {
            if (!visited.contains(neighbor)) {
                exploreIsland(neighbor, island, visited);
            }
        }
    }



}


