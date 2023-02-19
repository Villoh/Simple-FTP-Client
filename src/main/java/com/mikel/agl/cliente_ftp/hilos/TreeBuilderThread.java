/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mikel.agl.cliente_ftp.hilos;

import com.mikel.agl.cliente_ftp.ResizeHelper;
import com.mikel.agl.cliente_ftp.controladores.LoginController;
import com.mikel.agl.cliente_ftp.metodos.View;
import com.mikel.agl.cliente_ftp.metodos.TreeBuilder;
import java.io.File;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
/**
 *
 * @author Villoh
 */
public class TreeBuilderThread extends Thread{
    
    private static TreeItem<String> rootItem;
    private File file;
    private boolean isRoot;
    
    public TreeBuilderThread(File file, TreeItem<String> rootItem, boolean isRoot){
        this.file = file;
        TreeBuilderThread.rootItem = rootItem;
        this.isRoot = isRoot;
    }
    
    @Override
    public void run(){
        TreeBuilder.construyeArbol(file, rootItem, isRoot);
        Platform.runLater(() -> {
            View.load("main_menu", LoginController.newStage);
            ResizeHelper.addResizeListener(LoginController.newStage);
        });
    }
}
