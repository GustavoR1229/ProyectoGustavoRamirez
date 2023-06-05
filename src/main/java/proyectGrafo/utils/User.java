/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

/**
 * Clase que representa a un usuario.
 */
public class User {
    private int id;
    private String nombre;
    private int x;
    private int y;

    /**
     * Crea una instancia de User con el ID y nombre especificados.
     *
     * @param id     el ID del usuario
     * @param nombre el nombre del usuario
     */
    public User(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /**
     * Crea una instancia de User con el ID, nombre, coordenada X y coordenada Y especificados.
     *
     * @param id     el ID del usuario
     * @param nombre el nombre del usuario
     * @param x      la coordenada X del usuario
     * @param y      la coordenada Y del usuario
     */
    public User(int id, String nombre, int x, int y) {
        this.id = id;
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }

    /**
     * Obtiene el ID del usuario.
     *
     * @return el ID del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return el nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la coordenada X del usuario.
     *
     * @return la coordenada X del usuario
     */
    public int getX() {
        return x;
    }

    /**
     * Establece la coordenada X del usuario.
     *
     * @param x la coordenada X del usuario
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Obtiene la coordenada Y del usuario.
     *
     * @return la coordenada Y del usuario
     */
    public int getY() {
        return y;
    }

    /**
     * Establece la coordenada Y del usuario.
     *
     * @param y la coordenada Y del usuario
     */
    public void setY(int y) {
        this.y = y;
    }
}
