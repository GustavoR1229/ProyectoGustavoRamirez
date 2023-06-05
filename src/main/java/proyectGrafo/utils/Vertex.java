/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un vértice en un grafo.
 */
public class Vertex {
    private Object id;
    private int x;
    private int y;
    private List<Edge> edges;

    /**
     * Crea un nuevo vértice con el ID especificado.
     *
     * @param id el ID del vértice
     */
    public Vertex(Object id) {
        this.id = id;
        this.edges = new ArrayList<>();
    }

    /**
     * Obtiene el ID del vértice.
     *
     * @return el ID del vértice
     */
    public Object getId() {
        return id;
    }

    /**
     * Obtiene la coordenada X del vértice.
     *
     * @return la coordenada X del vértice
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada Y del vértice.
     *
     * @return la coordenada Y del vértice
     */
    public int getY() {
        return y;
    }

    /**
     * Establece las coordenadas (X, Y) del vértice.
     *
     * @param x la coordenada X del vértice
     * @param y la coordenada Y del vértice
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la lista de aristas conectadas al vértice.
     *
     * @return la lista de aristas conectadas al vértice
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Agrega una arista al vértice.
     *
     * @param edge la arista a agregar
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * Elimina una arista del vértice.
     *
     * @param edge la arista a eliminar
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    /**
     * Obtiene la lista de vértices vecinos del vértice actual.
     *
     * @return la lista de vértices vecinos del vértice actual
     */
    public List<Vertex> getNeighbors() {
        List<Vertex> neighbors = new ArrayList<>();

        for (Edge edge : edges) {
            Vertex source = edge.getSource();
            Vertex destination = edge.getTarget();

            if (source.equals(this)) {
                neighbors.add(destination);
            } else if (destination.equals(this)) {
                neighbors.add(source);
            }
        }

        return neighbors;
    }
}
