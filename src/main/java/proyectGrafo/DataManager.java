/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo;

import adacters.GraphAdacter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import proyectGrafo.utils.Relation;
import proyectGrafo.utils.Template;
import proyectGrafo.utils.User;

/**
 *
 * @author BIGWISE
 */
public class DataManager {
    private GraphAdacter graphAdacter;
    private String filePath;

    /**
     * Crea una nueva instancia de DataManager.
     * Establece el archivo de ruta predeterminado para cargar los datos del graphAdacter.
     */
    public DataManager() {
        filePath = "grafo.txt";
    }


    /**
     * Carga los datos del archivo en el graphAdacter.
     * Lee el archivo de datos línea por línea y realiza la carga de usuarios y relaciones en el graphAdacter.
     */
    public void loadFromFile() {
        graphAdacter = new GraphAdacter();
        Template.constantFile();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String linea;

            while ((linea = br.readLine()) != null) {
                if (linea.equals("Usuarios")) {
                    cargarUsuarios(br);
                } else if (linea.equals("Relaciones")) {
                    cargarRelaciones(br);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carga los usuarios desde el archivo de datos en el graphAdacter.
     * Lee cada línea del archivo hasta encontrar el indicador "Relaciones" o una línea en blanco.
     *
     * @param br El BufferedReader utilizado para leer el archivo.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    private void cargarUsuarios(BufferedReader br) throws IOException {
        String linea;

        while ((linea = br.readLine()) != null && !linea.equals("Relaciones") && !linea.equals("")) {
            String[] campos = linea.split(", ");
            int id = Integer.parseInt(campos[0].trim());
            String nombre = campos[1].trim();
            User user = new User(id, nombre);
            graphAdacter.addUser(user);
        }
    }


    /**
     * Carga las relaciones desde el archivo de datos en el graphAdacter.
     * Lee cada línea del archivo y agrega la relación al graphAdacter utilizando los datos de la línea.
     *
     * @param br El BufferedReader utilizado para leer el archivo.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    private void cargarRelaciones(BufferedReader br) throws IOException {
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] campos = linea.split(", ");
            int idUsuario1 = Integer.parseInt(campos[0].trim());
            int idUsuario2 = Integer.parseInt(campos[1].trim());
            int tiempoAmistad = Integer.parseInt(campos[2].trim());
            graphAdacter.addRelation(idUsuario1, idUsuario2, tiempoAmistad);
        }
    }



    /**
     * Guarda los datos del graphAdacter en un archivo de texto.
     * Escribe los usuarios y relaciones del graphAdacter en el archivo en un formato determinado.
     * El archivo existente será sobrescrito.
     */
    public void saveToFile() {
        try {
            FileWriter fw = new FileWriter(filePath, false); // Indicar que deseas sobrescribir el archivo

            // Escribir los users en el archivo
            fw.write("Usuarios\n");
            List<User> users = graphAdacter.getUsers();
            for (User user : users) {
                fw.write(user.getId() + ", " + user.getNombre() + "\n");
            }

            // Agregar línea en blanco para separar users de relaciones
            fw.write("\n");

            // Escribir las relaciones en el archivo
            fw.write("Relaciones\n");
            List<Relation> relaciones = graphAdacter.getRelations();
            for (Relation relation : relaciones) {
                fw.write(relation.getUser1().getId() + ", " + relation.getUser2().getId() + ", " + relation.getFriendshipTime() + "\n");
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Obtiene el graphAdacter almacenado en el DataManager.
     *
     * @return El objeto GraphAdacter almacenado en el DataManager.
     */
    public GraphAdacter getGraph() {
        return graphAdacter;
    }

    /**
     * Establece la ruta de archivo del DataManager.
     *
     * @param filePath La ruta de archivo a establecer.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
