/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adacters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import proyectGrafo.utils.Edge;
import proyectGrafo.utils.Graph;
import proyectGrafo.utils.Relation;
import proyectGrafo.utils.User;
import proyectGrafo.utils.Vertex;


/**
 * Clase que representa un grafo.
 * Contiene una lista de users, una lista de relaciones, un objeto Graph y un indicador de cambio.
 */
public class GraphAdacter {
    private List<User> users;
    private List<Relation> relaciones;
    private Graph graph;
    private boolean change;

    /**
     * Crea una nueva instancia de GraphAdacter.
     * Inicializa las listas de users y relaciones, el objeto Graph y el indicador de cambio.
     */
    public GraphAdacter() {
        this.users = new ArrayList<>();
        this.relaciones = new ArrayList<>();
        this.graph = new Graph();
        this.change = false;
    }

    /**
     * Obtiene la lista de users del grafo.
     *
     * @return La lista de users del grafo.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Obtiene la lista de relaciones del grafo.
     *
     * @return La lista de relaciones del grafo.
     */
    public List<Relation> getRelations() {
        return relaciones;
    }

    /**
     * Obtiene una relaci�n entre dos users especificados.
     *
     * @param user1 El primer usuario de la relaci�n.
     * @param user2 El segundo usuario de la relaci�n.
     * @return Un objeto Optional que contiene la relaci�n si existe, o un Optional vac�o si no existe.
     */
    public Optional<Relation> getRelation(User user1, User user2) {
        return getRelations().stream()
                .filter(s -> s.containsUsers(user1, user2))
                .findFirst();
    }

    /**
     * Obtiene la lista de amigos de un user espec�fico.
     *
     * @param user El user para el cual se desea obtener la lista de amigos.
     * @return La lista de amigos del user.
     */
    public List<User> getFriends(User user) {
        List<User> amigos = new ArrayList<>();
        for (Relation relation : relaciones) {
            if (relation.containsUser(user)) {
                User amigo = relation.getOtherUser(user);
                amigos.add(amigo);
            }
        }
        return amigos;
    }

    /**
     * Agrega un user al grafo.
     *
     * @param user El user a agregar.
     */
    public void addUser(User user) {
        users.add(user);
        updateGraph();
    }

    /**
     * Elimina un usuario del grafo y todas las relaciones asociadas a ese usuario.
     *
     * @param idUsuario El ID del usuario a eliminar.
     */
    public void deleteUser(int idUsuario) {
        users.removeIf(usuario -> usuario.getId() == idUsuario);
        relaciones.removeIf(relation -> relation.getUser1().getId() == idUsuario || relation.getUser2().getId() == idUsuario);
        updateGraph();
    }

    /**
     * Obtiene la cantidad de islas (componentes conectados) en el grafo.
     *
     * @param usarBFS Indica si se debe utilizar el algoritmo BFS (true) o DFS (false) para contar las islas.
     * @return La cantidad de islas en el grafo.
     */
    public int getCountIslands(boolean usarBFS) {
        Set<Vertex> visitados = new HashSet<>();
        int cantidadIslas = 0;

        for (Vertex vertex : graph.getVertexs()) {
            if (!visitados.contains(vertex)) {
                cantidadIslas++;
                if (usarBFS) {
                    bfs(graph, vertex, visitados);
                } else {
                    dfs(graph, vertex, visitados);
                }
            }
        }

        return cantidadIslas;
    }


        /**
         * Realiza un recorrido BFS (Breadth-First Search) en el grafo a partir de un v�rtice de inicio.
         *
         * @param graph        El objeto Graph que representa el grafo.
         * @param startVertex  El v�rtice de inicio del recorrido.
         * @param visitados    Un conjunto que almacena los v�rtices visitados durante el recorrido.
         */
        private void bfs(Graph graph, Vertex startVertex, Set<Vertex> visitados) {
            Queue<Vertex> queue = new LinkedList<>();
            queue.add(startVertex);
            visitados.add(startVertex);

            while (!queue.isEmpty()) {
                Vertex currentVertex = queue.poll();

                for (Vertex neighbor : currentVertex.getNeighbors()) {
                    if (!visitados.contains(neighbor)) {
                        queue.add(neighbor);
                        visitados.add(neighbor);
                    }
                }
            }
        }

    /**
     * Realiza un recorrido DFS (Depth-First Search) en el grafo a partir de un v�rtice de inicio.
     *
     * @param graph        El objeto Graph que representa el grafo.
     * @param currentVertex El v�rtice de inicio del recorrido.
     * @param visitados    Un conjunto que almacena los v�rtices visitados durante el recorrido.
     */
    private void dfs(Graph graph, Vertex currentVertex, Set<Vertex> visitados) {
        visitados.add(currentVertex);

        for (Vertex neighbor : currentVertex.getNeighbors()) {
            if (!visitados.contains(neighbor)) {
                dfs(graph, neighbor, visitados);
            }
        }
    }

