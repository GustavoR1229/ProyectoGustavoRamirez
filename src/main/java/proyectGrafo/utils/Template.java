/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase que representa una plantilla para generar un archivo constante.
 */
public class Template {
    private static final String NOMBRE_ARCHIVO = "grafo.txt";

    /**
     * Método que genera un archivo constante si no existe.
     * Si el archivo ya existe, verifica si cumple con el formato correcto.
     * Si no cumple, reemplaza el archivo existente con uno nuevo.
     */
    public static void constantFile() {
        File archivo = new File(NOMBRE_ARCHIVO);

        // Verificar si el archivo existe
        if (!archivo.exists()) {
            // Generar el archivo con la información
            try (FileWriter writer = new FileWriter(archivo)) {
                    writer.write("Usuarios\n");
                    writer.write("121, @Pepe_Gónzales\n");
                    writer.write("254, @StephaniaCominos\n");
                    writer.write("365, @AndreaStanislao\n");
                    writer.write("412, @Josefina_La_Sifrina\n");
                    writer.write("512, @RosaMosa\n");
                    writer.write("231, @EduardoPetardo\n");
                    writer.write("123, @EnriqueManrique\n");
                    writer.write("129, @casanova23\n");
                    writer.write("870, @venepositivo\n");
                    writer.write("758, @yosoylatorre\n");
                    writer.write("578, @pitiypo\n");
                    writer.write("909, @obiwan123\n");
                    writer.write("893, @caribu_sol\n");
                    writer.write("467, @trapos232\n");
                    writer.write("788, @bandido121\n");
                    writer.write("239, @justiciero11\n");
                    writer.write("443, @fuerza_bruta\n");
                    writer.write("907, @Presentesiempre\n\n");
                    writer.write("Relaciones\n");
                    writer.write("121, 254, 7\n");
                    writer.write("121, 909, 8\n");
                    writer.write("254, 909, 5\n");
                    writer.write("909, 893, 5\n");
                    writer.write("254, 893, 1\n");
                    writer.write("893, 129, 3\n");
                    writer.write("129, 512, 10\n");
                    writer.write("512, 412, 2\n");
                    writer.write("893, 412, 4\n");
                    writer.write("231, 870, 5\n");
                    writer.write("231, 123, 1\n");
                    writer.write("123, 870, 15\n");
                    writer.write("123, 467, 6\n");
                    writer.write("788, 239, 7\n");
                    writer.write("788, 443, 11\n");
                    writer.write("239, 443, 6\n");
                    writer.write("239, 907, 3\n");
                    writer.write("443, 907, 9\n");
                    writer.write("788, 412, 7\n");
                    writer.write("870, 578, 7\n");
                    writer.write("870, 758, 1\n");
                    writer.write("758, 365, 9\n");
                    writer.write("578, 365, 4\n");

                    System.out.println("El archivo se ha generado exitosamente.");
                } catch (IOException e) {
                    System.out.println("Error al generar el archivo: " + e.getMessage());
                }
            } else {
                System.out.println("El archivo ya existe.");
                TextFileValidator validator = new TextFileValidator();
                if(!validator.validateTextFile(NOMBRE_ARCHIVO)){
                    System.out.println("El archivo base no cumple con el formato correcto sera remplazado.");
                    archivo.delete();
                    constantFile();
                }

            }
        }


}
