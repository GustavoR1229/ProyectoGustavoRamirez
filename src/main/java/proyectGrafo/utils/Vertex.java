/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un v�rtice en un grafo.
 */
public class Vertex {
    private Object id;
    private int x;
    private int y;
    private List<Edge> edges;

    /**
     * Crea un nuevo v�rtice con el ID especificado.
     *
     * @param id el ID del v�rtice
     */
    public Vertex(Object id) {
        this.id = id;
        this.edges = new ArrayList<>();
    }

    /**
     * Obtiene el ID del v�rtice.
     *
     * @return el ID del v�rtice
     */
    public Object getId() {
        return id;
    }

    /**
     * Obtiene la coordenada X del v�rtice.
     *
     * @return la coordenada X del v�rtice
     */
    public int getX() {
        return x;
    }

    /**
     * Obtiene la coordenada Y del v�rtice.
     *
     * @return la coordenada Y del v�rtice
     */
    public int getY() {
        return y;
    }

    /**
     * Establece las coordenadas (X, Y) del v�rtice.
     *
     * @param x la coordenada X del v�rtice
     * @param y la coordenada Y del v�rtice
     */
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene la lista de aristas conectadas al v�rtice.
     *
     * @return la lista de aristas conectadas al v�rtice
     */
    public List<Edge> getEdges() {
        return edges;
    }

    /**
     * Agrega una arista al v�rtice.
     *
     * @param edge la arista a agregar
     */
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    /**
     * Elimina una arista del v�rtice.
     *
     * @param edge la arista a eliminar
     */
    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    /**
     * Obtiene la lista de v�rtices vecinos del v�rtice actual.
     *
     * @return la lista de v�rtices vecinos del v�rtice actual
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
