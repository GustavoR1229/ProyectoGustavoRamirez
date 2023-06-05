/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfac;

import adacters.GraphAdacter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import proyectGrafo.utils.User;

public class GraphNode extends JPanel {
    private int id;
    private String titulo;
    private GraphAdacter graphAdacter;
    private GraphVisualizer parent;
    private Color fondoColor = Color.WHITE; // Color de fondo predeterminado

    /**
     * Constructor de la clase GraphNode.
     *
     * @param user   El objeto User asociado al nodo gráfico.
     * @param graphAdacter  El objeto GraphAdacter al que pertenece el nodo.
     * @param parent El objeto GraphVisualizer que contiene el nodo.
     */
    public GraphNode(User user, GraphAdacter graphAdacter, GraphVisualizer parent) {
        this.id = user.getId();
        this.titulo = user.getNombre();
        this.graphAdacter = graphAdacter;
        this.parent = parent;

        setPreferredSize(new Dimension(50, 50));

        // Configurar el menú contextual al hacer clic derecho
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showContextualMenu(e);
                }
            }
        });
    }

    /**
     * Método que pinta el componente gráfico del nodo.
     *
     * @param g El objeto Graphics utilizado para pintar el componente.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        float grosor = 2.0f;
        g2d.setStroke(new BasicStroke(grosor));

        // Establecer el color de fondo
        g2d.setColor(fondoColor);
        int diametro = Math.min(getWidth(), getHeight()) - 1;
        int x = (getWidth() - diametro) / 2;
        int y = (getHeight() - diametro) / 2;
        g2d.fillOval(x, y, diametro, diametro);

        // Dibujar el contorno del círculo
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, diametro, diametro);

        // Dibujar el ID del nodo
        g2d.drawString(String.valueOf(id), 10, 30);
    }

    /**
     * Método que muestra el menú contextual al hacer clic derecho en el nodo.
     *
     * @param e El objeto MouseEvent generado por el clic derecho.
     */
    private void showContextualMenu(MouseEvent e) {
        JPopupMenu popupMenu = new JPopupMenu();
        JLabel titleLabel = new JLabel(titulo);
        popupMenu.add(titleLabel);

        // Opción de Nueva Relación
        JMenuItem newRelation = new JMenuItem("Nueva Relation");
        newRelation.addActionListener(e1 -> {
            parent.addRelation(id);
            parent.refresh();
        });
        popupMenu.add(newRelation);

        // Opción de Eliminar
        JMenuItem eliminarItem = new JMenuItem("Eliminar");
        eliminarItem.addActionListener(e1 -> {
            graphAdacter.deleteUser(id);
            parent.refresh();
        });
        popupMenu.add(eliminarItem);

        // Guardar el estado actual del nodo
        Color estadoAnterior = fondoColor;

        // Cambiar el color de fondo a amarillo
        changeBackgroundColor(Color.YELLOW);

        // Agregar un listener al menú emergente para detectar cuando se cierra
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                // No hacer nada
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                // Volver a cambiar el color de fondo al estado anterior (blanco)
                changeBackgroundColor(estadoAnterior);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                // Volver a cambiar el color de fondo al estado anterior (blanco)
                changeBackgroundColor(estadoAnterior);
            }
        });

        popupMenu.show(GraphNode.this, e.getX(), e.getY());
    }

    /**
     * Método que cambia el color de fondo del nodo.
     *
     * @param color El color de fondo a establecer.
     */
    public void changeBackgroundColor(Color color) {
        fondoColor = color;
        repaint(); // Volver a pintar el componente con el nuevo color
    }

    /**
     * Método que devuelve el ID del nodo.
     *
     * @return El ID del nodo.
     */
    public int getId() {
        return id;
    }
}
