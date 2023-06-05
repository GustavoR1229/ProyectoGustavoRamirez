/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectGrafo.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Clase que valida la estructura y el formato de un archivo de texto.
 */
public class TextFileValidator {

    /**
     * Valida un archivo de texto en el camino de archivo especificado.
     *
     * @param filePath el camino de archivo del archivo de texto a validar
     * @return true si el archivo de texto cumple con la estructura y el formato requeridos, false de lo contrario
     */
    public Boolean validateTextFile(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            boolean isValid = validateStructure(scanner);

            scanner.close();

            return isValid;
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe.");
        }
        return false;
    }

    /**
     * Valida la estructura y el formato del archivo de texto.
     *
     * @param scanner el objeto Scanner utilizado para leer el archivo de texto
     * @return true si el archivo de texto cumple con la estructura y el formato requeridos, false de lo contrario
     */
    private boolean validateStructure(Scanner scanner) {
        boolean isValid = validateSection(scanner, "Usuarios");
        isValid &= validateUsersSection(scanner);
        isValid &= validateSection(scanner, "Relaciones");
        return isValid && validateRelationshipsSection(scanner);
    }

    /**
     * Valida una sección del archivo de texto.
     *
     * @param scanner         el objeto Scanner utilizado para leer el archivo de texto
     * @param expectedSection el nombre de la sección esperada
     * @return true si la sección es válida y coincide con el nombre esperado, false de lo contrario
     */
    private boolean validateSection(Scanner scanner, String expectedSection) {
        if (!scanner.hasNextLine()) {
            return false;
        }

        String section = scanner.nextLine().trim();
        return section.equals(expectedSection);
    }

    /**
     * Valida la sección de usuarios del archivo de texto.
     *
     * @param scanner el objeto Scanner utilizado para leer el archivo de texto
     * @return true si la sección de usuarios es válida, false de lo contrario
     */
    private boolean validateUsersSection(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                break;  // Fin de la sección de usuarios
            }

            if (!line.matches("\\d+, @[\\p{L}\\p{M}\\p{Nd}_]+")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Valida la sección de relaciones del archivo de texto.
     *
     * @param scanner el objeto Scanner utilizado para leer el archivo de texto
     * @return true si la sección de relaciones es válida, false de lo contrario
     */
    private boolean validateRelationshipsSection(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (!line.matches("\\d+, \\d+, \\d+")) {
                return false;
            }
        }
        return true;
    }
}
