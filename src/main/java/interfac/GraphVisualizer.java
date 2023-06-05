/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package interfac;

import adacters.GraphAdacter;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import proyectGrafo.App;
import proyectGrafo.utils.Edge;
import proyectGrafo.utils.Relation;
import proyectGrafo.utils.TextFileValidator;
import proyectGrafo.utils.User;

/**
 *
 * @author BIGWISE
 */
public class GraphVisualizer extends javax.swing.JPanel {

private GraphAdacter graphAdacter;
    private String filePath=null;
    
    private List<GraphNode> graphNodeList;
  

    
      public GraphVisualizer(GraphAdacter graphAdacter) {
              initComponents();
        this.graphAdacter = graphAdacter;
        this.graphNodeList = new ArrayList<>();
        this.countIslandBox.addItem("BFS");
        this.countIslandBox.addItem("DFS");
        repaintNode();
    }
      
      
      

    /**
     * Realiza el evento para encontrar los puentes en el grafo.
     * Llama al método findBridges para obtener los puentes y los muestra en la lista bridgeList.
     */
    private void eventFindBridge() {
        bridgeList.setListData(new String []{});

        List<Edge> puentes = graphAdacter.findBridges();

        // Crear un modelo de lista para bridgeList
        ArrayList<String> listModel = new ArrayList();

        // Agregar los puentes al modelo de lista
        for (Edge puente : puentes) {
            listModel.add(puente.getSource().getId().toString() + "  ----  " + puente.getTarget().getId().toString());
        }
           System.out.print(listModel);
        // Establecer el modelo de lista en bridgeList
         String[] array = listModel.toArray(new String[0]);
            bridgeList.setListData(array);
    }

    /**
     * Realiza el evento para calcular la cantidad de islas en el grafo.
     * Utiliza el algoritmo especificado por el valor seleccionado en countIslandBox (BFS o DFS).
     * Muestra la cantidad de islas en un mensaje de diálogo.
     */
    private void eventCalculateIslands() {
        boolean usarBFS = countIslandBox.getSelectedItem().equals("BFS");
        int cantidadIslas = graphAdacter.getCountIslands(usarBFS);
        JOptionPane.showMessageDialog(null, "       Cantidad de Islas: " + cantidadIslas);
    }

    /**
     * Realiza el evento para reordenar el grafo.
     * Llama al método updateGraph en el graphAdacter y actualiza la visualización del grafo.
     */
    private void eventReorder() {
        graphAdacter.updateGraph();
        refresh();
    }

    /**
     * Carga un archivo de grafo desde el sistema de archivos.
     */
    private void loadFileProcess() {
        JFileChooser fileChooser = new JFileChooser();

        // Establecer el filtro de extensión de archivo
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // Modificar el título del diálogo
        fileChooser.setDialogTitle("Cargar archivo");

        // Modificar el texto del botón de aprobación
        fileChooser.setApproveButtonText("Cargar");

        // Mostrar el diálogo de selección de archivo
        int result = fileChooser.showOpenDialog(null);

        // Comprobar si el usuario ha seleccionado un archivo
        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener la ruta seleccionada
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            String[] options = { "Si", "Cancelar"};

           int selection =   JOptionPane.showOptionDialog(null,"Desea guardar la estructura de archivo","Advertencia",0,3,null,options,options[0]);

            if (selection == 0) {
                saveFileProcess();
            }

            // Validar la estructura del archivo de texto
            TextFileValidator validator = new TextFileValidator();
            if (!validator.validateTextFile(filePath)) {
                JOptionPane.showMessageDialog(null, "La estructura del archivo no es válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Guardar la ruta del archivo en el objeto DataManager
            App.getDataManager().setFilePath(filePath);
            // Cargar el grafo desde el archivo
            App.getDataManager().loadFromFile();
            graphAdacter = App.getDataManager().getGraph();
            graphAdacter.updateGraph();


            this.filePath = null;
            refresh();
        }
    }

    /**
     * Guarda el grafo en un archivo en el sistema de archivos.
     */
    private void saveFileProcess() {
        if (filePath == null) {
            JFileChooser fileChooser = new JFileChooser();

            // Establecer el filtro de extensión de archivo
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);

            // Modificar el título del diálogo
            fileChooser.setDialogTitle("Guardar archivo");

            // Modificar el texto del botón de aprobación
            fileChooser.setApproveButtonText("Guardar");

            // Mostrar el diálogo de selección de archivo
            int result = fileChooser.showOpenDialog(null);

            // Comprobar si el usuario ha seleccionado un archivo
            if (result == JFileChooser.APPROVE_OPTION) {
                // Obtener la ruta seleccionada
                filePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Agregar automáticamente la extensión .txt al nombre del archivo si no está presente
                if (!filePath.endsWith(".txt")) {
                    filePath += ".txt";
                }

                // Guardar la ruta del archivo en el objeto DataManager
                App.getDataManager().setFilePath(filePath);
            }
        }

        // Guardar el grafo en el archivo
        App.getDataManager().saveToFile();
    }


