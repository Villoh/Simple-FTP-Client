/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.hilos;

import com.mikel.agl.cliente_ftp.ResizeHelper;
import com.mikel.agl.cliente_ftp.metodos.ConexionFTP;
import com.mikel.agl.cliente_ftp.metodos.View;
import com.mikel.agl.cliente_ftp.metodos.TreeBuilder;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;
/**
 *
 * @author Villoh
 */
public class TreeBuilderThread extends Thread{
    
    private TreeItem<String> rootItem;
    private ConexionFTP ftp;
    private Stage stage;
    
    public TreeBuilderThread(ConexionFTP ftp, TreeItem<String> rootItem, Stage stage){
        this.ftp = ftp;
        this.rootItem = rootItem;
        this.stage = stage;
    }
    
    @Override
    public void run(){
        //Construye la vista en arbol.
        TreeBuilder.construyeArbol(ftp.getfTPClient(), rootItem, "/");
        Platform.runLater(() -> {
            //Crea un evento que cierra el FTPClient si el FTPClient está conectado, esto sucede cuando el usuario solicita cerrar la ventana.
            stage.setOnHiding(event -> {
                try {
                    if (ftp.isLogued()) {
                        ftp.getfTPClient().logout();
                        ftp.getfTPClient().disconnect();
                    }
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            });
            //Cuando termina de construirla carga la vista principal.
            View.loadMainMenu(stage, ftp, rootItem);
            //Listener para que se pueda redimensionar la vista.
            ResizeHelper.addResizeListener(stage);
        });
    }
}
