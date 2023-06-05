/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

/**
 * Clase que representa una arista en un grafo.
 */
public class Edge {
    private Vertex source;
    private Vertex target;

    /**
     * Constructor de la clase Edge.
     *
     * @param source El vértice de origen de la arista.
     * @param target El vértice de destino de la arista.
     */
    public Edge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
    }

    /**
     * Método que devuelve el vértice de origen de la arista.
     *
     * @return El vértice de origen.
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Método que establece el vértice de origen de la arista.
     *
     * @param source El vértice de origen a establecer.
     */
    public void setSource(Vertex source) {
        this.source = source;
    }

    /**
     * Método que devuelve el vértice de destino de la arista.
     *
     * @return El vértice de destino.
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * Método que establece el vértice de destino de la arista.
     *
     * @param target El vértice de destino a establecer.
     */
    public void setTarget(Vertex target) {
        this.target = target;
    }

    /**
     * Método que devuelve el vecino del vértice dado en esta arista.
     *
     * @param vertex El vértice del cual se desea obtener el vecino.
     * @return El vértice vecino.
     * @throws IllegalArgumentException Si el vértice dado no forma parte de esta arista.
     */
    public Vertex getNeighbor(Vertex vertex) {
        if (vertex.equals(source)) {
            return target;
        } else if (vertex.equals(target)) {
            return source;
        } else {
            throw new IllegalArgumentException("El vértice dado no forma parte de esta arista.");
        }
    }
}