    /**
     * Verifica si un usuario con el ID especificado existe en el grafo.
     *
     * @param idUsuario El ID del usuario a verificar.
     * @return true si el usuario existe, false de lo contrario.
     */
    public boolean userExists(int idUsuario) {
        return users.stream().anyMatch(usuario -> usuario.getId() == idUsuario);
    }

    /**
     * Obtiene el usuario con el ID especificado.
     *
     * @param idUsuario El ID del usuario a obtener.
     * @return El objeto User correspondiente al ID especificado.
     */
    public User getUser(int idUsuario) {
        return users.stream().filter(s -> s.getId() == idUsuario).findFirst().orElse(null);
    }

    /**
     * Agrega una relaci�n entre dos users al grafo.
     *
     * @param idUsuario1     El ID del primer usuario.
     * @param idUsuario2     El ID del segundo usuario.
     * @param tiempoAmistad  El tiempo de amistad entre los users.
     */
    public void addRelation(int idUsuario1, int idUsuario2, int tiempoAmistad) {
        User user1 = getUser(idUsuario1);
        User user2 = getUser(idUsuario2);

        Relation relation = new Relation(user1, user2, tiempoAmistad);
        relaciones.add(relation);
        updateGraph();
    }


    /**
     * Actualiza el objeto Graph con la informaci�n actual del grafo.
     * Asigna coordenadas a los v�rtices y actualiza las posiciones de los users.
     */
    public void updateGraph() {
        graph = new Graph();
        List<User> newList = new ArrayList<>();

        for (User user : users) {
            graph.addVertex(user.getId());
        }

        for (Relation relation : relaciones) {
            graph.addEdge(relation.getUser1().getId(), relation.getUser2().getId());
        }

        graph.assignCoordinates(graph, 800, 960);

        for (User user : users) {
            user.setX(graph.getVertex(user.getId()).getX());
            user.setY(graph.getVertex(user.getId()).getY());
            newList.add(user);
        }
        users = newList;
    }

    private int time;

    /**
     * Encuentra los puentes en el grafo.
     *
     * @return Una lista de aristas que representan los puentes en el grafo.
     */
    public List<Edge> findBridges() {
        List<Edge> bridges = new ArrayList<>();
        Set<Vertex> visited = new HashSet<>();
        Map<Vertex, Integer> discoveryTime = new HashMap<>();
        Map<Vertex, Integer> lowTime = new HashMap<>();

        time = 0;

        for (Vertex vertex : graph.getVertexs()) {
            if (!visited.contains(vertex)) {
                dfsMix(vertex, null, visited, discoveryTime, lowTime, bridges);
            }
        }

        return bridges;
    }

    /**
     * Realiza un recorrido en profundidad mixto (DFS) en el grafo para encontrar puentes.
     * Un puente es una arista cuya eliminaci�n aumenta la cantidad de componentes conexas en el grafo.
     *
     * @param vertex        El v�rtice actual.
     * @param parent        El v�rtice padre del v�rtice actual.
     * @param visited       Conjunto de v�rtices visitados.
     * @param discoveryTime Mapa que almacena los tiempos de descubrimiento de los v�rtices.
     * @param lowTime       Mapa que almacena los tiempos de baja de los v�rtices.
     * @param bridges       Lista de aristas que representan los puentes encontrados.
     */
    private void dfsMix(Vertex vertex, Vertex parent, Set<Vertex> visited, Map<Vertex, Integer> discoveryTime,
                        Map<Vertex, Integer> lowTime, List<Edge> bridges) {
        visited.add(vertex);
        discoveryTime.put(vertex, time);
        lowTime.put(vertex, time);
        time++;

        for (Edge edge : vertex.getEdges()) {
            Vertex neighbor = edge.getNeighbor(vertex);

            if (neighbor.equals(parent)) {
                continue; // Ignorar el enlace hacia el padre
            }

            if (!visited.contains(neighbor)) {
                dfsMix(neighbor, vertex, visited, discoveryTime, lowTime, bridges);
                lowTime.put(vertex, Math.min(lowTime.get(vertex), lowTime.get(neighbor)));

                if (lowTime.get(neighbor) > discoveryTime.get(vertex)) {
                    bridges.add(edge); // Agregar el arco como puente
                }
            } else {
                lowTime.put(vertex, Math.min(lowTime.get(vertex), discoveryTime.get(neighbor)));
            }
        }
    }

}

