/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.hilos;

import com.mikel.agl.cliente_ftp.ResizeHelper;
import com.mikel.agl.cliente_ftp.controladores.LoginController;
import com.mikel.agl.cliente_ftp.metodos.View;
import com.mikel.agl.cliente_ftp.metodos.TreeBuilder;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import org.apache.commons.net.ftp.FTPClient;
/**
 *
 * @author Villoh
 */
public class TreeBuilderThread extends Thread{
    
    private static TreeItem<String> rootItem;
    private FTPClient ftpClient;
    
    public TreeBuilderThread(FTPClient ftpClient, TreeItem<String> rootItem){
        this.ftpClient = ftpClient;
        TreeBuilderThread.rootItem = rootItem;
    }
    
    @Override
    public void run(){
        //Construye la vista en arbol.
        TreeBuilder.construyeArbol(ftpClient, rootItem, "/");
        Platform.runLater(() -> {
            //Cuando termina de construirla carga la vista principal.
            View.load("main_menu", LoginController.newStage);
            //Listener para que se pueda redimensionar la vista.
            ResizeHelper.addResizeListener(LoginController.newStage);
        });
    }
}
