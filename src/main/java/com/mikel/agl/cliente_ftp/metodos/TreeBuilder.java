/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.metodos;

import io.github.palexdev.materialfx.controls.MFXTreeItem;
import java.io.File;
import java.util.Arrays;
import javafx.scene.control.TreeItem;

/**
 *
 * @author Villoh
 */
public class TreeBuilder {
    
    //private static Thread treeThread;
    
    public static void construyeArbol(File file, MFXTreeItem<String> parent, boolean isRoot) {
        if (file.isFile()) {
            // Si el fichero es un archivo, crea un nodo hoja para el archivo y agrega el nodo hoja al padre
            MFXTreeItem<String> fileItem = new MFXTreeItem<>(file.getName());
            parent.getItems().add(fileItem);
        } else {
            // Si el archivo es un directorio, crea un nodo para el directorio y agrega el nodo al padre
            MFXTreeItem<String> directoryItem = new MFXTreeItem<>(file.getName());
            // Verifica si el resultado de file.listFiles() es null antes de intentar acceder a su propiedad length
            File[] files = file.listFiles();
            // Verifica si el directorio está vacío antes de agregarlo al árbol
            if (files != null && files.length > 1) {
                parent.getItems().add(directoryItem);
                // Recorre todos los archivos y subdirectorios dentro del directorio utilizando recursividad
                for (File subFile : file.listFiles()) {
                    construyeArbol(subFile, directoryItem, false);
                }
            }
        }
    }
    
    public static void construyeArbol(File file, TreeItem<String> parent, boolean isRoot) {
        if (file.isFile()) {
            // Si el fichero es un archivo, crea un nodo hoja para el archivo y agrega el nodo hoja al padre
            TreeItem<String> fileItem = new TreeItem<>(file.getName());
            parent.getChildren().add(fileItem);
        } else {
            TreeItem<String> directoryItem;
            //Comprueba si es el nodo raíz para que el nombre no esté vacio
            if(isRoot){
                // Si el archivo es un directorio, crea un nodo para el directorio y agrega el nodo al padre
                directoryItem = new TreeItem<>("Ruta raíz del servidor");
            }else{
                directoryItem = new TreeItem<>(file.getName());
            }
            System.out.println(file.getName());
            // Verifica si el resultado de file.listFiles() es null antes de intentar acceder a su propiedad length
            File[] files = file.listFiles();
            // Verifica si el directorio está vacío antes de agregarlo al árbol
            if (files != null && files.length > 1) {
                System.out.println(Arrays.toString(files));
                System.out.println(files.length);
                
                parent.getChildren().add(directoryItem);
                // Recorre todos los archivos y subdirectorios dentro del directorio utilizando recursividad
                for (File subFile : file.listFiles()) {
                    construyeArbol(subFile, directoryItem, false);
                }
            }
        }
    }
}

