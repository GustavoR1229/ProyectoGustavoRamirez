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
     * @param source El v�rtice de origen de la arista.
     * @param target El v�rtice de destino de la arista.
     */
    public Edge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
    }

    /**
     * M�todo que devuelve el v�rtice de origen de la arista.
     *
     * @return El v�rtice de origen.
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * M�todo que establece el v�rtice de origen de la arista.
     *
     * @param source El v�rtice de origen a establecer.
     */
    public void setSource(Vertex source) {
        this.source = source;
    }

    /**
     * M�todo que devuelve el v�rtice de destino de la arista.
     *
     * @return El v�rtice de destino.
     */
    public Vertex getTarget() {
        return target;
    }

    /**
     * M�todo que establece el v�rtice de destino de la arista.
     *
     * @param target El v�rtice de destino a establecer.
     */
    public void setTarget(Vertex target) {
        this.target = target;
    }

    /**
     * M�todo que devuelve el vecino del v�rtice dado en esta arista.
     *
     * @param vertex El v�rtice del cual se desea obtener el vecino.
     * @return El v�rtice vecino.
     * @throws IllegalArgumentException Si el v�rtice dado no forma parte de esta arista.
     */
    public Vertex getNeighbor(Vertex vertex) {
        if (vertex.equals(source)) {
            return target;
        } else if (vertex.equals(target)) {
            return source;
        } else {
            throw new IllegalArgumentException("El v�rtice dado no forma parte de esta arista.");
        }
    }
}
