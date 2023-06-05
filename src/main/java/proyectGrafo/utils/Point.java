/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;


/**
 * Clase que representa un punto en un sistema de coordenadas cartesianas.
 */
public class Point {
     int x;
     int y;

    /**
     * Constructor de la clase Point.
     *
     * @param x La coordenada x del punto.
     * @param y La coordenada y del punto.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Método que devuelve la coordenada x del punto.
     *
     * @return La coordenada x del punto.
     */
    public int getX() {
        return x;
    }

    /**
     * Método que establece la coordenada x del punto.
     *
     * @param x La coordenada x a establecer.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Método que devuelve la coordenada y del punto.
     *
     * @return La coordenada y del punto.
     */
    public int getY() {
        return y;
    }

    /**
     * Método que establece la coordenada y del punto.
     *
     * @param y La coordenada y a establecer.
     */
    public void setY(int y) {
        this.y = y;
    }
}
