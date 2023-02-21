/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Villoh
 */
public class FileUtils {
    /**
     * Inicializa y muestra un FileChooser para seleccionar un directorio donde guardar el fichero y devuelve el File seleccionado
     * @param stage stage (contexto) donde se va a abrir el FileChooser
     * @param fileName Nombre del fichero a guardar.
     * @return File
     */
    public static File seleccionaFicheroAGuardar(Stage stage, String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo");
        //Nombre inicial del archivo
        fileChooser.setInitialFileName(fileName);
        //Ruta inicial de guardado
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads/"));
        File selectedFile = fileChooser.showSaveDialog(stage);
        return selectedFile;
    }
    
    /**
     * Inicializa y muestra un FileChooser para seleccionar un archivo existente y devuelve el File (archivo) seleccionado
     * @param stage stage (contexto) donde se va a abrir el FileChooser
     * @return File
     */
    public static File seleccionaFicheroASubir(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo para subir");
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
}