    /**
     * Agrega un nuevo usuario al grafo.
     */
    private void addUser() {
        try {
            // Solicitar al usuario el ID del usuario a agregar
            String input = JOptionPane.showInputDialog("Ingrese el ID del usuario:");
            if (input == null) {
                return;
            }
            int idUsuario = Integer.parseInt(input);

            // Verificar si el ID ya existe
            if (graphAdacter.userExists(idUsuario)) {
                JOptionPane.showMessageDialog(null, "Advertencia: El ID de usuario ya existe.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Solicitar al usuario el nombre del usuario
            String nombreUsuario = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");

            if (nombreUsuario != null) {
                // Formatear el nombre de usuario
                nombreUsuario = nombreUsuario.replaceAll(" ", "_").replaceAll("[^0-9a-zA-Z_]", "");
                nombreUsuario = "@" + nombreUsuario;

                // Crear el objeto User con el ID y nombre ingresados
                User user = new User(idUsuario, nombreUsuario);

                // Agregar el usuario al grafo
                graphAdacter.addUser(user);

                // Preguntar al usuario si desea establecer una relación de amistad
                String opcionRelacion = JOptionPane.showInputDialog("¿Desea establecer una relación de amistad con algún usuario existente? (S/N)");

                if (opcionRelacion != null && opcionRelacion.equalsIgnoreCase("S")) {
                    // Obtener la lista de usuarios existentes
                    List<User> users = graphAdacter.getUsers().stream()
                            .sorted(Comparator.comparingInt(User::getId))
                            .collect(Collectors.toList());
                    String[] idsUsuarios = new String[users.size()];
                    for (int i = 0; i < users.size(); i++) {
                        idsUsuarios[i] = String.valueOf(users.get(i).getId());
                    }

                    // Mostrar el cuadro de diálogo de selección
                    String idUsuarioRelacionStr = (String) JOptionPane.showInputDialog(
                            null,
                            "Seleccione el ID del usuario con el que desea establecer la relación de amistad:",
                            "Selección de Usuario",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            idsUsuarios,
                            idsUsuarios[0]
                    );

                    if (idUsuarioRelacionStr != null) {
                        // Solicitar al usuario el tiempo de amistad
                        String tiempoAmistad = JOptionPane.showInputDialog("Ingrese el tiempo de la relación de amistad:");

                        if (tiempoAmistad != null) {
                            int idUsuarioRelacion = Integer.parseInt(idUsuarioRelacionStr);
                            int intTiempoAmistad = Integer.parseInt(tiempoAmistad);
                            // Agregar la relación de amistad
                            graphAdacter.addRelation(user.getId(), idUsuarioRelacion, intTiempoAmistad);
                        }
                    }
                }
            } else {
                // Se canceló la entrada del nombre del usuario
                // Eliminar el usuario recientemente agregado
                graphAdacter.deleteUser(idUsuario);
            }

            refresh();
            // Actualizar la visualización del grafo
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: El ID del usuario o el tiempo de amistad no son válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Agrega una relación de amistad para un usuario existente en el grafo.
     *
     * @param idUsuario el ID del usuario al que se le establecerá la relación de amistad
     */
    public void addRelation(int idUsuario) {
        try {
            // Obtener la lista de usuarios existentes
            List<User> users = graphAdacter.getUsers().stream()
                    .sorted(Comparator.comparingInt(User::getId))
                    .collect(Collectors.toList());
            String[] idsUsuarios = new String[users.size()];
            for (int i = 0; i < users.size(); i++) {
                idsUsuarios[i] = String.valueOf(users.get(i).getId());
            }

            // Mostrar el cuadro de diálogo de selección
            String idUsuarioRelacionStr = (String) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el ID del usuario con el que desea establecer la relación de amistad:",
                    "Selección de Usuario",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    idsUsuarios,
                    idsUsuarios[0]
            );

            if (idUsuarioRelacionStr != null) {
                // Solicitar al usuario el tiempo de amistad
                String tiempoAmistad = JOptionPane.showInputDialog("Ingrese el tiempo de la relación de amistad:");

                if (tiempoAmistad != null) {
                    int idUsuarioRelacion = Integer.parseInt(idUsuarioRelacionStr);
                    int intTiempoAmistad = Integer.parseInt(tiempoAmistad);
                    // Agregar la relación de amistad
                    graphAdacter.addRelation(idUsuario, idUsuarioRelacion, intTiempoAmistad);
                }
            }

            refresh();
            // Actualizar la visualización del grafo
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error: el tiempo de amistad no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Elimina un usuario existente del grafo.
     */
    private void deleteUser() {
        // Obtener la lista de usuarios existentes
        List<User> users = graphAdacter.getUsers().stream()
                .sorted(Comparator.comparingInt(User::getId))
                .collect(Collectors.toList());
        String[] idsUsuarios = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            idsUsuarios[i] = String.valueOf(users.get(i).getId());
        }

        // Mostrar el cuadro de diálogo de selección
        String idUsuarioStr = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione el ID del usuario que desea eliminar:",
                "Selección de Usuario",
                JOptionPane.PLAIN_MESSAGE,
                null,
                idsUsuarios,
                idsUsuarios[0]
        );

        if (idUsuarioStr != null) {
            int idUsuario = Integer.parseInt(idUsuarioStr);
            // Eliminar el usuario del grafo
            graphAdacter.deleteUser(idUsuario);
            refresh();
            // Actualizar la visualización del grafo
        } else {
            // El usuario ha cancelado la selección
            // Realizar cualquier acción adicional o mostrar un mensaje de cancelación
        }
    }

    /**
     * Actualiza la visualización del grafo y los componentes relacionados.
     */
    public void refresh() {
        panel1.repaint();
        repaintNode();
        Dimension panelSize = calculatePanelSize(panel1); // Calcula el tamaño del panel en base a los componentes contenidos
        panel1.setPreferredSize(panelSize);
        panel1.revalidate();
    }


    /**
     * Dibuja el grafo en el lienzo gráfico.
     *
     * @param g El objeto Graphics2D utilizado para dibujar.
     */
    private void drawGraph(Graphics2D g) {
        List<User> users = graphAdacter.getUsers();
        Set<String> parejasDibujadas = new HashSet<>();
        Map<Point, Integer> etiquetasPosiciones = new HashMap<>(); // Mapa para almacenar las posiciones ocupadas por las etiquetas
        Collections.shuffle(users);

        for (User user : users) {
            int x = user.getX();
            int y = user.getY();

            List<Integer> amigos = graphAdacter.getFriends(graphAdacter.getUser(user.getId())).stream()
                    .map(User::getId)
                    .collect(Collectors.toList());
            Collections.shuffle(amigos);

            for (int amigoId : amigos) {
                User amigo = graphAdacter.getUser(amigoId);

                if (amigo != null) {
                    int amigoX = amigo.getX() + 25;
                    int amigoY = amigo.getY() + 25;
                    int dx = amigoX - x + 25;
                    int dy = amigoY - y + 25;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    double ratio = 25 / distance; // 25 unidades de desplazamiento
                    int startX = x + 25 + (int) (dx * ratio);
                    int startY = y + 25 + (int) (dy * ratio);
                    int endX = amigoX - (int) (dx * ratio);
                    int endY = amigoY - (int) (dy * ratio);

                    String parejaActual = String.format("%d-%d", Math.min(user.getId(), amigoId), Math.max(user.getId(), amigoId));
                    if (!parejasDibujadas.contains(parejaActual)) {
                        g.drawLine(startX, startY, endX, endY);
                        parejasDibujadas.add(parejaActual);
                        int tiempoAmistad = graphAdacter.getRelation(user, amigo)
                                .map(Relation::getFriendshipTime)
                                .orElse(0);

                        // Dibujar el nombre de la rama
                        int ramaX = (x + amigoX) / 2;
                        int ramaY = (y + amigoY) / 2;
                        Point etiquetaPosicion = new Point(ramaX, ramaY);

                        // Verificar si hay una etiqueta en la misma posición
                        if (etiquetasPosiciones.containsKey(etiquetaPosicion)) {
                            // Obtener la posición opuesta
                            int offset = 10; // Distancia de desplazamiento de la etiqueta
                            int newY = ramaY;
                            if (ramaY <= etiquetasPosiciones.get(etiquetaPosicion)) {
                                newY += offset;
                            } else {
                                newY -= offset;
                            }
                            ramaY = newY;
                        }

                        etiquetasPosiciones.put(etiquetaPosicion, ramaY);
                        g.drawString(String.valueOf(tiempoAmistad), ramaX, ramaY);
                    }
                }
            }
        }
    }
    /**
     * Muestra el grafo en una ventana gráfica.
     */
    public void showGraph() {
        JFrame frame = new JFrame("Visualizador de GraphAdacter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * Crea los componentes de la interfac de usuario.
     */
    private void createUIComponents() {
        this.panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                drawGraph(g2d);
            }
        };
        repaintNode();
    }

    /**
     * Actualiza la representación gráfica de los nodos del grafo.
     */
    private void repaintNode() {
            this.panel1.removeAll();
        this.panel1.setLayout(null);
        this.graphNodeList = new ArrayList<>();
        List<User> users = graphAdacter.getUsers();
        Collections.shuffle(users);
        for (User user : users) {
            int x = user.getX();
            int y = user.getY();

            GraphNode nodo = new GraphNode(user, this.graphAdacter, this);
            nodo.setBounds(x, y, 50, 50);
            graphNodeList.add(nodo);
            this.panel1.add(nodo);
        }
    }

    /**
     * Calcula el tamaño del panel en base a los componentes contenidos.
     *
     * @param panel El panel del que se va a calcular el tamaño.
     * @return Las dimensiones calculadas del panel.
     */
    private Dimension calculatePanelSize(JPanel panel) {
        int maxWidth = 0;
        int totalHeight = 0;

        // Recorre todos los componentes del panel
        for (Component component : panel.getComponents()) {
            // Obtiene la posición y el tamaño del componente
            int x = component.getX();
            int y = component.getY();
            int width = component.getWidth();
            int height = component.getHeight();

            // Actualiza el ancho máximo y la altura total según las necesidades del componente actual
            maxWidth = Math.max(maxWidth, x + width);
            totalHeight = Math.max(totalHeight, y + height);
        }

        // Devuelve las dimensiones calculadas
        return new Dimension(maxWidth, totalHeight);
    }


    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrollpanel = new javax.swing.JScrollPane();
        panel1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        addUserButton = new javax.swing.JButton();
        deleteUserButton = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 0), new java.awt.Dimension(50, 32767));
        calculateIslandsButton = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        countIslandBox = new javax.swing.JComboBox<>();
        findBridgeButton = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        bridgeList = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        loadButton = new javax.swing.JButton();
        reorderButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();

        mainPanel.setLayout(new java.awt.GridLayout(1, 2));

        panel1.setBackground(new java.awt.Color(255, 255, 255));
        panel1.setPreferredSize(new java.awt.Dimension(800, 800));
        panel1.setLayout(new java.awt.GridLayout());

        panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                drawGraph(g2d);
            }
        };

        scrollpanel.setViewportView(panel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setLayout(new java.awt.GridLayout(1,2));
        mainPanel.add(jPanel2);

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 607));

        jPanel3.setLayout(new java.awt.GridLayout(2, 1, 0, 50));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Acciones");
        jPanel3.add(jLabel1);

        jPanel6.setLayout(new java.awt.GridLayout(1, 2, 10, 100));
        jPanel6.add(filler1);

        addUserButton.setText("Agregar Usuario");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });
        jPanel6.add(addUserButton);

        deleteUserButton.setText("Eliminar Usuario");
        deleteUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteUserButtonActionPerformed(evt);
            }
        });
        jPanel6.add(deleteUserButton);
        jPanel6.add(filler2);

        jPanel3.add(jPanel6);

        jPanel1.add(jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 204));
        jPanel5.setLayout(new java.awt.GridLayout(4, 1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Procesamiento");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1332, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 162, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(0, 68, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 69, Short.MAX_VALUE)))
        );

        jPanel5.add(jPanel7);

        jLabel2.setText("Cantidad Totales de Islas");
        jPanel8.add(jLabel2);
        jPanel8.add(filler3);

        calculateIslandsButton.setText("Calcular Islas");
        calculateIslandsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateIslandsButtonActionPerformed(evt);
            }
        });
        jPanel8.add(calculateIslandsButton);

        jPanel5.add(jPanel8);

        countIslandBox.setPreferredSize(new java.awt.Dimension(200, 22));
        countIslandBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countIslandBoxActionPerformed(evt);
            }
        });
        jPanel9.add(countIslandBox);

        findBridgeButton.setText("Buscar Puentes");
        findBridgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findBridgeButtonActionPerformed(evt);
            }
        });
        jPanel9.add(findBridgeButton);

        jPanel5.add(jPanel9);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        jPanel10.setLayout(flowLayout1);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        bridgeList.setMaximumSize(new java.awt.Dimension(500, 500));
        bridgeList.setMinimumSize(new java.awt.Dimension(500, 500));
        bridgeList.setPreferredSize(new java.awt.Dimension(500, 700));
        jScrollPane2.setViewportView(bridgeList);

        jPanel10.add(jScrollPane2);

        jPanel5.add(jPanel10);

        jPanel1.add(jPanel5);

        jPanel4.setMaximumSize(new java.awt.Dimension(200, 200));
        jPanel4.setPreferredSize(new java.awt.Dimension(550, 20));
        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 100, 0));

        loadButton.setText("Cargar");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        jPanel4.add(loadButton);

        reorderButton.setText("Reordenar");
        reorderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reorderButtonActionPerformed(evt);
            }
        });
        jPanel4.add(reorderButton);

        saveButton.setText("Guardar");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        jPanel4.add(saveButton);

        jPanel1.add(jPanel4);

        mainPanel.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
       this.addUser(); // TODO add your handling code here:
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void deleteUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteUserButtonActionPerformed
      this.deleteUser();  // TODO add your handling code here:
    }//GEN-LAST:event_deleteUserButtonActionPerformed

    private void calculateIslandsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateIslandsButtonActionPerformed
       this.eventCalculateIslands();    // TODO add your handling code here:
    }//GEN-LAST:event_calculateIslandsButtonActionPerformed

    private void findBridgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findBridgeButtonActionPerformed
       this.eventFindBridge(); // TODO add your handling code here:
    }//GEN-LAST:event_findBridgeButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        this.loadFileProcess();// TODO add your handling code here:
    }//GEN-LAST:event_loadButtonActionPerformed

    private void reorderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reorderButtonActionPerformed
        // TODO add your handling code here:
        this.eventReorder();
    }//GEN-LAST:event_reorderButtonActionPerformed

    private void countIslandBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countIslandBoxActionPerformed
    }//GEN-LAST:event_countIslandBoxActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
      this.saveFileProcess();  // TODO add your handling code here:
    }//GEN-LAST:event_saveButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton addUserButton;
    public static javax.swing.JList<String> bridgeList;
    public javax.swing.JButton calculateIslandsButton;
    public static javax.swing.JComboBox<String> countIslandBox;
    public javax.swing.JButton deleteUserButton;
    public javax.swing.Box.Filler filler1;
    public javax.swing.Box.Filler filler2;
    public javax.swing.Box.Filler filler3;
    public javax.swing.JButton findBridgeButton;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JPanel jPanel5;
    public javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JPanel jPanel8;
    public javax.swing.JPanel jPanel9;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JButton loadButton;
    public javax.swing.JPanel mainPanel;
    public javax.swing.JPanel panel1;
    public javax.swing.JButton reorderButton;
    public javax.swing.JButton saveButton;
    public javax.swing.JScrollPane scrollpanel;
    // End of variables declaration//GEN-END:variables
}
