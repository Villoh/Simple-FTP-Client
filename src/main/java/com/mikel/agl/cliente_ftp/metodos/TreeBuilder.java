/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import java.io.IOException;
import javafx.scene.control.TreeItem;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Villoh
 */
public class TreeBuilder {
    
    /**
     * Construye una vista en arbol mediante recursividad.
     * @param ftp FTPClient para obtener los ficheros.
     * @param padre Item padre para iniciar la vista en arbol.
     * @param currentDirectory Directorio en el que se encuentra actualmente.
     */
    public static void construyeArbol(FTPClient ftp, TreeItem<String> padre, String currentDirectory) {
        try {
            // Obtener una lista de archivos y directorios en el directorio actual
            FTPFile[] files = ftp.listFiles(currentDirectory);
            // Agregar un nodo para cada archivo o directorio
            for (FTPFile file : files) {
                TreeItem<String> nodo = new TreeItem<>(file.getName());
                padre.getChildren().add(nodo);
                
                if (file.isDirectory()) {
                    //Aplico recursividad para cada directorio del fichero padre.
                    construyeArbol(ftp, nodo, currentDirectory + file.getName() + "/");
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

