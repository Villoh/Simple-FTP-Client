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
    public static File seleccionaFicheroAGuardar(Stage stage, String fileName) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo");
        fileChooser.setInitialFileName(fileName);
        //fileChooser.setInitialDirectory("");
        File selectedFile = fileChooser.showSaveDialog(stage);
        return selectedFile;
    }
    
    public static File seleccionaFicheroASubir(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo para subir");
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }
}
